package ch.yoursource.causationfinder.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.yoursource.causationfinder.dto.ChangePasswordDto;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class ChangePasswordController {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    public ChangePasswordController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    private boolean isCorrectPassword(String password1, String password2)
    {
        return bCryptPasswordEncoder.matches(password1, password2);
    }
    
    @GetMapping("/changepassword")
    public String showChangePasswordForm(WebRequest request, Model model) {
        model.addAttribute("changePasswordDto", new ChangePasswordDto());
        return "user-profile/changepassword";
    }
    
    @PostMapping("/changepassword")
    public ModelAndView saveNewPassword(
            @ModelAttribute("changePasswordDto") @Valid ChangePasswordDto changePasswordDto, 
            BindingResult result,
            WebRequest request,
            Errors errors
            ) {
        boolean hasErrors = false;
        
        if (result.hasErrors()) {
            hasErrors = true;
        }
        
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        User theLoggedInUser = userService.findByUsername(username);
        
        // check if the old password is correct (like the one in database)
        if(!isCorrectPassword(changePasswordDto.getOldPassword(), theLoggedInUser.getPassword())) {
            errors.rejectValue("oldPassword", "messages.updatePassword.oldPasswordWrong");
            hasErrors = true;
        }
        
        // check if the new password matches confirmed password
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordConfirm())) {
            errors.rejectValue("newPasswordConfirm", "messages.updatePassword.passwordsDontMatch");
            hasErrors = true;
        }
        
        if (hasErrors) {
            System.out.println("\t>>> WITHIN ChangePasswordController: " + result.getFieldErrors().toString());
            // first parameter is path to changepassword-form, second parameter is variable-name to access dto in template/html file)
            return new ModelAndView("user-profile/changepassword", "changePasswordDto", changePasswordDto);
        }
        
        userService.saveChangedPassword(theLoggedInUser, changePasswordDto.getNewPassword());
        return new ModelAndView("home/userhome");
    }
}

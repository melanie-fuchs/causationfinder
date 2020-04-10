package ch.yoursource.causationfinder.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.yoursource.causationfinder.dto.UpdateUserDto;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class UpdateUserController {

    private UserService userService;

    @Autowired
    public UpdateUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/updateuser")
    public String showUpdateUserForm(WebRequest request, Model model) {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        User user = userService.findByUsername(username);
        UpdateUserDto updateUserDto = new UpdateUserDto(user);

        model.addAttribute("updateUserDto", updateUserDto);

        return "user-profile/updateuserform";
    }

    @PostMapping("/updateuser")
    public ModelAndView saveUpdate(
            @ModelAttribute("updateUserDto") @Valid UpdateUserDto updatedUserData, 
            BindingResult result,
            WebRequest request,
            Errors errors) {

        boolean hasErrors = false;

        if (result.hasErrors()) {
            hasErrors = true;
        }

        User oldUserData = getLoggedInUser();
        
        // get user with e-mail that the user entered in updateform. Might be null
        User existingUserByEmail = userService.findByEmail(updatedUserData.getEmail());
        // get user with username that the user entered in updateform. Might be null
        User existingUserByUsername = userService.findByUsername(updatedUserData.getUsername());

        
        if (existingUserByEmail != null
                && oldUserData.getId() != existingUserByEmail.getId()) {
            errors.rejectValue("email", "messages.updateuser.emailAlreadyExists");
            hasErrors = true;
        }
        
        if (existingUserByUsername != null
                && oldUserData.getId() != existingUserByUsername.getId()) {
            errors.rejectValue("username", "messages.updateuser.usernameAlreadyExists");
            hasErrors = true;
        }
        
        if (hasErrors) {
            // first parameter is path to registration-form, second parameter is variable-name to access dto in template/html file)
            return new ModelAndView("user-profile/updateuserform", "updateUserDto", updatedUserData);
        }
        
        
        userService.saveUpdatedUserData(oldUserData, updatedUserData);
        return new ModelAndView("home/userhome");
    }
    
    private User getLoggedInUser()
    {        
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        return userService.findByUsername(username);
    }
}

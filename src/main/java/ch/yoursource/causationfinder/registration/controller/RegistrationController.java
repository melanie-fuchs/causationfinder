package ch.yoursource.causationfinder.registration.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.registration.dto.UserRegistrationDto;
import ch.yoursource.causationfinder.service.EmailExistsException;
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;

	@GetMapping("/registration") // comes from our configuration file (DemoSecurityConfiguration)
	public String showRegistrationForm(WebRequest request, Model model) {
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		model.addAttribute("userRegistrationDto", userRegistrationDto);

		return "user-registration/user-registration-form";
	}

	@PostMapping("/registration")
	public String saveUser(
			@ModelAttribute("userRegistrationDto") @Valid UserRegistrationDto accountDto,
			BindingResult result,
			WebRequest request,
			Errors errors) {
		User registered = new User();
		
		try {
			if (!result.hasErrors()) {
				registered = createUserAccount(accountDto, result);
			}
		} catch (EmailExistsException e) {
			result.rejectValue("email", "message.regError");
		}

		if (result.hasErrors()) {
	        return "user-registration/registration-failed-form";
	    } 
	    else {
	        return "user-registration/registration-succeeded-form";
	    }
	}

	private User createUserAccount(UserRegistrationDto accountDto, BindingResult result) 
		throws EmailExistsException {
			User registered = userService.registerNewUser(accountDto);
			
			return registered;
		}
}
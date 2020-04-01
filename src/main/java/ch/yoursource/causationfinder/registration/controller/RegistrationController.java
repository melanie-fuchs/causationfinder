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
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView saveUser(
			@ModelAttribute("userRegistrationDto") @Valid UserRegistrationDto userRegistrationDto,
			BindingResult result,
			WebRequest request,
			Errors errors) {
		User registered = new User();
		
		try {
			if (!result.hasErrors()) {
				registered = createUserAccount(userRegistrationDto, result);
			}
		} catch (EmailExistsException e) {
			result.rejectValue("email", "message.regError");
		}

		if (result.hasErrors()) {
			// first parameter is path to registration-form, second parameter is variable-name to access dto in template/html file)
			return new ModelAndView("user-registration/registration-failed-form", "userRegistrationDto", userRegistrationDto);
	    } 
	    else {
	    	// first parameter is path to registration-form, second parameter is variable-name to access dto in template/html file)
			return new ModelAndView("user-registration/registration-succeeded", "userRegistrationDto", userRegistrationDto);
	    }
	}

	private User createUserAccount(UserRegistrationDto accountDto, BindingResult result) 
		throws EmailExistsException {
			User registered = userService.registerNewUser(accountDto);
			
			return registered;
		}
}
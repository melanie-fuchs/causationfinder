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
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;

	@GetMapping("/registration") // comes from our configuration file (DemoSecurityConfiguration)
	public String showRegistrationForm(WebRequest request, Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "user-registration/user-registration-form";
	}

	@PostMapping("/registration")
	public ModelAndView saveUser(
			@ModelAttribute("user") @Valid User user,
			BindingResult result,
			WebRequest request,
			Errors errors) {		
		boolean hasErrors = false;
		
		if (result.hasErrors()) {
			hasErrors = true;			
		}
		
		if (userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "messages.registration.emailDuplicate");
			hasErrors = true;
		}
		
		if (userService.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "messages.registration.usernameDuplicate");
			hasErrors = true;
		}
		
		if (!user.getPassword().equals(user.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm", "messages.registration.passwordsDontMatch");
			hasErrors = true;
		}
		
		if (hasErrors) {
			System.out.println("\t>>> WITHIN RegistrationController: " + result.getFieldErrors().toString());
			// first parameter is path to registration-form, second parameter is variable-name to access dto in template/html file)
			return new ModelAndView("user-registration/user-registration-form", "user", user);
		}		
			
		userService.save(user);
		return new ModelAndView("user-registration/registration-succeeded", "user", user);
	}
}
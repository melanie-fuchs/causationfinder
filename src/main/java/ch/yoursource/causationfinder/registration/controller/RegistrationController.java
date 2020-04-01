package ch.yoursource.causationfinder.registration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import ch.yoursource.causationfinder.registration.dto.UserRegistrationDto;

@Controller
public class RegistrationController {
		
		@GetMapping("/registration") // comes from our configuration file (DemoSecurityConfiguration)
		public String showRegistrationForm(WebRequest request, Model model) {
			UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
			model.addAttribute("user", userRegistrationDto);
			
			return "registration";
		}
}
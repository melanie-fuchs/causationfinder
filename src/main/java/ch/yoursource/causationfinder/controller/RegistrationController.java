package ch.yoursource.causationfinder.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.yoursource.causationfinder.entity.ConfirmationToken;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.ConfirmationTokenRepository;
import ch.yoursource.causationfinder.service.EmailSenderService;
import ch.yoursource.causationfinder.service.ParameterService;
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class RegistrationController {	
	private UserService userService;
	private ParameterService parameterService;
	private ConfirmationTokenRepository confirmationTokenRepository;
	private EmailSenderService emailSenderService;
	
	@Autowired
	public RegistrationController(
		UserService userService, 
		ParameterService parameterService,
		ConfirmationTokenRepository confirmationTokenRepository,
		EmailSenderService emailSenderService
	) {
		this.userService = userService;
		this.parameterService = parameterService;
		this.confirmationTokenRepository = confirmationTokenRepository;
		this.emailSenderService = emailSenderService;
	}

	@GetMapping("/registration")
	public ModelAndView showRegistrationForm(
	        ModelAndView modelAndView,
	        WebRequest request) {
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("user-registration/user-registration-form");

		return modelAndView;
	}

	@PostMapping("/registration")
	public ModelAndView saveUser(
			@ModelAttribute("user") @Valid User user,
			BindingResult result,
			WebRequest request,
			Errors errors,
			ModelAndView modelAndView) {		
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
			// first parameter is path to registration-form, second parameter is variable-name to access dto in template/html file)
			// return new ModelAndView("user-registration/user-registration-form", "user", user);
		    modelAndView.addObject("user", user);
		    modelAndView.setViewName("user-registration/user-registration-form");
		    return modelAndView;
		}		

		// everything is fine and the user should now get an email to verify the address.
		user.setEnabled(false);
        userService.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration");
        mailMessage.setFrom("registration@yoursource.ch");
        mailMessage.setText("To confirm your account, please click here: " +
                "http://localhost:8080/user-registration/confirm-account?token="+confirmationToken.getConfirmationToken());
        emailSenderService.sendEmail(mailMessage);
        modelAndView.addObject("email", user.getEmail());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user-registration/registration-succeeded");
        
		return modelAndView;
	}

	@RequestMapping(value="/user-registration/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView confirmUserAccount(
	        ModelAndView modelAndView,
	        @RequestParam("token") String confirmationToken) {
	    ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
	    
	    if(token != null) {
	        User user = userService.findByEmail(token.getUser().getEmail());
	        user.setEnabled(true);
	        parameterService.activateAllPredefinedParameters(user);
	        userService.update(user);
	        modelAndView.setViewName("user-registration/account-verified");
	    } else {
	        // TODO return errorpage
	        // modelAndView.addObject("message", "link is invalid or broken");
	        // modelAndView.setViewName("error");
	    }
	    
	    return modelAndView;
	   
	}
}
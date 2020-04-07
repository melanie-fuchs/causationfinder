package ch.yoursource.causationfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserHomeController {
	
	@GetMapping("/userhome")
	public String showUserHome(WebRequest request) {
		return "home/userhome";
	}

}

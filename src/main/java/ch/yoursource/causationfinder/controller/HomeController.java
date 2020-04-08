package ch.yoursource.causationfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class HomeController {
	@GetMapping("/")
	public String showHome(WebRequest request) {
	    return "home/home";
	}
}

package ch.yoursource.causationfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ManageParameterController {
    
    @GetMapping("/data/manageparameter")
    public String showManageParameterForm(WebRequest request) {
        //TODO: do
        return null;
    }
}

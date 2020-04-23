package ch.yoursource.causationfinder.controller.msq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class MsqController {

    @GetMapping("/msq")
    public String showMsqController(WebRequest request) {
        return "msq/msq";
    }
}

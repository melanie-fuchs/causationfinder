package ch.yoursource.causationfinder.controller.datacollection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class EnterAndAnalyzeDataController {

    @GetMapping("/data/datacollection/enter-and-analyze-data")
    public String showEnterAndAnalyteDataController(WebRequest request) {
        return "data/datacollection/enter-and-analyze-data";
    }
}

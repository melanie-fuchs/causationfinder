package ch.yoursource.causationfinder.controller.datacollection;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnalyzeDataController {
    
    @GetMapping("/data/datacollection/analyze-data")
    public String showAnalyzeDataController(
            WebRequest request,
            Model model) {
        
        return "/data/datacollection/analyze-data";
    }

    
//    @PostMapping("data/datacollection/analyze-data")
    
}

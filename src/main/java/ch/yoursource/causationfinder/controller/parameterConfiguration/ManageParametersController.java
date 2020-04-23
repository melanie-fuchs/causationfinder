package ch.yoursource.causationfinder.controller.parameterConfiguration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ManageParametersController {

    @GetMapping("/data/parameter-configuration/manage-parameters")
    public String showManageParametersController(WebRequest request) {
        return "data/parameter-configuration/manage-parameters";
    }
}

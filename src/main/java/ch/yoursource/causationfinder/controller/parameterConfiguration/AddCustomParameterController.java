package ch.yoursource.causationfinder.controller.parameterConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.CustomParameterRepository;
import ch.yoursource.causationfinder.service.UserService;
import ch.yoursource.causationfinder.setup.ParameterType;

@Controller
public class AddCustomParameterController {
    
    private CustomParameterRepository customParameterRepository;
    private UserService userService;
    
    @Autowired
    public AddCustomParameterController(
        CustomParameterRepository customParameterRepository,
        UserService userService
    ) {
        this.customParameterRepository = customParameterRepository;
        this.userService = userService;
    }
    
    @GetMapping("/data/parameter-configuration/add-parameter")
    public String showAddCustomParameterForm(WebRequest request) {        
        return "data/parameter-configuration/add-parameter";
    }
    
    @PostMapping("/data/parameter-configuration/add-parameter")
    public String addCustomParameter(
        @RequestParam(value="type", required = true) String type,
        @RequestParam(value="parameterName", required = true) String parameterName,
        @RequestParam(value="description", required = false) String description,
        @RequestParam(value="minValue", required = false) Double minValue,
        @RequestParam(value="maxValue", required = false) Double maxValue,
        WebRequest request,
        Model model
    ) { 
        CustomParameter customParameter = new CustomParameter();
        customParameter.setUser(this.getLoggedInUser());       
        customParameter.setActive(true);
        customParameter.setParamName(parameterName);
        customParameter.setDescription(description);
        switch (type) {
            case "STRING":
                customParameter.setType(ParameterType.STRING);
                break;
            case "BOOLEAN":
                customParameter.setType(ParameterType.BOOLEAN);
                break;
            case "NUMERIC":
                customParameter.setType(ParameterType.NUMERIC);
                customParameter.setMinValue(minValue);
                customParameter.setMaxValue(maxValue);
                break;
            default:
                customParameter.setType(ParameterType.NUMERIC);
        }
        customParameterRepository.save(customParameter);
        
        return "data/parameter-configuration/manage-parameters";       
    }

    private User getLoggedInUser()
    {        
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        return userService.findByUsername(username);
    }
}

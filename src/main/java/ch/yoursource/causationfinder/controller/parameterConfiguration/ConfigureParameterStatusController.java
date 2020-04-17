package ch.yoursource.causationfinder.controller.parameterConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.CustomParameterRepository;
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class ConfigureParameterStatusController {
    
    private CustomParameterRepository customParameterRepository;
    private UserService userService;
    
    @Autowired
    public ConfigureParameterStatusController(
        CustomParameterRepository customParameterRepository,
        UserService userService
    ) {
        this.customParameterRepository = customParameterRepository;
        this.userService = userService;
    }
    
    @GetMapping("/data/parameter-configuration/configure-parameter-status")
    public String showManageParameterForm(
            WebRequest request,
            Model model) {
        // prepare all predefined parameters
        List<CustomParameter> predefinedParameters = this.getPredefinedParametersByCurrentUser();
        model.addAttribute("managepredefinedparameter", predefinedParameters); 
        model.addAllAttributes(predefinedParameters);    
        
        // prepare all custom parameters
        List<CustomParameter> customParameters = this.getCustomParametersByCurrentUser();
        model.addAttribute("managecustomparameter", customParameters);
        model.addAllAttributes(customParameters); 
        
        return "/data/parameter-configuration/configure-parameter-status";
    }
    
    @PostMapping("/data/parameter-configuration/configure-parameter-status")
    public ModelAndView changeActiveStateOfPredefinedParameters(
        @RequestParam(value="checkedPredefinedParameters[]", required = false) int[] predefinedParameterIds,
        @RequestParam(value="checkedCustomParameters[]", required = false) int[] customParameterIds,
        WebRequest request,
        Model model
    ) {
        List<CustomParameter> predefinedParameters = this.getPredefinedParametersByCurrentUser();    
        model.addAttribute("managepredefinedparameter", predefinedParameters);
        
        List<CustomParameter> customParameters = this.getCustomParametersByCurrentUser();    
        model.addAttribute("managecustomparameter", customParameters);
        
        List<Integer> checkedParameterIds = new ArrayList<Integer>();
        
        // make sure the status is being updated in the database even tho no params
        // were transferred from the html-form --> html-form only sends checked params
        if (predefinedParameterIds != null) {
            for (int checkedParameterId : predefinedParameterIds) {
                checkedParameterIds.add(checkedParameterId);
            }
        }
        
        if (customParameterIds != null) {
            for (int checkedParameterId : customParameterIds) {
                checkedParameterIds.add(checkedParameterId);
            }
        }
        
        for (CustomParameter parameter : predefinedParameters) {
            int parameterId = parameter.getId();
            
            boolean isChecked = checkedParameterIds.contains(Integer.valueOf(parameterId));
            
            if (parameter.isActive() == isChecked) {
                //active state of this parameter was not changed
                continue;
            }
            
            try {
                CustomParameter c = customParameterRepository.findById(parameterId).orElseThrow();
                c.setActive(isChecked);
                customParameterRepository.save(c);
            } catch (NoSuchElementException e) {
                throw new RuntimeException(e);
            }
        }
        
        return new ModelAndView("/data/parameter-configuration/configure-parameter-status");
    }
    
    private List<CustomParameter> getPredefinedParametersByCurrentUser()
    {
        // Sort the list so it always shows the same order (else it orders depending on active status or sth)
        List<CustomParameter> predefinedParameters = this.customParameterRepository.findPredefinedByUser(getLoggedInUser());
        predefinedParameters.sort(
            (CustomParameter a, CustomParameter b) -> {
                return a.getParamName().compareTo(b.getParamName());
            });
        
        return predefinedParameters;        
    }
    
    private List<CustomParameter> getCustomParametersByCurrentUser()
    {
        // Sort the list so it always shows the same order (else it orders depending on active status or sth)
        List<CustomParameter> customParameters = this.customParameterRepository.findCustomByUser(getLoggedInUser());
        customParameters.sort(
            (CustomParameter a, CustomParameter b) -> {
                return a.getParamName().compareTo(b.getParamName());
            });
        
        return customParameters;        
    }
    
    private User getLoggedInUser()
    {        
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        return userService.findByUsername(username);
    }
}

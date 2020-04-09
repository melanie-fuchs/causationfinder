package ch.yoursource.causationfinder.controller;

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
public class ManagePredefinedParameterController {
    
    private CustomParameterRepository customParameterRepository;
    private UserService userService;
    
    @Autowired
    public ManagePredefinedParameterController(
        CustomParameterRepository customParameterRepository,
        UserService userService
    ) {
        this.customParameterRepository = customParameterRepository;
        this.userService = userService;
    }
    
    @GetMapping("/data/managepredefinedparameter")
    public String showManageParameterForm(WebRequest request, Model model) {
        List<CustomParameter> predefinedParameters = this.getPredefinedParametersByCurrentUser();
        
        model.addAttribute("managepredefinedparameter", predefinedParameters);
        
        model.addAllAttributes(predefinedParameters);    
        
        return "data/managepredefinedparameter";
    }
    
    @PostMapping("/data/managepredefinedparameter")
    public ModelAndView changeActiveStateOfPredefinedParameters(
        @RequestParam(value="checkedPredefinedParameters[]", required = true) int[] parameterIds,
        WebRequest request,
        Model model
    ) {        
        List<CustomParameter> predefinedParameters = this.getPredefinedParametersByCurrentUser();    
        
        model.addAttribute("managepredefinedparameter", predefinedParameters);
        
        List<Integer> checkedParameterIds = new ArrayList<Integer>();
        
        for (int checkedParameterId : parameterIds) {
            checkedParameterIds.add(checkedParameterId);
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
        
        return new ModelAndView("/data/managepredefinedparameter");
        
    }
    
    private List<CustomParameter> getPredefinedParametersByCurrentUser()
    {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        User user = userService.findByUsername(username);
        
        
        // Sort the list so it always shows the same order (else it orders depending on active status or sth)
        List<CustomParameter> predefinedParameters = this.customParameterRepository.findPredefinedByUser(user);
        predefinedParameters.sort(
            (CustomParameter a, CustomParameter b) -> {
                return a.getParamName().compareTo(b.getParamName());
            });
        
        return predefinedParameters;        
    }
}

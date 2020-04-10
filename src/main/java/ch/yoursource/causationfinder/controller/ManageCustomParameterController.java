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
public class ManageCustomParameterController {
    
    private CustomParameterRepository customParameterRepository;
    private UserService userService;
    
    @Autowired
    public ManageCustomParameterController(
        CustomParameterRepository customParameterRepository,
        UserService userService
    ) {
        this.customParameterRepository = customParameterRepository;
        this.userService = userService;
    }
    
    @GetMapping("/data/managecustomparameter")
    public String showManageParameterForm(WebRequest request, Model model) {
        List<CustomParameter> customParameters = this.getCustomParametersByCurrentUser();
        
        model.addAttribute("managecustomparameter", customParameters);
        
        model.addAllAttributes(customParameters);    
        
        return "data/managecustomparameter";
    }
    
    @PostMapping("/data/managecustomparameter")
    public ModelAndView changeActiveStateOfCustomParameters(
        @RequestParam(value="checkedCustomParameters[]", required = false) int[] parameterIds,
        WebRequest request,
        Model model
    ) {        
        List<CustomParameter> customParameters = this.getCustomParametersByCurrentUser();    
        
        model.addAttribute("managecustomparameter", customParameters);
        
        List<Integer> checkedParameterIds = new ArrayList<Integer>();
        
        // make sure the status is being updated in the database even tho no params are
        // were transferred from the html-form (html form will only send the checked id's)
        if (parameterIds != null) {
            for (int checkedParameterId : parameterIds) {
                checkedParameterIds.add(checkedParameterId);
            }
        }
        
        for (CustomParameter parameter : customParameters) {
            int parameterId = parameter.getId();
            
            boolean isChecked = checkedParameterIds.contains(Integer.valueOf(parameterId));
            
            System.out.println(">>>  Within for-loop: iChecked: " + isChecked);
            
            if (parameter.isActive() == isChecked) {
                //active state of this parameter was not changed
                continue;
            }
            
            try {
                CustomParameter c = customParameterRepository.findById(parameterId).orElseThrow();
                c.setActive(isChecked);
                System.out.println(">>>  Within try before its saved in repo: iChecked: " + isChecked);
                customParameterRepository.save(c);
                System.out.println(">>>  Within try after its saved in repo: iChecked: " + isChecked);

            } catch (NoSuchElementException e) {
                System.out.println(">>>  Within catch before runtime is thrown: iChecked: " + isChecked);

                throw new RuntimeException(e);
            }
        }
        
        return new ModelAndView("/data/managecustomparameter");
        
    }
    
    private List<CustomParameter> getCustomParametersByCurrentUser()
    {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        User user = userService.findByUsername(username);
        
        
        // Sort the list so it always shows the same order (else it orders depending on active status or sth)
        List<CustomParameter> customParameters = this.customParameterRepository.findCustomByUser(user);
        customParameters.sort(
            (CustomParameter a, CustomParameter b) -> {
                return a.getParamName().compareTo(b.getParamName());
            });
        
        return customParameters;        
    }
}

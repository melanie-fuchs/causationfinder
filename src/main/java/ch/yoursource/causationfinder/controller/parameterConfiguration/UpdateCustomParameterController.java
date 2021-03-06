package ch.yoursource.causationfinder.controller.parameterConfiguration;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.yoursource.causationfinder.dto.UpdateCustomParameterDto;
import ch.yoursource.causationfinder.dto.UpdateCustomParameterDtoListWrapper;
import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.CustomParameterRepository;
import ch.yoursource.causationfinder.repository.ObservedDayValueRepository;
import ch.yoursource.causationfinder.service.ParameterService;
import ch.yoursource.causationfinder.service.UserService;
import ch.yoursource.causationfinder.setup.ParameterType;

@Controller
public class UpdateCustomParameterController {

    CustomParameterRepository customParameterRepository;
    UserService userService;
    ParameterService parameterService;
    ObservedDayValueRepository observedDayValueRepository;
    
    @Autowired
    public UpdateCustomParameterController(
            CustomParameterRepository customParameterRepository,
            UserService userService,
            ParameterService parameterService,
            ObservedDayValueRepository observedDayValueRepository) {
        this.customParameterRepository = customParameterRepository;
        this.userService = userService;
        this.parameterService = parameterService;
        this.observedDayValueRepository = observedDayValueRepository;
    }

    @GetMapping("/data/parameter-configuration/update-customparameter")
    public String showConfigureMinMax(
            Model model) {
        
        // find all active customParameters of ParameterType.NUMERIC
        UpdateCustomParameterDtoListWrapper updateCustomParameterDtoListWrapper = new UpdateCustomParameterDtoListWrapper();
        updateCustomParameterDtoListWrapper.setUpdateCustomParameterDtoList(this.getCustomParameterDtos());
        
        model.addAttribute("updateCustomParameterDtoListWrapper", updateCustomParameterDtoListWrapper);
        //model.addAllAttributes(customParameterDtos);
        return "/data/parameter-configuration/update-customparameter";
    }

    
    @PostMapping("/data/parameter-configuration/update-customparameter")
    public ModelAndView saveUpdatedParameters(
            Model model,
            @ModelAttribute("updateCustomParameterDtoListWrapper") @Valid UpdateCustomParameterDtoListWrapper updateCustomParameterDtoListWrapper,
            BindingResult result,
            WebRequest request,
            Errors errors) {
        boolean hasErrors = false;
        
        if (result.hasErrors()) {
            hasErrors = true;
        }
        
        for(UpdateCustomParameterDto c : updateCustomParameterDtoListWrapper.getUpdateCustomParameterDtoList()) {
            parameterService.updateParameter(c);
        }
        
        if (hasErrors) {
            model.addAttribute("updateCustomParameterDtoListWrapper", updateCustomParameterDtoListWrapper);
            return new ModelAndView("/data/parameter-configuration/update-customparameter", "updateCustomParameterDtoListWrapper", updateCustomParameterDtoListWrapper);
        }
        
        return new ModelAndView("data/parameter-configuration/manage-parameters");
        
    }
    
    private List<UpdateCustomParameterDto> getCustomParameterDtos()
    {
        // Sort the list so it always shows the same order (else it orders depending on active status or sth)
        List<CustomParameter> parameters = this.customParameterRepository.findActiveByUserAndTypeExcludePredefined(this.getLoggedInUser(), ParameterType.NUMERIC);
        List<UpdateCustomParameterDto> updateCustomParameterDtos = new ArrayList<UpdateCustomParameterDto>();
        
        for(CustomParameter c : parameters) {
            UpdateCustomParameterDto updateCustomParameterDto = new UpdateCustomParameterDto(c);
            updateCustomParameterDto.setLowestValue(observedDayValueRepository.findLowestValueByCustomParameter(c));
            updateCustomParameterDto.setHighestValue(observedDayValueRepository.findHighestValueByCustomParameter(c));
            updateCustomParameterDtos.add(updateCustomParameterDto);
        }
        updateCustomParameterDtos.sort(
            (UpdateCustomParameterDto a, UpdateCustomParameterDto b) -> {
            return a.getParamName().compareTo(b.getParamName());
        });
        return updateCustomParameterDtos;        
    }
    
    private User getLoggedInUser()
    {        
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        return userService.findByUsername(username);
    }
}

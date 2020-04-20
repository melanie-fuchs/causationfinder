package ch.yoursource.causationfinder.controller.datacollection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.yoursource.causationfinder.dto.CustomParameterDayStringDayValueDto;
import ch.yoursource.causationfinder.dto.CustomParameterDayStringDto;
import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.ObservedDayValue;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.ObservedDayValueRepository;
import ch.yoursource.causationfinder.service.UserService;
import ch.yoursource.causationfinder.setup.ParameterType;

@Controller
public class DisplayTextsController {
    
    private UserService userService;
    private ObservedDayValueRepository observedDayValueRepository;
    
    
    public DisplayTextsController(
            UserService userService,
            ObservedDayValueRepository observedDayValueRepository) {
        this.userService = userService;
        this.observedDayValueRepository = observedDayValueRepository;
    }
    
    @GetMapping("/data/datacollection/display-texts")
    public String showAnalyzeDataController(
            WebRequest request,
            Model model) {
        
        return "/data/datacollection/display-texts";
    }

    @PostMapping("/data/datacollection/display-texts")
    public ModelAndView analyzeData(
            @RequestParam(value="startDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value="endDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            WebRequest request,
            Model model) {
        User user = this.getLoggedInUser();
        
        List<ObservedDayValue> observedValuesInRange = this.observedDayValueRepository
                .findActiveByUserInRange(this.getDateFromLocalDate(startDate), this.getDateFromLocalDate(endDate), user);

        // get all customParameters that were used between the two dates and save them in a Set to prevent double entries
        HashSet<CustomParameter> allCustomParametersSet = new HashSet<CustomParameter>();
        for (ObservedDayValue o : observedValuesInRange) {
            allCustomParametersSet.add(o.getCustomParameter());
        }
        // save the HashSet to a List to be able to get the index of a specific customParameter
        List<CustomParameter> allCustomParameters = new ArrayList<CustomParameter>();
        allCustomParameters.addAll(allCustomParametersSet);

        // get all Dates between the two dates (add one day (plusDays(1L)) to the endDate, so the enddate itself will be analyzed as well)
        List<LocalDate> allDates = startDate.datesUntil(endDate.plusDays(1L)).collect(Collectors.toList());

        // create matrix, x and y axis
        String[][] valuesByParametersAndDate = new String[allCustomParameters.size()][allDates.size()];
        
        // set the String for each date in the Array valuesByParametersAndDate
        for (ObservedDayValue observedDayValue : observedValuesInRange) {
            if(observedDayValue.getCustomParameter().getType() == ParameterType.NUMERIC) {
                continue;
            }
            if(observedDayValue.getCustomParameter().getType() == ParameterType.BOOLEAN) {
                continue;
            }
               
            String value = "";
            LocalDate date = getLocalDateFromDate(observedDayValue.getDate());
            if (observedDayValue.getCustomParameter().getType() == ParameterType.STRING) {
                value = observedDayValue.getStringValue();
            }
            
            // get position of the date and the parameter in the matrix
            int indexOfCustomParameter = allCustomParameters.indexOf(observedDayValue.getCustomParameter());
            int indexOfDate = allDates.indexOf(date);

            // set the value to the specific spot in the matrix
            valuesByParametersAndDate[indexOfCustomParameter][indexOfDate] = value;
        }
        
        // an arraylist that holds objects with a parameterName and a list of Strings
        List<CustomParameterDayStringDto> data = new ArrayList<CustomParameterDayStringDto>();
        
        for (CustomParameter cp : allCustomParameters) {
            if (cp.getType() == ParameterType.NUMERIC) {
                continue;
            }
            if (cp.getType() == ParameterType.BOOLEAN) {
                continue;
            }
   
            int indexOfCustomParameter = allCustomParameters.indexOf(cp);
            
            // create an object that holds the parametername and a list of all the daily strings
            CustomParameterDayStringDto parameterStringDto = new CustomParameterDayStringDto();
            parameterStringDto.setParameterName(cp.getParamName());
            
            for (LocalDate date : allDates) {                
                parameterStringDto.addDailyValue(new CustomParameterDayStringDayValueDto(
                        date,
                        valuesByParametersAndDate[indexOfCustomParameter][allDates.indexOf(date)]
                ));
            }
                        
            data.add(parameterStringDto);            
        }
        
        model.addAttribute("data", data);
        model.addAttribute("allDates", allDates);
       
        return new ModelAndView("data/datacollection/show-texts"); 
    }
    
    private User getLoggedInUser()
    {        
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        return userService.findByUsername(username);
    }
    
    private Date getDateFromLocalDate(LocalDate localDate)
    {   
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    private LocalDate getLocalDateFromDate(Date date)
    {   
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    
}

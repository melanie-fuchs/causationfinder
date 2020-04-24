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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import ch.yoursource.causationfinder.dto.CustomParameterDayAnalyzeDayValueDto;
import ch.yoursource.causationfinder.dto.CustomParameterDayAnalyzeDto;
import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.ObservedDayValue;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.ObservedDayValueRepository;
import ch.yoursource.causationfinder.serializer.CustomParameterDayAnalyzeDtoSerializer;
import ch.yoursource.causationfinder.service.UserService;
import ch.yoursource.causationfinder.setup.ParameterType;

@Controller
public class AnalyzeDataController {
    
    private UserService userService;
    private ObservedDayValueRepository observedDayValueRepository;
    
    
    public AnalyzeDataController(
            UserService userService,
            ObservedDayValueRepository observedDayValueRepository) {
        this.userService = userService;
        this.observedDayValueRepository = observedDayValueRepository;
    }
    
    @GetMapping("/data/datacollection/analyze-data")
    public String showAnalyzeDataController(
            WebRequest request,
            Model model) {
        
        return "/data/datacollection/analyze-data";
    }

    @PostMapping("data/datacollection/analyze-data")
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
        Double[][] valuesByParametersAndDate = new Double[allCustomParameters.size()][allDates.size()];
        
        for (ObservedDayValue observedDayValue : observedValuesInRange) {
            if (observedDayValue.getCustomParameter().getType() == ParameterType.STRING) {
                continue;
            }

            Double value = 0.0;
            LocalDate date = getLocalDateFromDate(observedDayValue.getDate());
            
            // get position of the date and the parameter in the matrix
            int indexOfCustomParameter = allCustomParameters.indexOf(observedDayValue.getCustomParameter());
            int indexOfDate = allDates.indexOf(date);
            
            if(observedDayValue.getCustomParameter().getType() == ParameterType.NUMERIC) {
                value = observedDayValue.getNumericValue();
            }
            if(observedDayValue.getCustomParameter().getType() == ParameterType.BOOLEAN) {
                if (observedDayValue.getBooleanValue() == null) {
                    value = null;
                } else {
                    if (observedDayValue.getBooleanValue()) {
                        value = 1.0;
                    } else {
                        value = 0.0;
                    }
                }
            }
            
            // set the value to the specific spot in the matrix
            valuesByParametersAndDate[indexOfCustomParameter][indexOfDate] = value;
        }
        
        
        List<CustomParameterDayAnalyzeDto> data = new ArrayList<CustomParameterDayAnalyzeDto>();
        
        for (CustomParameter cp : allCustomParameters) {
            if (cp.getType() == ParameterType.STRING) {
                continue;
            }

            Double minValue = cp.getMinValue();
            Double maxValue = cp.getMaxValue();
            
            // set max- and min-range values for boolean. 1 if true and 0 if false
            if (cp.getType() == ParameterType.BOOLEAN) {
                minValue = 0.0;
                maxValue = 1.0;
            }
            
            int indexOfCustomParameter = allCustomParameters.indexOf(cp);
            
            CustomParameterDayAnalyzeDto parameterDataDto = new CustomParameterDayAnalyzeDto();
            parameterDataDto.setParameterName(cp.getParamName());
            parameterDataDto.setMinValue(minValue);
            parameterDataDto.setMaxValue(maxValue);
            parameterDataDto.setBoolean(cp.getType() == ParameterType.BOOLEAN);
            
            for (LocalDate date : allDates) {                
                parameterDataDto.addDailyValue(new CustomParameterDayAnalyzeDayValueDto(
                        date,
                        valuesByParametersAndDate[indexOfCustomParameter][allDates.indexOf(date)]
                ));
            }
                        
            data.add(parameterDataDto);
        }        
       
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(CustomParameterDayAnalyzeDto.class, new CustomParameterDayAnalyzeDtoSerializer());
        objectMapper.registerModule(module);

        try {
            // writeValueAsString will kick off the method serialize() in the serializer and write the json
            model.addAttribute("data", objectMapper.writeValueAsString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("data/datacollection/show-data"); 
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

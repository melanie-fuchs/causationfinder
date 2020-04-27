package ch.yoursource.causationfinder.controller.datacollection;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import ch.yoursource.causationfinder.dto.CustomParameterDayAnalyzeDayValueDto;
import ch.yoursource.causationfinder.dto.CustomParameterDayAnalyzeDto;
import ch.yoursource.causationfinder.dto.StartEndDateDto;
import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.MedicalSymptomsQuestionnaire;
import ch.yoursource.causationfinder.entity.ObservedDayValue;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.MsqRepository;
import ch.yoursource.causationfinder.repository.ObservedDayValueRepository;
import ch.yoursource.causationfinder.serializer.CustomParameterDayAnalyzeDtoSerializer;
import ch.yoursource.causationfinder.service.UserService;
import ch.yoursource.causationfinder.setup.ParameterType;

@Controller
public class AnalyzeDataController {
    
    private UserService userService;
    private ObservedDayValueRepository observedDayValueRepository;
    private MsqRepository msqRepository;
    
    
    public AnalyzeDataController(
            UserService userService,
            ObservedDayValueRepository observedDayValueRepository,
            MsqRepository msqRepository) {
        this.userService = userService;
        this.observedDayValueRepository = observedDayValueRepository;
        this.msqRepository = msqRepository;
    }
    
    @GetMapping("/data/datacollection/analyze-data")
    public String showAnalyzeDataController(
            WebRequest request,
            Model model) {
        
        StartEndDateDto starEndDateDto = new StartEndDateDto();
        
        model.addAttribute(starEndDateDto);
        
        return "/data/datacollection/analyze-data";
    }

    @PostMapping("data/datacollection/analyze-data")
    public ModelAndView analyzeData(
            @ModelAttribute("startEndDateDto") StartEndDateDto startEndDateDto,
            BindingResult result,
            WebRequest request,
            Errors errors,
            Model model) {
        User user = this.getLoggedInUser();
        
        boolean hasErrors = false;
        
        LocalDate startDate = startEndDateDto.getStartDate();
        LocalDate endDate = startEndDateDto.getEndDate();
        
        if(result.hasErrors()) {
            hasErrors = true;
        }
        
        if(startDate.isAfter(endDate)) {
            errors.rejectValue("startDate", "messages.validation.start-end-invalid");
            hasErrors = true;
        }
        
        if(hasErrors) {
            return new ModelAndView("data/datacollection/analyze-data", "startEndDateDto", startEndDateDto);
        }
        
        
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
        

        // adding MSQ-data to the list of Data, if the user has any data in the daterange
        this.addMsqDataIfAvailable(data, startDate, endDate, user, allDates);
        
       
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
    

    /*
     * The method checks if the user has entered MSQ-data in the timerange. If not,
     * MSQ will not be analyzed at all.
     * If there is data, there will be created a new CustomParameterDayAnalyzeDto which
     * will be added to "data" in the end.
     * If there's no data available on one day, the method will create a
     * CustomParameterDayAnalyzeDayValueDto, containing the date and null as value.
     * If there is data, the method retrieves it by looping over all the fields in the
     * class (using BeanInfo) and summing it up to the day's value.
     */
    private void addMsqDataIfAvailable(
            List<CustomParameterDayAnalyzeDto> data,
            LocalDate startDate,
            LocalDate endDate,
            User user,
            List<LocalDate> allDates) {
        
        // get a List of MedicalSymptomsQuestionnaire within the daterange
        List<MedicalSymptomsQuestionnaire> msqData = msqRepository.findByUserInRange(
                    user,
                    getDateFromLocalDate(startDate),
                    getDateFromLocalDate(endDate));
        
        // if there's no data entered within the daterange, MSQ will not be displayed at
        // all and the method ends here
        if(msqData.size() == 0) {
            return;
        }
        
        /*
         * Creating an instance of CustomParameterDayAnalyzeDto and setting the data
         */
        CustomParameterDayAnalyzeDto msqCustomParameter = new CustomParameterDayAnalyzeDto();
        msqCustomParameter.setBoolean(true); // will make the line curved or straight
        msqCustomParameter.setMinValue(0.0);
        msqCustomParameter.setMaxValue(null); // null, so the serializer will use the highest number the user has ever reached (total of all points would be 284 (71 params x 4 max points), but nobody should ever reach that. Line would not look good if using 284 as max value)
        msqCustomParameter.setParameterName("MSQ");

        // Creating a list of all dates. Will be used to loop over and find dates that
        // do not have data available in the database
        List<LocalDate> msqDates = new ArrayList<LocalDate>();
        for(MedicalSymptomsQuestionnaire m : msqData) {
            msqDates.add(getLocalDateFromDate(m.getDate()));
        }
        
        /*
         *  Creating a map of MedicalSymptomsQuestionnaire, where the key is the msq's date
         *  This is needed so BeanInfo can access it when usinf the date (as String) as a key
         *  to have access to the MedicalSymptomsQuestionnaire-object
         */        
        Map<String, MedicalSymptomsQuestionnaire> map = new HashMap<String, MedicalSymptomsQuestionnaire>();
        
        /*
         * Filling the map wth all the found MedicalSymptomsQuestionnaire's from DB
         */
        for(MedicalSymptomsQuestionnaire m : msqData) {
            map.put(getLocalDateFromDate(m.getDate()).toString(), m);
        }
        
        for (LocalDate d : allDates) {
            CustomParameterDayAnalyzeDayValueDto msqDay;
            if (!msqDates.contains(d)) {
                msqDay = new CustomParameterDayAnalyzeDayValueDto(d, null);
            } else {
                Double dailyTotal = 0.0;
                MedicalSymptomsQuestionnaire m = map.get(d.toString());
                try {
                    BeanInfo beanInfo = Introspector.getBeanInfo(m.getClass());
                    for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                        String propertyNameee = propertyDescriptor.getName();
                        if (!propertyNameee.equals("date") && !propertyNameee.equals("user")
                                && !propertyNameee.equals("class") && !propertyNameee.equals("id")) {
                            try {
                                Object value = propertyDescriptor.getReadMethod().invoke(m);
                                Double val = Double.valueOf(String.valueOf(value));
                                dailyTotal += val;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msqDay = new CustomParameterDayAnalyzeDayValueDto(d, dailyTotal);
            }
            msqCustomParameter.addDailyValue(msqDay);
        }   
        // adding the CustomParameter-object that contains all the daily values
        data.add(msqCustomParameter);
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

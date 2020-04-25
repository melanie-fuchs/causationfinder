package ch.yoursource.causationfinder.controller.datacollection;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.ObservedDayValue;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.CustomParameterRepository;
import ch.yoursource.causationfinder.repository.ObservedDayValueRepository;
import ch.yoursource.causationfinder.service.UserService;
import ch.yoursource.causationfinder.setup.ParameterType;

@Controller
public class EnterDataController {
    CustomParameterRepository customParameterRepository;
    ObservedDayValueRepository observedDayValueRepository;
    UserService userService;
    
    @Autowired
    public EnterDataController(
            CustomParameterRepository customParameterRepository,
            ObservedDayValueRepository observedDayValueRepository,
            UserService userService) {
        this.customParameterRepository = customParameterRepository;
        this.observedDayValueRepository = observedDayValueRepository;
        this.userService = userService;
    }

    @GetMapping("/data/datacollection/enter-data")
    public String showEnterDataController(
        @RequestParam(value="date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        WebRequest request,
        Model model
    ) {
        LocalDate givenDay = date != null ? date : LocalDate.now();
        
        User user = getLoggedInUser();
        
        // List of all values of today (given day)
        List<ObservedDayValue> observedValuesFromGivenDay = this.observedDayValueRepository.findByDateAndUser(getDateFromLocalDate(givenDay), user);
        
        this.prepareModelData(model, givenDay, user, observedValuesFromGivenDay);
        
        return "data/datacollection/enter-data";
    }

    @PostMapping("data/datacollection/enter-data")
    public ModelAndView saveEntry(
        @RequestParam(value="date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        WebRequest request,
        Model model
    ) {
        LocalDate givenDay = date;
        
        User user = getLoggedInUser();
        
        // List of all values of today (given day)
        List<ObservedDayValue> observedValuesFromGivenDay = this.observedDayValueRepository.findByDateAndUser(getDateFromLocalDate(givenDay), user);
        
        // transform list into map for easy access per CustomParameterId
        HashMap<Integer, ObservedDayValue> observedValuesPerParameterIdFromGivenDay = new HashMap<Integer, ObservedDayValue>();
        for (ObservedDayValue observedValueFromGivenDay : observedValuesFromGivenDay) {
            observedValuesPerParameterIdFromGivenDay.put(
                observedValueFromGivenDay.getCustomParameter().getId(),
                observedValueFromGivenDay
            );
        }   
        
        Set<String> keySet = request.getParameterMap().keySet();
        String numericParameterPrefix = "numeric_parameter_";
        String stringParameterPrefix = "string_parameter_";
        String booleanParameterPrefix = "boolean_parameter_";
        
        List<Integer> checkedBooleanParameters = new ArrayList<Integer>();        
        
        for(String key : keySet) {   

            if (key.contains(numericParameterPrefix)) {
                String[] value = request.getParameterMap().get(key);
                
                int parameterId = Integer.valueOf(key.substring(numericParameterPrefix.length()));
                
                ObservedDayValue valueForGivenDayAndParameter = observedValuesPerParameterIdFromGivenDay.get(parameterId);

                //what happens if we didnt find a value?
                if (valueForGivenDayAndParameter == null) {
                    //we didnt find a value, create new one and set customParameter
                    valueForGivenDayAndParameter = new ObservedDayValue();
                    try {
                        //TODO: optimize, don't run sql query for each parameter
                        valueForGivenDayAndParameter.setCustomParameter(customParameterRepository.findById(parameterId).orElseThrow());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                valueForGivenDayAndParameter.setNumericValue(Double.parseDouble(value[0]));
                valueForGivenDayAndParameter.setDate(this.getDateFromLocalDate(givenDay));
                observedDayValueRepository.save(valueForGivenDayAndParameter);
            }
            
            if (key.contains(stringParameterPrefix)) {
                String[] value = request.getParameterMap().get(key);
                
                int parameterId = Integer.valueOf(key.substring(stringParameterPrefix.length()));
                //TODO
                
                ObservedDayValue valueForGivenDayAndParameter = observedValuesPerParameterIdFromGivenDay.get(parameterId);

                //what happens if we didnt find a value?
                if (valueForGivenDayAndParameter == null) {
                    //we didnt find a value, create new one and set customParameter
                    valueForGivenDayAndParameter = new ObservedDayValue();
                    try {
                        //TODO: optimize, don't run sql query for each parameter
                        valueForGivenDayAndParameter.setCustomParameter(customParameterRepository.findById(parameterId).orElseThrow());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                valueForGivenDayAndParameter.setStringValue(value[0]);
                valueForGivenDayAndParameter.setDate(this.getDateFromLocalDate(givenDay));
                observedDayValueRepository.save(valueForGivenDayAndParameter);
                
            }
            
            if (key.contains(booleanParameterPrefix)) {
                int parameterId = Integer.valueOf(key.substring(booleanParameterPrefix.length()));
                
                String[] value = request.getParameterMap().get(key);
                
                ObservedDayValue valueForGivenDayAndParameter = observedValuesPerParameterIdFromGivenDay.get(parameterId);
                
                //what happens if we didnt find a value?
                if (valueForGivenDayAndParameter == null) {
                    //we didnt find a value, create new one and set customParameter
                    valueForGivenDayAndParameter = new ObservedDayValue();
                    try {
                        //TODO: optimize, don't run sql query for each parameter
                        valueForGivenDayAndParameter.setCustomParameter(customParameterRepository.findById(parameterId).orElseThrow());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                
                if(value[0].equals("true")) {
                    valueForGivenDayAndParameter.setBooleanValue(true);
                } else {
                    valueForGivenDayAndParameter.setBooleanValue(false);
                }
                valueForGivenDayAndParameter.setDate(this.getDateFromLocalDate(givenDay));
                observedDayValueRepository.save(valueForGivenDayAndParameter);
            }
        }
        
        
        // reload all the customparameters and their values so they will be set once the submitbutton is pressed
        observedValuesFromGivenDay = this.observedDayValueRepository.findByDateAndUser(getDateFromLocalDate(givenDay), user);
        this.prepareModelData(model, givenDay, user, observedValuesFromGivenDay);

        return new ModelAndView("/data/datacollection/enter-data");
    }
    
    private void prepareModelData(
        Model model,
        LocalDate givenDay,
        User user,
        List<ObservedDayValue> observedValuesFromGivenDay){
        // HashMaps to store all the day-values. Key = parameterId
        HashMap<Integer, Double> numericParameterMap = new HashMap<Integer, Double>();
        HashMap<Integer, String> stringParameterMap = new HashMap<Integer, String>();
        HashMap<Integer, Boolean> booleanParameterMap = new HashMap<Integer, Boolean>();
        
        // fill parameter maps so currently entered data for the given day can be retrieved in the form
        for (ObservedDayValue observedValue : observedValuesFromGivenDay) {
            switch (observedValue.getCustomParameter().getType()) {
                case BOOLEAN:
                    // add to booleanParameterMap
                    booleanParameterMap.put(observedValue.getCustomParameter().getId(), observedValue.getBooleanValue());
                    break;
                case NUMERIC:
                    // add to numericParameterMap
                    numericParameterMap.put(observedValue.getCustomParameter().getId(), observedValue.getNumericValue());
                    break;
                case STRING:
                    // add to stringParameterMap
                    stringParameterMap.put(observedValue.getCustomParameter().getId(), observedValue.getStringValue());
                    break;
                default:
                    break;
            }
        }
        
        // add variables to html form
        model.addAttribute("numericParameterMap", numericParameterMap);
        model.addAttribute("stringParameterMap", stringParameterMap);
        model.addAttribute("booleanParameterMap", booleanParameterMap);
        
        // get all active custom parameters by type
        List<CustomParameter> activeParametersNumeric = customParameterRepository.findActiveByUserAndType(user, ParameterType.NUMERIC);
        List<CustomParameter> activeParametersBoolean = customParameterRepository.findActiveByUserAndType(user, ParameterType.BOOLEAN);
        List<CustomParameter> activeParametersString = customParameterRepository.findActiveByUserAndType(user, ParameterType.STRING);
        
        
        // add variables to html form to iterate over
        model.addAttribute("date", givenDay);
        model.addAttribute("observedValuesFromGivenDay", observedValuesFromGivenDay);
        model.addAttribute("activeParametersNumeric", activeParametersNumeric);
        model.addAttribute("activeParametersBoolean", activeParametersBoolean);
        model.addAttribute("activeParametersString", activeParametersString);
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
}

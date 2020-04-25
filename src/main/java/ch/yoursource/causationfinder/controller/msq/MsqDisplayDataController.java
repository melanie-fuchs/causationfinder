package ch.yoursource.causationfinder.controller.msq;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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

import ch.yoursource.causationfinder.dto.StartEndDateDto;
import ch.yoursource.causationfinder.entity.MedicalSymptomsQuestionnaire;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.MsqRepository;
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class MsqDisplayDataController {

    private UserService userService;
    private MsqRepository msqRepository;
    private MessageSource messageSource;
    
    public MsqDisplayDataController(
            UserService userService,
            MsqRepository msqRepository,
            MessageSource messageSource) {
        this.userService = userService;
        this.msqRepository = msqRepository;
        this.messageSource = messageSource;
    }
    
    @GetMapping("/msq/display-data")
    public String showDisplayDataController(
            WebRequest request,
            Model model) {
        
        StartEndDateDto startEndDateDto = new StartEndDateDto();
        
        model.addAttribute(startEndDateDto);
        
        return "/msq/display-data";
        
    }
    
    @PostMapping("/msq/display-data")
    public ModelAndView displayData(
            @ModelAttribute("startEndDateDto") StartEndDateDto startEndDateDto,
            BindingResult result,
            WebRequest request,
            Model model,
            Errors errors,
            Locale locale) {
        
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
            return new ModelAndView("msq/display-data", "startEndDateDto", startEndDateDto);
        }
        
        
        // get all Dates between the two dates (add one day (plusDays(1L)) to the endDate,
        // so the enddate itself will be analyzed as well)
        List<LocalDate> allDates = startDate.datesUntil(endDate.plusDays(1L)).collect(Collectors.toList());
        
        // get a list, ordered by date ASC, of all the msq-entries within date-range
        List<MedicalSymptomsQuestionnaire> allMsq = this.msqRepository.findByUserInRange(user, this.getDateFromLocalDate(startDate), this.getDateFromLocalDate(endDate));
        
        // creating a list with the parameter-names to have the wanted order
        // when retrieving the data and display it
        List<String> msqFields = new ArrayList<String>();
        msqFields.add("headHeadaches");
        msqFields.add("headFaintness");
        msqFields.add("headDizziness");
        msqFields.add("headInsomnia");
        msqFields.add("eyesWatery");
        msqFields.add("eyesSwollen");
        msqFields.add("eyesBags");
        msqFields.add("eyesBlurred");
        msqFields.add("earsItchy");
        msqFields.add("earsEaraches");
        msqFields.add("earsDrainage");
        msqFields.add("earsRinging");
        msqFields.add("noseStuffy");
        msqFields.add("noseSinus");
        msqFields.add("noseHay");
        msqFields.add("noseSneezing");
        msqFields.add("noseMucus");
        msqFields.add("mouthCoughing");
        msqFields.add("mouthGagging");
        msqFields.add("mouthSore");
        msqFields.add("mouthSwollen");
        msqFields.add("mouthCanker");
        msqFields.add("skinAcne");
        msqFields.add("skinHives");
        msqFields.add("skinHairloss");
        msqFields.add("skinFlushing");
        msqFields.add("skinSweating");
        msqFields.add("heartIrregular");
        msqFields.add("heartRapid");
        msqFields.add("heartChestpain");
        msqFields.add("lungsCongestion");
        msqFields.add("lungsAsthma");
        msqFields.add("lungsShortness");
        msqFields.add("lungsDifficulty");
        msqFields.add("digestiveNausea");
        msqFields.add("digestiveDiarrhea");
        msqFields.add("digestiveConstipation");
        msqFields.add("digestiveBloated");
        msqFields.add("digestiveBelching");
        msqFields.add("digestiveHeartburn");
        msqFields.add("digestivePain");
        msqFields.add("jointsPain");
        msqFields.add("jointsArthritis");
        msqFields.add("jointsStiffness");
        msqFields.add("jointsMusclepain");
        msqFields.add("jointsWeakness");
        msqFields.add("weightBinge");
        msqFields.add("weightCraving");
        msqFields.add("weightExcessive");
        msqFields.add("weightCompulsive");
        msqFields.add("weightWater");
        msqFields.add("weightUnderweight");
        msqFields.add("energyFatigue");
        msqFields.add("energyApathy");
        msqFields.add("energyHyperactivity");
        msqFields.add("energyRestlessness");
        msqFields.add("mindMemory");
        msqFields.add("mindConfusion");
        msqFields.add("mindConcentration");
        msqFields.add("mindCoordination");
        msqFields.add("mindDecisions");
        msqFields.add("mindStuttering");
        msqFields.add("mindSlurred");
        msqFields.add("mindLearning");
        msqFields.add("emotionsSwings");
        msqFields.add("emotionsAnxiety");
        msqFields.add("emotionsAnger");
        msqFields.add("emotionsDepression");
        msqFields.add("otherIllness");
        msqFields.add("otherUrination");
        msqFields.add("otherGenital");
        
        // array that contains the sum of each parameter per day
        int[] sumByDate = new int[allDates.size()];
        
        /*      ------------MAP fieldValuesByDate-------------
         *      
         *      DATE (as String)    , ----- MAP fieldValues -----
         *                           field (eg earsitchy),  value
         */ 
        Map<String, Map<String, Integer>> fieldValuesByDate = new HashMap<String, Map<String, Integer>>();
        for (LocalDate date : allDates) {
            Map<String, Integer> fieldValues = new HashMap<String, Integer>();
            // filling the inner map with field-names and null-value
            for (String field : msqFields) {
                fieldValues.put(field, null);
            }
            
            fieldValuesByDate.put(date.toString(), fieldValues);
        }

        // the method retrieves every msq's date and makes a String from it.
        // Then, it retrieves the value for each of the fields and puts the date,
        // the fieldname and the value into the nested map
        for (MedicalSymptomsQuestionnaire msq : allMsq) {
            String dateString = this.getLocalDateFromDate(msq.getDate()).toString();
            
            for (String fieldName : msqFields) {
                Integer value = this.getValueByFieldname(msq, fieldName);
                
                fieldValuesByDate.get(dateString).put(fieldName, value);
            }
        }
        
        // The array tableData is the matrix. It requires two  more rows: one for
        // the title and one to display the totals. It also needs one more column to
        // display the field-names
        String[][] tableData = new String[msqFields.size() + 2][allDates.size() + 1];
        // set value for the top left corner
        tableData[0][0] = "Parameter";
        
        // setting the column-titles (date)
        for (int i = 0; i < allDates.size(); i++) {            
            String dateString = allDates.get(i).toString();
            
            // add dates for header row
            tableData[0][i + 1] = dateString;
        }
                
        // setting the row-titles and fill in the "table"
        for (int j = 0; j < msqFields.size(); j ++) {
            String msqField = msqFields.get(j);
            
            // setting the values for the left column
            tableData[j+1][0] = this.messageSource.getMessage(
                    new DefaultMessageSourceResolvable(
                            this.getTranslationKeyFromFieldName(msqField)
                    ), locale
            );
            
            // filling data row by row
            for (int i = 0; i < allDates.size(); i++) {
                String dateString = allDates.get(i).toString();
                
                Integer valueAtDate = fieldValuesByDate.get(dateString).get(msqField);
                
                tableData[j+1][i+1] = String.valueOf(valueAtDate);
                
                //continuous sum of values per date
                if (valueAtDate != null) {
                    sumByDate[i] += valueAtDate;
                }
            }
        }
        
        // setting the title "total" in the bottom left corner
        tableData[tableData.length - 1][0] = "Total";
        for (int i = 0; i < sumByDate.length; i++) {
            // adding all the sums for each column
            tableData[tableData.length - 1][i + 1] = String.valueOf(sumByDate[i]);
        }
        
        model.addAttribute("tableData", tableData);
        
        return new ModelAndView("msq/show-data"); 
    }
    
    // method to return a value of a class (msq) for a specific field, using BeanInfo
    private Integer getValueByFieldname(MedicalSymptomsQuestionnaire msq, String fieldName) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(msq.getClass());
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                // returns every field as a String of the class
                String propertyName = propertyDescriptor.getName();
                
                if (propertyName.equals(fieldName)) {
                    try {
                        Object value = propertyDescriptor.getReadMethod().invoke(msq);
                        return Integer.valueOf(String.valueOf(value));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        
        return null;
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

    private String getTranslationKeyFromFieldName(String fieldName) {
        Pattern pattern = Pattern.compile("([A-Z])");
        Matcher matcher = pattern.matcher(fieldName);

        if (matcher.find()) {
            String allLower = fieldName.toLowerCase();
            return "messages.msq.enterdata." +
                    allLower.substring(0, matcher.start()) +
                    "." +
                    allLower.substring(matcher.start());
        }

        return fieldName;
    }
}

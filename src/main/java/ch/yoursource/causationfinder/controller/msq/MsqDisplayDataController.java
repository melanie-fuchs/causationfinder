package ch.yoursource.causationfinder.controller.msq;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import ch.yoursource.causationfinder.entity.MedicalSymptomsQuestionnaire;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.MsqRepository;
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class MsqDisplayDataController {

    private UserService userService;
    private MsqRepository msqRepository;
    
    public MsqDisplayDataController(
            UserService userService,
            MsqRepository msqRepository) {
        this.userService = userService;
        this.msqRepository = msqRepository;
    }
    
    @GetMapping("/msq/display-data")
    public String showDisplayDataController(
            WebRequest request,
            Model model) {
        
        return "/msq/display-data";
        
    }
    
    @PostMapping("/msq/display-data")
    public ModelAndView displayData(
            @RequestParam(value="startDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value="endDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            WebRequest request,
            Model model) {
        
        User user = this.getLoggedInUser();
        
        // get all Dates between the two dates (add one day (plusDays(1L)) to the endDate, so the enddate itself will be analyzed as well)
        List<LocalDate> allDates = startDate.datesUntil(endDate.plusDays(1L)).collect(Collectors.toList());
        
        // get a list of all the msq-entries within date-range
        List<MedicalSymptomsQuestionnaire> allMsq = this.msqRepository.findByUserInRange(user, this.getDateFromLocalDate(startDate), this.getDateFromLocalDate(endDate));
        //TODO: sort allMsq by date ascending
        
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
        
        int[] sumByDate = new int[allDates.size()];
        Map<String, Map<String, Integer>> fieldValuesByDate = new HashMap<String, Map<String, Integer>>();
        for (LocalDate date : allDates) {
            Map<String, Integer> fieldValues = new HashMap<String, Integer>();
            
            for (String field : msqFields) {
                fieldValues.put(field, null);
            }
            
            fieldValuesByDate.put(date.toString(), fieldValues);
        }

        for (MedicalSymptomsQuestionnaire msq : allMsq) {
            String dateString = this.getLocalDateFromDate(msq.getDate()).toString();
            
            for (String fieldName : msqFields) {
                Integer value = this.getValueByFieldname(msq, fieldName);
                
                fieldValuesByDate.get(dateString).put(fieldName, value);
            }
        }
        
        String[][] tableData = new String[msqFields.size() + 2][allDates.size() + 1];
        tableData[0][0] = "Parameter";
        
        for (int i = 0; i < allDates.size(); i++) {            
            String dateString = allDates.get(i).toString();
            
            // add dates for header row
            tableData[0][i + 1] = dateString;
        }
                
        for (int j = 0; j < msqFields.size(); j ++) {
            String msqField = msqFields.get(j);
            
            // add column for field name
            tableData[j+1][0] = msqField;
            
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
        
        
        tableData[tableData.length - 1][0] = "Total";
        for (int i = 0; i < sumByDate.length; i++) {
            tableData[tableData.length - 1][i + 1] = String.valueOf(sumByDate[i]);
        }
        
        model.addAttribute("tableData", tableData);
        
        return new ModelAndView("msq/show-data"); 
    }
    
    private Integer getValueByFieldname(MedicalSymptomsQuestionnaire msq, String fieldName) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(msq.getClass());
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
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
      
}

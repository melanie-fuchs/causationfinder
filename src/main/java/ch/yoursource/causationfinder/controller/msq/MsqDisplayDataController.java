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
        
        // get a list of all the msq-entries within date-range
        List<MedicalSymptomsQuestionnaire> allMsq = this.msqRepository.findByUserInRange(user, this.getDateFromLocalDate(startDate), this.getDateFromLocalDate(endDate));
         
        // get all the dates in range
        List<LocalDate> allDates = startDate.datesUntil(endDate.plusDays(1L)).collect(Collectors.toList());
        
        // get a list that contains all the fieldnames of class MedicalSymptomsQuestionnaire
        List<String> allFields = this.getAllFields();
        
        // create matrix
        Integer[][] valuesByParameterAndDate = new Integer[allMsq.size()][allDates.size()];
        
        for(MedicalSymptomsQuestionnaire msq : allMsq) {
            Integer value = msq.getHeadDizziness();
            
        }
        
        
        
        return null;
    }

    private List<String> getAllFields() {
        List<String> allFields = new ArrayList<String>();
        
        BeanInfo beanInfo;
        MedicalSymptomsQuestionnaire medicalSymptomsQuestionnaire = new MedicalSymptomsQuestionnaire();
        try {
            beanInfo = Introspector.getBeanInfo(MedicalSymptomsQuestionnaire.class);
            for(PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                String propertyName = propertyDescriptor.getName();
                allFields.add(propertyName);
                System.out.println(" > > > > > property: " + propertyName);
                try {
                    Object value = propertyDescriptor.getReadMethod().invoke(medicalSymptomsQuestionnaire);
                    System.out.println(" > > > > > its' value: " + value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        
        return allFields;
        
        
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

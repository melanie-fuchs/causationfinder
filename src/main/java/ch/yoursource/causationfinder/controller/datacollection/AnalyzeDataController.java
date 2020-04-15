package ch.yoursource.causationfinder.controller.datacollection;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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

import ch.yoursource.causationfinder.entity.ObservedDayValue;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.ObservedDayValueRepository;
import ch.yoursource.causationfinder.service.UserService;

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

        System.out.println("StartDate: " + startDate + " EndDate: " + endDate);
        
        User user = this.getLoggedInUser();
        
        List<ObservedDayValue> observedValuesInRange = this.observedDayValueRepository
                .findActiveByUserInRange(this.getDateFromLocalDate(startDate), this.getDateFromLocalDate(endDate), user);
        
        for (ObservedDayValue o : observedValuesInRange) {
            System.out.print("id: " + o.getId());
            System.out.println("   min: " + o.getCustomParameter().getMinValue());
        }
        System.out.println("    SIZE OF LIST: " + observedValuesInRange.size());
        
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
    
}

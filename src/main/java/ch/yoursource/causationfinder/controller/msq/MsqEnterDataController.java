package ch.yoursource.causationfinder.controller.msq;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.entity.msq.MedicalSymptomsQuestionnaire;
import ch.yoursource.causationfinder.repository.MsqRepository;
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class MsqEnterDataController {

    private UserService userService;
    private MsqRepository msqRepository;
    
    public MsqEnterDataController(
            UserService userService,
            MsqRepository msqRepository) {
        this.userService = userService;
        this.msqRepository = msqRepository;
    }

    @GetMapping("/msq/enter-data")
    public String showMsqEnterDataForm(
            @RequestParam(value="data", required = false) @DateTimeFormat(iso=  DateTimeFormat.ISO.DATE) LocalDate date,
            WebRequest request,
            Model model
            ){ 
    
        // set date to date in form if one was set, else set date to today
        LocalDate givenDay = date != null ? date : LocalDate.now();

        // get the current user
        User user = this.getLoggedInUser();
        System.out.println(">>>> Date: " + givenDay + "   User: " + user);
        List<MedicalSymptomsQuestionnaire> msq = msqRepository.findByUserAndDate(user, getDateFromLocalDate(givenDay));
        System.out.println(">>>>>>>>>>> SIZE: " + msq.size());
        model.addAttribute("msq", msq);
        
        return "msq/enter-data";
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

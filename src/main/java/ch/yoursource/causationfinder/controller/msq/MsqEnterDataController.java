package ch.yoursource.causationfinder.controller.msq;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.yoursource.causationfinder.dto.MedicalSymptomsQuestionnaireDto;
import ch.yoursource.causationfinder.entity.MedicalSymptomsQuestionnaire;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.MsqRepository;
import ch.yoursource.causationfinder.service.UserService;

@Controller
public class MsqEnterDataController {

    private UserService userService;
    private MsqRepository msqRepository;
    
    @Autowired
    public MsqEnterDataController(
            UserService userService,
            MsqRepository msqRepository) {
        this.userService = userService;
        this.msqRepository = msqRepository;
    }

    @GetMapping("/msq/enter-data")
    public String showMsqEnterDataForm(
            @RequestParam(value = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            WebRequest request,
            Model model) {

        LocalDate givenDay = date != null ? date : LocalDate.now();
        User user = this.getLoggedInUser();

        MedicalSymptomsQuestionnaireDto medicalSymptomsQuestionnaireDto;
        if ((msqRepository.findByUserAndDate(user, getDateFromLocalDate(givenDay)) != null)) {
            MedicalSymptomsQuestionnaire msq = msqRepository.findByUserAndDate(user, getDateFromLocalDate(givenDay));
            medicalSymptomsQuestionnaireDto = new MedicalSymptomsQuestionnaireDto(msq);
        } else {
            medicalSymptomsQuestionnaireDto = new MedicalSymptomsQuestionnaireDto();

        }
        model.addAttribute("date", givenDay);
        model.addAttribute("medicalSymptomsQuestionnaireDto", medicalSymptomsQuestionnaireDto);
        
        return "msq/enter-data";
    }
    
    @PostMapping("msq/enter-data")
    public ModelAndView saveEntry(
        @ModelAttribute("medicalSymptomsQuestionnaireDto") MedicalSymptomsQuestionnaireDto medicalSymptomsQuestionnaireDto,
        @RequestParam(value="date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        WebRequest request,
        ModelAndView modelAndView) {

        LocalDate givenDay = date;
        User user = getLoggedInUser();

        MedicalSymptomsQuestionnaire msq;
        // if the MSQ of this user and date is already in database, then load it an
        // update its data
        if (msqRepository.findByUserAndDate(getLoggedInUser(),
                getDateFromLocalDate(date)) != null) {
            msq = msqRepository.findByUserAndDate(getLoggedInUser(),
                    getDateFromLocalDate(date));
        } else {
            msq = new MedicalSymptomsQuestionnaire();
        }
       
        msq.setDate(getDateFromLocalDate(givenDay));
        msq.setUser(user);
        msq.setEyesBags(medicalSymptomsQuestionnaireDto.getEyesBags());
        msq.setEyesBlurred(medicalSymptomsQuestionnaireDto.getEyesBlurred());
        msq.setEyesSwollen(medicalSymptomsQuestionnaireDto.getEyesSwollen());
        msq.setEyesWatery(medicalSymptomsQuestionnaireDto.getEyesWatery());
        msq.setHeadDizziness(medicalSymptomsQuestionnaireDto.getHeadDizziness());
        msq.setHeadFaintness(medicalSymptomsQuestionnaireDto.getHeadFaintness());
        msq.setHeadHeadaches(medicalSymptomsQuestionnaireDto.getHeadHeadaches());
        msq.setHeadInsomnia(medicalSymptomsQuestionnaireDto.getHeadInsomnia());

        msqRepository.save(msq);

        return new ModelAndView("home/userhome");
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

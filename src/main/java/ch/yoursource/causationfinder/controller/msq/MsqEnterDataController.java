package ch.yoursource.causationfinder.controller.msq;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

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
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.entity.msq.MedicalSymptomsQuestionnaire;
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

        System.out.println("> User id: " + user.getId() + "    Date = " + givenDay);
        MedicalSymptomsQuestionnaireDto medicalSymptomsQuestionnaireDto;
        if ((msqRepository.findByUserAndDate(user, getDateFromLocalDate(givenDay)) != null)) {
            MedicalSymptomsQuestionnaire msq = msqRepository.findByUserAndDate(user, getDateFromLocalDate(givenDay));
            medicalSymptomsQuestionnaireDto = new MedicalSymptomsQuestionnaireDto(msq);
            System.out.println("WITHIHN IF");
        } else {
            medicalSymptomsQuestionnaireDto = new MedicalSymptomsQuestionnaireDto();

        }
        System.out.println("> msqDto: Exyebags:  " + medicalSymptomsQuestionnaireDto.getEyesBags());
        model.addAttribute("date", givenDay);
        model.addAttribute("medicalSymptomsQuestionnaireDto", medicalSymptomsQuestionnaireDto);
        
//        System.out.println("ALLES GAGGI");
//        // set date to date in form if one was set, else set date to today
//        LocalDate givenDay = date != null ? date : LocalDate.now();
//
//        // get the current user
//        User user = this.getLoggedInUser();
//
//        MedicalSymptomsQuestionnaire msq = new MedicalSymptomsQuestionnaire();
//        if((msqRepository.findByUserAndDate(user, getDateFromLocalDate(givenDay)) != null)) {
//            msq = msqRepository.findByUserAndDate(user, getDateFromLocalDate(givenDay));
//            System.out.println(">>>>>>>> OBJECT FOUND");
//        } else {
//            msq.setDate(getDateFromLocalDate(givenDay));
//            msq.setUser(user);
//            msq.setEyesBags(0);
//            msq.setEyesBlurred(0);
//            msq.setEyesSwollen(0);
//            msq.setEyesWatery(0);
//            msq.setHeadDizziness(0);
//            msq.setHeadFaintness(0);
//            msq.setHeadHeadaches(0);
//            msq.setHeadInsomnia(500);
//        }
//        
//  
//        
//        model.addAttribute("date", givenDay);
//        model.addAttribute("msq", msq);
//        

        System.out.println(">>>>>>>>>>>> END OF GET");
        
        return "msq/enter-data";
    }
    
    @PostMapping("msq/enter-data")
    public ModelAndView saveEntry(
        @ModelAttribute("medicalSymptomsQuestionnaireDto") MedicalSymptomsQuestionnaireDto medicalSymptomsQuestionnaireDto,
        @RequestParam(value="date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        WebRequest request,
        ModelAndView modelAndView) {

        System.out.println(">>>>>>>>>>>> date: " + date);

        LocalDate givenDay = date;
        User user = getLoggedInUser();

//        Set<String> keySet = request.getParameterMap().keySet();
//
//        System.out.println(">>>>>>>>>>>> keyset-size: " + keySet.size());
        ////////////////////// new below

        MedicalSymptomsQuestionnaire msq;
        // if the MSQ of this user and date is already in database, then load it an
        // update its data
        if (msqRepository.findByUserAndDate(getLoggedInUser(),
                getDateFromLocalDate(date)) != null) {
            System.out.println("WITHIN IF IN POSTMAPING");
            msq = msqRepository.findByUserAndDate(getLoggedInUser(),
                    getDateFromLocalDate(date));

            // else, use the msq that was passed

            System.out.println(">>>>>>>> OBJECT ALREADY EXISTED");
        } else {
            msq = new MedicalSymptomsQuestionnaire();
        }

        System.out.println("   >> MSQ EYES BAGS = " + msq.getEyesBags());
        System.out.println("   >> MSQ DTO EYES BAGS = " + medicalSymptomsQuestionnaireDto.getEyesBags());
        
        msq.setEyesBags(medicalSymptomsQuestionnaireDto.getEyesBags());
        msq.setEyesBlurred(medicalSymptomsQuestionnaireDto.getEyesBlurred());
        msq.setEyesSwollen(medicalSymptomsQuestionnaireDto.getEyesSwollen());
        msq.setEyesWatery(medicalSymptomsQuestionnaireDto.getEyesWatery());
        msq.setHeadDizziness(medicalSymptomsQuestionnaireDto.getHeadDizziness());
        msq.setHeadFaintness(medicalSymptomsQuestionnaireDto.getHeadFaintness());
        msq.setHeadHeadaches(medicalSymptomsQuestionnaireDto.getHeadHeadaches());
        msq.setHeadInsomnia(medicalSymptomsQuestionnaireDto.getHeadInsomnia());
        
//        for (String key : keySet) {
//            String[] value = request.getParameterMap().get(key);
//            if (key.contains("headHeadaches")) {
//                msq.setHeadHeadaches(Integer.parseInt(value[0]));
//                System.out.println(">>>>>>>>headHeadaches 2");
//            }
//            if (key.contains("headDizziness")) {
//                msq.setHeadDizziness(Integer.parseInt(value[0]));
//                System.out.println(">>>>>>>>headDizziness 2");
//            }
//            if (key.contains("headFaintness")) {
//                msq.setHeadFaintness(Integer.parseInt(value[0]));
//                System.out.println(">>>>>>>>headFaintness 2");
//            }
//            if (key.contains("headInsomnia")) {
//                msq.setHeadInsomnia(Integer.parseInt(value[0]));
//                System.out.println(">>>>>>>> headInsomnia2");
//            }
//            if (key.contains("eyesBags")) {
//                msq.setEyesBags(Integer.parseInt(value[0]));
//                System.out.println(">>>>>>>> eyesBags2");
//            }
//            if (key.contains("eyesBlurred")) {
//                msq.setEyesBlurred(Integer.parseInt(value[0]));
//                System.out.println(">>>>>>>>eyesBlurred 2");
//            }
//            if (key.contains("eyesSwollen")) {
//                msq.setEyesSwollen(Integer.parseInt(value[0]));
//                System.out.println(">>>>>>>> eyesSwollen2");
//            }
//            if (key.contains("eyesWatery")) {
//                msq.setEyesWatery(Integer.parseInt(value[0]));
//                System.out.println(">>>>>>>>eyesWatery 2");
//            }
//        }

        msq.setDate(getDateFromLocalDate(givenDay));
        msq.setUser(user);

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

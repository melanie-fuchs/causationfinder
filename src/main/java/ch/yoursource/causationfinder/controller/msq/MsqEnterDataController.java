package ch.yoursource.causationfinder.controller.msq;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
        msq.setHeadHeadaches(medicalSymptomsQuestionnaireDto.getHeadHeadaches());
        msq.setHeadFaintness(medicalSymptomsQuestionnaireDto.getHeadFaintness());
        msq.setHeadDizziness(medicalSymptomsQuestionnaireDto.getHeadDizziness());
        msq.setHeadInsomnia(medicalSymptomsQuestionnaireDto.getHeadInsomnia());
        msq.setEyesBags(medicalSymptomsQuestionnaireDto.getEyesBags());
        msq.setEyesBlurred(medicalSymptomsQuestionnaireDto.getEyesBlurred());
        msq.setEyesSwollen(medicalSymptomsQuestionnaireDto.getEyesSwollen());
        msq.setEyesWatery(medicalSymptomsQuestionnaireDto.getEyesWatery());
        msq.setEarsDrainage(medicalSymptomsQuestionnaireDto.getEarsDrainage());
        msq.setEarsEaraches(medicalSymptomsQuestionnaireDto.getEarsEaraches());
        msq.setEarsItchy(medicalSymptomsQuestionnaireDto.getEarsItchy());
        msq.setEarsRinging(medicalSymptomsQuestionnaireDto.getEarsRinging());
        msq.setNoseHay(medicalSymptomsQuestionnaireDto.getNoseHay());
        msq.setNoseMucus(medicalSymptomsQuestionnaireDto.getNoseMucus());
        msq.setNoseSinus(medicalSymptomsQuestionnaireDto.getNoseSinus());
        msq.setNoseSneezing(medicalSymptomsQuestionnaireDto.getNoseSneezing());
        msq.setNoseStuffy(medicalSymptomsQuestionnaireDto.getNoseStuffy());
        msq.setMouthCanker(medicalSymptomsQuestionnaireDto.getMouthCanker());
        msq.setMouthCoughing(medicalSymptomsQuestionnaireDto.getMouthCoughing());
        msq.setMouthGagging(medicalSymptomsQuestionnaireDto.getMouthGagging());
        msq.setMouthSore(medicalSymptomsQuestionnaireDto.getMouthSore());
        msq.setMouthSwollen(medicalSymptomsQuestionnaireDto.getMouthSwollen());
        msq.setSkinAcne(medicalSymptomsQuestionnaireDto.getSkinAcne());
        msq.setSkinFlushing(medicalSymptomsQuestionnaireDto.getSkinFlushing());
        msq.setSkinHairloss(medicalSymptomsQuestionnaireDto.getSkinHairloss());
        msq.setSkinHives(medicalSymptomsQuestionnaireDto.getSkinHives());
        msq.setSkinSweating(medicalSymptomsQuestionnaireDto.getSkinSweating());
        msq.setHeartChestpain(medicalSymptomsQuestionnaireDto.getHeartChestpain());
        msq.setHeartIrregular(medicalSymptomsQuestionnaireDto.getHeartIrregular());
        msq.setHeartRapid(medicalSymptomsQuestionnaireDto.getHeartRapid());
        msq.setLungsAsthma(medicalSymptomsQuestionnaireDto.getLungsAsthma());
        msq.setLungsCongestion(medicalSymptomsQuestionnaireDto.getLungsCongestion());
        msq.setLungsDifficulty(medicalSymptomsQuestionnaireDto.getLungsDifficulty());
        msq.setLungsShortness(medicalSymptomsQuestionnaireDto.getLungsShortness());
        msq.setDigestiveBelching(medicalSymptomsQuestionnaireDto.getDigestiveBelching());
        msq.setDigestiveBloated(medicalSymptomsQuestionnaireDto.getDigestiveBloated());
        msq.setDigestiveConstipation(medicalSymptomsQuestionnaireDto.getDigestiveConstipation());
        msq.setDigestiveDiarrhea(medicalSymptomsQuestionnaireDto.getDigestiveDiarrhea());
        msq.setDigestiveHeartburn(medicalSymptomsQuestionnaireDto.getDigestiveHeartburn());
        msq.setDigestiveNausea(medicalSymptomsQuestionnaireDto.getDigestiveNausea());
        msq.setDigestivePain(medicalSymptomsQuestionnaireDto.getDigestivePain());
        msq.setJointsArthritis(medicalSymptomsQuestionnaireDto.getJointsArthritis());
        msq.setJointsMusclepain(medicalSymptomsQuestionnaireDto.getJointsMusclepain());
        msq.setJointsPain(medicalSymptomsQuestionnaireDto.getJointsPain());
        msq.setJointsStiffness(medicalSymptomsQuestionnaireDto.getJointsStiffness());
        msq.setJointsWeakness(medicalSymptomsQuestionnaireDto.getJointsWeakness());
        msq.setWeightBinge(medicalSymptomsQuestionnaireDto.getWeightBinge());
        msq.setWeightCompulsive(medicalSymptomsQuestionnaireDto.getWeightCompulsive());
        msq.setWeightCraving(medicalSymptomsQuestionnaireDto.getWeightCraving());
        msq.setWeightExcessive(medicalSymptomsQuestionnaireDto.getWeightExcessive());
        msq.setWeightUnderweight(medicalSymptomsQuestionnaireDto.getWeightUnderweight());
        msq.setWeightWater(medicalSymptomsQuestionnaireDto.getWeightWater());
        msq.setEnergyApathy(medicalSymptomsQuestionnaireDto.getEnergyApathy());
        msq.setEnergyFatigue(medicalSymptomsQuestionnaireDto.getEnergyFatigue());
        msq.setEnergyHyperactivity(medicalSymptomsQuestionnaireDto.getEnergyHyperactivity());
        msq.setEnergyRestlessness(medicalSymptomsQuestionnaireDto.getEnergyRestlessness());
        msq.setMindConcentration(medicalSymptomsQuestionnaireDto.getMindConcentration());
        msq.setMindConfusion(medicalSymptomsQuestionnaireDto.getMindConfusion());
        msq.setMindCoordination(medicalSymptomsQuestionnaireDto.getMindCoordination());
        msq.setMindDecisions(medicalSymptomsQuestionnaireDto.getMindDecisions());
        msq.setMindLearning(medicalSymptomsQuestionnaireDto.getMindLearning());
        msq.setMindMemory(medicalSymptomsQuestionnaireDto.getMindMemory());
        msq.setMindSlurred(medicalSymptomsQuestionnaireDto.getMindSlurred());
        msq.setMindStuttering(medicalSymptomsQuestionnaireDto.getMindStuttering());
        msq.setEmotionsAnger(medicalSymptomsQuestionnaireDto.getEmotionsAnger());
        msq.setEmotionsAnxiety(medicalSymptomsQuestionnaireDto.getEmotionsAnxiety());
        msq.setEmotionsDepression(medicalSymptomsQuestionnaireDto.getEmotionsDepression());
        msq.setEmotionsSwings(medicalSymptomsQuestionnaireDto.getEmotionsSwings());
        msq.setOtherGenital(medicalSymptomsQuestionnaireDto.getOtherGenital());
        msq.setOtherIllness(medicalSymptomsQuestionnaireDto.getOtherIllness());
        msq.setOtherUrination(medicalSymptomsQuestionnaireDto.getOtherUrination());    

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

package ch.yoursource.causationfinder.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.entity.msq.MedicalSymptomsQuestionnaire;

public class MedicalSymptomsQuestionnaireDto {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;
    
    @NotNull
    private User user;
    
    @NotNull
    private Integer headHeadaches;
    
    @NotNull
    private Integer headFaintness;
    
    @NotNull
    private Integer headDizziness;
    
    @NotNull
    private Integer headInsomnia;
    
    @NotNull
    private Integer eyesWatery;
    
    @NotNull
    private Integer eyesSwollen;

    @NotNull
    private Integer eyesBags;

    @NotNull
    private Integer eyesBlurred;
    
    public MedicalSymptomsQuestionnaireDto() {}
    
    public MedicalSymptomsQuestionnaireDto(MedicalSymptomsQuestionnaire msq) {
        this.date = msq.getDate();
        this.headDizziness = msq.getHeadDizziness();
        this.headFaintness = msq.getHeadFaintness();
        this.headHeadaches = msq.getHeadHeadaches();
        this.headInsomnia = msq.getHeadInsomnia();
        this.eyesBags = msq.getEyesBags();
        this.eyesBlurred = msq.getEyesBlurred();
        this.eyesSwollen = msq.getEyesSwollen();
        this.eyesWatery = msq.getEyesWatery();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getHeadHeadaches() {
        return headHeadaches;
    }

    public void setHeadHeadaches(Integer headHeadaches) {
        this.headHeadaches = headHeadaches;
    }

    public Integer getHeadFaintness() {
        return headFaintness;
    }

    public void setHeadFaintness(Integer headFaintness) {
        this.headFaintness = headFaintness;
    }

    public Integer getHeadDizziness() {
        return headDizziness;
    }

    public void setHeadDizziness(Integer headDizziness) {
        this.headDizziness = headDizziness;
    }

    public Integer getHeadInsomnia() {
        return headInsomnia;
    }

    public void setHeadInsomnia(Integer headInsomnia) {
        this.headInsomnia = headInsomnia;
    }

    public Integer getEyesWatery() {
        return eyesWatery;
    }

    public void setEyesWatery(Integer eyesWatery) {
        this.eyesWatery = eyesWatery;
    }

    public Integer getEyesSwollen() {
        return eyesSwollen;
    }

    public void setEyesSwollen(Integer eyesSwollen) {
        this.eyesSwollen = eyesSwollen;
    }

    public Integer getEyesBags() {
        return eyesBags;
    }

    public void setEyesBags(Integer eyesBags) {
        this.eyesBags = eyesBags;
    }

    public Integer getEyesBlurred() {
        return eyesBlurred;
    }

    public void setEyesBlurred(Integer eyesBlurred) {
        this.eyesBlurred = eyesBlurred;
    }


}

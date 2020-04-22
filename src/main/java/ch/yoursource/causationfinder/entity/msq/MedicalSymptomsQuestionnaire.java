package ch.yoursource.causationfinder.entity.msq;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import ch.yoursource.causationfinder.entity.User;

/*
 * This class represents the MSQ itself and contains objects
 * of the single points of the msq
 */
@Entity
public class MedicalSymptomsQuestionnaire {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private int id;
    
    @NotNull
    @Column(name="date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotNull
    @Column(name="head_Headaches")
    private Integer headHeadaches;
    
    @NotNull
    @Column(name="head_faintness")
    private Integer headFaintness;
    
    @NotNull
    @Column(name="head_dizziness")
    private Integer headDizziness;
    
    @NotNull
    @Column(name="head_insomnia")
    private Integer headInsomnia;
    
    @NotNull
    @Column(name="eyes_watery")
    private Integer eyesWatery;
    
    @NotNull
    @Column(name="eyes_swollen")
    private Integer eyesSwollen;

    @NotNull
    @Column(name="eyes_bags")
    private Integer eyesBags;

    @NotNull
    @Column(name="eyes_blurred")
    private Integer eyesBlurred;

    public MedicalSymptomsQuestionnaire() {}
    
    public MedicalSymptomsQuestionnaire(@NotNull Date date, @NotNull User user, @NotNull Integer headHeadaches,
            @NotNull Integer headFaintness, @NotNull Integer headDizziness, @NotNull Integer headInsomnia,
            @NotNull Integer eyesWatery, @NotNull Integer eyesSwollen, @NotNull Integer eyesBags,
            @NotNull Integer eyesBlurred) {
        this.date = date;
        this.user = user;
        this.headHeadaches = headHeadaches;
        this.headFaintness = headFaintness;
        this.headDizziness = headDizziness;
        this.headInsomnia = headInsomnia;
        this.eyesWatery = eyesWatery;
        this.eyesSwollen = eyesSwollen;
        this.eyesBags = eyesBags;
        this.eyesBlurred = eyesBlurred;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    


    
//    
//    //@NotNull
//    private MsqEyes eyes;
//    
//    //@NotNull
//    private MsqEars msqEars;

    
//    private MsqNose msqNose;
//    
//    private MsqMouthThroat msqMouthThroat;
//    
//    private MsqSkin msqSkin;
//    
//    private MsqHeart msqHear;
//    
//    private MsqLungs msqLungs;
//    
//    private MsqDigestiveTract msqDigestiveTract;
//    
//    private MsqJointsMuscles msqJointsMuscles;
//    
//    private MsqWeight msqWeight;
//    
//    private MsqEnergyActivity msqEnergyActivity;
//    
//    private MsqMind msqMind;
//    
//    private MsqEmotions msqEmotions;
//    
//    private MsqOther msqOther;

}

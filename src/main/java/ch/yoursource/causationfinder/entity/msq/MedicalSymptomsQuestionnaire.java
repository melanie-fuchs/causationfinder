package ch.yoursource.causationfinder.entity.msq;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

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
    
    //@NotNull
    @Column(name="date")
    private Date date;
    
    //@NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    //@NotNull
    @OneToOne
    @JoinColumn(name = "msq_head_id", nullable = false)
    private MsqHead msqHead;
    
    //@NotNull
    @OneToOne
    @JoinColumn(name = "msq_eyes_id", nullable = false)
    private MsqEyes msqEyes;
    
    //@NotNull
    @OneToOne
    @JoinColumn(name = "msq_ears_id", nullable = false)
    private MsqEars msqEars;
    
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

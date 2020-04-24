package ch.yoursource.causationfinder.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/*
 * This class represents the MSQ itself and contains objects
 * of the single points of the msq
 */
@Entity
public class MedicalSymptomsQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
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

    @NotNull
    private Integer earsItchy;

    @NotNull
    private Integer earsEaraches;

    @NotNull
    private Integer earsDrainage;

    @NotNull
    private Integer earsRinging;

    @NotNull
    private Integer noseStuffy;

    @NotNull
    private Integer noseSinus;

    @NotNull
    private Integer noseHay;

    @NotNull
    private Integer noseSneezing;

    @NotNull
    private Integer noseMucus;

    @NotNull
    private Integer mouthCoughing;

    @NotNull
    private Integer mouthGagging;

    @NotNull
    private Integer mouthSore;

    @NotNull
    private Integer mouthSwollen;

    @NotNull
    private Integer mouthCanker;

    @NotNull
    private Integer skinAcne;

    @NotNull
    private Integer skinHives;

    @NotNull
    private Integer skinHairloss;

    @NotNull
    private Integer skinFlushing;

    @NotNull
    private Integer skinSweating;

    @NotNull
    private Integer heartIrregular;

    @NotNull
    private Integer heartRapid;

    @NotNull
    private Integer heartChestpain;

    @NotNull
    private Integer lungsCongestion;

    @NotNull
    private Integer lungsAsthma;

    @NotNull
    private Integer lungsShortness;

    @NotNull
    private Integer lungsDifficulty;

    @NotNull
    private Integer digestiveNausea;

    @NotNull
    private Integer digestiveDiarrhea;

    @NotNull
    private Integer digestiveConstipation;

    @NotNull
    private Integer digestiveBloated;

    @NotNull
    private Integer digestiveBelching;

    @NotNull
    private Integer digestiveHeartburn;

    @NotNull
    private Integer digestivePain;

    @NotNull
    private Integer jointsPain;

    @NotNull
    private Integer jointsArthritis;

    @NotNull
    private Integer jointsStiffness;

    @NotNull
    private Integer jointsMusclepain;

    @NotNull
    private Integer jointsWeakness;

    @NotNull
    private Integer weightBinge;

    @NotNull
    private Integer weightCraving;

    @NotNull
    private Integer weightExcessive;

    @NotNull
    private Integer weightCompulsive;

    @NotNull
    private Integer weightWater;

    @NotNull
    private Integer weightUnderweight;

    @NotNull
    private Integer energyFatigue;

    @NotNull
    private Integer energyApathy;

    @NotNull
    private Integer energyHyperactivity;

    @NotNull
    private Integer energyRestlessness;

    @NotNull
    private Integer mindMemory;

    @NotNull
    private Integer mindConfusion;

    @NotNull
    private Integer mindConcentration;

    @NotNull
    private Integer mindCoordination;

    @NotNull
    private Integer mindDecisions;

    @NotNull
    private Integer mindStuttering;

    @NotNull
    private Integer mindSlurred;

    @NotNull
    private Integer mindLearning;

    @NotNull
    private Integer emotionsSwings;

    @NotNull
    private Integer emotionsAnxiety;

    @NotNull
    private Integer emotionsAnger;

    @NotNull
    private Integer emotionsDepression;

    @NotNull
    private Integer otherIllness;

    @NotNull
    private Integer otherUrination;

    @NotNull
    private Integer otherGenital;

    public MedicalSymptomsQuestionnaire() {
    }

    public MedicalSymptomsQuestionnaire(@NotNull Date date, @NotNull User user, @NotNull Integer headHeadaches,
            @NotNull Integer headFaintness, @NotNull Integer headDizziness, @NotNull Integer headInsomnia,
            @NotNull Integer eyesWatery, @NotNull Integer eyesSwollen, @NotNull Integer eyesBags,
            @NotNull Integer eyesBlurred, @NotNull Integer earsItchy, @NotNull Integer earsEaraches,
            @NotNull Integer earsDrainage, @NotNull Integer earsRinging, @NotNull Integer noseStuffy,
            @NotNull Integer noseSinus, @NotNull Integer noseHay, @NotNull Integer noseSneezing,
            @NotNull Integer noseMucus, @NotNull Integer mouthCoughing, @NotNull Integer mouthGagging,
            @NotNull Integer mouthSore, @NotNull Integer mouthSwollen, @NotNull Integer mouthCanker,
            @NotNull Integer skinAcne, @NotNull Integer skinHives, @NotNull Integer skinHairloss,
            @NotNull Integer skinFlushing, @NotNull Integer skinSweating, @NotNull Integer heartIrregular,
            @NotNull Integer heartRapid, @NotNull Integer heartChestpain, @NotNull Integer lungsCongestion,
            @NotNull Integer lungsAsthma, @NotNull Integer lungsShortness, @NotNull Integer lungsDifficulty,
            @NotNull Integer digestiveNausea, @NotNull Integer digestiveDiarrhea,
            @NotNull Integer digestiveConstipation, @NotNull Integer digestiveBloated,
            @NotNull Integer digestiveBelching, @NotNull Integer digestiveHeartburn, @NotNull Integer digestivePain,
            @NotNull Integer jointsPain, @NotNull Integer jointsArthritis, @NotNull Integer jointsStiffness,
            @NotNull Integer jointsMusclepain, @NotNull Integer jointsWeakness, @NotNull Integer weightBinge,
            @NotNull Integer weightCraving, @NotNull Integer weightExcessive, @NotNull Integer weightCompulsive,
            @NotNull Integer weightWater, @NotNull Integer weightUnderweight, @NotNull Integer energyFatigue,
            @NotNull Integer energyApathy, @NotNull Integer energyHyperactivity, @NotNull Integer energyRestlessness,
            @NotNull Integer mindMemory, @NotNull Integer mindConfusion, @NotNull Integer mindConcentration,
            @NotNull Integer mindCoordination, @NotNull Integer mindDecisions, @NotNull Integer mindStuttering,
            @NotNull Integer mindSlurred, @NotNull Integer mindLearning, @NotNull Integer emotionsSwings,
            @NotNull Integer emotionsAnxiety, @NotNull Integer emotionsAnger, @NotNull Integer emotionsDepression,
            @NotNull Integer otherIllness, @NotNull Integer otherUrination, @NotNull Integer otherGenital) {
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
        this.earsItchy = earsItchy;
        this.earsEaraches = earsEaraches;
        this.earsDrainage = earsDrainage;
        this.earsRinging = earsRinging;
        this.noseStuffy = noseStuffy;
        this.noseSinus = noseSinus;
        this.noseHay = noseHay;
        this.noseSneezing = noseSneezing;
        this.noseMucus = noseMucus;
        this.mouthCoughing = mouthCoughing;
        this.mouthGagging = mouthGagging;
        this.mouthSore = mouthSore;
        this.mouthSwollen = mouthSwollen;
        this.mouthCanker = mouthCanker;
        this.skinAcne = skinAcne;
        this.skinHives = skinHives;
        this.skinHairloss = skinHairloss;
        this.skinFlushing = skinFlushing;
        this.skinSweating = skinSweating;
        this.heartIrregular = heartIrregular;
        this.heartRapid = heartRapid;
        this.heartChestpain = heartChestpain;
        this.lungsCongestion = lungsCongestion;
        this.lungsAsthma = lungsAsthma;
        this.lungsShortness = lungsShortness;
        this.lungsDifficulty = lungsDifficulty;
        this.digestiveNausea = digestiveNausea;
        this.digestiveDiarrhea = digestiveDiarrhea;
        this.digestiveConstipation = digestiveConstipation;
        this.digestiveBloated = digestiveBloated;
        this.digestiveBelching = digestiveBelching;
        this.digestiveHeartburn = digestiveHeartburn;
        this.digestivePain = digestivePain;
        this.jointsPain = jointsPain;
        this.jointsArthritis = jointsArthritis;
        this.jointsStiffness = jointsStiffness;
        this.jointsMusclepain = jointsMusclepain;
        this.jointsWeakness = jointsWeakness;
        this.weightBinge = weightBinge;
        this.weightCraving = weightCraving;
        this.weightExcessive = weightExcessive;
        this.weightCompulsive = weightCompulsive;
        this.weightWater = weightWater;
        this.weightUnderweight = weightUnderweight;
        this.energyFatigue = energyFatigue;
        this.energyApathy = energyApathy;
        this.energyHyperactivity = energyHyperactivity;
        this.energyRestlessness = energyRestlessness;
        this.mindMemory = mindMemory;
        this.mindConfusion = mindConfusion;
        this.mindConcentration = mindConcentration;
        this.mindCoordination = mindCoordination;
        this.mindDecisions = mindDecisions;
        this.mindStuttering = mindStuttering;
        this.mindSlurred = mindSlurred;
        this.mindLearning = mindLearning;
        this.emotionsSwings = emotionsSwings;
        this.emotionsAnxiety = emotionsAnxiety;
        this.emotionsAnger = emotionsAnger;
        this.emotionsDepression = emotionsDepression;
        this.otherIllness = otherIllness;
        this.otherUrination = otherUrination;
        this.otherGenital = otherGenital;
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

    public Integer getEarsItchy() {
        return earsItchy;
    }

    public void setEarsItchy(Integer earsItchy) {
        this.earsItchy = earsItchy;
    }

    public Integer getEarsEaraches() {
        return earsEaraches;
    }

    public void setEarsEaraches(Integer earsEaraches) {
        this.earsEaraches = earsEaraches;
    }

    public Integer getEarsDrainage() {
        return earsDrainage;
    }

    public void setEarsDrainage(Integer earsDrainage) {
        this.earsDrainage = earsDrainage;
    }

    public Integer getEarsRinging() {
        return earsRinging;
    }

    public void setEarsRinging(Integer earsRinging) {
        this.earsRinging = earsRinging;
    }

    public Integer getNoseStuffy() {
        return noseStuffy;
    }

    public void setNoseStuffy(Integer noseStuffy) {
        this.noseStuffy = noseStuffy;
    }

    public Integer getNoseSinus() {
        return noseSinus;
    }

    public void setNoseSinus(Integer noseSinus) {
        this.noseSinus = noseSinus;
    }

    public Integer getNoseHay() {
        return noseHay;
    }

    public void setNoseHay(Integer noseHay) {
        this.noseHay = noseHay;
    }

    public Integer getNoseSneezing() {
        return noseSneezing;
    }

    public void setNoseSneezing(Integer noseSneezing) {
        this.noseSneezing = noseSneezing;
    }

    public Integer getNoseMucus() {
        return noseMucus;
    }

    public void setNoseMucus(Integer noseMucus) {
        this.noseMucus = noseMucus;
    }

    public Integer getMouthCoughing() {
        return mouthCoughing;
    }

    public void setMouthCoughing(Integer mouthCoughing) {
        this.mouthCoughing = mouthCoughing;
    }

    public Integer getMouthGagging() {
        return mouthGagging;
    }

    public void setMouthGagging(Integer mouthGagging) {
        this.mouthGagging = mouthGagging;
    }

    public Integer getMouthSore() {
        return mouthSore;
    }

    public void setMouthSore(Integer mouthSore) {
        this.mouthSore = mouthSore;
    }

    public Integer getMouthSwollen() {
        return mouthSwollen;
    }

    public void setMouthSwollen(Integer mouthSwollen) {
        this.mouthSwollen = mouthSwollen;
    }

    public Integer getMouthCanker() {
        return mouthCanker;
    }

    public void setMouthCanker(Integer mouthCanker) {
        this.mouthCanker = mouthCanker;
    }

    public Integer getSkinAcne() {
        return skinAcne;
    }

    public void setSkinAcne(Integer skinAcne) {
        this.skinAcne = skinAcne;
    }

    public Integer getSkinHives() {
        return skinHives;
    }

    public void setSkinHives(Integer skinHives) {
        this.skinHives = skinHives;
    }

    public Integer getSkinHairloss() {
        return skinHairloss;
    }

    public void setSkinHairloss(Integer skinHairloss) {
        this.skinHairloss = skinHairloss;
    }

    public Integer getSkinFlushing() {
        return skinFlushing;
    }

    public void setSkinFlushing(Integer skinFlushing) {
        this.skinFlushing = skinFlushing;
    }

    public Integer getSkinSweating() {
        return skinSweating;
    }

    public void setSkinSweating(Integer skinSweating) {
        this.skinSweating = skinSweating;
    }

    public Integer getHeartIrregular() {
        return heartIrregular;
    }

    public void setHeartIrregular(Integer heartIrregular) {
        this.heartIrregular = heartIrregular;
    }

    public Integer getHeartRapid() {
        return heartRapid;
    }

    public void setHeartRapid(Integer heartRapid) {
        this.heartRapid = heartRapid;
    }

    public Integer getHeartChestpain() {
        return heartChestpain;
    }

    public void setHeartChestpain(Integer heartChestpain) {
        this.heartChestpain = heartChestpain;
    }

    public Integer getLungsCongestion() {
        return lungsCongestion;
    }

    public void setLungsCongestion(Integer lungsCongestion) {
        this.lungsCongestion = lungsCongestion;
    }

    public Integer getLungsAsthma() {
        return lungsAsthma;
    }

    public void setLungsAsthma(Integer lungsAsthma) {
        this.lungsAsthma = lungsAsthma;
    }

    public Integer getLungsShortness() {
        return lungsShortness;
    }

    public void setLungsShortness(Integer lungsShortness) {
        this.lungsShortness = lungsShortness;
    }

    public Integer getLungsDifficulty() {
        return lungsDifficulty;
    }

    public void setLungsDifficulty(Integer lungsDifficulty) {
        this.lungsDifficulty = lungsDifficulty;
    }

    public Integer getDigestiveNausea() {
        return digestiveNausea;
    }

    public void setDigestiveNausea(Integer digestiveNausea) {
        this.digestiveNausea = digestiveNausea;
    }

    public Integer getDigestiveDiarrhea() {
        return digestiveDiarrhea;
    }

    public void setDigestiveDiarrhea(Integer digestiveDiarrhea) {
        this.digestiveDiarrhea = digestiveDiarrhea;
    }

    public Integer getDigestiveConstipation() {
        return digestiveConstipation;
    }

    public void setDigestiveConstipation(Integer digestiveConstipation) {
        this.digestiveConstipation = digestiveConstipation;
    }

    public Integer getDigestiveBloated() {
        return digestiveBloated;
    }

    public void setDigestiveBloated(Integer digestiveBloated) {
        this.digestiveBloated = digestiveBloated;
    }

    public Integer getDigestiveBelching() {
        return digestiveBelching;
    }

    public void setDigestiveBelching(Integer digestiveBelching) {
        this.digestiveBelching = digestiveBelching;
    }

    public Integer getDigestiveHeartburn() {
        return digestiveHeartburn;
    }

    public void setDigestiveHeartburn(Integer digestiveHeartburn) {
        this.digestiveHeartburn = digestiveHeartburn;
    }

    public Integer getDigestivePain() {
        return digestivePain;
    }

    public void setDigestivePain(Integer digestivePain) {
        this.digestivePain = digestivePain;
    }

    public Integer getJointsPain() {
        return jointsPain;
    }

    public void setJointsPain(Integer jointsPain) {
        this.jointsPain = jointsPain;
    }

    public Integer getJointsArthritis() {
        return jointsArthritis;
    }

    public void setJointsArthritis(Integer jointsArthritis) {
        this.jointsArthritis = jointsArthritis;
    }

    public Integer getJointsStiffness() {
        return jointsStiffness;
    }

    public void setJointsStiffness(Integer jointsStiffness) {
        this.jointsStiffness = jointsStiffness;
    }

    public Integer getJointsMusclepain() {
        return jointsMusclepain;
    }

    public void setJointsMusclepain(Integer jointsMusclepain) {
        this.jointsMusclepain = jointsMusclepain;
    }

    public Integer getJointsWeakness() {
        return jointsWeakness;
    }

    public void setJointsWeakness(Integer jointsWeakness) {
        this.jointsWeakness = jointsWeakness;
    }

    public Integer getWeightBinge() {
        return weightBinge;
    }

    public void setWeightBinge(Integer weightBinge) {
        this.weightBinge = weightBinge;
    }

    public Integer getWeightCraving() {
        return weightCraving;
    }

    public void setWeightCraving(Integer weightCraving) {
        this.weightCraving = weightCraving;
    }

    public Integer getWeightExcessive() {
        return weightExcessive;
    }

    public void setWeightExcessive(Integer weightExcessive) {
        this.weightExcessive = weightExcessive;
    }

    public Integer getWeightCompulsive() {
        return weightCompulsive;
    }

    public void setWeightCompulsive(Integer weightCompulsive) {
        this.weightCompulsive = weightCompulsive;
    }

    public Integer getWeightWater() {
        return weightWater;
    }

    public void setWeightWater(Integer weightWater) {
        this.weightWater = weightWater;
    }

    public Integer getWeightUnderweight() {
        return weightUnderweight;
    }

    public void setWeightUnderweight(Integer weightUnderweight) {
        this.weightUnderweight = weightUnderweight;
    }

    public Integer getEnergyFatigue() {
        return energyFatigue;
    }

    public void setEnergyFatigue(Integer energyFatigue) {
        this.energyFatigue = energyFatigue;
    }

    public Integer getEnergyApathy() {
        return energyApathy;
    }

    public void setEnergyApathy(Integer energyApathy) {
        this.energyApathy = energyApathy;
    }

    public Integer getEnergyHyperactivity() {
        return energyHyperactivity;
    }

    public void setEnergyHyperactivity(Integer energyHyperactivity) {
        this.energyHyperactivity = energyHyperactivity;
    }

    public Integer getEnergyRestlessness() {
        return energyRestlessness;
    }

    public void setEnergyRestlessness(Integer energyRestlessness) {
        this.energyRestlessness = energyRestlessness;
    }

    public Integer getMindMemory() {
        return mindMemory;
    }

    public void setMindMemory(Integer mindMemory) {
        this.mindMemory = mindMemory;
    }

    public Integer getMindConfusion() {
        return mindConfusion;
    }

    public void setMindConfusion(Integer mindConfusion) {
        this.mindConfusion = mindConfusion;
    }

    public Integer getMindConcentration() {
        return mindConcentration;
    }

    public void setMindConcentration(Integer mindConcentration) {
        this.mindConcentration = mindConcentration;
    }

    public Integer getMindCoordination() {
        return mindCoordination;
    }

    public void setMindCoordination(Integer mindCoordination) {
        this.mindCoordination = mindCoordination;
    }

    public Integer getMindDecisions() {
        return mindDecisions;
    }

    public void setMindDecisions(Integer mindDecisions) {
        this.mindDecisions = mindDecisions;
    }

    public Integer getMindStuttering() {
        return mindStuttering;
    }

    public void setMindStuttering(Integer mindStuttering) {
        this.mindStuttering = mindStuttering;
    }

    public Integer getMindSlurred() {
        return mindSlurred;
    }

    public void setMindSlurred(Integer mindSlurred) {
        this.mindSlurred = mindSlurred;
    }

    public Integer getMindLearning() {
        return mindLearning;
    }

    public void setMindLearning(Integer mindLearning) {
        this.mindLearning = mindLearning;
    }

    public Integer getEmotionsSwings() {
        return emotionsSwings;
    }

    public void setEmotionsSwings(Integer emotionsSwings) {
        this.emotionsSwings = emotionsSwings;
    }

    public Integer getEmotionsAnxiety() {
        return emotionsAnxiety;
    }

    public void setEmotionsAnxiety(Integer emotionsAnxiety) {
        this.emotionsAnxiety = emotionsAnxiety;
    }

    public Integer getEmotionsAnger() {
        return emotionsAnger;
    }

    public void setEmotionsAnger(Integer emotionsAnger) {
        this.emotionsAnger = emotionsAnger;
    }

    public Integer getEmotionsDepression() {
        return emotionsDepression;
    }

    public void setEmotionsDepression(Integer emotionsDepression) {
        this.emotionsDepression = emotionsDepression;
    }

    public Integer getOtherIllness() {
        return otherIllness;
    }

    public void setOtherIllness(Integer otherIllness) {
        this.otherIllness = otherIllness;
    }

    public Integer getOtherUrination() {
        return otherUrination;
    }

    public void setOtherUrination(Integer otherUrination) {
        this.otherUrination = otherUrination;
    }

    public Integer getOtherGenital() {
        return otherGenital;
    }

    public void setOtherGenital(Integer otherGenital) {
        this.otherGenital = otherGenital;
    }

}

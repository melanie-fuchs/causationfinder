package ch.yoursource.causationfinder.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import ch.yoursource.causationfinder.entity.MedicalSymptomsQuestionnaire;
import ch.yoursource.causationfinder.entity.User;

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

    public MedicalSymptomsQuestionnaireDto() {
    }

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
        this.earsItchy = msq.getEarsItchy();
        this.earsEaraches = msq.getEarsEaraches();
        this.earsDrainage = msq.getEarsDrainage();
        this.earsRinging = msq.getEarsRinging();
        this.noseStuffy = msq.getNoseStuffy();
        this.noseSinus = msq.getNoseSinus();
        this.noseHay = msq.getNoseHay();
        this.noseSneezing = msq.getNoseSneezing();
        this.noseMucus = msq.getNoseMucus();
        this.mouthCoughing = msq.getMouthCoughing();
        this.mouthGagging = msq.getMouthGagging();
        this.mouthSwollen = msq.getMouthSwollen();
        this.mouthCanker = msq.getMouthCanker();
        this.skinAcne = msq.getSkinAcne();
        this.skinHives = msq.getSkinHives();
        this.skinHairloss = msq.getSkinHairloss();
        this.skinFlushing = msq.getSkinFlushing();
        this.skinSweating =msq.getSkinSweating();
        this.heartIrregular = msq.getHeartIrregular();
        this.heartRapid = msq.getHeartRapid();
        this.heartChestpain = msq.getHeartChestpain();
        this.lungsCongestion = msq.getLungsCongestion();
        this.lungsAsthma = msq.getLungsAsthma();
        this.lungsShortness = msq.getLungsShortness();
        this.lungsDifficulty = msq.getLungsDifficulty();
        this.digestiveNausea = msq.getDigestiveNausea();
        this.digestiveDiarrhea = msq.getDigestiveDiarrhea();
        this.digestiveConstipation = msq.getDigestiveConstipation();
        this.digestiveBloated = msq.getDigestiveBloated();
        this.digestiveBelching = msq.getDigestiveBelching();
        this.digestiveHeartburn = msq.getDigestiveHeartburn();
        this.digestivePain = msq.getDigestivePain();
        this.jointsPain = msq.getJointsPain();
        this.jointsArthritis = msq.getJointsArthritis();
        this.jointsStiffness = msq.getJointsStiffness();
        this.jointsMusclepain = msq.getJointsMusclepain();
        this.jointsWeakness = msq.getJointsWeakness();
        this.weightBinge = msq.getWeightBinge();
        this.weightCraving = msq.getWeightCraving();
        this.weightExcessive = msq.getWeightExcessive();
        this.weightCompulsive = msq.getWeightCompulsive();
        this.weightWater = msq.getWeightWater();
        this.weightUnderweight = msq.getWeightUnderweight();
        this.energyFatigue = msq.getEnergyFatigue();
        this.energyApathy = msq.getEnergyApathy();
        this.energyHyperactivity = msq.getEnergyHyperactivity();
        this.energyRestlessness = msq.getEnergyRestlessness();
        this.mindMemory = msq.getMindMemory();
        this.mindConfusion = msq.getMindConfusion();
        this.mindConcentration = msq.getMindConcentration();
        this.mindCoordination = msq.getMindCoordination();
        this.mindDecisions = msq.getMindDecisions();
        this.mindStuttering = msq.getMindStuttering();
        this.mindSlurred = msq.getMindSlurred();
        this.mindLearning = msq.getMindLearning();
        this.emotionsSwings = msq.getEmotionsSwings();
        this.emotionsAnxiety = msq.getEmotionsAnxiety();
        this.emotionsAnger = msq.getEmotionsAnger();
        this.emotionsDepression = msq.getEmotionsDepression();
        this.otherIllness = msq.getOtherIllness();
        this.otherUrination = msq.getOtherUrination();
        this.otherGenital = msq.getOtherGenital();
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

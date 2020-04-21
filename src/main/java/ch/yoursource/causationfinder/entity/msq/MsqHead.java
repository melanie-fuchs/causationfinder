package ch.yoursource.causationfinder.entity.msq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class MsqHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private int id;
    
    @NotNull
    @Column(name="headaches")
    private int headaches;
    
    @NotNull
    @Column(name="faintness")
    private int faintness;
    
    @NotNull
    @Column(name="dizziness")
    private int dizziness;
    
    @NotNull
    @Column(name="insomnia")
    private int insomnia;

    public MsqHead(@NotNull int headaches, @NotNull int faintness, @NotNull int dizziness, @NotNull int insomnia) {
        this.headaches = headaches;
        this.faintness = faintness;
        this.dizziness = dizziness;
        this.insomnia = insomnia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeadaches() {
        return headaches;
    }

    public void setHeadaches(int headaches) {
        this.headaches = headaches;
    }

    public int getFaintness() {
        return faintness;
    }

    public void setFaintness(int faintness) {
        this.faintness = faintness;
    }

    public int getDizziness() {
        return dizziness;
    }

    public void setDizziness(int dizziness) {
        this.dizziness = dizziness;
    }

    public int getInsomnia() {
        return insomnia;
    }

    public void setInsomnia(int insomnia) {
        this.insomnia = insomnia;
    }
    
    
}

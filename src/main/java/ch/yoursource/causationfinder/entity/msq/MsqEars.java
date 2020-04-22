package ch.yoursource.causationfinder.entity.msq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class MsqEars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private int id;

    @NotNull
    @Column(name="itchy")
    private int itchy;

    @NotNull
    @Column(name="earchaes")
    private int earchaes;

    @NotNull
    @Column(name="drainage")
    private int drainage;

    @NotNull
    @Column(name="ringing")
    private int ringing;

    public MsqEars() {
        this.setDrainage(5);
        this.setEarchaes(5);
        this.setItchy(5);
        this.setRinging(5);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItchy() {
        return itchy;
    }

    public void setItchy(int itchy) {
        this.itchy = itchy;
    }

    public int getEarchaes() {
        return earchaes;
    }

    public void setEarchaes(int earchaes) {
        this.earchaes = earchaes;
    }

    public int getDrainage() {
        return drainage;
    }

    public void setDrainage(int drainage) {
        this.drainage = drainage;
    }

    public int getRinging() {
        return ringing;
    }

    public void setRinging(int ringing) {
        this.ringing = ringing;
    }
}

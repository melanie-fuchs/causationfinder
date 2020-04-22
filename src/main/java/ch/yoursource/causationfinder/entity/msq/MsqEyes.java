package ch.yoursource.causationfinder.entity.msq;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class MsqEyes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="id")
    private int id;
    
    @NotNull
    @Column(name="watery")
    private int watery;
    
    @NotNull
    @Column(name="swollen")
    private int swollen;
    
    @NotNull
    @Column(name="bags")
    private int bags;
    
    @NotNull
    @Column(name="blurred")
    private int blurred;

    public MsqEyes() {
        this.setBags(0);
        this.setBlurred(0);
        this.setWatery(0);
        this.setSwollen(0);
    }
    
    
    public MsqEyes(@NotNull int watery, @NotNull int swollen, @NotNull int bags, @NotNull int blurred) {
        this.watery = watery;
        this.swollen = swollen;
        this.bags = bags;
        this.blurred = blurred;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getWatery() {
        return watery;
    }
    public void setWatery(int watery) {
        this.watery = watery;
    }
    public int getSwollen() {
        return swollen;
    }
    public void setSwollen(int swollen) {
        this.swollen = swollen;
    }
    public int getBags() {
        return bags;
    }
    public void setBags(int bags) {
        this.bags = bags;
    }
    public int getBlurred() {
        return blurred;
    }
    public void setBlurred(int blurred) {
        this.blurred = blurred;
    }
    
    
    
}

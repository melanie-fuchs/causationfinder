package ch.yoursource.causationfinder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ch.yoursource.causationfinder.setup.ParameterType;

@Entity
@Table(name = "predefined_parameter")
public class PredefinedParameter {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotNull
    @Enumerated(EnumType.STRING)
	@Column(name = "type")
	private ParameterType type;
	
	@NotNull
	@Column(name = "param_name_de")
	private String paramNameDe;

	@Lob
    @Column(name = "description_de")
    private String descriptionDe;
    
    @NotNull
    @Column(name = "param_name_en")
    private String paramNameEn;

    @Lob
    @Column(name = "description_en")
    private String descriptionEn;
    
    @Column(name="min_value")
    private Double minValue;
    
    @Column(name="max_value")
    private Double maxValue;

	public PredefinedParameter() {}

	public PredefinedParameter(
	        @NotNull ParameterType type,
	        @NotNull String paramNameDe,
	        String descriptionDe,
	        @NotNull String paramNameEn,
	        String descriptionEn,
	        Double minValue,
	        Double maxValue) {
		this.type = type;
		this.paramNameDe = paramNameDe;
		this.descriptionDe = descriptionDe;
        this.paramNameEn = paramNameEn;
        this.descriptionEn = descriptionEn;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParameterType getType() {
		return type;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}

	public String getParamNameDe() {
		return paramNameDe;
	}

	public void setParamNameDe(String paramNameDe) {
		this.paramNameDe = paramNameDe;
	}

	public String getDescriptionDe() {
        return descriptionDe;
    }

    public void setDescriptionDe(String descriptionDe) {
        this.descriptionDe = descriptionDe;
    }

    public String getParamNameEn() {
        return paramNameEn;
    }

    public void setParamNameEn(String paramNameEn) {
        this.paramNameEn = paramNameEn;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }
    
    
    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return "PredefinedParameter [id=" + id + ", type=" + type + ", paramName=" + paramNameEn + ", description="
                + descriptionEn + ", minValue=" + minValue + ", maxValue=" + maxValue + "]";
    }
}

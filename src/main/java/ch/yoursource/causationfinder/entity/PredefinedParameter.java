package ch.yoursource.causationfinder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name = "param_name")
	private String paramName;

    @Column(name = "description")
    private String description;

	public PredefinedParameter() {}

	public PredefinedParameter(@NotNull ParameterType type, @NotNull String paramName, String description) {
		this.type = type;
		this.paramName = paramName;
		this.description = description;
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

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PredefinedParameter [id=" + id + ", type=" + type + ", paramName=" + paramName + ", description="
                + description + "]";
    }

}

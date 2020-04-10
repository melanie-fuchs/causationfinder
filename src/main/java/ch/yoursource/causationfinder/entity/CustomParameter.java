package ch.yoursource.causationfinder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ch.yoursource.causationfinder.setup.ParameterType;

@Entity
@Table(name = "custom_parameter")
public class CustomParameter {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private ParameterType type;
	
	@NotNull
	@Column(name = "param_name")
	private String paramName;
	
	@ManyToOne
	@JoinColumn(name = "predefined_param_id", nullable = true)
	private PredefinedParameter predefinedParam;
	
    @Column(name = "description")
    private String description;
	
	@NotNull
	@Column(name = "active")
	private boolean active;

	public CustomParameter() {}

	public CustomParameter(User user, @NotNull ParameterType type, @NotNull String paramName,
            PredefinedParameter predefinedParam, String description, @NotNull boolean active) {
        this.user = user;
        this.type = type;
        this.paramName = paramName;
        this.predefinedParam = predefinedParam;
        this.description = description;
        this.active = active;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public PredefinedParameter getPredefinedParam() {
		return predefinedParam;
	}

	public void setPredefinedParam(PredefinedParameter predefinedParam) {
		this.predefinedParam = predefinedParam;
	}
	
	public boolean isActive() {
        return active;
    }
	
	public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CustomParameter [id=" + id + ", user=" + user + ", type=" + type + ", paramName=" + paramName
                + ", predefinedParam=" + predefinedParam + ", description=" + description + ", active=" + active + "]";
    }
}

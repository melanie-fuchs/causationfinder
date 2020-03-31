package ch.yoursource.causationfinder.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "observed_day_value")
public class ObservedDayValue {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotNull
	@Column(name = "date")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "custom_parameter_id", nullable = false)
	private CustomParameter customParameter;
			
	@Column(name = "numeric_value")
	private double numericValue;
	
	@Column(name = "boolean_value")
	private boolean booleanValue;

	@Column(name = "string_value")
	private String stringValue;
	
	public ObservedDayValue() {}

	public ObservedDayValue(@NotNull int id, @NotNull Date date, CustomParameter customParameter, double numericValue,
			boolean booleanValue, String stringValue) {
		this.id = id;
		this.date = date;
		this.customParameter = customParameter;
		this.numericValue = numericValue;
		this.booleanValue = booleanValue;
		this.stringValue = stringValue;
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

	public CustomParameter getCustomParameter() {
		return customParameter;
	}

	public void setCustomParameter(CustomParameter customParameter) {
		this.customParameter = customParameter;
	}

	public double getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(double numericValue) {
		this.numericValue = numericValue;
	}

	public boolean isBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public String isStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	@Override
	public String toString() {
		return "ObservedDayValue [id=" + id + ", date=" + date + ", customParameter=" + customParameter
				+ ", numericValue=" + numericValue + ", booleanValue=" + booleanValue + ", stringValue=" + stringValue
				+ "]";
	}


}

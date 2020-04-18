package ch.yoursource.causationfinder.dto;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import ch.yoursource.causationfinder.entity.CustomParameter;

public class UpdateCustomParameterDto {

    // will be used to identify the CustomParameter that should be changed
    @Id
    @NotNull
    private int id;

    // TODO might implement possibility to change later
    @NotNull
    private String paramName;

    // TODO might implement possibility to change later
    private String description;
    
    private Double minValue;
    
    private Double maxValue;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    public UpdateCustomParameterDto() {
        
    }

    public UpdateCustomParameterDto(CustomParameter customParameter) {
        this.id = customParameter.getId();
        this.paramName = customParameter.getParamName();
        this.description = customParameter.getDescription();
        this.minValue = customParameter.getMinValue();
        this.maxValue = customParameter.getMaxValue();
    }
}

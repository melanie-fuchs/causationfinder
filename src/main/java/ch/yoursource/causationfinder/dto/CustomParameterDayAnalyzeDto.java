package ch.yoursource.causationfinder.dto;

import java.util.ArrayList;
import java.util.List;

/*
 * An instance of this class represents a customParameter with it's minValue and maxValue, the parameterName
 * and a List of all the values per day
 */
public class CustomParameterDayAnalyzeDto {
    private String parameterName;
    private Double minValue;
    private Double maxValue;
    
    // The List contains objects that hold a value and a day
    private List<CustomParameterDayAnalyzeDayValueDto> dailyValues = new ArrayList<CustomParameterDayAnalyzeDayValueDto>();
    
    public String getParameterName() {
        return parameterName;
    }
    
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
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
    
    public List<CustomParameterDayAnalyzeDayValueDto> getDailyValues() {
        return dailyValues;
    }
    
    public void addDailyValue(CustomParameterDayAnalyzeDayValueDto dayValue) {
        this.dailyValues.add(dayValue);
    }
}

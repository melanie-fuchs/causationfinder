package ch.yoursource.causationfinder.dto;

import java.util.ArrayList;
import java.util.List;

/*
 * An instance of this class represents a string-customParameter with its the parameterName
 * and a List of all the Strings per day
 */
public class CustomParameterDayStringDto {
    private String parameterName;
    
    // The List contains objects that hold a String and a day
    private List<CustomParameterDayStringDayValueDto> dailyValues = new ArrayList<CustomParameterDayStringDayValueDto>();
    
    public String getParameterName() {
        return parameterName;
    }
    
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public List<CustomParameterDayStringDayValueDto> getDailyValues() {
        return dailyValues;
    }
    
    public void addDailyValue(CustomParameterDayStringDayValueDto dayValue) {
        this.dailyValues.add(dayValue);
    }
}

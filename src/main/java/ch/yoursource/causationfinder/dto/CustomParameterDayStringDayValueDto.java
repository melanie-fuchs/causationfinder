package ch.yoursource.causationfinder.dto;

import java.time.LocalDate;

/*
 * An instance of this class represents the value on a day for a string-customParameter
 */
public class CustomParameterDayStringDayValueDto {
    private LocalDate date;
    private String value;
    
    public CustomParameterDayStringDayValueDto(LocalDate date, String value) {
        this.date = date;
        this.value = value;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public String getValue() {
        return value;
    }
}

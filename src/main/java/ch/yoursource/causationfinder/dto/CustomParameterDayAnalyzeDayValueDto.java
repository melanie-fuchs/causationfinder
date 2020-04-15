package ch.yoursource.causationfinder.dto;

import java.time.LocalDate;

/*
 * An instance of this class represents the value on a day for a customParameter
 */
public class CustomParameterDayAnalyzeDayValueDto {
    private LocalDate date;
    private Double value;
    
    public CustomParameterDayAnalyzeDayValueDto(LocalDate date, Double value) {
        this.date = date;
        this.value = value;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public Double getValue() {
        return value;
    }
}

package ch.yoursource.causationfinder.serializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ch.yoursource.causationfinder.dto.CustomParameterDayAnalyzeDayValueDto;
import ch.yoursource.causationfinder.dto.CustomParameterDayAnalyzeDto;

public class CustomParameterDayAnalyzeDtoSerializer extends StdSerializer<CustomParameterDayAnalyzeDto> {

    public CustomParameterDayAnalyzeDtoSerializer() {
        this(null);
    }
    
    public CustomParameterDayAnalyzeDtoSerializer(Class<CustomParameterDayAnalyzeDto> t) {
        super(t);
    }

    /*
     * write the parameters in json, structure like this:
     * [
           {
              "parameterName":"Quality Of Sleep",
              "values":[
                 {
                    "date":"2020-04-10",
                    "value":10.0
                 }
              ]
           },
           {
              "parameterName":"TheNewParam",
              "minValue":0.0,
              "maxValue":1.0,
              "values":[
                 {
                    "date":"2020-04-10",
                    "value":0.0
                 }
              ]
           }
        ]
     */
    
    /*
     * will be called as soon as a method like writeValueAsString() is called on the ObjectMapper-object
     */
    @Override
    public void serialize(
            CustomParameterDayAnalyzeDto value,
            JsonGenerator jgen,
            SerializerProvider provider)
            throws IOException, JsonGenerationException {
        
        jgen.writeStartObject();
        jgen.writeStringField("parameterName", value.getParameterName());
        if (value.getMinValue() != null) {
            jgen.writeNumberField("minValue", value.getMinValue());            
        } else {
            jgen.writeNumberField("minValue", this.getLowestValue(value));
        }
        if (value.getMaxValue() != null) {
            jgen.writeNumberField("maxValue", value.getMaxValue());
        } else {
            jgen.writeNumberField("maxValue", this.getHighestValue(value));
        }
        jgen.writeArrayFieldStart("values");
        for (CustomParameterDayAnalyzeDayValueDto v : value.getDailyValues()) {
            jgen.writeStartObject();
            jgen.writeStringField("date", v.getDate().toString());
            jgen.writeNumberField("value", v.getValue());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
    
    // method to return the smallest value the user has ever entered within the date-range
    private Double getHighestValue(CustomParameterDayAnalyzeDto value) {
        HashSet<Double> valuesOfParameter = new HashSet<Double>();
        for (CustomParameterDayAnalyzeDayValueDto v : value.getDailyValues()) {
            valuesOfParameter.add(v.getValue());
        }
        return Collections.max(valuesOfParameter);        
    }

    // method to return the highest value the user has ever entered within the date-range
    private Double getLowestValue(CustomParameterDayAnalyzeDto value) {
        HashSet<Double> valuesOfParameter = new HashSet<Double>();
        for (CustomParameterDayAnalyzeDayValueDto v : value.getDailyValues()) {
            valuesOfParameter.add(v.getValue());
        }
        return Collections.min(valuesOfParameter);        
    }

}

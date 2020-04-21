package ch.yoursource.causationfinder.service;

import java.util.Locale;

import ch.yoursource.causationfinder.dto.UpdateCustomParameterDto;
import ch.yoursource.causationfinder.entity.User;

public interface ParameterService {
    void activateAllPredefinedParameters(User user, Locale locale);
    public void updateParameter(UpdateCustomParameterDto updateCustomParameterDto);
    
}

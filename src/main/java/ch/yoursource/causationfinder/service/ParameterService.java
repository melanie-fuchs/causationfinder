package ch.yoursource.causationfinder.service;

import ch.yoursource.causationfinder.dto.UpdateCustomParameterDto;
import ch.yoursource.causationfinder.entity.User;

public interface ParameterService {
    void activateAllPredefinedParameters(User user);
    public void updateParameter(UpdateCustomParameterDto updateCustomParameterDto);
    
}

package ch.yoursource.causationfinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.PredefinedParameter;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.CustomParameterRepository;
import ch.yoursource.causationfinder.repository.PredefinedParameterRepository;

@Service
public class ParameterServiceImpl implements ParameterService {

    private CustomParameterRepository customParameterRepository;
    private PredefinedParameterRepository predefinedParameterRepository;
    
    @Autowired
    public ParameterServiceImpl(CustomParameterRepository customParameterRepository,
            PredefinedParameterRepository predefinedParameterRepository) {
        this.customParameterRepository = customParameterRepository;
        this.predefinedParameterRepository = predefinedParameterRepository;
    }

    // load all predefined parameters into customparameter for the given user and
    // set all parameters active
    @Override
    public void activateAllPredefinedParameters(User user) {
        List<PredefinedParameter> predefinedParameters = predefinedParameterRepository.findAll();
        
        for (PredefinedParameter p : predefinedParameters) {
            CustomParameter c = new CustomParameter();
            c.setActive(true);
            c.setPredefinedParam(p);
            c.setUser(user);
            c.setType(p.getType());
            c.setParamName(p.getParamName());
            customParameterRepository.save(c);
        }
    }

}

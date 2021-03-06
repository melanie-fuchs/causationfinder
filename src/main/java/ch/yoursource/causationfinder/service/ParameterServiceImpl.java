package ch.yoursource.causationfinder.service;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.yoursource.causationfinder.dto.UpdateCustomParameterDto;
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

    // load all predefined parameters into customParameters for the given user and
    // set all parameters active
    @Override
    public void activateAllPredefinedParameters(User user, Locale locale) {
        List<PredefinedParameter> predefinedParameters = predefinedParameterRepository.findAll();
        
        for (PredefinedParameter p : predefinedParameters) {
            CustomParameter c = new CustomParameter();
            c.setActive(true);
            c.setPredefinedParam(p);
            c.setUser(user);
            c.setType(p.getType());
            c.setMinValue(p.getMinValue());
            c.setMaxValue(p.getMaxValue());
            if (locale == Locale.GERMAN || locale == Locale.GERMANY) {
                c.setParamName(p.getParamNameDe());
                c.setDescription(p.getDescriptionDe());
            } else {
                c.setParamName(p.getParamNameEn());
                c.setDescription(p.getDescriptionEn());
            }
            customParameterRepository.save(c);
        }
    }
    
    public void updateParameter(UpdateCustomParameterDto updateCustomParameterDto) {
        try {
            CustomParameter c = customParameterRepository.findById(updateCustomParameterDto.getId()).orElseThrow();
            c.setDescription(updateCustomParameterDto.getDescription());
            c.setParamName(updateCustomParameterDto.getParamName());
            c.setMinValue(updateCustomParameterDto.getMinValue());
            c.setMaxValue(updateCustomParameterDto.getMaxValue());
            customParameterRepository.save(c);
        } catch (NoSuchElementException e) {
            throw new RuntimeException(e);
        }
    }
}

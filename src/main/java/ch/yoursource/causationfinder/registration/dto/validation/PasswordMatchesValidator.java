package ch.yoursource.causationfinder.registration.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ch.yoursource.causationfinder.registration.annotation.PasswordMatches;
import ch.yoursource.causationfinder.registration.dto.UserRegistrationDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		UserRegistrationDto user = (UserRegistrationDto) value;
		return user.getPassword().equals(user.getValidatingPassword());
	}
}

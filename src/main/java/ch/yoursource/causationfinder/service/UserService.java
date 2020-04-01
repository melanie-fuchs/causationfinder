package ch.yoursource.causationfinder.service;

import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.registration.dto.UserRegistrationDto;

public interface UserService {

	public User registerNewUser(UserRegistrationDto userRegistrationDto) throws EmailExistsException;
}

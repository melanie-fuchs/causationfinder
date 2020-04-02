package ch.yoursource.causationfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.registration.dto.UserRegistrationDto;
import ch.yoursource.causationfinder.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User registerNewUser(UserRegistrationDto userRegistrationDto) throws EmailExistsException {
		
		if (emailExists(userRegistrationDto.getEmail())) {
			throw new EmailExistsException("There is already an account with e-mail address: " + 
					userRegistrationDto.getEmail());
		}
		
		User user = new User();
		user.setUsername(userRegistrationDto.getUsername());
		user.setFirstName(userRegistrationDto.getFirstName());
		user.setLastName(userRegistrationDto.getLastName());
		user.setBirthdate(userRegistrationDto.getBirthdate());
		user.setEmail(userRegistrationDto.getEmail());
		user.setPassword(userRegistrationDto.getPassword());
		
		return userRepository.save(user);
	}

	private boolean emailExists(String email) {
		User user = userRepository.findByEmail(email);
		if(user != null) {
			return true;
		}
		return false;
	}

}

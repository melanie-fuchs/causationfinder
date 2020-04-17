package ch.yoursource.causationfinder.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.yoursource.causationfinder.dto.UpdateUserDto;
import ch.yoursource.causationfinder.entity.Role;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.RoleRepository;
import ch.yoursource.causationfinder.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(
        UserRepository userRepository,
        RoleRepository roleRepository,
        BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
	    this.userRepository = userRepository;
	    this.roleRepository = roleRepository;
	    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public void save(User user) {
	    if (user.getPassword() != null) {
	        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    }
		
		Role userRole = roleRepository.findByName("ROLE_USER");
		
		if (userRole == null) {
			throw new RuntimeException("Default user role not found.");
		}
		
		Set<Role> userRoles = new HashSet<Role>();
		userRoles.add(userRole);
		user.setRoles(userRoles);
		
		userRepository.save(user);
	}
	
	// method needed because spring would encode the already hashed password again
	// when saving the user after registration confirmed
	@Override
	public void update(User user) {
	    userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

    @Override
    public void saveUpdatedUserData(User currentUser, UpdateUserDto updateUserDto) {
        currentUser.setUsername(updateUserDto.getUsername());
        currentUser.setFirstName(updateUserDto.getFirstName());
        currentUser.setLastName(updateUserDto.getLastName());
        currentUser.setEmail(updateUserDto.getEmail());
        currentUser.setBirthdate(updateUserDto.getBirthdate());
        
        userRepository.save(currentUser);
    }
    
    @Override
    public void saveChangedPassword(User currentUser, String newPassword) {
        currentUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        
        userRepository.save(currentUser);
    }

}

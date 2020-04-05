package ch.yoursource.causationfinder.service;

import ch.yoursource.causationfinder.entity.User;

public interface UserService {
	
	void save(User user);
	
	User findByUsername(String username);
	User findByEmail(String email);
}

package ch.yoursource.causationfinder.service;

import ch.yoursource.causationfinder.dto.UpdateUserDto;
import ch.yoursource.causationfinder.entity.User;

public interface UserService {
	
	void save(User user);
    void update(User user);
	void saveUpdatedUserData(User currentUser, UpdateUserDto updateUserDto);
	void saveChangedPassword(User currentUser, String newPassword);
	User findByUsername(String username);
	User findByEmail(String email);
}

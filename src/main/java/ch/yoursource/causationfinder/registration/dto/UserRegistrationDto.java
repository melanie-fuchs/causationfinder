package ch.yoursource.causationfinder.registration.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRegistrationDto {

	@NotNull
	@NotEmpty
	private String username;
	
	@NotEmpty
	@NotNull
	private String email;
	
	@NotNull
	@NotEmpty
	private String password;
	private String validatingPassword;
	
	private String firstName;

	private String lastName;
	
	private Date birthdate;
	

}

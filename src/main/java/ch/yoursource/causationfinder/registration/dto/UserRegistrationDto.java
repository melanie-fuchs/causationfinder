package ch.yoursource.causationfinder.registration.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import ch.yoursource.causationfinder.registration.annotation.PasswordMatches;

@PasswordMatches
public class UserRegistrationDto {

	@NotNull
	@NotEmpty
	private String username;
	
	@NotEmpty
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@NotEmpty
	private String password;
	private String validatingPassword;
	
	private String firstName;

	private String lastName;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date birthdate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidatingPassword() {
		return validatingPassword;
	}

	public void setValidatingPassword(String validatingPassword) {
		this.validatingPassword = validatingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}

package ch.yoursource.causationfinder.service;

public class EmailExistsException extends RuntimeException {

	public EmailExistsException(String message) {
		super(message);
	}

}

package pt.ist.sonet.exception;

import java.io.Serializable;

public class UsernameAlreadyExistsException extends SoNetException implements Serializable{

	private static final long serialVersionUID = 1L;

	private String conflictingUsername;
	
	public UsernameAlreadyExistsException(String conflictingUsername) {
		this.conflictingUsername = conflictingUsername;
	}
	
	public UsernameAlreadyExistsException(){}
	
	public String getConflictingUsername() {
		return this.conflictingUsername;
	}
	
}

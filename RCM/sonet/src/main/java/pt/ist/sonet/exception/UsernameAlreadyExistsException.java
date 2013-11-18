package pt.ist.sonet.exception;

public class UsernameAlreadyExistsException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private String conflictingUsername;
	
	public UsernameAlreadyExistsException(String conflictingUsername) {
		this.conflictingUsername = conflictingUsername;
	}
	
	public String getConflictingName() {
		return this.conflictingUsername;
	}
	
}

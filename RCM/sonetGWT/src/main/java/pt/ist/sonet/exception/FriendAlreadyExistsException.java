package pt.ist.sonet.exception;

public class FriendAlreadyExistsException extends SoNetException {
	
	private static final long serialVersionUID = 1L;

	private String conflictingName;
	
	public FriendAlreadyExistsException (String conflictingName) {
		this.conflictingName = conflictingName;
	}
	
	public FriendAlreadyExistsException (){}
	
	public String getConflictingName() {
		return this.conflictingName;
	}
	

}
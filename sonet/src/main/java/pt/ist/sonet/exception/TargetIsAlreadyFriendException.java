package pt.ist.sonet.exception;

public class TargetIsAlreadyFriendException extends SoNetException{

	private static final long serialVersionUID = 1L;

	private String conflictingName;
	
	public TargetIsAlreadyFriendException (String conflictingName) {
		this.conflictingName = conflictingName;
	}
	
	public TargetIsAlreadyFriendException (){}
	
	public String getConflictingName() {
		return this.conflictingName;
	}	
	
}

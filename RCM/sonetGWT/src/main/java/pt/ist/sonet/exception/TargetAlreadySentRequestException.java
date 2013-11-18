package pt.ist.sonet.exception;

public class TargetAlreadySentRequestException extends SoNetException{
	
	private static final long serialVersionUID = 1L;

	private String conflictingName;
	
	public TargetAlreadySentRequestException (String conflictingName) {
		this.conflictingName = conflictingName;
	}
	
	public TargetAlreadySentRequestException (){}
	
	public String getConflictingName() {
		return this.conflictingName;
	}	

	
	
}

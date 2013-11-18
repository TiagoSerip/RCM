package pt.ist.sonet.exception;

public class YouAlreadySentRequestException extends SoNetException{
	
	private static final long serialVersionUID = 1L;

	private String conflictingName;
	
	public YouAlreadySentRequestException (String conflictingName) {
		this.conflictingName = conflictingName;
	}
	public YouAlreadySentRequestException (){}
	
	public String getConflictingName() {
		return this.conflictingName;
	}	

}
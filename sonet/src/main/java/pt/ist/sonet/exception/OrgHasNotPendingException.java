package pt.ist.sonet.exception;

public class OrgHasNotPendingException extends SoNetException {

	private static final long serialVersionUID = 1L;

	public OrgHasNotPendingException() {
	}
	
	public String reason() {
		return "Organizational agents accept friend requests by default";
	}
}

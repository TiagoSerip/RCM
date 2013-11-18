package pt.ist.sonet.exception;

public class SendRequestForYourselfException extends SoNetException {
	
	private static final long serialVersionUID = 1L;

	public SendRequestForYourselfException() {
	}
	
	public String reason() {
		return "You can't send a friend request for yourself.";
	}
}

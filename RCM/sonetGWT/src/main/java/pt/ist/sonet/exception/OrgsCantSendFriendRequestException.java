package pt.ist.sonet.exception;

public class OrgsCantSendFriendRequestException extends SoNetException {

	private static final long serialVersionUID = 1L;

	public OrgsCantSendFriendRequestException() {
	}
	
	public String reason() {
		return "Organizational agents can not send friend requests so you don't need to see who isn't your friend yet.";
	}
}

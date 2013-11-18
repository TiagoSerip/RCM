package pt.ist.sonet.exception;

public class FriendLimitExceededException extends SoNetException{
	
	private static final long serialVersionUID = 1L;

	private String username;
	
	public FriendLimitExceededException (String username) {
		this.username = username;
	}
	
	public FriendLimitExceededException (){}
	
	public String getUsername() {
		return this.username;
	}	

}
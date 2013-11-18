package pt.ist.sonet.exception;

public class AgentUsernameDoesNotExistsException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private String username;
	
	public AgentUsernameDoesNotExistsException(String user) {
		this.username = user;
	}
	//for serialization
	public AgentUsernameDoesNotExistsException() {
	}
	
	public String getUsername() {
		return username;
	}
	
}

package pt.ist.sonet.exception;

public class YouArentAFriendException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private String agentName;
	
	public YouArentAFriendException(String name) {
		this.agentName = name;
	}
	//for serialization
	public YouArentAFriendException() {
	}
	
	public String reason() {
		return "Agent " + this.agentName + " has a permission of type amigo and you aren't friends";
	}
	
	
}

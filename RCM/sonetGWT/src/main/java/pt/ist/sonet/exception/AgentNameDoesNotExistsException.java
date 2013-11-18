package pt.ist.sonet.exception;

public class AgentNameDoesNotExistsException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private String name;
	
	public AgentNameDoesNotExistsException(String id) {
		this.name = id;
	}
	
	public String getName() {
		return name;
	}
	
}

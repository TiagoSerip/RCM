package pt.ist.sonet.exception;

public class AgentAlreadyBlockedException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private String agentId;
	private int pubId;
	
	public AgentAlreadyBlockedException(String agentId, int pubId) {
		this.agentId = agentId;
		this.pubId = pubId;
		
	}
	
	public String getBlockedId() {
		return "Agent id:"+this.agentId + " Publication id:"+ this.pubId;
	}
	
	
}

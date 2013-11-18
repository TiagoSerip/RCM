package pt.ist.sonet.exception;

public class AlreadyVotedException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private String agentId;
	private int pubId;
	
	public AlreadyVotedException(String agentId, int pubId) {
		this.agentId = agentId;
		this.pubId = pubId;
		
	}

	public AlreadyVotedException() {}

	
	public String getAlreadyVotedId() {
		return "Agent id:"+this.agentId + " Publication id:"+ this.pubId;
	}
	
	
}

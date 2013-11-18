package pt.ist.sonet.exception;

public class AgentsCantVoteInTheirOwnPublicationsException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private String agentName;
	private int pubId;
	
	public AgentsCantVoteInTheirOwnPublicationsException(String agentName, int pubId) {
		this.agentName = agentName;
		this.pubId = pubId;
		
	}
	
	public AgentsCantVoteInTheirOwnPublicationsException(){}
	
	public String getWhoCanVoteId() {
		return "Agent id:"+this.agentName + " Publication id:"+ this.pubId;
	}
	
	
}

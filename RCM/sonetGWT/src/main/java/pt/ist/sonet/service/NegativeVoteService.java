package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;

public class NegativeVoteService extends SonetService {

	private int apId;
	private String agentUser;

	public NegativeVoteService(String user, int ap) {
		apId = ap;
		agentUser = user;
	}	

	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		
		Agent voter = network.getAgentByUsername(agentUser);
		if(voter == null)
			throw new AgentUsernameDoesNotExistsException(agentUser);
		AP ap = network.getApById(apId);
		if(ap == null)
			throw new ApIdDoesNotExistsException(apId);
		
		network.negVote(voter, ap);
		
	}
	
}

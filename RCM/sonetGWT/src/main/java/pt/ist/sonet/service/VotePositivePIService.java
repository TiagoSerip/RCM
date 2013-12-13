package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.PI;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.PIIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;

public class VotePositivePIService extends SonetService {

	private int piId;
	private String agentUser;

	public VotePositivePIService(String user, int pi) {
		piId = pi;
		agentUser = user;
	}	

	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		
		Agent voter = network.getAgentByUsername(agentUser);
		if(voter == null)
			throw new AgentUsernameDoesNotExistsException(agentUser);
		PI pi = network.getPIByID(piId);
		if(pi == null)
			throw new PIIdDoesNotExistsException(piId);
		
		network.posVotePI(voter, pi);
		
	}
	
}

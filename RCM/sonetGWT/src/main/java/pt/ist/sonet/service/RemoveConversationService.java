package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.PI;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.AgentDto;

public class RemoveConversationService extends SonetService {

	private String requesterUser;
	private String otherGuyUser;
	
	public RemoveConversationService(String req, String other) {
		requesterUser = req;
		otherGuyUser = other;
	}
	
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException{
		
		SoNet sonet = FenixFramework.getRoot();
		Agent requester = sonet.getAgentByUsername(requesterUser);
		if(requester == null)
			throw new AgentUsernameDoesNotExistsException(requesterUser);
		Agent otherGuy = sonet.getAgentByUsername(otherGuyUser);
		if(otherGuy == null)
			throw new AgentUsernameDoesNotExistsException(otherGuyUser);
		sonet.getLastConversationWithSomeone(requester, otherGuy).clear();
		sonet.getLastConversationWithSomeone(otherGuy, requester).clear(); //esta na base de dados dos dois

	}
	
}

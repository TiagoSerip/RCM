package pt.ist.sonet.service;

import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Individual;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.OrgsCantSendFriendRequestException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * @author ES Grupo 8
 */
public class GetAllNonFriendsService extends SonetService {
	
	private StringListDto dto;
	private String requesterUser;

	public GetAllNonFriendsService(String requesterUser, StringListDto dto) { 
		this.dto = dto;
		this.requesterUser = requesterUser;
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 */
	@Override
	protected void dispatch() throws SoNetException, OrgsCantSendFriendRequestException, 
		AgentUsernameDoesNotExistsException {

		SoNet network = FenixFramework.getRoot();
		List<Agent> combined = network.allAgents();
		
		if(network.hasOrgByUsername(requesterUser)) {
			throw new OrgsCantSendFriendRequestException();
		}
			
		if(!network.hasIndividualByUsername(requesterUser)) {
			throw new AgentUsernameDoesNotExistsException(requesterUser);
		}
		
		Individual requester = network.getIndividualByUsername(requesterUser);
		
		for(Agent a : combined) {
			if(!requester.isMyFriend(a) && !(a.getUsername().equals(requesterUser))) {
				dto.addTolisting(a.getUsername());
			}
		}
	}
}

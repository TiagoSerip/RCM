package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * @author ES Grupo 8
 */
public class GetFriendsLastPublicationService extends SonetService {
	
	private StringListDto dto;
	private String username;
	
	public GetFriendsLastPublicationService(String user, StringListDto dto) { 
		this.dto = dto;
		this.username = user;
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 */
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		Agent agent = network.getAgentByUsername(this.username);
		if(agent == null){
			throw new AgentUsernameDoesNotExistsException(this.username);
		}
		
		for(Agent a : network.getFriendsOf(agent)) {
			Publication pub = network.getUserLatestPublication(a.getUsername());
			 
			if(pub == null) {		
				dto.addTolisting("Agent " + a.getUsername() + " doesn't have publications.");
			}
			else {		
				dto.addTolisting(pub.toString());
			}
		}
	}

}
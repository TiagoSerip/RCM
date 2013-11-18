package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.PublicationIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.PublicationViewDto;

/**
 * @author ES Grupo 8
 */
public class GetMyLastPublicationService extends SonetService {
	
	private PublicationViewDto pubdto;
	private String username;
	
	public GetMyLastPublicationService(String user) { 
		this.username = user;
		this.pubdto=null;
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
		
		Publication tempPub = network.getUserLatestPublication(agent.getUsername());
		if(tempPub == null) {
			throw new PublicationIdDoesNotExistsException();
		}
		
		this.pubdto = tempPub.createDto();
	}
	
	/**
	 * Devolve a ultima publicacao de um agente utilizando DTOs 
	 * 
	 * @return ArrayList<PublicationViewDto>
	 */
	public PublicationViewDto getPublicationViewDto() {
		return this.pubdto;
	}
	
}
package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.ApDto;

/**
 * Classe GetAllPublicationsService que herda de SonetService. Este servico permite que um agente visualize,
 * caso seja possivel, os detalhes de uma publicacao 
 */
public class GetAgentByUsernameService extends SonetService{
	
	private AgentDto dto;
	private String username;
	
	/**
	 * Construtor
	 * 
	 * @param String asking - username do agente que quer ver as publicacoes
	 * @param int pubid - id da publicacao que se quer ver
	 */
	public GetAgentByUsernameService(String user) {
		this.username = user;	
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException
	 * @throws YouArentAFriendException
	 * @throws ApIdDoesNotExistsException
	 *
	 */
	@Override
	protected void dispatch() throws SoNetException, ApIdDoesNotExistsException{
		SoNet sonet = FenixFramework.getRoot();
		
		Agent a = sonet.getAgentByUsername(username);
		if(a == null)
			throw new AgentUsernameDoesNotExistsException(username);
		
		dto = new AgentDto(a.getUsername(), a.getPassword(), a.getName(), a.getAp().getId(), a.getRssi(), a.getIp());

	}
	
	/**
	 * Devolve a publicacao utilizando DTOs
	 * 
	 * @return PublicationViewDto
	 */
	public AgentDto getDto(){
		return dto;
	}
	
}

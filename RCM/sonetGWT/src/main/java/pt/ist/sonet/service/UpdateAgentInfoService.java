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
public class UpdateAgentInfoService extends SonetService{
	
	private AgentDto dto;
	
	/**
	 * Construtor
	 * 
	 * @param String asking - username do agente que quer ver as publicacoes
	 * @param int pubid - id da publicacao que se quer ver
	 */
	public UpdateAgentInfoService(AgentDto dto) {
		this.dto = dto;	
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
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException{
		SoNet sonet = FenixFramework.getRoot();
		
		Agent a = sonet.getAgentByUsername(dto.getUsername());
		if(a == null)
			throw new AgentUsernameDoesNotExistsException(dto.getUsername());
		
		AP ap = sonet.getApById(dto.getAp());
		if(ap == null)
			throw new ApIdDoesNotExistsException(dto.getAp());
		
		a.setName(dto.getName());
		a.setRssi(dto.getRssi());
		a.setIp(dto.getIp());
		a.setAp(ap);
		
	}
	
}

package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.PI;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.PIIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.PIDto;

/**
 * Classe GetAllPublicationsService que herda de SonetService. Este servico permite que um agente visualize,
 * caso seja possivel, os detalhes de uma publicacao 
 */
public class GetPIByIdService extends SonetService{
	
	private PIDto dto;
	private int piId;
	
	/**
	 * Construtor
	 * 
	 * @param String asking - username do agente que quer ver as publicacoes
	 * @param int pubid - id da publicacao que se quer ver
	 */
	public GetPIByIdService(int piId) {
		this.piId = piId;	
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
		
		PI pi = sonet.getPIByID(piId);
		if(pi == null)
			throw new PIIdDoesNotExistsException(piId);
		
		dto = new PIDto(pi.getId(), pi.getName(), pi.getLocation(), pi.getDescription());

	}
	
	/**
	 * Devolve a publicacao utilizando DTOs
	 * 
	 * @return PublicationViewDto
	 */
	public PIDto getPI(){
		return dto;
	}
	
}
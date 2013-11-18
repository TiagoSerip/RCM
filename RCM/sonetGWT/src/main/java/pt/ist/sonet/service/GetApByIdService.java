package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.ApDto;

/**
 * Classe GetAllPublicationsService que herda de SonetService. Este servico permite que um agente visualize,
 * caso seja possivel, os detalhes de uma publicacao 
 */
public class GetApByIdService extends SonetService{
	
	private ApDto dto;
	private int apId;
	
	/**
	 * Construtor
	 * 
	 * @param String asking - username do agente que quer ver as publicacoes
	 * @param int pubid - id da publicacao que se quer ver
	 */
	public GetApByIdService(int apId) {
		this.apId = apId;	
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
		
		AP ap = sonet.getApById(apId);
		if(ap == null)
			throw new ApIdDoesNotExistsException(apId);
		
		dto = new ApDto(ap.getId(), ap.getSubnet(), ap.getPosVotes(), ap.getNegVotes());

	}
	
	/**
	 * Devolve a publicacao utilizando DTOs
	 * 
	 * @return PublicationViewDto
	 */
	public ApDto getPublication(){
		return dto;
	}
	
}

package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.YouArentAFriendException;
import pt.ist.sonet.exception.PublicationIdDoesNotExistsException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.PublicationViewDto;

/**
 * Classe GetAllPublicationsService que herda de SonetService. Este servico permite que um agente visualize,
 * caso seja possivel, os detalhes de uma publicacao 
 */
public class GetPublicationByIdService extends SonetService{
	
	private String requester;
	private String requested;
	private PublicationViewDto pubdto;
	private int pubid;
	
	/**
	 * Construtor
	 * 
	 * @param String asking - username do agente que quer ver as publicacoes
	 * @param int pubid - id da publicacao que se quer ver
	 */
	public GetPublicationByIdService(String asking, int pubid) {
		this.requester = asking;
		this.pubid = pubid;	
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException
	 * @throws YouArentAFriendException
	 * @throws PublicationIdDoesNotExistsException
	 *
	 */
	@Override
	protected void dispatch() throws SoNetException, YouArentAFriendException, PublicationIdDoesNotExistsException{
		SoNet sonet = FenixFramework.getRoot();
		
		Publication pub = sonet.getPublicationById(pubid);
		if(pub == null)
			throw new PublicationIdDoesNotExistsException(pubid);
		
		requested=pub.getAgent().getUsername();
		
		Agent requesterAgent = sonet.getAgentByUsername(requester);
		Agent requestedAgent = sonet.getAgentByUsername(requested);
		
		if(!requestedAgent.getPermission().canAcess(requesterAgent, requestedAgent))
			throw new YouArentAFriendException(requested);

		pubdto = pub.createDto();

	}
	
	/**
	 * Devolve a publicacao utilizando DTOs
	 * 
	 * @return PublicationViewDto
	 */
	public PublicationViewDto getPublication(){
		return pubdto;
	}
	
}
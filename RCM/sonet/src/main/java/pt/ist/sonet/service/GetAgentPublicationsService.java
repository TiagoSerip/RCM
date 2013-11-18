package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.YouArentAFriendException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.PublicationDto;
import pt.ist.sonet.service.dto.PublicationListDto;

/**
 * Classe GetAllPublicationsService que herda de SonetService. Este servico permite que um agente visualize,
 * caso seja possivel, as publicacoes de outro agente 
 */
public class GetAgentPublicationsService extends SonetService{

	private String requester;
	private String requested;
	private PublicationListDto allpubs;
	
	/**
	 * Construtor
	 * 
	 * @param String agentreq - username do agente que quer ver as publicacoes
	 * @param String agentsend - username do agente detentor das publicacoes
	 * @param ListingDto
	 */
	public GetAgentPublicationsService(String from, String asking, PublicationListDto pubdto){
		allpubs=pubdto;
		requested=from;
		requester=asking;
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException
	 * @throws YouArentAFriendException
	 */
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, YouArentAFriendException {
		SoNet sonet = FenixFramework.getRoot();
		
		Agent requesterAgent = sonet.getAgentByUsername(requester);
		Agent requestedAgent = sonet.getAgentByUsername(requested);

		if(requesterAgent== null)
			throw new AgentUsernameDoesNotExistsException(requester);
		if(requestedAgent== null)
			throw new AgentUsernameDoesNotExistsException(requested);
		if(!requestedAgent.getPermission().canAcess(requesterAgent, requestedAgent))
			throw new YouArentAFriendException(requested);
		for(Publication pub : sonet.getUserPublicationsByUsername(requested)){
			PublicationDto dto = new PublicationDto(pub.getId(), pub.getAgent().getUsername(), pub.getLabel(), 
						pub.getPosVotes(), pub.getNegVotes(), pub.getCommentsCount());
			allpubs.addTolisting(dto);
		}
	}
	
}
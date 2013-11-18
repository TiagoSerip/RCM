package pt.ist.sonet.service;

import java.util.ArrayList;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.YouArentAFriendException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.PublicationViewDto;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * Classe GetAllPublicationsService que herda de SonetService. Este servico permite que um agente visualize,
 * caso seja possivel, as publicacoes de outro agente 
 */
public class GetAllPublicationsService extends SonetService{

	private StringListDto pubdto;
	private String requester;
	private String requested;
	private ArrayList<Publication> allpubs;
	private ArrayList<PublicationViewDto> pubListDto;
	
	/**
	 * Construtor
	 * 
	 * @param String agentreq - username do agente que quer ver as publicacoes
	 * @param String agentsend - username do agente detentor das publicacoes
	 * @param ListingDto
	 */
	public GetAllPublicationsService(String from, String asking, StringListDto pubdto){
		this.pubdto=pubdto;
		this.requested=from;
		this.requester=asking;
		this.allpubs=new ArrayList<Publication>();
		this.pubListDto=new ArrayList<PublicationViewDto>();
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
			pubdto.addTolisting(pub.toStringWithComments()+"\n");
			allpubs.add(pub);
			this.pubListDto.add(pub.createDto());
		}
	}
	
	/**
	 * Devolve todas as publicacoes existentes. (USADO APENAS NUM DOS TESTES JUNIT) 
	 * 
	 * @return ArrayList<Publication>
	 */
	public ArrayList<Publication> getPublications(){
		return allpubs;
	}
	
	/**
	 * Devolve todas as publicacoes existentes utilizando DTOs. (USADO APENAS NUM DOS TESTES JUNIT) 
	 * 
	 * @return ArrayList<PublicationViewDto>
	 */
	public ArrayList<PublicationViewDto> getAllPublicationsViewDto(){
		return this.pubListDto;
	}

	/**
	 * Devolve todas as publicacoes existentes utilizando DTOs
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getListing(){
		return pubdto.getlisting();
	}
}
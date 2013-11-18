package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.PublicationIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;

/**
 * Classe PositiveVoteService que herda de SonetService. Este servico permite que um agente
 * vote na publicacao de um outro agente (desde que tenha permissao para isso)
 */
public class PositiveVoteService extends SonetService {

	private int pubID;
	private String agentUser;
	
	/**
	 * Construtor
	 * 
	 * @param String user - username do agente que quer votar na publicacao
	 * @param int pubId - identificador da publicacao
	 */
	public PositiveVoteService(String user, int pubId) {
		pubID = pubId;
		agentUser = user;
	}	
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException
	 */
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		
		Agent voter = network.getAgentByUsername(agentUser);
		if(voter == null)
			throw new AgentUsernameDoesNotExistsException(agentUser);
		Publication pub = network.getPublicationById(pubID);
		if(pub == null)
			throw new PublicationIdDoesNotExistsException(pubID);
		
		network.posVote(voter, pub);
		
	}
	
}
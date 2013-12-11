package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.PI;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.PIIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;

/**
 * Classe NegativeVoteService que herda de SonetService. Este servico permite que um agente
 * vote na publicacao de um outro agente (desde que tenha permissao para isso)
 */
public class VoteNegativePIService extends SonetService {

	private int piId;
	private String agentUser;
	
	/**
	 * Construtor
	 * 
	 * @param String user - username do agente que quer votar na publicacao
	 * @param int pubId - identificador da publicacao
	 */
	public VoteNegativePIService(String user, int pi) {
		piId = pi;
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
		PI pi = network.getPIByID(piId);
		if(pi == null)
			throw new PIIdDoesNotExistsException(piId);
		
		network.negVotePI(voter, pi);
		
	}
	
}

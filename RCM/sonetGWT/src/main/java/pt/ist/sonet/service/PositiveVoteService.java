package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;

/**
 * Classe PositiveVoteService que herda de SonetService. Este servico permite que um agente
 * vote na publicacao de um outro agente (desde que tenha permissao para isso)
 */
public class PositiveVoteService extends SonetService {

	private int apId;
	private String agentUser;
	
	/**
	 * Construtor
	 * 
	 * @param String user - username do agente que quer votar na publicacao
	 * @param int pubId - identificador da publicacao
	 */
	public PositiveVoteService(String user, int ap) {
		apId = ap;
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
		AP ap = network.getApById(apId);
		if(ap == null)
			throw new ApIdDoesNotExistsException(apId);
		
		network.posVote(voter, ap);
		
	}
	
}

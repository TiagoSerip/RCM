/**
 * 
 */
package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Individual;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.FriendLimitExceededException;
import pt.ist.sonet.exception.SoNetException;

/**
 * @author ES Grupo 8
 */
public class ConfirmFriendRequestService extends SonetService {

	private String fromUser;
	private String toUser;
	
	/**
	 * Construtor
	 * 
	 * @param String from
	 * @param String to
	 */
	public ConfirmFriendRequestService(String from, String to) {
		this.fromUser = from;
		this.toUser = to;
	}	
	
	/**
	 * Faz o dispach do servico.
	 *
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExists
	 */
	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, FriendLimitExceededException {
		
		SoNet rede = FenixFramework.getRoot();
		Individual requester = (Individual) rede.getAgentByUsername(fromUser);
		if(requester == null)
			throw new AgentUsernameDoesNotExistsException(fromUser);
		Individual requested = (Individual) rede.getAgentByUsername(toUser);
		if(requested == null)
			throw new AgentUsernameDoesNotExistsException(toUser);
		rede.confirmFriendRequest(requester, requested);
	}
		
}

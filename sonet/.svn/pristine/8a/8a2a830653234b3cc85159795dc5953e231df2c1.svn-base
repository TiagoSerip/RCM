package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Individual;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.FriendAlreadyExistsException;
import pt.ist.sonet.exception.FriendLimitExceededException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.TargetAlreadySentRequestException;
import pt.ist.sonet.exception.TargetIsAlreadyFriendException;
import pt.ist.sonet.exception.YouAlreadySentRequestException;

/**
 * @author ES Grupo 8
 */
public class FriendRequestService extends SonetService {

	private String requesterUser;
	private String requestedUser;
	
	/**
	 * Construtor
	 * 
	 * @param String from - username do agente individual que envia o pedido de amizade
	 * @param String to - username do agente (individual ou organizacional) que recebe o pedido de amizade
	 */
	public FriendRequestService(String from, String to) {
		requesterUser = from;
		requestedUser = to;
	}

	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException
	 */
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, TargetAlreadySentRequestException, TargetIsAlreadyFriendException, YouAlreadySentRequestException, FriendLimitExceededException, FriendAlreadyExistsException {

		SoNet rede = FenixFramework.getRoot();
		Individual from = (Individual) rede.getAgentByUsername(requesterUser);
		if(from == null)
			throw new AgentUsernameDoesNotExistsException(requesterUser);
		Agent to = rede.getAgentByUsername(requestedUser);
		if(to == null)
			throw new AgentUsernameDoesNotExistsException(requestedUser);
		rede.friendRequest(from, to);

	}


}


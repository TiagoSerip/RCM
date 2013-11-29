package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.MessageDto;

public class SendPrivateMessageService extends SonetService {
	
	private MessageDto message;
	
	public SendPrivateMessageService(MessageDto m) {
		message = m;
	}
	
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
		SoNet network = FenixFramework.getRoot();
		Agent sender = network.getAgentByUsername(message.getSenderUsername());
		if(sender == null)
			throw new AgentUsernameDoesNotExistsException(message.getSenderUsername());
		Agent receiver = network.getAgentByUsername(message.getReceiverUsername());
		if(receiver == null)
			throw new AgentUsernameDoesNotExistsException(message.getReceiverUsername());
//		try {
			network.talkTo(message.getMessageId(), sender, receiver, message.getText());
//		} catch (YouArentConnected e) {
//			throw new CanNotSendMessageException();
//		}		
	}
	
}

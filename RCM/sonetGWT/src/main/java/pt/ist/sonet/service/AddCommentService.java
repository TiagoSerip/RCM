package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.CanNotCommentException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.CommentDto;

public class AddCommentService extends SonetService {
	
	private CommentDto dto;
	
	public AddCommentService(CommentDto comment) {
		this.dto = comment;
	}
	

	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, CanNotCommentException {
		SoNet network = FenixFramework.getRoot();
		Agent commentator = network.getAgentByUsername(dto.getUser());
		if(commentator == null)
			throw new AgentUsernameDoesNotExistsException(dto.getUser());
		AP ap = network.getApById(dto.getApId());
		if(ap == null)
			throw new ApIdDoesNotExistsException(dto.getApId());
//		try {
			network.commentAp(commentator,ap,dto.getText());
//		} catch (YouArentConnected e) {
//			throw new CanNotCommentException(commentator.getUsername(), dto.getApId(), commentator.getName());
//		}
	}
	
}

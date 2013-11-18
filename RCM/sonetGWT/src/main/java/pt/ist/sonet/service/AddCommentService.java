package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.CanNotCommentException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.YouArentAFriendException;
import pt.ist.sonet.service.dto.CommentDto;

/**
 * @author ES Grupo 8
 */
public class AddCommentService extends SonetService {
	
	private CommentDto dto;
	
	/**
	 * Construtor
	 * 
	 * @param String user - username do agente individual que quer comentar
	 * @param String text - texto do comentario
	 * @param int pubId - identificador da publicacao que se quer comentar
	 */
	public AddCommentService(CommentDto comment) {
		this.dto = comment;
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException
	 * @throws CanNotCommentException
	 */
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, CanNotCommentException {
		SoNet network = FenixFramework.getRoot();
		Agent commentator = network.getAgentByUsername(dto.getUser());
		if(commentator == null)
			throw new AgentUsernameDoesNotExistsException(dto.getUser());
		Publication pub = network.getPublicationById(dto.getPubId());
		if(pub == null)
			throw new ApIdDoesNotExistsException(dto.getPubId());
		try {
			network.commentPublication(commentator,pub, dto.getText());
		} catch (YouArentAFriendException e) {
			throw new CanNotCommentException(commentator.getUsername(), dto.getPubId(), pub.getAgent().getName());
		}
	}
	
}

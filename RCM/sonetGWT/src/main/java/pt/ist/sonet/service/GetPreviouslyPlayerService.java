package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.BoardIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.AgentDto;

public class GetPreviouslyPlayerService extends SonetService{

	private AgentDto dto;
	private int boardId;
	
	public GetPreviouslyPlayerService(int id) {
		boardId = id;
	}	
	
	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, BoardIdDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		Board board = network.getBoardById(boardId);
		if(board == null)
			throw new BoardIdDoesNotExistsException(boardId);
		Agent previously = network.getAgentByUsername(board.getTurn());
		if(previously == null)
			throw new AgentUsernameDoesNotExistsException(board.getTurn());
		
		dto = new AgentDto(previously.getUsername(), previously.getPassword(), previously.getName(), 
				previously.getAp().getId(), previously.getRssi(), previously.getIp());
	}
	
	public AgentDto getPreviously(){
		return dto;
	}

}

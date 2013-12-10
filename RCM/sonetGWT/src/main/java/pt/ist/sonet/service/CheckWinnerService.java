package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.BoardIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.BooleanDto;

public class CheckWinnerService extends SonetService{

	private BooleanDto dto;
	private int boardId;
	private String playerUser;
	
	public CheckWinnerService(int id, BooleanDto dto, String user) {
		this.dto = dto;
		boardId = id;
		playerUser = user;
	}	
	
	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		Agent player = network.getAgentByUsername(playerUser);
		if(player == null)
			throw new AgentUsernameDoesNotExistsException(playerUser);
		Board board = player.getBoardById(boardId);
		if(board == null)
			throw new BoardIdDoesNotExistsException(boardId);
		if(network.checkWinner(board))
			dto.setValue(true);
		else
			dto.setValue(false);
	}

}

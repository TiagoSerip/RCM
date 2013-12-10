package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.BoardIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.AgentDto;

public class GetWinnerService extends SonetService{

	private AgentDto dto;
	private int boardId;
	private String playerUser;
	
	public GetWinnerService(int id, String user) {
		this.playerUser = user;
		boardId = id;
	}	
	
	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, BoardIdDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		Agent player = network.getAgentByUsername(playerUser);
		if(player == null)
			throw new AgentUsernameDoesNotExistsException(playerUser);
		Board board = player.getBoardById(boardId);
		if(board == null)
			throw new BoardIdDoesNotExistsException(boardId);
		Agent winner = network.getWinner(board);
		if(winner == null)
			throw new AgentUsernameDoesNotExistsException(network.getWinner(board).getUsername());
		
		dto = new AgentDto(winner.getUsername(), winner.getPassword(), winner.getName(), 
				winner.getAp().getId(), winner.getRssi(), winner.getIp());
	}
	
	public AgentDto getWinner(){
		return dto;
	}

}

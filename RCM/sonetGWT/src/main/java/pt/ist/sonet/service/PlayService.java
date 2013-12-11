package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.BoardIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;

public class PlayService extends SonetService {
	
	private String playerUsername;
	private int[] jogada;
	private int boardId;
	
	public PlayService(int id, String player, int[] jogada){
		this.playerUsername = player;
		this.jogada = jogada;
		this.boardId = id;		
	}
	
	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, BoardIdDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		Agent player = network.getAgentByUsername(playerUsername);
		if(player == null)
			throw new AgentUsernameDoesNotExistsException(playerUsername);
		Board board = network.getBoardById(boardId);
		if(board == null)
			throw new BoardIdDoesNotExistsException(boardId);
		network.play(board, player, jogada);
	}

}

package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.BoardIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.PlayDto;

public class PlayService extends SonetService {
	
	private PlayDto dto;
	
	public PlayService(PlayDto play) {
		dto = play;
	}
	
	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, BoardIdDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		Agent player = network.getAgentByUsername(dto.getPlayerUsername());
		if(player == null)
			throw new AgentUsernameDoesNotExistsException(dto.getPlayerUsername());
		Board board = player.getBoardById(dto.getBoardId());
		if(board == null)
			throw new BoardIdDoesNotExistsException(dto.getBoardId());
		network.play(board, player, dto.getJogada());
	}

}

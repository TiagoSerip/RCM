package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.BoardDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.BooleanDto;

public class CheckHasBoardService extends SonetService{
	
	private String player1User;
	private String player2User;
	private BooleanDto dto;
	
	public CheckHasBoardService(String player1User, String player2User, BooleanDto dto) {
		this.player1User = player1User;	
		this.player2User = player2User;
		this.dto=dto;
	}
	
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, BoardDoesNotExistsException {

		SoNet network = FenixFramework.getRoot();
		
		Agent player1 = network.getAgentByUsername(player1User);
		if(player1 == null)
			throw new AgentUsernameDoesNotExistsException(player1User);
		Agent player2 = network.getAgentByUsername(player2User);
		if(player2 == null)
			throw new AgentUsernameDoesNotExistsException(player2User);
		
		Board board = network.getBoardByUsers(player1, player2);
		if(board != null)
			dto.setValue(true);
		else
			dto.setValue(false);
		
	//	boardDto = new BoardDto(board.getId(), board.getMatrix());
	}
	
}

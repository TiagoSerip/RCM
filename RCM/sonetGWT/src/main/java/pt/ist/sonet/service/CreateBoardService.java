package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.BoardDto;

public class CreateBoardService extends SonetService{

		private BoardDto dto;
		private int boardId;
		private Board board;
		
		public CreateBoardService(BoardDto boardDto) {
			dto = boardDto;
		}	
		
		@Override
		public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
			
			SoNet network = FenixFramework.getRoot();
			Agent player1 = network.getAgentByUsername(dto.getPlayer1Username());
			if(player1 == null)
				throw new AgentUsernameDoesNotExistsException(dto.getPlayer1Username());
			Agent player2 = network.getAgentByUsername(dto.getPlayer2Username());
			if(player2 == null)
				throw new AgentUsernameDoesNotExistsException(dto.getPlayer2Username());
			
			board = network.createBoard(player1, player2);
			boardId = board.getId();
		}
		
		public int getNewBoardId() {
			return boardId;
		}
}

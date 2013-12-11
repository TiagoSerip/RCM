package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;

public class CreateBoardService extends SonetService{

		private String hostUser;
		private String guestUser;
		private Board board;
		private int boardId;
		
		public CreateBoardService(String host, String guest) {
			this.hostUser = host;
			this.guestUser = guest;
		}	
		
		@Override
		public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
			
			SoNet network = FenixFramework.getRoot();
			Agent host = network.getAgentByUsername(hostUser);
			if(host == null)
				throw new AgentUsernameDoesNotExistsException(hostUser);
			Agent guest = network.getAgentByUsername(guestUser);
			if(guest == null)
				throw new AgentUsernameDoesNotExistsException(guestUser);
			
			board = network.createBoard(host, guest);
			boardId = board.getId();
		}
		
		public int getNewBoardId() {
			return boardId;
		}
}

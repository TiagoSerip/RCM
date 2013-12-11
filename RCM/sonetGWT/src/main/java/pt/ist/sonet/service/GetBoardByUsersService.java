package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.BoardDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.BoardDto;

public class GetBoardByUsersService extends SonetService{
	
	private String player1User;
	private String player2User;
	private String[][] matrix = {{null, null, null},{null, null, null},{null, null, null}};
	private BoardDto boardDto;
	
	public GetBoardByUsersService(String player1User, String player2User) {
		this.player1User = player1User;	
		this.player2User = player2User;
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
		if(board == null)
			throw new BoardDoesNotExistsException(player1User, player2User);
		
		for(int i=0; i<3; i++) {
			for(int j=0;j<3; j++){
				matrix[i][j]=board.getMatrix()[i][j].getUsername();
			}
		}
		
		boardDto = new BoardDto(board.getId(), matrix);
	}
	
	public BoardDto getBoard(){
		return boardDto;
	}

}

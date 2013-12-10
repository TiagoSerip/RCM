package pt.ist.sonet.service.dto;

import pt.ist.sonet.domain.Agent;

public class BoardDto {

	private static final long serialVersionUID = 1L;
	String player1Username;
	String player2Username;
	
	public BoardDto(String user1, String user2){
		this.player1Username = user1;
		this.player2Username = user2;
	}
	
	//for serialization
	public BoardDto(){}
	
	public String getPlayer1Username() {
		return player1Username;
	}

	public void setPlayer1Username(String user1) {
		this.player1Username = user1;
	}
	
	public String getPlayer2Username() {
		return player2Username;
	}

	public void setPlayer2Username(String user2) {
		this.player2Username = user2;
	}
	
}

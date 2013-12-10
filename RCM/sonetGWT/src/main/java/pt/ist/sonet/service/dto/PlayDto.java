package pt.ist.sonet.service.dto;

public class PlayDto {
	
	private static final long serialVersionUID = 1L;
	String playerUsername;
	int[] jogada;
	int boardId;
	
	public PlayDto(int id, String player, int[] jogada){
		this.playerUsername = player;
		this.jogada = jogada;
		this.boardId = id;		
	}
	
	//for serialization
	public PlayDto(){}
	
	public int getBoardId() {
		return boardId;
	}
	
	public void setBoardId(int id) {
		boardId = id;
	}
	
	public String getPlayerUsername() {
		return playerUsername;
	}

	public void setPlayerUsername(String user) {
		this.playerUsername = user;
	}
	
	public int[] getJogada() {
		return this.jogada;
	}

	public void setJogada(int[] j) {
		this.jogada = j;
	}

}

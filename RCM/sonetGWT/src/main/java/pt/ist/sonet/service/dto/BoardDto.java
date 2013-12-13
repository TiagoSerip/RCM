package pt.ist.sonet.service.dto;

import java.io.Serializable;

public class BoardDto implements Serializable {

	private static final long serialVersionUID = 1L;
	int boardId;
	String[] vector = {null, null, null, null, null, null, null, null, null};

	public BoardDto(int id, String[] tabuleiro){
		this.boardId = id;
		this.vector = tabuleiro;
	}

	//for serialization
	public BoardDto(){}

	public String[] getVector() {
		return vector;
	}

	public void setVector(String[] tabuleiro) {
		this.vector = tabuleiro;
	}

	public int getBoardId() {
		return boardId;
	}	

	public void setBoardId(int id) {
		this.boardId = id;
	}
}

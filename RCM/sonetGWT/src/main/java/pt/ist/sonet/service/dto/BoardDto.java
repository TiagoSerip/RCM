package pt.ist.sonet.service.dto;

import pt.ist.sonet.domain.Agent;

public class BoardDto {

	private static final long serialVersionUID = 1L;
	int boardId;
	Agent[][] matrix;

	public BoardDto(int id, Agent[][] tabuleiro){
		this.boardId = id;
		this.matrix = tabuleiro;
	}

	//for serialization
	public BoardDto(){}

	public Agent[][] getBoard() {
		return matrix;
	}

	public void setBoard(Agent[][] tabuleiro) {
		this.matrix = tabuleiro;
	}

	public int getBoardId() {
		return boardId;
	}	

	public void setBoardId(int id) {
		this.boardId = id;
	}
}

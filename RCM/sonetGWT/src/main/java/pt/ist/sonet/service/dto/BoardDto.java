package pt.ist.sonet.service.dto;

import java.io.Serializable;

public class BoardDto implements Serializable {

	private static final long serialVersionUID = 1L;
	int boardId;
	String[] matrixLine1 = {null, null, null};
	String[] matrixLine2 = {null, null, null};
	String[] matrixLine3 = {null, null, null};


	public BoardDto(int id, String[] tabuleiroLinha1, String[] tabuleiroLinha2, String[] tabuleiroLinha3){
		this.boardId = id;
		this.matrixLine1 = tabuleiroLinha1;
		this.matrixLine2 = tabuleiroLinha2;
		this.matrixLine3 = tabuleiroLinha3;
	}

	//for serialization
	public BoardDto(){}

	public String[] getMatrixLine1() {
		return matrixLine1;
	}

	public void setBoardLine1(String[] tabuleiroLinha1) {
		this.matrixLine1 = tabuleiroLinha1;
	}
	
	public String[] getMatrixLine2() {
		return matrixLine2;
	}

	public void setBoardLine2(String[] tabuleiroLinha2) {
		this.matrixLine2 = tabuleiroLinha2;
	}
	
	public String[] getMatrixLine3() {
		return matrixLine3;
	}

	public void setBoardLine3(String[] tabuleiroLinha3) {
		this.matrixLine3 = tabuleiroLinha3;
	}

	public int getBoardId() {
		return boardId;
	}	

	public void setBoardId(int id) {
		this.boardId = id;
	}
}

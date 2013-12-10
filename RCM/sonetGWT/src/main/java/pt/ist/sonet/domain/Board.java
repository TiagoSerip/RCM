package pt.ist.sonet.domain;

public class Board extends Board_Base {
    
	public Agent[][] matrix;
	
    public  Board() {}
    
	public void init(int id, Agent player1, Agent player2, Agent[][] matrix ) {
		setId(id);
		setPlayer1(player1);
		setPlayer2(player2);
		this.matrix = matrix;
	}
	
	public void setMatrix(Agent[][] board) {
		this.matrix = board;
	}
	
	public Agent[][] getMatrix() {
		return this.matrix;
		
		
	}
    
}

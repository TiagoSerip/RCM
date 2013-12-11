package pt.ist.sonet.domain;

public class Board extends Board_Base {
    
	public Agent[][] matrix;
	
    public  Board() {}
    
	public void init(int id, Agent host, Agent guest, Agent[][] matrix ) {
		setId(id);
		setHost(host);
		setGuest(guest);
		this.matrix = matrix;
	}
	
	public void setMatrix(Agent[][] board) {
		this.matrix = board;
	}
	
	public Agent[][] getMatrix() {
		return this.matrix;
		
		
	}
    
}

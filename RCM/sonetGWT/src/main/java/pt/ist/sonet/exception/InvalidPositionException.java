package pt.ist.sonet.exception;

public class InvalidPositionException extends SoNetException {
	
	private static final long serialVersionUID = 1L;
	
	int row;
	int column;
	String whoPlayedHere;
	
	public InvalidPositionException(int linha, int coluna, String quemJogou){
		this.row = linha;
		this.column = coluna;
		this.whoPlayedHere = quemJogou;		
	}
	
	InvalidPositionException(){}
	
	public String toString() {
		return whoPlayedHere+" already played in position [" + row + "," + column + "].";
	}


}

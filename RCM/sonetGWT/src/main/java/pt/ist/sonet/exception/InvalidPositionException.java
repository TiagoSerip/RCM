package pt.ist.sonet.exception;

import pt.ist.sonet.domain.Agent;

public class InvalidPositionException extends SoNetException {
	
	private static final long serialVersionUID = 1L;
	
	int row;
	int column;
	Agent whoPlayedHere;
	
	public InvalidPositionException(int linha, int coluna, Agent quemJogou){
		this.row = linha;
		this.column = coluna;
		this.whoPlayedHere = quemJogou;		
	}
	
	InvalidPositionException(){}
	
	public String toString() {
		return whoPlayedHere.getUsername()+" already played in position [" + row + "," + column + "].";
	}


}

package pt.ist.sonet.exception;

import java.io.Serializable;

public class BoardDoesNotExistsException extends SoNetException implements Serializable {

	private static final long serialVersionUID = 1L;

	private String player1User;
	private String player2User;
	
	public BoardDoesNotExistsException(String user1, String user2) {
		this.player1User = user1;
		this.player2User = user2;
	}
	
	//For serialization
	public BoardDoesNotExistsException() {}
	
	public String getid() {
		return "There is no board associated to " + player1User + " and " + player2User + ".";
	}

}

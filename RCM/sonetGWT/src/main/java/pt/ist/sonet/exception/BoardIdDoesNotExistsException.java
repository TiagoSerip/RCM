package pt.ist.sonet.exception;

import java.io.Serializable;

public class BoardIdDoesNotExistsException extends SoNetException implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	
	public BoardIdDoesNotExistsException(int id) {
		this.id = id;
	}
	
	//For serialization
	public BoardIdDoesNotExistsException() {}
	
	public String getid() {
		return ""+this.id;
	}

}

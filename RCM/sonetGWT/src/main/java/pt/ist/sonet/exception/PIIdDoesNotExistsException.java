package pt.ist.sonet.exception;

import java.io.Serializable;

public class PIIdDoesNotExistsException extends SoNetException implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	
	public PIIdDoesNotExistsException(int id) {
		this.id = id;
	}
	
	//For serialization
	public PIIdDoesNotExistsException() {}
	
	public String getid() {
		return ""+this.id;
	}
}

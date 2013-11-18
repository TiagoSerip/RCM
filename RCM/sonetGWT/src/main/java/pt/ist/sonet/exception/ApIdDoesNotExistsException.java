package pt.ist.sonet.exception;

import java.io.Serializable;

public class ApIdDoesNotExistsException extends SoNetException implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	public ApIdDoesNotExistsException(int id) {
		this.id = id;
	}
	//For serialization
	public ApIdDoesNotExistsException() {}
	
	public String getid() {
		return ""+this.id;
	}
	
}

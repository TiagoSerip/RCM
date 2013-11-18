package pt.ist.sonet.exception;

import java.io.Serializable;

public class PublicationIdDoesNotExistsException extends SoNetException implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	public PublicationIdDoesNotExistsException(int id) {
		this.id = id;
	}
	//For serialization
	public PublicationIdDoesNotExistsException() {}
	
	public String getid() {
		return ""+this.id;
	}
	
}

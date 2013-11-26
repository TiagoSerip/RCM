package pt.ist.sonet.exception;

import java.io.Serializable;

public class PINameAlreadyExistsException extends SoNetException implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	
	public PINameAlreadyExistsException(String name) {
		this.name = name;
	}
	
	public PINameAlreadyExistsException(){}
	
	public String getConflictingUsername() {
		return this.name;
	}
}

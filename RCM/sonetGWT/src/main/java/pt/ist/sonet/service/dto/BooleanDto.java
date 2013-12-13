
package pt.ist.sonet.service.dto;

import java.io.Serializable;

public class BooleanDto implements Serializable{

	private static final long serialVersionUID = -6906838957265157374L;
	private Boolean value;

	public BooleanDto() {
	}


	public Boolean getValue() {
		return value;
	}


	public void setValue(boolean value) {
		this.value = new Boolean(value);
	}


	
	
}

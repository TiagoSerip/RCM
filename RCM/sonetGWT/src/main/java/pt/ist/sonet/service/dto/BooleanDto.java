/**
 * 
 */
package pt.ist.sonet.service.dto;

import java.io.Serializable;

/**
 * @author ES Grupo 8
 *
 */
public class BooleanDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6906838957265157374L;
	private Boolean value;
	
	/**
	 *Constructor
	 */
	public BooleanDto() {
	}

	
	/**
	 * @return the value
	 */
	public Boolean getValue() {
		return value;
	}

	/**
	 * @param value the value of the boolean
	 */
	public void setValue(Boolean value) {
		this.value = value;
	}


	
	
}

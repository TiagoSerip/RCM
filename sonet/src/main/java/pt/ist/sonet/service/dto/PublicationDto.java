/**
 * 
 */
package pt.ist.sonet.service.dto;

import java.io.Serializable;

/**
 * @author ES Grupo 8
 *
 */
public class PublicationDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String label;
	private String ownerUsername;
	private int positive;
	private int negative;
	private int coments;
	private String type;
	

	public PublicationDto(int id, String ownerUsername, String label, int positive , int negative , int coments) {
		this.id = id;
		this.label = label;
		this.ownerUsername = ownerUsername;
		this.positive = positive;
		this.negative = negative;
		this.coments = coments;
	}
	public PublicationDto() {
		
	}

	public int getPositive() {
		return positive;
	}

	public int getNegative() {
		return negative;
	}

	public int getComents() {
		return coments;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * @return the ownerUsername
	 */
	public String getOwnerUsername() {
		return ownerUsername;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String toString(){
		return label+" | "+positive+" | "+negative+" | "+coments;
	}
	
	
}
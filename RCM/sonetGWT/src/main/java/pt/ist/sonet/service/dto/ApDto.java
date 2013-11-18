/**
 * 
 */
package pt.ist.sonet.service.dto;

import java.io.Serializable;

/**
 * @author ES Grupo 8
 *
 */
public class ApDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String subnet;
	private int positive;
	private int negative;
	

	public ApDto(int id, String subnet, int positive , int negative) {
		this.id = id;
		this.positive = positive;
		this.negative = negative;
		this.subnet = subnet;
	}
	public ApDto() {
		
	}

	public int getPositive() {
		return positive;
	}

	public int getNegative() {
		return negative;
	}

	/**
	 * @return the subnet
	 */
	public String getSubnet() {
		return subnet;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public String toString(){
		return "AP"+id+" | "+positive+" | "+negative+" | "+subnet;
	}
	
	
}

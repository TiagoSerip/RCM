/**
 * 
 */
package pt.ist.sonet.service.dto;

import java.io.Serializable;

public class ApDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String subnet;
	private int rssi;
	private int positive;
	private int negative;
	

	public ApDto(int id, String subnet, int rssi, int positive , int negative) {
		this.id = id;
		this.positive = positive;
		this.negative = negative;
		this.subnet = subnet;
		this.rssi=rssi;
	}
	public ApDto() {
		
	}

	public int getPositive() {
		return positive;
	}

	public int getNegative() {
		return negative;
	}

	public String getSubnet() {
		return subnet;
	}

	public int getId() {
		return id;
	}
	
	public int getRssi() {
		return rssi;
	}

	public String toString(){
		return "AP"+id+" | "+positive+" | "+negative+" | "+subnet+" | "+rssi;
	}
	
	
}

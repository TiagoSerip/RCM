/**
 * 
 */
package pt.ist.sonet.service.dto;

import java.io.Serializable;

public class AgentDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String name;
	private String pass;
	private int ap;
	private int rssi;
	private String ip;
	

	public AgentDto(String username, String pass, String name, int ap, int rssi, String ip) {

		this.username = username;
		this.name = name;
		this.setIp(ip);
		this.pass = pass;
		this.setRssi(rssi);
		this.setAp(ap);
	}
	
	public AgentDto(){};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUsername() {
		return username;
	}

	public int getAp() {
		return ap;
	}

	public void setAp(int ap) {
		this.ap = ap;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	
}

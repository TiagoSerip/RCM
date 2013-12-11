package pt.ist.sonet.service.dto;

import java.io.Serializable;

public class PIDto implements Serializable{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	String location;
	String name;
	int piID;
	String description;
	String link;
	int pos;
	int neg;
	
	public PIDto(int id, String name, String location, String description, String link, int pos, int neg){
		this.location = location;
		this.name = name;
		this.description = description;
		this.piID = id;
		this.link = link;
		this.pos=pos;
		this.neg=neg;
		
	}
	
	//for serialization
	public PIDto(){}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getId() {
		return piID;
	}

	public void setId(int id) {
		this.piID = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getNeg() {
		return neg;
	}

	public void setNeg(int neg) {
		this.neg = neg;
	}
	
}

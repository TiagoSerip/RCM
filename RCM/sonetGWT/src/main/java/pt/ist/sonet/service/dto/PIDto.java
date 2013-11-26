package pt.ist.sonet.service.dto;

public class PIDto {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	String location;
	String name;
	int piID;
	String description;
	
	public PIDto(int id, String name, String location, String description){
		this.location = location;
		this.name = name;
		this.description = description;
		this.piID = id;
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
	
}

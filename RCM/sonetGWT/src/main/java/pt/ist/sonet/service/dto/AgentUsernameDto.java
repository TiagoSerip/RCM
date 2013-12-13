
package pt.ist.sonet.service.dto;


public class AgentUsernameDto {
	
	private String user;
	
	public AgentUsernameDto(String user) {
		this.user = user;
	}
	
	public String getUsername(){
		return user;
	}

}

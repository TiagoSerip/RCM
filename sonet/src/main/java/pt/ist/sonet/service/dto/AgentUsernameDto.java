
package pt.ist.sonet.service.dto;

/**
 * DTO para representar um username de um agente.
 * @author ES Grupo 8
 *
 */
public class AgentUsernameDto {
	
	private String user;
	
	public AgentUsernameDto(String user) {
		this.user = user;
	}
	
	public String getUsername(){
		return user;
	}

}

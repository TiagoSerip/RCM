/**
 * 
 */
package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.BooleanDto;


public class AgentLoginService extends SonetService {

	private String username;
	private String password;
	private BooleanDto dto;

	public AgentLoginService(String user, String pass, BooleanDto dto) {
		this.username = user;
		this.password = pass;
		this.dto = dto;
	}
	

	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
		SoNet rede = FenixFramework.getRoot();
		Agent agent = rede.getAgentByUsername(username);
		if(agent == null)
			throw new AgentUsernameDoesNotExistsException(username);
		if(agent.getPassword().equals(password))
			dto.setValue(true);
		else
			dto.setValue(false);
	}
		
}

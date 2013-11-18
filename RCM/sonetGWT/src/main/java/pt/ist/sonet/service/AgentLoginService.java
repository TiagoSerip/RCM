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

/**
 * @author ES Grupo 8
 */
public class AgentLoginService extends SonetService {

	private String username;
	private String password;
	private BooleanDto dto;
	/**
	 * Construtor
	 * 
	 * @param String user username
	 * @param String pass agent password
	 */
	public AgentLoginService(String user, String pass, BooleanDto dto) {
		this.username = user;
		this.password = pass;
		this.dto = dto;
	}
	
	
	/**
	 * Faz o dispach do servico.
	 *
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExists
	 */
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

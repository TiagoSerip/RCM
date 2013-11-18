/**
 * 
 */
package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * @author ES Grupo 8
 */
public class FriendsOfAgentService extends SonetService {

	private String username;
	private StringListDto dto;
	/**
	 * Construtor
	 * 
	 * @param String user username
	 * @param String pass agent password
	 */
	public FriendsOfAgentService(String user, StringListDto dto) {
		this.username = user;
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
		for(Agent a : rede.getFriendsOf(agent)){
			dto.addTolisting(a.getUsername());
		}	
	}
		
}

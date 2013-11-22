/**/
package pt.ist.sonet.service;


import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentNameDoesNotExistsException;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.fenixframework.FenixFramework;

/**
 * @author ES Grupo 8
 */

public class RegisterAgentService extends SonetService {

	private AgentDto dto;
	
	/**
	 * 
	 * Construtor
	 * 
	 * @param String type - tipo de agente "Individual" ou "Organizational"
	 */	
	public RegisterAgentService(AgentDto agent) {
		dto = agent;
	}	
	
	/**
	 *  Faz o dispach do servico
	 *  
	 *  @throws SoNetException
	 */
	@Override
	public void dispatch() throws SoNetException, UsernameAlreadyExistsException {
		
		SoNet network = FenixFramework.getRoot();
		
			Agent agent = network.createAgent(dto.getUsername(), dto.getPass(), dto.getName(), dto.getAp(),
					dto.getRssi(), dto.getIp());
			network.addAgent(agent);
		
		
	}
	
}

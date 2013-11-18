/**/
package pt.ist.sonet.service;


import pt.ist.sonet.domain.Individual;
import pt.ist.sonet.domain.Organizational;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentNameDoesNotExistsException;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
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
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, AgentNameDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		
		if(dto.getType().equals(network.INDIVIDUAL)) {
			Individual agent = network.createIndividualAgent(dto.getUsername(), dto.getName(), dto.getEmail(), dto.getPass(),
					dto.getCity(), dto.getNation(), dto.getPermission());
			network.addIndividual(agent);
		}
		
		if(dto.getType().equals(network.ORGANIZATIONAL)) {
			Organizational agent = network.createOrganizationalAgent(dto.getUsername(), dto.getName(), dto.getEmail(), dto.getPass(),
					dto.getCity(), dto.getNation(), dto.getPermission());
			network.addOrganizational(agent);
		}
		
	}
	
}

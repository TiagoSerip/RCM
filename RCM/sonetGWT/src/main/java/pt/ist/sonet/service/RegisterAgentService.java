/**/
package pt.ist.sonet.service;


import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.fenixframework.FenixFramework;

public class RegisterAgentService extends SonetService {

	private AgentDto dto;

	public RegisterAgentService(AgentDto agent) {
		dto = agent;
	}	

	@Override
	public void dispatch() throws SoNetException, UsernameAlreadyExistsException {
		
		SoNet network = FenixFramework.getRoot();
		
			Agent agent = network.createAgent(dto.getUsername(), dto.getPass(), dto.getName(), dto.getAp(),
					dto.getRssi(), dto.getIp());
			network.addAgent(agent);
		
		
	}
	
}

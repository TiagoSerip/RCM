package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.IpOutOfMeshException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.AgentDto;


public class UpdateAgentIpService extends SonetService{
	
	private AgentDto dto;

	public UpdateAgentIpService(AgentDto dto) {
		this.dto = dto;	
	}

	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, IpOutOfMeshException{
		SoNet sonet = FenixFramework.getRoot();
		
		Agent a = sonet.getAgentByUsername(dto.getUsername());
		if(a == null)
			throw new AgentUsernameDoesNotExistsException(dto.getUsername());
		
		sonet.updateAgentIP(a, dto.getIp());
				
	}
	
}

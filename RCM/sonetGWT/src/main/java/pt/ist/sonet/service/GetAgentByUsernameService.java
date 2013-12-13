package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.AgentDto;


public class GetAgentByUsernameService extends SonetService{
	
	private AgentDto dto;
	private String username;
	

	public GetAgentByUsernameService(String user) {
		this.username = user;	
	}

	@Override
	protected void dispatch() throws SoNetException, ApIdDoesNotExistsException{
		SoNet sonet = FenixFramework.getRoot();
		
		Agent a = sonet.getAgentByUsername(username);
		if(a == null)
			throw new AgentUsernameDoesNotExistsException(username);
		
		dto = new AgentDto(a.getUsername(), a.getPassword(), a.getName(), a.getAp().getId(), a.getRssi(), a.getIp());

	}

	public AgentDto getDto(){
		return dto;
	}
	
}

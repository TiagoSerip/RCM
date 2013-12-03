package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.StringListDto;

public class GetAllOtherAgentsService extends SonetService{

	private StringListDto listDto;
	private String active;
	
	public GetAllOtherAgentsService(StringListDto dto, String user){
		this.listDto = dto;
		this.active = user;
	}
	
	@Override
	protected void dispatch() throws SoNetException{
		SoNet sonet = FenixFramework.getRoot();
		
		if(!sonet.hasAnyAgent())
			return;
		
		Agent agentActive = sonet.getAgentByUsername(active);
		if(agentActive == null)
			throw new AgentUsernameDoesNotExistsException(active);
		
		for(Agent a : sonet.getAgentSet()){
			if(!a.equals(agentActive))
				listDto.addTolisting(a.getUsername());
		}
	}
	
	public StringListDto getAllAgentsUser(){
		return listDto;
	}
}

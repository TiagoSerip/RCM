package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.StringListDto;

public class GetAllAgentsService extends SonetService{

	private StringListDto listDto;
	
	public GetAllAgentsService(StringListDto dto){
		this.listDto = dto;
	}
	
	@Override
	protected void dispatch() throws SoNetException{
		SoNet sonet = FenixFramework.getRoot();
		
		if(!sonet.hasAnyAgent())
			return;
		
		for(Agent a : sonet.getAgentSet()){
			listDto.addTolisting(a.getUsername());
		}
	}
	
	public StringListDto getAllAgentsUser(){
		return listDto;
	}
}

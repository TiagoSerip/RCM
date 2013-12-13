package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.ApListDto;

public class GetAllApService extends SonetService{

	private ApListDto listDto;

	public GetAllApService(){
		this.listDto=new ApListDto();
	}

	@Override
	protected void dispatch() throws SoNetException{
		SoNet sonet = FenixFramework.getRoot();
		
		if(!sonet.hasAnyAp())
			return;
		
		for(AP ap : sonet.getApSet()){
			listDto.addTolisting(new ApDto(ap.getId(), ap.getSubnet(), ap.getRssi(), ap.getPosVotes(), ap.getNegVotes()));
		}
	}
		

	public ApListDto getListing(){
		return listDto;
	}
}
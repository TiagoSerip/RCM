package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.*;
import pt.ist.sonet.exception.*;
import pt.ist.sonet.service.dto.*;

public class GetAllPIFromAPService extends SonetService {

	private PIListDto listDto;
	private int apID;

	public GetAllPIFromAPService(int ap) {
		this.listDto = new PIListDto();
		this.apID = ap;
	}
	

	@Override
	protected void dispatch() throws SoNetException{
		
		SoNet sonet = FenixFramework.getRoot();
		AP ap = sonet.getApById(apID);
		if(ap == null)
			throw new ApIdDoesNotExistsException(apID);
		for(PI pi : sonet.getAllPIFromAP(ap)){
			listDto.addTolisting(new PIDto(pi.getId(), pi.getName(), pi.getLocation(), pi.getDescription(), pi.getLink(), pi.getPositive(), pi.getNegative()));
		}
	}
		

	public PIListDto getListing(){
		return listDto;
	}
}
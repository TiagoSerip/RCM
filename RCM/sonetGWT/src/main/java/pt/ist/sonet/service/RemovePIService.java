package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.PI;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.*;

public class RemovePIService extends SonetService {

	private PIDto piDto;
	private int apID;
	
	public RemovePIService(int ap, PIDto pi) {
		this.piDto = pi;
		this.apID = ap;
	}

	@Override
	protected void dispatch() throws SoNetException{
		
		SoNet sonet = FenixFramework.getRoot();
		AP ap = sonet.getApById(apID);
		if(ap == null)
			throw new ApIdDoesNotExistsException(apID);
		PI pi = sonet.getPIByID(piDto.getId());
		sonet.removePI(pi, ap);
	}
}

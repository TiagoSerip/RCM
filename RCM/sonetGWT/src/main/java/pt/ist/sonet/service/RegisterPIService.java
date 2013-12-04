package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.*;
import pt.ist.sonet.exception.*;
import pt.ist.sonet.service.dto.*;

public class RegisterPIService extends SonetService {

	private PIDto PIdto;
	private int apID;
	
	public RegisterPIService(PIDto pi, int ap) {
		PIdto = pi;
		apID = ap;
	}	
	
	@Override
	public void dispatch() throws SoNetException {
		
		SoNet network = FenixFramework.getRoot();
		AP ap = network.getApById(apID);
		if(ap==null)
			throw new ApIdDoesNotExistsException(apID);
		
		network.createPI(PIdto.getName(), PIdto.getLocation(), PIdto.getDescription(), PIdto.getLink(), ap);
	
		
	}
	
}

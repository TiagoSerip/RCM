/**/
package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.ApIdAlreadyExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.ApDto;

public class RegisterApService extends SonetService {

	private ApDto dto;

	public RegisterApService(ApDto ap) {
		dto = ap;
	}	

	@Override
	public void dispatch() throws SoNetException, ApIdAlreadyExistsException {
		
		SoNet network = FenixFramework.getRoot();

			AP ap = network.createAP(dto.getId(), dto.getSubnet(), dto.getRssi());
			network.addAp(ap);
		
		
	}
	
}

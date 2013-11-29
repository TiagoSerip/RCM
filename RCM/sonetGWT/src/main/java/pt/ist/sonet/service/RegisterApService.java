/**/
package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentNameDoesNotExistsException;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.ApIdAlreadyExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.ApDto;

/**
 * @author ES Grupo 8
 */

public class RegisterApService extends SonetService {

	private ApDto dto;
	
	/**
	 * 
	 * Construtor
	 * 
	 * @param String type - tipo de agente "Individual" ou "Organizational"
	 */	
	public RegisterApService(ApDto ap) {
		dto = ap;
	}	
	
	/**
	 *  Faz o dispach do servico
	 *  
	 *  @throws SoNetException
	 */
	@Override
	public void dispatch() throws SoNetException, ApIdAlreadyExistsException {
		
		SoNet network = FenixFramework.getRoot();
		
			AP ap = network.createAP(dto.getId(), dto.getSubnet(), dto.getRssi());
			network.addAp(ap);
		
		
	}
	
}

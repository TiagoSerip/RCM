package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Organizational;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * @author ES Grupo 8
 */
public class GetAllOrganizationalAgentsService extends SonetService {
	
	private StringListDto dto;

	public GetAllOrganizationalAgentsService(StringListDto dto) { 
		this.dto = dto;
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 */
	@Override
	protected void dispatch() throws SoNetException {

		SoNet network = FenixFramework.getRoot();
		
		for(Organizational o : network.getOrganizationalSet()) {
			dto.addTolisting(o.getUsername());
		}
	}

}
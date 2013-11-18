package pt.ist.sonet.service;

import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * @author ES Grupo 8
 */
public class AllAgentsService extends SonetService {
	
	private StringListDto dto;

	public AllAgentsService(StringListDto dto) { 
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
		
		SoNet rede = FenixFramework.getRoot();
		List<Agent> combined = rede.allAgents();
		
		for(Agent a : combined) {
			dto.addTolisting(a.getUsername());

		}
	}

}
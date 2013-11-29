package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * @author ES Grupo 8
 */
public class AgentsByApService extends SonetService {
	
	private StringListDto dto;
	private int id;

	public AgentsByApService(int ap, StringListDto dto) { 
		this.dto = dto;
		this.id=ap;
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
		
		AP ap = rede.getApById(id);
		
		if(ap==null)
			throw new ApIdDoesNotExistsException(id);
		
		for(Agent a : ap.getAgentSet()) {
			dto.addTolisting(a.viewString()+" > radius: "+rede.calculateDistanceToAP(a)+"m (+/-5m).");
		}
	}

}

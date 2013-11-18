package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Individual;
import pt.ist.sonet.domain.Organizational;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.ListingDto;

/**
 * @author ES Grupo 8
 */
public class ListAllService extends SonetService {
	
	private ListingDto dto;

	public ListAllService(ListingDto dto) { 
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
		
		if(rede.getOrganizationalSet().isEmpty() && rede.getIndividualSet().isEmpty()){
			dto.addTolisting("There are no registered Agents.");
			return;
		}
		dto.addTolisting("Agent Listing (Type | username | Name | email | Location):\n");
		for(Organizational o : rede.getOrganizationalSet()){
			dto.addTolisting(o.toString()+"\n");
			dto.addTolisting(o.myPublicationsToString()+"\n");
		}
		for(Individual i : rede.getIndividualSet()){
			dto.addTolisting(i.toString()+"\n");
			dto.addTolisting(i.myPublicationsToString()+"\n");
		}
		dto.addTolisting("----- End of List -----");

	}

}

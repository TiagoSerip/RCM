package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.*;
import pt.ist.sonet.exception.*;
import pt.ist.sonet.service.dto.*;

public class GetAllPIFromAPService extends SonetService {

	private PIListDto listDto;
	private int apID;
	
	/**
	 * Construtor
	 * 
	 * @param String agentreq - username do agente que quer ver as publicacoes
	 * @param String agentsend - username do agente detentor das publicacoes
	 * @param ListingDto
	 */
	public GetAllPIFromAPService(int ap) {
		this.listDto = new PIListDto();
		this.apID = ap;
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException
	 * @throws YouArentAFriendException
	 */
	@Override
	protected void dispatch() throws SoNetException{
		
		SoNet sonet = FenixFramework.getRoot();
		AP ap = sonet.getApById(apID);
		if(ap == null)
			throw new ApIdDoesNotExistsException(apID);
		for(PI pi : sonet.getAllPIFromAP(ap)){
			listDto.addTolisting(new PIDto(pi.getId(), pi.getName(), pi.getLocation(), pi.getDescription()));
		}
	}
		
	/**
	 * Devolve todos os PI's existentes num determinado AP utilizando DTOs
	 * 
	 * @return ArrayList<String>
	 */
	public PIListDto getListing(){
		return listDto;
	}
}
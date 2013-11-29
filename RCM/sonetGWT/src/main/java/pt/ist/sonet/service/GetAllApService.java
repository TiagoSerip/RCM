package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.ApListDto;

/**
 * Classe GetAllPublicationsService que herda de SonetService. Este servico permite que um agente visualize,
 * caso seja possivel, as publicacoes de outro agente 
 */
public class GetAllApService extends SonetService{

	private ApListDto listDto;
	
	/**
	 * Construtor
	 * 
	 * @param String agentreq - username do agente que quer ver as publicacoes
	 * @param String agentsend - username do agente detentor das publicacoes
	 * @param ListingDto
	 */
	public GetAllApService(){
		this.listDto=new ApListDto();
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
		
		if(!sonet.hasAnyAp())
			return;
		
		for(AP ap : sonet.getApSet()){
			listDto.addTolisting(new ApDto(ap.getId(), ap.getSubnet(), ap.getRssi(), ap.getPosVotes(), ap.getNegVotes()));
		}
	}
		
	/**
	 * Devolve todos os AP's existentes utilizando DTOs
	 * 
	 * @return ArrayList<String>
	 */
	public ApListDto getListing(){
		return listDto;
	}
}
package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Comment;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.PublicationIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * Classe GetAllPublicationsService que herda de SonetService. Este servico permite que um agente visualize,
 * caso seja possivel, as publicacoes de outro agente 
 */
public class GetApCommentsService extends SonetService{

	private int apId;
	private StringListDto dto;
	
	/**
	 * Construtor
	 * 
	 * @param String agentreq - username do agente que quer ver as publicacoes
	 * @param String agentsend - username do agente detentor das publicacoes
	 * @param ListingDto
	 */
	public GetApCommentsService(int apId, StringListDto dto){
		this.dto = dto;
		this.apId = apId;
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 */
	@Override
	protected void dispatch() throws SoNetException, PublicationIdDoesNotExistsException {
		SoNet sonet = FenixFramework.getRoot();
		
		AP ap = sonet.getApById(apId);
		if(ap == null)
			throw new PublicationIdDoesNotExistsException(apId);
		
		for(Comment c : ap.getCommentsSet()){
			dto.addTolisting(c.toString());
		}
		
	}
	
}
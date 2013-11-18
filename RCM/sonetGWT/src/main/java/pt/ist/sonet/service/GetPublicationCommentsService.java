package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Comment;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.PublicationIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * Classe GetAllPublicationsService que herda de SonetService. Este servico permite que um agente visualize,
 * caso seja possivel, as publicacoes de outro agente 
 */
public class GetPublicationCommentsService extends SonetService{

	private int pubId;
	private StringListDto dto;
	
	/**
	 * Construtor
	 * 
	 * @param String agentreq - username do agente que quer ver as publicacoes
	 * @param String agentsend - username do agente detentor das publicacoes
	 * @param ListingDto
	 */
	public GetPublicationCommentsService(int pubId, StringListDto dto){
		this.dto = dto;
		this.pubId = pubId;
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
		
		Publication pub = sonet.getPublicationById(pubId);
		if(pub == null)
			throw new PublicationIdDoesNotExistsException(pubId);
		
		for(Comment c : pub.getCommentsSet()){
			dto.addTolisting(c.getId() + " | " + c.getAgent().getName() + ": " + c.getText());
		}
		
	}
	
}
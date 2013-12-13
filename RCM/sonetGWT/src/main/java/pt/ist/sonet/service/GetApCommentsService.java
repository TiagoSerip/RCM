package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Comment;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.StringListDto;

public class GetApCommentsService extends SonetService{

	private int apId;
	private StringListDto dto;
	

	public GetApCommentsService(int apId, StringListDto dto){
		this.dto = dto;
		this.apId = apId;
	}

	@Override
	protected void dispatch() throws SoNetException, ApIdDoesNotExistsException {
		SoNet sonet = FenixFramework.getRoot();
		
		AP ap = sonet.getApById(apId);
		if(ap == null)
			throw new ApIdDoesNotExistsException(apId);
		
		for(Comment c : ap.getCommentsSet()){
			dto.addTolisting(c.toString());
		}
		
	}
	
}
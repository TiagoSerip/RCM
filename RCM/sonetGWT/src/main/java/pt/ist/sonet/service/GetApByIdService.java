package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.ApDto;


public class GetApByIdService extends SonetService{
	
	private ApDto dto;
	private int apId;
	

	public GetApByIdService(int apId) {
		this.apId = apId;	
	}

	@Override
	protected void dispatch() throws SoNetException, ApIdDoesNotExistsException{
		SoNet sonet = FenixFramework.getRoot();
		
		AP ap = sonet.getApById(apId);
		if(ap == null)
			throw new ApIdDoesNotExistsException(apId);
		
		dto = new ApDto(ap.getId(), ap.getSubnet(), ap.getRssi(), ap.getPosVotes(), ap.getNegVotes());

	}
	
	/**
	 * Devolve a publicacao utilizando DTOs
	 * 
	 * @return PublicationViewDto
	 */
	public ApDto getPublication(){
		return dto;
	}
	
}

package pt.ist.sonet.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.PI;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.PIIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.PIDto;

public class GetPIByIdService extends SonetService{
	
	private PIDto dto;
	private int piId;

	public GetPIByIdService(int piId) {
		this.piId = piId;	
	}

	@Override
	protected void dispatch() throws SoNetException, ApIdDoesNotExistsException{
		SoNet sonet = FenixFramework.getRoot();
		
		PI pi = sonet.getPIByID(piId);
		if(pi == null)
			throw new PIIdDoesNotExistsException(piId);
		
		dto = new PIDto(pi.getId(), pi.getName(), pi.getLocation(), pi.getDescription(), pi.getLink(), pi.getPositive(), pi.getNegative());

	}
	
	/**
	 * Devolve a publicacao utilizando DTOs
	 * 
	 * @return PublicationViewDto
	 */
	public PIDto getPI(){
		return dto;
	}
	
}

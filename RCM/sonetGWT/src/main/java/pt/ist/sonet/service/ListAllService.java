package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.SonetService;
import pt.ist.sonet.service.dto.ListingDto;


public class ListAllService extends SonetService {
	
	private ListingDto dto;

	public ListAllService(ListingDto dto) { 
		this.dto = dto;
	}

	@Override
	protected void dispatch() throws SoNetException {
		
		SoNet rede = FenixFramework.getRoot();
		
		if(rede.getApSet().isEmpty()){
			dto.addTolisting("There are no registered AP's.");
			return;
		}
		dto.addTolisting("AP Listing (Id | subnet | positive | negative):\n");
		for(AP ap : rede.getApSet()){
			dto.addTolisting(ap.toString()+"\n");
			if(ap.hasAnyAgent()){
				dto.addTolisting("\t"+"Username | Name | Password | AP-ID | RSSI | IP Address\n");
				for(Agent user : ap.getApAgents()){
					dto.addTolisting("\t"+user.toString()+"\n");
				}
			}
		}
		dto.addTolisting("----- End of List -----");

	}

}

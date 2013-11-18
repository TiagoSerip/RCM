package pt.ist.sonet.service;

import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Individual;
import pt.ist.sonet.domain.Organizational;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.OrgHasNotPendingException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * @author ES Grupo 8
 */
public class SentFriendRequestsService extends SonetService {

	private String username;
	private StringListDto dto;
	
	/**
	 * Construtor
	 * 
	 * @param String user username
	 * @param String pass agent password
	 */
	public SentFriendRequestsService(String user, StringListDto dto) {
		this.username = user;
		this.dto = dto;
	}
		
	/**
	 * Faz o dispach do servico.
	 *
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExists
	 */
	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, OrgHasNotPendingException {
		
		SoNet network = FenixFramework.getRoot();
		
		Organizational org = network.getOrganizationalByName(this.username);
		if(org != null){
			throw new OrgHasNotPendingException();
		}
		
		Individual ind = network.getIndividualByUsername(this.username);
		if(ind == null){
			throw new AgentUsernameDoesNotExistsException(this.username);
		}
		
		List<Individual> allPendings = ind.getPendingRequest();

		for(Individual i : allPendings) {
			dto.addTolisting(i.getUsername());
		}		
		
	}
		
}

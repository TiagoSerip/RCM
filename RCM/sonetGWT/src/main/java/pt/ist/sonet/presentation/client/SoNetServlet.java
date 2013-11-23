package pt.ist.sonet.presentation.client;

import java.util.ArrayList;

import pt.ist.sonet.exception.AgentNameDoesNotExistsException;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.AlreadyVotedException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.ListingDto;
import pt.ist.sonet.service.dto.StringListDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("sonet")
public interface SoNetServlet extends RemoteService {
	void init(String serverType);
	ListingDto displaySonet();
	Boolean agentLogin(String username, String password) throws AgentUsernameDoesNotExistsException;
	void addNote(String username, String label, String text);
	void commentAp(String username, int apId, String text) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException;
	StringListDto getAgents(int ap) throws ApIdDoesNotExistsException;
	StringListDto getApComments(int apId) throws ApIdDoesNotExistsException;
	ApDto viewAp(int apId) throws ApIdDoesNotExistsException;
	void positiveVote(String user, int pubId) throws AlreadyVotedException;
	void negativeVote(String user, int pubId) throws AlreadyVotedException;
	ArrayList<ApDto> getApList();
	AgentDto getAgent(String user) throws AgentUsernameDoesNotExistsException;
	void updateAgentProfile(AgentDto dto) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException;
	void changeAgentPassword(AgentDto dto) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException;
	String getAgentIP();
	Integer loadRSSIMacOS();
	void registerAgentProfile(AgentDto dto) throws UsernameAlreadyExistsException;

}

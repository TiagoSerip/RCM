package pt.ist.sonet.presentation.client;

import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.AlreadyVotedException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.IpOutOfMeshException;
import pt.ist.sonet.exception.PIIdDoesNotExistsException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.ApListDto;
import pt.ist.sonet.service.dto.ListingDto;
import pt.ist.sonet.service.dto.PIDto;
import pt.ist.sonet.service.dto.PIListDto;
import pt.ist.sonet.service.dto.StringListDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("sonet")
public interface SoNetServlet extends RemoteService {
	String init(String serverType);
	ListingDto displaySonet();
	Boolean agentLogin(String username, String password) throws AgentUsernameDoesNotExistsException;
	void addNote(String username, String label, String text);
	void commentAp(String username, int apId, String text) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException;
	StringListDto getAgents(int ap) throws ApIdDoesNotExistsException;
	StringListDto getApComments(int apId) throws ApIdDoesNotExistsException;
	ApDto viewAp(int apId) throws ApIdDoesNotExistsException;
	void positiveVote(String user, int pubId) throws AlreadyVotedException;
	void negativeVote(String user, int pubId) throws AlreadyVotedException;
	StringListDto getAllAgents();
	ApListDto getApList();
	void sendPrivateMessage(String user, String otherGuy, String text) throws AgentUsernameDoesNotExistsException;
	StringListDto getConversation(String user, String otherGuy) throws AgentUsernameDoesNotExistsException;
	AgentDto getAgent(String user) throws AgentUsernameDoesNotExistsException;
	void updateAgentProfile(AgentDto dto) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException;
	void changeAgentPassword(AgentDto dto) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException;
	String getAgentIP();
	Integer loadRSSIMacOS();
	void registerAgentProfile(AgentDto dto) throws UsernameAlreadyExistsException;
	int updateAgentIP(AgentDto dto) throws AgentUsernameDoesNotExistsException, IpOutOfMeshException;
	PIListDto getPIsByAp(int apId) throws ApIdDoesNotExistsException;
	PIDto getPIById(int id) throws PIIdDoesNotExistsException;
}

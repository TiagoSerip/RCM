package pt.ist.sonet.presentation.client;

import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.AlreadyVotedException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.ApListDto;
import pt.ist.sonet.service.dto.ListingDto;
import pt.ist.sonet.service.dto.PIDto;
import pt.ist.sonet.service.dto.PIListDto;
import pt.ist.sonet.service.dto.StringListDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SoNetServletAsync {
	void displaySonet(AsyncCallback<ListingDto> callback);
	void agentLogin(String username, String password, AsyncCallback<Boolean> callback) throws AgentUsernameDoesNotExistsException;
	void addNote(String username, String label, String text, AsyncCallback<Void> callback);
	void commentAp(String username, int apId, String text, AsyncCallback<Void> callback) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException;
	void getApList(AsyncCallback<ApListDto> asyncCallback);
	void getAgents(int ap, AsyncCallback<StringListDto> asyncCallback) throws ApIdDoesNotExistsException;
	void getApComments(int apId, AsyncCallback<StringListDto> asyncCallback)throws ApIdDoesNotExistsException;
	void negativeVote(String user, int apId, AsyncCallback<Void> callback) throws AlreadyVotedException, ApIdDoesNotExistsException;
	void positiveVote(String user, int apId, AsyncCallback<Void> callback) throws AlreadyVotedException, ApIdDoesNotExistsException;
	void viewAp(int pubId, AsyncCallback<ApDto> asyncCallback)throws ApIdDoesNotExistsException;
	void init(String serverType, AsyncCallback<String> callback);
	void getAgent(String user, AsyncCallback<AgentDto> callback);
	void getAllOtherAgents(String user, AsyncCallback<StringListDto> callback) throws AgentUsernameDoesNotExistsException;
	void updateAgentProfile(AgentDto dto, AsyncCallback<Void> callback);
	void changeAgentPassword(AgentDto dto, AsyncCallback<Void> callback);
	void getConversation(String user, String otherGuy, AsyncCallback<StringListDto> callback) throws AgentUsernameDoesNotExistsException;
	void getAgentIP(AsyncCallback<String> callback);
	void sendPrivateMessage(String user, String otherGuy, String text, AsyncCallback<Void> callback) throws AgentUsernameDoesNotExistsException;
	void loadRSSIMacOS(AsyncCallback<Integer> callback);
	void registerAgentProfile(AgentDto dto, AsyncCallback<Void> asyncCallback) throws UsernameAlreadyExistsException;
	void updateAgentIP(AgentDto dto, AsyncCallback<Integer> callback);
	void getPIsByAp(int apId, AsyncCallback<PIListDto> callback);
	void getPIById(int id, AsyncCallback<PIDto> callback);
	void createPI(int ap, String name, String location, String description, String link, AsyncCallback<Void> callback);
}

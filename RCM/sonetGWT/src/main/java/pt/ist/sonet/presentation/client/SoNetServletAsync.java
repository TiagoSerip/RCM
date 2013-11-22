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

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SoNetServletAsync {
	void displaySonet(AsyncCallback<ListingDto> callback);
	void agentLogin(String username, String password, AsyncCallback<Boolean> callback) throws AgentUsernameDoesNotExistsException;
	void addNote(String username, String label, String text, AsyncCallback<Void> callback);
	void commentAp(String username, int apId, String text, AsyncCallback<Void> callback) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException;
	void getApList(AsyncCallback<ArrayList<ApDto>> asyncCallback);
	void getAgents(AsyncCallback<StringListDto> asyncCallback);
	void getApComments(int apId, AsyncCallback<StringListDto> asyncCallback)throws ApIdDoesNotExistsException;
	void negativeVote(String user, int apId, AsyncCallback<Void> callback) throws AlreadyVotedException, ApIdDoesNotExistsException;
	void positiveVote(String user, int apId, AsyncCallback<Void> callback) throws AlreadyVotedException, ApIdDoesNotExistsException;
	void viewAp(int pubId, AsyncCallback<ApDto> asyncCallback)throws ApIdDoesNotExistsException;
	void init(String serverType, AsyncCallback<Void> callback);
	void getAgent(String user, AsyncCallback<AgentDto> callback);
	void updateAgentProfile(AgentDto dto, AsyncCallback<Void> callback);
	void changeAgentPassword(AgentDto dto, AsyncCallback<Void> callback);
	void getAgentIP(AsyncCallback<String> callback);
	void loadRSSIMacOS(AsyncCallback<Integer> callback);
	void registerAgentProfile(AgentDto dto, AsyncCallback<Void> asyncCallback) throws UsernameAlreadyExistsException;
}

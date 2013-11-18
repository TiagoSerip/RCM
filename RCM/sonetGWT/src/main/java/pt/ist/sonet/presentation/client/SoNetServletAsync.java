package pt.ist.sonet.presentation.client;

import java.util.ArrayList;


import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.AgentsCantVoteInTheirOwnPublicationsException;
import pt.ist.sonet.exception.AlreadyVotedException;
import pt.ist.sonet.exception.FriendAlreadyExistsException;
import pt.ist.sonet.exception.FriendLimitExceededException;
import pt.ist.sonet.exception.IndividualsCantAcceptDonationsException;
import pt.ist.sonet.exception.LargaCaixaTransferException;
import pt.ist.sonet.exception.OnVoteLimitException;
import pt.ist.sonet.exception.OrgsCantSendFriendRequestException;
import pt.ist.sonet.exception.PagAmigoTransferException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.TargetAlreadySentRequestException;
import pt.ist.sonet.exception.TargetIsAlreadyFriendException;
import pt.ist.sonet.exception.YouAlreadySentRequestException;
import pt.ist.sonet.exception.YouArentAFriendException;
import pt.ist.sonet.service.dto.*;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SoNetServletAsync {
	void displaySonet(AsyncCallback<ListingDto> callback);
	void agentLogin(String username, String password, AsyncCallback<Boolean> callback) throws AgentUsernameDoesNotExistsException;
	void addNote(String username, String label, String text, AsyncCallback<Void> callback);
	void commentPublication(String username, int pubId, String text, AsyncCallback<Void> callback) throws OnVoteLimitException;
	void getFriends(String username, AsyncCallback<StringListDto> callback);
	void getPublicationList(String from, String asking, AsyncCallback<ArrayList<ApDto>> asyncCallback) throws YouArentAFriendException;
	void getAgents(AsyncCallback<StringListDto> asyncCallback);
	void listPendingRequests(String username, AsyncCallback<StringListDto> asyncCallback);
	void getOrganizationalAgents(AsyncCallback<StringListDto> asyncCallback);
	void makeDonation(String first, String second, int amount, String description, AsyncCallback<Void> callback) throws PagAmigoTransferException, AgentUsernameDoesNotExistsException, IndividualsCantAcceptDonationsException;
	void getPublicationComments(int pubId, AsyncCallback<StringListDto> asyncCallback);
	void negativeVote(String user, int pubId, AsyncCallback<Void> callback) throws AlreadyVotedException, OnVoteLimitException, AgentsCantVoteInTheirOwnPublicationsException;
	void positiveVote(String user, int pubId, AsyncCallback<Void> callback) throws AlreadyVotedException, OnVoteLimitException, AgentsCantVoteInTheirOwnPublicationsException;
	void viewPublication(String asking, int pubId, AsyncCallback<PublicationViewDto> asyncCallback)throws YouArentAFriendException, ApIdDoesNotExistsException;
	void getContent(String user, int pubId, AsyncCallback<Void> callback) throws PagAmigoTransferException, LargaCaixaTransferException;
	void getFriendRequestAgents(String user, AsyncCallback<StringListDto> callback) throws OrgsCantSendFriendRequestException, AgentUsernameDoesNotExistsException;
	void acceptRequest(String from, String to, AsyncCallback<Void> callback) throws AgentUsernameDoesNotExistsException, FriendLimitExceededException;
	void rejectRequest(String from, String to, AsyncCallback<Void> callback) throws AgentUsernameDoesNotExistsException;
	void sendRequest(String from, String to, AsyncCallback<Void> callback) throws AgentUsernameDoesNotExistsException, TargetAlreadySentRequestException, TargetIsAlreadyFriendException, YouAlreadySentRequestException, FriendLimitExceededException, FriendAlreadyExistsException;
	void loadLatestPublications(String user, AsyncCallback<StringListDto> callback);
	void init(String serverType, AsyncCallback<Void> callback);
}

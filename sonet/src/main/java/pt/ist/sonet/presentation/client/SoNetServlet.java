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
import pt.ist.sonet.exception.PublicationIdDoesNotExistsException;
import pt.ist.sonet.exception.TargetAlreadySentRequestException;
import pt.ist.sonet.exception.TargetIsAlreadyFriendException;
import pt.ist.sonet.exception.YouAlreadySentRequestException;
import pt.ist.sonet.exception.YouArentAFriendException;
import pt.ist.sonet.service.dto.PublicationDto;
import pt.ist.sonet.service.dto.PublicationViewDto;
import pt.ist.sonet.service.dto.StringListDto;

import pt.ist.sonet.service.dto.ListingDto;


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
	void commentPublication(String username, int pubId, String text) throws OnVoteLimitException;
	StringListDto getFriends(String username);
	StringListDto listPendingRequests(String username);
	StringListDto getAgents();
	ArrayList<PublicationDto> getPublicationList(String from, String asking) throws YouArentAFriendException;
	StringListDto getPublicationComments(int pubId);
	StringListDto getOrganizationalAgents();
	PublicationViewDto viewPublication(String asking, int pubId) throws YouArentAFriendException, PublicationIdDoesNotExistsException;
	void makeDonation(String first, String second, int amount, String description) throws PagAmigoTransferException, AgentUsernameDoesNotExistsException, IndividualsCantAcceptDonationsException;
	void positiveVote(String user, int pubId) throws AlreadyVotedException, OnVoteLimitException, AgentsCantVoteInTheirOwnPublicationsException;
	void negativeVote(String user, int pubId) throws AlreadyVotedException, OnVoteLimitException, AgentsCantVoteInTheirOwnPublicationsException;
	void getContent(String user, int pubId) throws PagAmigoTransferException, LargaCaixaTransferException;
	StringListDto getFriendRequestAgents(String user) throws OrgsCantSendFriendRequestException, AgentUsernameDoesNotExistsException;
	void acceptRequest(String from, String to) throws AgentUsernameDoesNotExistsException, FriendLimitExceededException;
	void rejectRequest(String from, String to) throws AgentUsernameDoesNotExistsException;
	void sendRequest(String from, String to) throws AgentUsernameDoesNotExistsException, TargetAlreadySentRequestException, TargetIsAlreadyFriendException, YouAlreadySentRequestException, FriendLimitExceededException, FriendAlreadyExistsException;
	StringListDto loadLatestPublications(String user);
}

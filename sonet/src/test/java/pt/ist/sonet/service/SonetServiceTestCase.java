package pt.ist.sonet.service;

import java.util.Set;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Individual;
import pt.ist.sonet.domain.Organizational;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.CommentDto;
import pt.ist.sonet.service.dto.StringListDto;
import junit.framework.TestCase;
import jvstm.Atomic;

public abstract class SonetServiceTestCase extends TestCase {
	
	static {
		if(FenixFramework.getConfig()==null) {
			FenixFramework.initialize(new Config() {{
				domainModelPath="src/main/dml/sonet.dml";
				dbAlias = "//localhost:3306/sonetdb"; 
				dbUsername = "sonet";
				dbPassword = "s0n3t";
				domainModelPath="src/main/dml/sonet.dml";
				rootClass=SoNet.class;
			}});
		}
	}

	protected SonetServiceTestCase(String msg){
		super(msg);
	}

	protected SonetServiceTestCase(){
		super();
	}

	protected void setUp() {
		//cleanSonet();
	}	


	protected void tearDown() {
		//cleanSonet();
	}

	//	@AfterClass
	//	public static void cleanDB(){
	//		System.out.println("cleanDB");
	//		cleanSonet();
	//	}
	//	
	//	@BeforeClass
	//	public static void init(){
	//		System.out.println("cleanSonet");
	//		cleanSonet();
	//	}

	protected GetAllPublicationsService getPublications(String requested, String requester, StringListDto pubdto) {
		return new GetAllPublicationsService(requested, requester, pubdto);
	}

	protected void addAgent(String type, String user, String name, String email, String pass, String city, String nation, String permission) {
		AgentDto agent = new AgentDto(type, user, name, email, pass, city, nation);
		agent.setPermission(permission);
		new RegisterAgentService(agent).execute();
	}

	protected void addFriend(String from, String to) {
		FriendRequestService request = new FriendRequestService(from, to);
		request.execute();
		ConfirmFriendRequestService confirm = new ConfirmFriendRequestService(from, to);
		confirm.execute();
	}

	protected RegisterAgentService registerAgent(String type, String user, String name, String email, String pass, String city, String nation, String permission) {
		AgentDto agent = new AgentDto(type, user, name, email, pass, city, nation);
		agent.setPermission(permission);		
		return new RegisterAgentService(agent);
	}

	protected void addNote(String text, String username, String label) {
		new AddNoteService(username, label, text).execute();
	}

    protected void addComment(String text, String username, int pubId ) {
		CommentDto comment = new CommentDto(username, pubId, text);
       	new AddCommentService(comment).execute();
    }
	
    protected void addNegativeVote(String user,int pubId ) {
		new NegativeVoteService(user, pubId).execute();
    }
	
    protected void addPositiveVote(String user,int pubId ) {
		new PositiveVoteService(user, pubId).execute();
    }
	
	protected Agent getAgent(String user) {
		SoNet rede = FenixFramework.getRoot();
		return rede.getAgentByUsername(user);
	}
	@Atomic
	protected int getAgentPubNumber(String user) {
		SoNet rede = FenixFramework.getRoot();
		Agent testUser=rede.getAgentByUsername(user);
		return testUser.getPublicationsCount();
	}

	@Atomic
	protected boolean checkRequest(String user, String isOnList){
		boolean pending = false;
		SoNet rede = FenixFramework.getRoot();
		Individual user1 = (Individual) rede.getAgentByUsername(user);
		if(user1==null)
			throw new AgentUsernameDoesNotExistsException(user);
		for(Individual req : user1.getPendingRequestSet())
			if(req.getUsername().equals(isOnList)){
				pending=true;
				break;
			}
		return pending;
	}

	@Atomic
	protected boolean checkExistingAgent(String user){
		SoNet rede = FenixFramework.getRoot();
		Agent user1 = rede.getAgentByUsername(user);
		if(user1==null){
			return false;
		}
		else return true;
	}

	@Atomic
	protected boolean checkIsFriend(String user, String toTest){
		Boolean friends = false;
		SoNet rede = FenixFramework.getRoot();
		Agent user1 = rede.getAgentByUsername(user);
		if(user1==null)
			throw new AgentUsernameDoesNotExistsException(user);
		Agent user2 = rede.getAgentByUsername(toTest);
		if(user2==null)
			throw new AgentUsernameDoesNotExistsException(toTest);
		friends = user1.isMyFriend(user2);
		return friends;
	}

	@Atomic
	protected void setSoNetFriendLimit(int i){
		SoNet rede = FenixFramework.getRoot();
		rede.setFriendLimit(i);
	}

	protected FriendRequestService friendRequest(String from, String to) {
		return new FriendRequestService(from, to);
	}


	protected ConfirmFriendRequestService confirmRequest(String from, String to) {
		return new ConfirmFriendRequestService(from, to);
	}


	protected RejectFriendRequestService rejectRequest(String from, String to) {
		return new RejectFriendRequestService(from, to);
	}

	@Atomic
	protected boolean existsOrganizationalAgent(String user) {
		boolean exist = false;
		SoNet rede = FenixFramework.getRoot();
		Organizational org = (Organizational) rede.getAgentByUsername(user);
		if(org == null) {
			throw new AgentUsernameDoesNotExistsException(user);
		}
		exist = true;
		return exist;
	}

	@Atomic
	protected static void cleanSonet() {

		SoNet trash = new SoNet();

		SoNet rede = FenixFramework.getRoot();
		Set<Individual> allIndividuals = rede.getIndividualSet();
		for(Individual i : allIndividuals){
			i.setSonet(trash);
		}
		Set<Organizational> allOrganizationals = rede.getOrganizationalSet();
		for(Organizational o : allOrganizationals){
			o.setSonet(trash);
		}
		Set<Publication> allPubs = rede.getPublicationsSet();
		for(Publication p : allPubs){
			p.setSonet(trash);
		}
		allPubs.clear();
		allIndividuals.clear();
		allOrganizationals.clear();
	}


}

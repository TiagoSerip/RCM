package pt.ist.sonet.service;

import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.OnVoteLimitException;
import pt.ist.sonet.exception.PublicationIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.TargetIsAlreadyFriendException;
import pt.ist.sonet.exception.YouArentAFriendException;
import pt.ist.sonet.service.dto.PublicationViewDto;

public class VotePublicationTestCase extends SonetServiceTestCase {

	private static String ORGANIZATIONAL = "Organizational";
	private static String INDIVIDUAL = "Individual";
	private static String INVALID_USER = "Jorge Jesus";
	private static String IND_AGENT = "Tiago Manuel";
	private static String IND_AGENT_USER = "tima";
	private static String IND_AGENT_EMAIL = "tima@mail.pt";
	private static String IND_AGENT_PASS = "123tima";
	private static String ORG_AGENT = "Sotinco";
	private static String ORG_AGENT_USER = "sotinco";
	private static String ORG_AGENT_EMAIL = "sotinco_organization@mail.pt";
	private static String ORG_AGENT_PASS = "sotincosorg1";
	private static String IND_AGENT_2 = "Maria José";
	private static String IND_AGENT_2_USER = "mariajose";
	private static String IND_AGENT_2_EMAIL = "mariajose@mail.pt";
	private static String IND_AGENT_2_PASS = "mapass";
	private static String IA1 = "Vitor Jose";
	private static String IA1U="vijo";
	private static String IA1E="vijo@mm.pt";
	private static String IA1P="123vijo";
	private static String IA2 = "Fafa de Arem";
	private static String IA2U="faar";
	private static String IA2E="faar@arem.pt";
	private static String IA2P="123faar";
	private static String IA3 = "Joana Maria";
	private static String IA3U="joma";
	private static String IA3E="joma@portugal.pt";
	private static String IA3P="123joma";
	private static String IA4 = "João Jose";
	private static String IA4U="jojose";
	private static String IA4E="jojose@chile.pt";
	private static String IA4P="123jojose";
	private static String IA5 = "Maria Couves";
	private static String IA5U="maco";
	private static String IA5E="maco@terra.pt";
	private static String IA5P="123terra";
	private static String IA6 = "Esdrovina";
	private static String IA6U="esdro";
	private static String IA6E="esdrovina@terra.pt";
	private static String IA6P="123es";
	private static String IA7 = "Luduvina";
	private static String IA7U="luda";
	private static String IA7E="luduvina@terra.pt";
	private static String IA7P="123lu";
	private static String IA8 = "Frederico Jose";
	private static String IA8U="fred";
	private static String IA8E="fredjose@ta.pt";
	private static String IA8P="123fe";
	private static String CITY = "LISBOA";
	private static String COUNTRY = "PORTUGAL";
	private static String PERMISSION_F = "amigo";
	private static String PERMISSION_P = "publica";
	private static String TEXT = "Bem-vindos a Sotinco";
	private static String TEXT_2 = "Sempre Sempre!!";
	private static String TEXT_3 = "Mesmo muitas coisas";
	private static String TEXT_4 = "Nao nao vai";
	private static String TEXT_5 = "certos acontecimentos coisas";
	private static String TEXT_6 = "nao gosto do facebook";
	private static String TEXT_7 = "podia ter sido";
	private static String LABEL = "Welcome";
	private static String LABEL_2 = "Tudo Tudo";
	private static String LABEL_3 = "Coisas";
	private static String LABEL_4 = "Algumas cenas";
	private static String LABEL_5 = "Talvez fosse";
	private static String LABEL_6 = "No face";
	private static String LABEL_7 = "Nao da";

	public VotePublicationTestCase() {
		super();
	}

	public VotePublicationTestCase(String msg) {
		super(msg);
	}

	@Override
	public void setUp() {
		super.setUp();
		try {
			addAgent(ORGANIZATIONAL, ORG_AGENT_USER, ORG_AGENT, ORG_AGENT_EMAIL, ORG_AGENT_PASS, CITY, COUNTRY, PERMISSION_P);
			addAgent(INDIVIDUAL, IND_AGENT_USER, IND_AGENT, IND_AGENT_EMAIL, IND_AGENT_PASS, CITY, COUNTRY, PERMISSION_P);
			addAgent(INDIVIDUAL, IND_AGENT_2_USER, IND_AGENT_2, IND_AGENT_2_EMAIL, IND_AGENT_2_PASS, CITY, COUNTRY, PERMISSION_P);
			addAgent(INDIVIDUAL, IA1U, IA1, IA1E, IA1P, CITY, COUNTRY, PERMISSION_P);
			addAgent(INDIVIDUAL, IA2U, IA2, IA2E, IA2P, CITY, COUNTRY, PERMISSION_P);
			addAgent(INDIVIDUAL, IA3U, IA3, IA3E, IA3P, CITY, COUNTRY, PERMISSION_P);
			addAgent(INDIVIDUAL, IA4U, IA4, IA4E, IA4P, CITY, COUNTRY, PERMISSION_P);
			addAgent(INDIVIDUAL, IA5U, IA5, IA5E, IA5P, CITY, COUNTRY, PERMISSION_P);
			addAgent(INDIVIDUAL, IA6U, IA6, IA6E, IA6P, CITY, COUNTRY, PERMISSION_P);
			addAgent(INDIVIDUAL, IA7U, IA7, IA7E, IA7P, CITY, COUNTRY, PERMISSION_F);
			addAgent(INDIVIDUAL, IA8U, IA8, IA8E, IA8P, CITY, COUNTRY, PERMISSION_F);
			addFriend(IND_AGENT_USER, IND_AGENT_2_USER);
		}
		catch (SoNetException e) {
		}
	}
	
	/**
	 * Adiciona um voto positivo e outro negativo a uma publicação. Testa se a diferenca de votos e nula
	 */
	public void testOneVoteOnPub() {

		// Arrange
		addNote(TEXT, ORG_AGENT_USER, LABEL);
		int tempPub;
		int PositiveVotesOnPub;
		int NegativeVotesOnPub;
		int result;
		boolean finalResult;
		GetMyLastPublicationService service = new GetMyLastPublicationService(ORG_AGENT_USER); 
		PublicationViewDto pub = null;		
		
		// Act
		try {
			service.execute();
			pub = service.getPublicationViewDto();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Username dont belong to any agent" + e);
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.getId();
		
		try {
			addNegativeVote(IND_AGENT_2_USER, tempPub);
			addPositiveVote(IND_AGENT_USER, tempPub);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		} catch (OnVoteLimitException e) {
		}
		
		pub = service.getPublicationViewDto();
		tempPub = pub.getId();
		PositiveVotesOnPub =  pub.getPositive();
		NegativeVotesOnPub =  pub.getNegative();
		result= PositiveVotesOnPub - NegativeVotesOnPub;

		finalResult = (result <= 0);
		
		// Assert
		assertTrue("The number of positive votes isn't correct", finalResult);
	}
	
	
	/**
	 * Testa a impossibilidade de votar numa publicacao controversa. Adiciona 5 votos negativos para a razao 
	 * entre negativos e positivos ser >5 e demonstra que nao e efectuado o ultimo voto
	 */
	public void testControversialVoteOnPub() {

		// Arrange
		addNote(TEXT_2, IND_AGENT_USER, LABEL_2);
		int tempPub;
		int PositiveVotesOnPub;
		int NegativeVotesOnPub;
		int result=0;
		GetMyLastPublicationService service = new GetMyLastPublicationService(IND_AGENT_USER); 
		PublicationViewDto pub = null;
		
		// Act
		try {
			service.execute();
			pub = service.getPublicationViewDto();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Username dont belong to any agent" + e);
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.getId();
		
		try {
			addNegativeVote(IA1U, tempPub);
			addNegativeVote(IA2U, tempPub);
			addNegativeVote(IA3U, tempPub);
			addNegativeVote(IA4U, tempPub);
			addNegativeVote(IA5U, tempPub);
			addPositiveVote(IA6U, tempPub);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}catch (OnVoteLimitException e) {
		}
		
		try{
			service.execute();
			pub = service.getPublicationViewDto();
			tempPub = pub.getId();
			PositiveVotesOnPub =  pub.getPositive();
			NegativeVotesOnPub =  pub.getNegative();
			result= PositiveVotesOnPub - NegativeVotesOnPub;
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}
		
		assertEquals(result, -5);
	}
	
	/**
	 * Testa se e possivel votar de modo positivo numa publicacao
	 */
	public void testPosVote() {

		// Arrange
		int tempPub;
		int PositiveVotesOnPub = 0;
		addNote(TEXT_5, IA4U, LABEL_5);
		GetMyLastPublicationService serviceLast = new GetMyLastPublicationService(IA4U); 
		PublicationViewDto pub = null;
		
		// Act
		try {
			serviceLast.execute();
			pub = serviceLast.getPublicationViewDto();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Username dont belong to any agent" + e);
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.getId();
		
		try {
			addPositiveVote(IND_AGENT_USER, tempPub);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}catch (OnVoteLimitException e) {
		}
		
		try{
			serviceLast.execute();
			pub = serviceLast.getPublicationViewDto();
			tempPub = pub.getId();
			PositiveVotesOnPub =  pub.getPositive();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}
		
		// Assert
		assertEquals(PositiveVotesOnPub, 1);
	}

	/**
	 * Testa se e possivel votar de modo negativo numa publicacao
	 */
	public void testNegVote() {

		// Arrange
		int tempPub;
		int NegativeVotesOnPub = 0;
		addNote(TEXT_4, IA5U, LABEL_4);
		GetMyLastPublicationService serviceLast = new GetMyLastPublicationService(IA5U); 
		PublicationViewDto pub = null;
		
		// Act
		try {
			serviceLast.execute();
			pub = serviceLast.getPublicationViewDto();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Username dont belong to any agent" + e);
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.getId();
		
		try {
			addNegativeVote(IND_AGENT_USER, tempPub);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		} catch (OnVoteLimitException e) {
		}
		
		try {
			serviceLast.execute();
			pub = serviceLast.getPublicationViewDto();
			tempPub = pub.getId();
			NegativeVotesOnPub =  pub.getNegative();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}
		
		// Assert
		assertEquals(NegativeVotesOnPub, 1);
	}
	
	/**
	 * Obtem uma publicacao com um id válido e tenta votar com um id invalido
	 */
	public void testInvalidUserVote() {

		// Arrange
		addNote(TEXT_3, IND_AGENT_2_USER, LABEL_3);
		int tempPub;
		boolean existAgent=true;
		GetMyLastPublicationService service = new GetMyLastPublicationService(IND_AGENT_2_USER); 
		PublicationViewDto pub = null;
		
		// Act
		try {
			service.execute();
			pub = service.getPublicationViewDto();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.getId();
		
		try {
			addNegativeVote(INVALID_USER, tempPub);
			fail("AgentUsernameDoesNotExistsException not caught: "	+ INVALID_USER);
		} catch (AgentUsernameDoesNotExistsException e) {
			existAgent = checkExistingAgent(e.toString());			
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}catch (OnVoteLimitException e) {
		}
		
		// Assert
		assertEquals(existAgent, false);
	}
	
	/**
	 * Testa o caso de votar positivamente numa publicacao que nao existe
	 */
	public void testPosVoteInvalidPub() {

		// Arrange
		int invPubId=99999999;
		boolean existPub=true;
		
		//act
		try {
			addPositiveVote(IND_AGENT_USER, invPubId);
			fail("PublicationIdDoesNotExistsException not caught: " + INVALID_USER);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("AgentUsernameDoesNotExistsException does not exist:" + e.toString());			
		} catch (PublicationIdDoesNotExistsException e) {
			existPub = false;			
		}catch (OnVoteLimitException e) {
			fail("OnVoteLimitException cannot vote on this pub:" + e.toString());
		}
				
		// Assert
		assertEquals(existPub, false);
	}
	
	/**
	 * Testa o caso de votar negativamente numa publicacao que nao existe
	 */
	public void testNegVoteInvalidPub() {

		// Arrange
		int invPubId=99999999;
		boolean existPub=true;

		// Act
		try {
			addNegativeVote(IND_AGENT_USER, invPubId);
			fail("PublicationIdDoesNotExistsException not caught: " + INVALID_USER);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("AgentUsernameDoesNotExistsException does not exist:" + e.toString());			
		} catch (PublicationIdDoesNotExistsException e) {
			existPub = false;			
		}catch (OnVoteLimitException e) {
			fail("OnVoteLimitException cannot vote on this pub:" + e.toString());
		}
		
		// Assert
		assertEquals(existPub, 	false);
	}

	/**
	 * Tenta votar numa publicacao de uma agente do qual nao e amigo
	 */
	public void testNonFriendPostiveVote() {

		// Arrange
		addNote(TEXT_6, IA7U, LABEL_6);
		int tempPub;
		boolean voteSucceeded=true;
		GetMyLastPublicationService service = new GetMyLastPublicationService(IA7U); 

		PublicationViewDto pub = null;
		
		// Act
		try {
			service.execute();
			pub = service.getPublicationViewDto();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.getId();
		
		try {
			addPositiveVote(IA8U, tempPub);
			fail("Agent has no permission to vote, permission exception not caught " + INVALID_USER);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		} catch (OnVoteLimitException e) {
		} catch(YouArentAFriendException e){
			voteSucceeded=false;
		}

		// Assert
		assertEquals(voteSucceeded,	false);
	}
	
	/**
	 * Tenta votar numa publicacao de um agente do qual nao e amigo
	 */
	public void testNonFriendNegativeVote() {

		// Arrange
		addNote(TEXT_6, IA7U, LABEL_6);
		int tempPub;
		boolean voteSucceeded=true;
		GetMyLastPublicationService service = new GetMyLastPublicationService(IA7U); 
		PublicationViewDto pub = null;

		// Act
		try {
			service.execute();
			pub = service.getPublicationViewDto();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.getId();
		
		try {
			addNegativeVote(IA8U, tempPub);
			fail("Agent has no permission to vote, permission exception not caught " + INVALID_USER);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		} catch (OnVoteLimitException e) {
		} catch(YouArentAFriendException e){
			voteSucceeded=false;
		}
		
		// Assert
		assertEquals(voteSucceeded,	false);
	}
	
	/**
	 * Tenta votar positivamente numa publicacao de uma agente do qual e amigo
	 */
	public void testPositiveFriendVote() {

		// Arrange
		addNote(TEXT_6, IA7U, LABEL_6);
	
		try{
			addFriend(IA7U, IA8U);
		}catch (TargetIsAlreadyFriendException e){
		}

		int tempPub;
		int PositiveVotesOnPub = 0;
		GetMyLastPublicationService service = new GetMyLastPublicationService(IA7U); 

		PublicationViewDto pub = null;
		
		// Act
		try {
			service.execute();
			pub = service.getPublicationViewDto();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.getId();
		
		try {
			addPositiveVote(IA8U, tempPub);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		} catch (OnVoteLimitException e) {
		} catch(YouArentAFriendException e){
			fail("You Arent A Friend" + e.toString());
		}
		
		try{
			service.execute();
			pub = service.getPublicationViewDto();
			tempPub = pub.getId();
			PositiveVotesOnPub =  pub.getPositive();
		}catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}			
		
		// Assert
		assertEquals(PositiveVotesOnPub, 1);
	}

	/**
	 * Tenta votar negativamente numa publicacao de uma agente do qual e amigo
	 */
	public void testNegativeFriendVote() {

		// Arrange
		addNote(TEXT_7, IA8U, LABEL_7);
		
		try{
			addFriend(IA5U, IA8U);
		}catch (TargetIsAlreadyFriendException e){
		}

		int tempPub;
		int NegativeVotesOnPub = 0;
		GetMyLastPublicationService service = new GetMyLastPublicationService(IA8U); 
		PublicationViewDto pub = null;

		// Act
		try {
			service.execute();
			pub = service.getPublicationViewDto();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.getId();
		
		try {
			addNegativeVote(IA5U, tempPub);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		} catch (OnVoteLimitException e) {
		} catch(YouArentAFriendException e){
			fail("You Arent A Friend" + e.toString());
		}
		
		try{
			service.execute();
			pub = service.getPublicationViewDto();
			tempPub = pub.getId();
			NegativeVotesOnPub =  pub.getNegative();
		}catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}
			
		// Assert
		assertEquals(NegativeVotesOnPub, 1);
	}

}


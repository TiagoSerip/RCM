package pt.ist.sonet.service;

import java.util.ArrayList;
import pt.ist.fenixframework.pstm.Transaction;
import pt.ist.sonet.domain.Note;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.exception.*;
import pt.ist.sonet.service.dto.StringListDto;

public class ShowAllPublicationsTestCase extends SonetServiceTestCase {


	private static String ORGANIZATIONAL = "Organizational";
	private static String INDIVIDUAL = "Individual";
	private static String IA1 = "Manel Camarinha";
	private static String IA1U="Mane";
	private static String IA1E="manecama@mm.pt";
	private static String IA1P="123mane";
	private static String IA2 = "Fafa de Belem";
	private static String IA2U="fafa";
	private static String IA2E="fafa@brasil.pt";
	private static String IA2P="123fafa";
	private static String IA3 = "Rita Joana";
	private static String IA3U="rija";
	private static String IA3E="rija@brasil.pt";
	private static String IA3P="123rija";
	private static String IA4 = "Joana João";
	private static String IA4U="jojo";
	private static String IA4E="jojo@brasil.pt";
	private static String IA4P="123jojo";
	private static String IA5 = "Maria Rosalina";
	private static String IA5U="maro";
	private static String IA5E="maro@brasil.pt";
	private static String IA5P="123maro";
	private static String INDIVIDUAL_AGENT = "Zeze Camarinha";
	private static String INDIVIDUAL_AGENT_USER = "zeze";
	private static String INDIVIDUAL_AGENT_EMAIL = "camarinha@mail.pt";
	private static String INDIVIDUAL_AGENT_PASS = "123camarinha";
	private static String ORGANIZATIONAL_AGENT = "Kikas";
	private static String ORGANIZATIONAL_AGENT_USER = "kikas";
	private static String ORGANIZATIONAL_AGENT_EMAIL = "kikas_organization@mail.pt";
	private static String ORGANIZATIONAL_AGENT_PASS = "kikasorg1";
	private static String INDIVIDUAL_AGENT_2 = "Osvaldo Costa";
	private static String INDIVIDUAL_AGENT_2_USER = "osvaldo";
	private static String INDIVIDUAL_AGENT_2_EMAIL = "osvaldocosta@mail.pt";
	private static String INDIVIDUAL_AGENT_2_PASS = "osvaldopassword";
	private static String CITY = "LISBOA";
	private static String COUNTRY = "PORTUGAL";
	private static String PERMISSION = "amigo";
	private static String PERMISSIONORG = "publica";
	private static String TEXT = "Bem-vindos a Kikas";
	private static String TEXT_2 = "Temos novos descontos!!";
	private static String TEXT_3 = "Agora leve 2 produtos e paga apenas 1";
	private static String TEXT_4 = "trovao";
	private static String LABEL = "Bem-vindos";
	private static String LABEL_2 = "Descontos";
	private static String LABEL_3 = "leve 2 pague 1";
	private static String LABEL_4 = "Muito muito mais";
	private static String COMMENT_TEST = "Muito bom";

	public ShowAllPublicationsTestCase() {
		super();
	}

	public ShowAllPublicationsTestCase(String msg) {
		super(msg);
	}

	@Override
	public void setUp() {
		super.setUp();
		try {
			addAgent(ORGANIZATIONAL, ORGANIZATIONAL_AGENT_USER, ORGANIZATIONAL_AGENT, ORGANIZATIONAL_AGENT_EMAIL,
					ORGANIZATIONAL_AGENT_PASS, CITY, COUNTRY, PERMISSIONORG);
			addAgent(INDIVIDUAL, INDIVIDUAL_AGENT_USER, INDIVIDUAL_AGENT, INDIVIDUAL_AGENT_EMAIL, 
					INDIVIDUAL_AGENT_PASS, CITY, COUNTRY, PERMISSION);
			addAgent(INDIVIDUAL, INDIVIDUAL_AGENT_2_USER, INDIVIDUAL_AGENT_2,INDIVIDUAL_AGENT_2_EMAIL, 
					INDIVIDUAL_AGENT_2_PASS, CITY, COUNTRY, PERMISSION);
			addAgent(INDIVIDUAL, IA1U, IA1, IA1E, IA1P, CITY, COUNTRY, PERMISSIONORG);
			addAgent(INDIVIDUAL, IA2U, IA2, IA2E, IA2P, CITY, COUNTRY, PERMISSIONORG);
			addAgent(INDIVIDUAL, IA3U, IA3, IA3E, IA3P, CITY, COUNTRY, PERMISSIONORG);
			addAgent(INDIVIDUAL, IA4U, IA4, IA4E, IA4P, CITY, COUNTRY, PERMISSIONORG);
			addAgent(INDIVIDUAL, IA5U, IA5, IA5E, IA5P, CITY, COUNTRY, PERMISSIONORG);
			addFriend(INDIVIDUAL_AGENT_USER, INDIVIDUAL_AGENT_2_USER);
			addNote(TEXT, ORGANIZATIONAL_AGENT_USER, LABEL);
			addNote(TEXT_2, ORGANIZATIONAL_AGENT_USER, LABEL_2);
			addNote(TEXT_3, ORGANIZATIONAL_AGENT_USER, LABEL_3);
			addNote(TEXT_4, INDIVIDUAL_AGENT_2_USER, LABEL_4);
		}
		catch (SoNetException e) {
		}
	}

	/**
	 * Lista as publicacoes de um agente organizacional com permissao do tipo
	 * publica Testa se a listagem foi feita com sucesso
	 */
	public void testListFromOrganizationalPublic() {
		
		// Arrange
		StringListDto dto = new StringListDto();
		GetAllApService testAllPubs = getPublications(ORGANIZATIONAL_AGENT_USER, INDIVIDUAL_AGENT_USER, dto);
		
		// act
		try {
			testAllPubs.execute();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Could not get pubs from unexisting agent: " + e.toString());
		} catch (YouArentAFriendException e) {
			fail("Could not get pubs from agent you're not friend: " + e.toString());

		}
		
		// Assert
		assertEquals(testAllPubs.getPublications().size(), 3);
	}

	/**
	 * Lista as publicacoes de um agente que nao existe Testa se o sistema lanca
	 * a excepcao de que o agente nao existe e nao lista publicacoes
	 */
	public void testListFromNonExistingOrganizational() {
		
		// Arrange
		boolean existAgent = true;
		StringListDto pubdto = new StringListDto();
		GetAllApService pubservice = new GetAllApService("tiago", INDIVIDUAL_AGENT_USER, pubdto);
		
		// act
		try {
			pubservice.execute();
			fail("AgentUsernameDoesNotExistsException not caught: " + INDIVIDUAL_AGENT_USER);
		} catch (AgentUsernameDoesNotExistsException e) {
			existAgent = checkExistingAgent(e.toString());
		}
		
		// Assert
		assertFalse("Agent must not exist", existAgent);
	}

	/**
	 * Verifica se as notas textuais sao adicionadas com sucesso no agente
	 * organizacional Testa o numero de notas antes e depois da adicao
	 */
	public void testCheckAddNoteOrganizational() {
	
		// Arrange
		StringListDto dto = new StringListDto();
		GetAllApService testAllPubs = getPublications(ORGANIZATIONAL_AGENT_USER, INDIVIDUAL_AGENT_USER, dto);
		int before = getAgentPubNumber(ORGANIZATIONAL_AGENT_USER);
		
		// act
		try {
			testAllPubs.execute();
			addNote(TEXT_4, ORGANIZATIONAL_AGENT_USER, LABEL_4);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Could not get pubs from unexisting agent: " + e.toString());
		} catch (YouArentAFriendException e) {
			fail("Could not get pubs from agent you're not friend: " + e.toString());

		}
		
		// Assert
		assertEquals(getAgentPubNumber(ORGANIZATIONAL_AGENT_USER), before + 1);
	}

	/**
	 * Verifica se as notas textuais sao adicionadas com sucesso no agente
	 * Individual Testa o numero de notas antes e depois da adicao
	 */
	public void testCheckAddNoteIndividual() {
		
		// Arrange
		
		int before = getAgentPubNumber(INDIVIDUAL_AGENT_USER);
		
		// act
		try {
			addNote(TEXT_4, INDIVIDUAL_AGENT_USER, LABEL_4);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Could not get pubs from unexisting agent: " + e.toString());
		} catch (YouArentAFriendException e) {
			fail("Could not get pubs from agent you're not friend: " + e.toString());
		}
		
		// Assert
		assertEquals(getAgentPubNumber(INDIVIDUAL_AGENT_USER), before + 1);
	}

	/**
	 * Tenta listar publicacoes de um agente do qual nao e amigo Testa se ha um
	 * agente que nao e amigo consegue listar as publicacoes de outro
	 */
	public void testListFromWhoAreNotFriend() {
		
		// Arrange
		boolean areTheyFriends = true;
		StringListDto dto = new StringListDto();
		GetAllApService testAllPubs = getPublications(INDIVIDUAL_AGENT_USER, ORGANIZATIONAL_AGENT_USER, dto);
		// act
		try {
			testAllPubs.execute();
			fail("They aren't friends the exception must be thrown");
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Could not get pubs from unexisting agent: " + e.toString());
		} catch (YouArentAFriendException e) {
			areTheyFriends = checkIsFriend(ORGANIZATIONAL_AGENT_USER, INDIVIDUAL_AGENT_USER);
		}
		
		// Assert
		assertFalse(
				"He could not list pubs becaus agent has friend permission ", areTheyFriends);
	}

	/**
	 * Lista as publicacoes de um agente individual com permissao do tipo
	 * publica Testa se a listagem foi feita com sucesso
	 */
	public void testListFromIndividualAgent() {

		// Arrange
		int pub = 0;
		StringListDto dto = new StringListDto();
		GetAllApService service = new GetAllApService(INDIVIDUAL_AGENT_2_USER, INDIVIDUAL_AGENT_USER, dto);

		// Act
		try {
			service.execute();
			pub = getAgentPubNumber(INDIVIDUAL_AGENT_2_USER);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Username dont belong to any agent" + e);
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}

		// Assert
		assertTrue("There are no publications", pub == 1);
	}

	/**
	 * Lista a publicacao de um agente Testa se o conteudo dessa publicacao esta
	 * correcto
	 */
	public void testContentOnListing() {

		// Arrange
		int pub1 = 0;
		String labelOnPub;
		String textOnPub;
		StringListDto dto = new StringListDto();
		GetAllApService service = new GetAllApService(INDIVIDUAL_AGENT_2_USER, INDIVIDUAL_AGENT_USER, dto);

		ArrayList<Publication> pub = null;

		// Act
		try {
			service.execute();
			Transaction.begin();
			pub1 = service.getPublications().size();
			pub = service.getPublications();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Username dont belong to any agent" + e);
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		labelOnPub = pub.get(0).getLabel();
		textOnPub = ((Note) pub.get(0)).getText();
		Transaction.commit();
		
		// Assert
		assertTrue("There are no publications", pub1 == 1);
		assertEquals("The content on Label wasn't correct", LABEL_4, labelOnPub);
		assertEquals("The content on text wasn't correct", TEXT_4, textOnPub);
	}

	/**
	 * Lista a publicacao e o comentario referente Testa se o conteudo dessa
	 * comentario esta correcto
	 */
	public void testCommentOnPub() {

		// Arrange
		int tempPub;
		String commentOnPub;
		StringListDto dto = new StringListDto();
		GetAllApService service = new GetAllApService(INDIVIDUAL_AGENT_2_USER, INDIVIDUAL_AGENT_USER, dto);

		ArrayList<Publication> pub = null;
		// Act
		try {
			service.execute();
			Transaction.begin();
			pub = service.getPublications();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Username dont belong to any agent" + e);
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.get(0).getId();
		Transaction.commit();
		
		try {
			addComment(COMMENT_TEST, INDIVIDUAL_AGENT_2_USER, tempPub);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		} catch (CanNotCommentException e) {
			fail("You can't comment this pub:" + e.toString());
		}

		Transaction.begin();
		commentOnPub = pub.get(0).getComments().get(0).getText();
		Transaction.commit();

		// Assert
		assertEquals("The content on Label wasn't correct", COMMENT_TEST, commentOnPub);
	}

	/**
	 * Lista a publicacao e numero de votos negativos Testa se o numero de votos
	 * esta correcto
	 */
	public void testNegativeVoteOnPub() {

		// Arrange
		int tempPub;
		int NegativeVotesOnPub;
		StringListDto dto = new StringListDto();
		GetAllApService service = new GetAllApService(INDIVIDUAL_AGENT_2_USER, INDIVIDUAL_AGENT_USER, dto);

		ArrayList<Publication> pub = null;

		// Act
		try {
			service.execute();
			Transaction.begin();
			pub = service.getPublications();

		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Username dont belong to any agent" + e);
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.get(0).getId();
		Transaction.commit();
		
		try {
			addNegativeVote(INDIVIDUAL_AGENT_USER, tempPub);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}

		Transaction.begin();
		NegativeVotesOnPub = pub.get(0).getNegVotes();
		Transaction.commit();

		// Assert
		assertEquals("The number of negative votes isn't correct", NegativeVotesOnPub, 1);
	}

	/**
	 * Lista a publicacao e numero de votos positivos Testa se o numero de votos
	 * esta correcto
	 */
	public void testPositiveVoteOnPub() {

		// Arrange
		int tempPub;
		int PositiveVotesOnPub;
		StringListDto dto = new StringListDto();
		GetAllApService service = new GetAllApService(ORGANIZATIONAL_AGENT_USER, INDIVIDUAL_AGENT_USER, dto);
		ArrayList<Publication> pub = null;
		
		// Act
		try {
			service.execute();
			Transaction.begin();
			pub = service.getPublications();
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Username dont belong to any agent" + e);
		} catch (YouArentAFriendException e) {
			fail("The usernames given dont belong to agents that are friends" + e);
		}
		
		tempPub = pub.get(0).getId();
		Transaction.commit();
		
		try {
			addPositiveVote(INDIVIDUAL_AGENT_USER, tempPub);
		} catch (AgentUsernameDoesNotExistsException e) {
			fail("Agent does not exist:" + e.toString());
		} catch (PublicationIdDoesNotExistsException e) {
			fail("Publication does not exist:" + e.toString());
		}

		Transaction.begin();
		PositiveVotesOnPub = pub.get(0).getPosVotes();
		Transaction.commit();

		// Assert
		assertEquals("The number of positive votes isn't correct", PositiveVotesOnPub, 1);
	}	
}
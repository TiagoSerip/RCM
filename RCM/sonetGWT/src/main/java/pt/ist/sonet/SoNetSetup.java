/**
 * 
 */
package pt.ist.sonet;

import jvstm.Atomic;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.FriendAlreadyExistsException;
import pt.ist.sonet.exception.NameAlreadyExistsException;
import pt.ist.sonet.exception.TargetIsAlreadyFriendException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.service.AddCommentService;
import pt.ist.sonet.service.AddContentService;
import pt.ist.sonet.service.AddNoteService;
import pt.ist.sonet.service.ConfirmFriendRequestService;
import pt.ist.sonet.service.FriendRequestService;
import pt.ist.sonet.service.RegisterAgentService;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.CommentDto;

/**
 * Popula a SoNet
 * @author ES Grupo 8
 *
 */
public class SoNetSetup {

	private static String ORGANIZATIONAL = "Organizational";
	private static String INDIVIDUAL = "Individual";
	private static String FRIEND = "amigo";
	private static String PUBLIC = "publica";
	
	

	/**
	 * Executa a SoNet e efectua os testes pedidos no enunciado.
	 * @param args
	 */
	public static void main(String[] args) {
		FenixFramework.initialize(new Config() {{
			dbAlias = "//localhost:3306/sonetdb"; 
			dbUsername = "sonet";
			dbPassword = "s0n3t";
			domainModelPath="src/main/dml/sonet.dml";
			rootClass=SoNet.class;
		}});

		System.out.println("Initializing SoNet...");

		SoNet rede = startSoNet();
		createTecnico(rede);
		System.out.println("----------");
		createIstFreeContent(rede);
		System.out.println("----------");
		createIstPaidContent(rede);
		System.out.println("----------");
		createBibNacional(rede);
		System.out.println("----------");
		createBNHobbitContent(rede);
		System.out.println("----------");
		createBNLusiadasContent(rede);
		System.out.println("----------");
		createBNMensagemContent(rede);
		System.out.println("----------");
		createMaria(rede);
		System.out.println("----------");
		mariaSendIst(rede);
		System.out.println("----------");
		createMariaNote(rede);
		System.out.println("----------");
		istCommentMaria(rede);
		System.out.println("----------");
		createZe(rede);
		System.out.println("----------");
		zeSendMaria(rede);
		System.out.println("----------");
		mariaAcceptZe(rede);
		System.out.println("----------");
		zeCommentMaria(rede);
		System.out.println("----------");
		createAlice(rede);
		System.out.println("----------");
		createBruno(rede);
		System.out.println("----------");
		createCarlos(rede);
		System.out.println("----------");
		System.out.println("SoNet Terminated.");
	}

	/**
	 * Inicializa a SoNet.
	 * 
	 * @return SoNet
	 */
	@Atomic
	public static SoNet startSoNet(){
		SoNet rede = FenixFramework.getRoot();
		return rede;
	}

	/**
	 * Tenta registar um agente organizacional.
	 * Resultado expectavel de sucesso no registo.
	 * 
	 * @param rede
	 */

	public static void createTecnico(SoNet rede){
		try{
			AgentDto agent = new AgentDto(ORGANIZATIONAL, "ist", "Técnico Lisboa", "info@ist.utl.pt", "t3cn1co", "Lisboa", "Portugal");
			agent.setPermission(PUBLIC);
			new RegisterAgentService(agent).execute();
		}catch (NameAlreadyExistsException e){
			System.out.println("Organization '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Organization 'Técnico Lisboa'.");

	}
	
	/**
	 * Tenta registar um agente organizacional.
	 * Resultado expectavel de sucesso no registo.
	 * 
	 * @param rede
	 */

	public static void createBibNacional(SoNet rede){
		try{
			AgentDto agent = new AgentDto(ORGANIZATIONAL, "bn", "Biblioteca Nacional", "info@bn.pt", "l1vr0s", "Lisboa", "Portugal");
			agent.setPermission(PUBLIC);
			new RegisterAgentService(agent).execute();
		}catch (NameAlreadyExistsException e){
			System.out.println("Organization '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Organization 'Biblioteca Nacional'.");

	}
	/**
	 * Tenta registar um novo agente idividual.
	 * 
	 * Resultado expectavel de sucesso no registo.
	 * @param rede
	 */
	public static void createZe(SoNet rede){
		try{
			AgentDto agent = new AgentDto(INDIVIDUAL, "zeninguem", "Zé Ninguém", "ze.ninguem@ist.utl.pt", "zeze", "Lisbon, NY", "USA");
			agent.setPermission(FRIEND);
			new RegisterAgentService(agent).execute();
		}catch (UsernameAlreadyExistsException e){
			System.out.println("Individual '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Individual 'Zé Ninguém'.");

	}
	/**
	 * Tenta registar um novo agente individual.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createMaria(SoNet rede){
		try{
			AgentDto agent = new AgentDto(INDIVIDUAL, "mariazinha", "Maria Silva", "maria.silva@ist.utl.pt", "****", "Oeiras", "Portugal");
			agent.setPermission(FRIEND);
			new RegisterAgentService(agent).execute();
		}catch (UsernameAlreadyExistsException e){
			System.out.println("Individual '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Individual 'Maria Silva'.");

	}
	
	/**
	 * Tenta registar um novo agente individual.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createAlice(SoNet rede){
		try{
			AgentDto agent = new AgentDto(INDIVIDUAL, "alice", "Alice Alves", "alice.alves@sonet.pt", "aaa", "Aveiro", "Portugal");
			agent.setPermission(FRIEND);
			new RegisterAgentService(agent).execute();
		}catch (UsernameAlreadyExistsException e){
			System.out.println("Individual '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Individual 'Alice Alves'.");

	}
	
	/**
	 * Tenta registar um novo agente individual.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createBruno(SoNet rede){
		try{
			AgentDto agent = new AgentDto(INDIVIDUAL, "bruno", "Bruno Bento", "bruno.bento@sonet.pt", "bbb", "Beja", "Portugal");
			agent.setPermission(FRIEND);
			new RegisterAgentService(agent).execute();
		}catch (UsernameAlreadyExistsException e){
			System.out.println("Individual '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Individual 'Bruno Bento'.");

	}
	
	/**
	 * Tenta registar um novo agente individual.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createCarlos(SoNet rede){
		try{
			AgentDto agent = new AgentDto(INDIVIDUAL, "carlos", "Carlos Calado", "carlos.calado@sonet.pt", "ccc", "Coimbra", "Portugal");
			agent.setPermission(FRIEND);
			new RegisterAgentService(agent).execute();
		}catch (UsernameAlreadyExistsException e){
			System.out.println("Individual '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Individual 'Carlos Calado'.");

	}

	/**
	 * Cria uma nova nota com a agente 'mariazinha'.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createMariaNote(SoNet rede){
		try{
			new AddNoteService("mariazinha", "Fim de Curso", "Acabei!").execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}

		System.out.println("Created a new Note!");
	}
	

	/**
	 * Cria um novo conteudo com o agente 'ist'.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createIstPaidContent(SoNet rede){
		try{
			new AddContentService("ist", "História do Técnico", "ISTory", 5).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}

		System.out.println("Created a new Content!");
	}
	
	/**
	 * Cria um novo conteudo com o agente 'ist'.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createIstFreeContent(SoNet rede){
		try{
			new AddContentService("ist", "Logotipo", "Tecnico", 0).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}

		System.out.println("Created a new Content!");
	}
	
	/**
	 * Cria um novo conteudo com o agente 'bn'.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createBNHobbitContent(SoNet rede){
		try{
			new AddContentService("bn", "Hobbit", "Tolkien", 10).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}

		System.out.println("Created a new Content!");
	}
	
	/**
	 * Cria um novo conteudo com o agente 'bn'.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createBNLusiadasContent(SoNet rede){
		try{
			new AddContentService("bn", "Os Lusíadas", "Camoes", 0).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}

		System.out.println("Created a new Content!");
	}
	
	/**
	 * Cria um novo conteudo com o agente 'bn'.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void createBNMensagemContent(SoNet rede){
		try{
			new AddContentService("bn", "A Mensagem", "Pessoa", 0).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}

		System.out.println("Created a new Content!");
	}
	
	/**
	 * Estabelece um pedido de amizade entre dois agentes individuais.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */

	public static void mariaSendIst(SoNet rede){
		try{
			FriendRequestService service = new FriendRequestService("mariazinha", "ist");
			service.execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (TargetIsAlreadyFriendException e){
			System.out.println("Agent '"+e.getConflictingName()+"' is already a friend of 'ist'.");
			return;
		}
		System.out.println("'mariazinha' is now friends with 'ist'.");
	}

	/**
	 * Estabelece um pedido de amizade entre dois agentes individuais.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */

	public static void zeSendMaria(SoNet rede){
		try{
			FriendRequestService service = new FriendRequestService("zeninguem", "mariazinha");
			service.execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (TargetIsAlreadyFriendException e){
			System.out.println("Agent '"+e.getConflictingName()+"' is already a friend of 'zeninguem'.");
			return;
		}
		System.out.println("'zeninguem' sent 'mariazinha' a friend request.");
	}
	
	/**
	 * Agente aceita pedido de amizade de outro.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */

	public static void mariaAcceptZe(SoNet rede){
		try{
			ConfirmFriendRequestService service = new ConfirmFriendRequestService("zeninguem", "mariazinha");
			service.execute();
			System.out.println("'mariazinha' is now friends with 'zeninguem'.");
			return;
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (FriendAlreadyExistsException e){
			System.out.println("'mariazinha' is already friends with 'zeninguem'.");
		}
	}
	
	/**
	 * Comenta publicacao
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */

	public static void istCommentMaria(SoNet rede){
		try{
			AddCommentService service = new AddCommentService(new CommentDto("ist", 5, "Parabéns Maria!"));
			service.execute();
			System.out.println("'ist' posted a comment on 'mariazinha' note.");
			return;
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (FriendAlreadyExistsException e){
			System.out.println("'mariazinha' is already friends with 'zeninguem'.");
		}
	}
	
	/**
	 * Comenta publicacao
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */

	public static void zeCommentMaria(SoNet rede){
		try{
			AddCommentService service = new AddCommentService(new CommentDto("zeninguem", 5, "Parabéns Maria!"));
			service.execute();
			System.out.println("'ist' posted a comment on 'mariazinha' note.");
			return;
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (FriendAlreadyExistsException e){
			System.out.println("'mariazinha' is already friends with 'zeninguem'.");
		}
	}

}

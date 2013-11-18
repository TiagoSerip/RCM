package pt.ist.sonet.presentationserver;

import jvstm.Atomic;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.*;
import pt.ist.sonet.service.*;
import pt.ist.sonet.service.bridge.PagAmigoServerBridge;
import pt.ist.sonet.service.bridge.PagAmigoServerLocal;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.CommentDto;
import pt.ist.sonet.service.dto.ListingDto;
import pt.ist.sonet.service.dto.PaymentDto;
import pt.ist.sonet.service.dto.StringListDto;

/**
 * PresentationServer. Esta class implementa a rede social SoNet
 * via servicos e executa os casos de teste fornecidos no enunciado.
 * @author ES Grupo 8
 *
 */
public class PresentationServer {

	private static String ORGANIZATIONAL = "Organizational";
	private static String INDIVIDUAL = "Individual";
	private static String FRIEND = "amigo";
	private static String PUBLIC = "publica";
	private static PagAmigoServerBridge paga = new PagAmigoServerLocal();

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
		scene1(rede);
		System.out.println("----------");
		scene2(rede);
		System.out.println("----------");
		scene3(rede);
		System.out.println("----------");
		scene4(rede);
		System.out.println("----------");
		listAgents(rede);
		System.out.println("----------");
		scene6(rede);
		System.out.println("----------");
		scene7(rede);
		System.out.println("----------");
		scene8(rede);
		System.out.println("----------");
		scene9(rede);
		System.out.println("----------");
		scene10(rede);
		System.out.println("----------");
		scene11(rede);
		System.out.println("----------");
		scene12(rede);
		System.out.println("----------");
		scene13(rede);
		System.out.println("----------");
		listAgents(rede);
		System.out.println("----------");
		System.out.println("SoNet Terminated.");
		extras(rede);

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
	 * Lista todos os agentes da SoNet, suas publicacoes e comentarios.
	 * 
	 * @param rede
	 */
	public static void listAgents(SoNet rede){
		ListingDto dto = new ListingDto();
		new ListAllService(dto).execute();
		System.out.println(dto.getlisting());
	}

	/**
	 * Tenta registar um agente organizacional com um nome ja' existente.
	 * 
	 * Resultado expectavel de erro por nome ja' em uso.
	 * @param rede
	 */
	public static void scene1(SoNet rede){
		try{
			AgentDto agent = new AgentDto(ORGANIZATIONAL, "tecnico", "Técnico Lisboa", "mail@ist.utl.pt", "eIeSeIST", "Lisboa", "Portugal");
			agent.setPermission(PUBLIC);
			new RegisterAgentService(agent).execute();
		}catch (NameAlreadyExistsException e){
			System.out.println("Organization '"+e.getConflictingName()+"' already exists.");
			return;
		}catch (UsernameAlreadyExistsException e){
			System.out.println("'"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Organization 'Técnico Lisboa'.");
	}

	/**
	 * Tenta registar um agente com um username ja' existente.
	 * 
	 * Resultado expectavel de erro por username ja' estar em uso.
	 * @param rede
	 */
	public static void scene2(SoNet rede){
		try{
			AgentDto agent = new AgentDto(INDIVIDUAL, "zeninguem", "Zé Ninguém", "ze.ninguem@ist.utl.pt", "zeze", "Lisboa", "Portugal");
			agent.setPermission(FRIEND);
			new RegisterAgentService(agent).execute();
		}catch (UsernameAlreadyExistsException e){
			System.out.println("Individual '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Individual 'Zé Ninguém'.");

	}

	/**
	 * Agente zeninguem faz um comentario numa publicacao de outro agente.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void scene3(SoNet rede){
		try{
			CommentDto comment = new CommentDto("zeninguem", 0, "Congratulations, Maria!");
			new AddCommentService(comment).execute();
			System.out.println("Your comment was posted.");
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}
		catch (YouArentAFriendException e){
			System.out.println("You don't have permission to post this comment.\n"+e.reason());
			return;
		}catch (CanNotCommentException e){
			System.out.println(e.toString());
			return;
		}

	}

	/**
	 * Agente ist faz um comentario numa publicacao de outro agente.
	 * 
	 * Resultado expectavel de insucesso.
	 * @param rede
	 */
	public static void scene4(SoNet rede){
		try{
			CommentDto comment = new CommentDto("ist", 0, "Parabéns, Maria!");
			new AddCommentService(comment).execute();
			System.out.println("Your comment was posted.");
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}
		catch (YouArentAFriendException e){
			System.out.println("You don't have permission to post this comment.\n"+e.reason());
			return;
		}catch (CanNotCommentException e){
			System.out.println(e.toString());
			return;
		}

	}

	/**
	 * Estabelece um pedido de amizade entre dois agentes individuais.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void scene6(SoNet rede){
		try{
			new FriendRequestService("zeninguem", "mariazinha").execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (TargetIsAlreadyFriendException e){
			System.out.println("Agent '"+e.getConflictingName()+"' is already a friend of 'zeninguem'.");
			return;
		}catch (FriendAlreadyExistsException e){
			System.out.println("Agent '"+e.getConflictingName()+"' is already a friend of 'zeninguem'.");
			return;
		}catch (FriendLimitExceededException e){
			System.out.println("Agent '"+e.getUsername()+"' exceded the limit of friends.");
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
	public static void scene7(SoNet rede){
		try{
			new ConfirmFriendRequestService("zeninguem", "mariazinha").execute();
			System.out.println("'mariazinha' is now friends with 'zeninguem'.");
			return;
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (FriendAlreadyExistsException e){
			System.out.println("'mariazinha' is already friends with 'zeninguem'.");
		}catch (FriendLimitExceededException e){
			System.out.println("Agent '"+e.getUsername()+"' exceded the limit of friends.");
			return;
		}
	}

	public static void scene8(SoNet rede){
		try{
			new FriendRequestService("mariazinha", "ist").execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (TargetIsAlreadyFriendException e){
			System.out.println("Agent '"+e.getConflictingName()+"' is already a friend of 'zeninguem'.");
			return;
		}catch (FriendAlreadyExistsException e){
			System.out.println("Agent '"+e.getConflictingName()+"' is already a friend of 'zeninguem'.");
			return;
		}catch (FriendLimitExceededException e){
			System.out.println("Agent '"+e.getUsername()+"' exceded the limit of friends.");
			return;
		}
		System.out.println("'mariazinha' sent 'ist' a friend request.");
	}

	public static void scene9(SoNet rede){
		scene3(rede);
	}

	public static void scene10(SoNet rede){
		scene4(rede);
	}

	/**
	 * Agente maria faz um comentario numa publicacao sua.
	 * 
	 * Resultado expectavel de insucesso.
	 * @param rede
	 */
	public static void scene11(SoNet rede){
		try{
			CommentDto comment = new CommentDto("mariazinha", 0, "Obrigada!");
			new AddCommentService(comment).execute();
			System.out.println("Your comment was posted.");
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}
		catch (YouArentAFriendException e){
			System.out.println("You don't have permission to post this comment.\n"+e.reason());
			return;
		}catch (CanNotCommentException e){
			System.out.println(e.toString());
			return;
		}

	}


	/**
	 * Cria uma nova nota com a agente 'zeninguem'.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void scene12(SoNet rede){
		try{
			new AddNoteService("zeninguem", "Maria's Graduation!", "Congratulations!").execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}

		System.out.println("Created a new Note!");
	}

	public static void scene13(SoNet rede){
		try{
			CommentDto comment = new CommentDto("mariazinha", 3, "Thanks!");
			new AddCommentService(comment).execute();
			System.out.println("Your comment was posted.");
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}
		catch (YouArentAFriendException e){
			System.out.println("You don't have permission to post this comment.\n"+e.reason());
			return;
		}catch (CanNotCommentException e){
			System.out.println(e.toString());
			return;
		}

	}

	public static void extras(SoNet rede){
		System.out.println(">>>>>>>>>><<<<<<<<<<\n-- Let's do some extra stuff --\n----------");
		votaPropria(rede);
		System.out.println("----------");
		votaOutroSemPermissao(rede);
		System.out.println("----------");
		votaOutro(rede);
		System.out.println("----------");
		listAgents(rede);
		System.out.println("----------");
		pagaSucesso(rede);
		System.out.println("----------");
		pagaInsucesso(rede);
		System.out.println("----------");
		verZeIst(rede);
		System.out.println("----------");
		verMariaZe(rede);
		System.out.println("----------");
	}


	/**
	 * Tenta votar com um agente numa das suas proprias publicacoes.
	 * 
	 * Resultado expectavel de erro por violacao de regras de negocio.
	 * @param rede
	 */

	public static void votaPropria(SoNet rede){
		try{
			new PositiveVoteService("mariazinha", 0).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}catch (YouArentAFriendException e){
			System.out.println("You don't have permission to vote on this publication.\n"+e.reason());
			return;
		}catch (AlreadyVotedException e){
			System.out.println("Agent already voted on this publication.\nAction made by "+e.getAlreadyVotedId());
			return;
		}catch (AgentsCantVoteInTheirOwnPublicationsException e){
			System.out.println("Agents can't vote on their own publications.\nAction made by "+e.getWhoCanVoteId());
			return;
		}
		System.out.println("Vote Acepted.");

	}

	/**
	 * Tentar votar com um agente numa publicacao de outro.
	 * 
	 * Resultado expectavel de erro por falta de premissao de acesso.
	 * @param rede
	 */
	public static void votaOutroSemPermissao(SoNet rede){
		try{
			new PositiveVoteService("ist", 1).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}catch (YouArentAFriendException e){
			System.out.println("You don't have permission to vote on this publication.\n"+e.reason());
			return;
		}catch (AlreadyVotedException e){
			System.out.println("Agent already voted on this publication.\nAction made by "+e.getAlreadyVotedId());
			return;
		}catch (AgentsCantVoteInTheirOwnPublicationsException e){
			System.out.println("Agents can't vote on their own publications.\nAction made by "+e.getWhoCanVoteId());
			return;
		}
		System.out.println("Vote Acepted.");
		//System.out.println("Vote Acepted. Positive votes are now: "+rede.getPublicationById(0).getPosVotes());

	}



	/**
	 * Tentar votar com um agente numa publicacao de outro.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	public static void votaOutro(SoNet rede){
		try{
			new PositiveVoteService("zeninguem", 0).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}catch (YouArentAFriendException e){
			System.out.println("You don't have permission to vote on this publication.\n"+e.reason());
			return;
		}catch (AgentsCantVoteInTheirOwnPublicationsException e){
			System.out.println("Agents can't vote on their own publications.\nAction made by "+e.getWhoCanVoteId());
			return;
		}catch (AlreadyVotedException e){
			System.out.println("Agent already voted on this publication.\nAction made by "+e.getAlreadyVotedId());
			return;
		}
		//System.out.println("Vote Acepted. Positive votes are now: "+rede.getPublicationById(0).getPosVotes());
		System.out.println("Vote Acepted.");
	}


	/**
	 * Testa um pagamento com sucesso
	 * @param rede
	 */
	public static void pagaSucesso(SoNet rede){
		//ApplicationServerBridge paga = new LocalApplicationServer();
		try{
			new ProcessPaymentService(paga, new PaymentDto("mariazinha", "zeninguem", "Aqui tens o troco.", 75)).execute();
			//new ProcessPaymentService("mariazinha", "zeninguem", 75, "Aqui tens o troco.").execute();
			System.out.println("Your transfer was placed with success.");
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PagAmigoTransferException e){
			System.out.println("Failed to transfer the amount of "+e.getAmount()+" from '"+e.getFrom()+"' to '"+e.getTo()+"'.");
		}
	}

	/**
	 * Testa um pagamento sem sucesso
	 * @param rede
	 */
	public static void pagaInsucesso(SoNet rede){
		try{
			new ProcessPaymentService(paga, new PaymentDto("mariazinha", "zeninguem", "Aqui tens o troco.", 205)).execute();
			//new ProcessPaymentService("mariazinha", "zeninguem", 205, "Aqui tens o troco.").execute();
			System.out.println("Your transfer was placed with success.");
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PagAmigoTransferException e){
			System.out.println("Failed to transfer the amount of "+e.getAmount()+" from '"+e.getFrom()+"' to '"+e.getTo()+"'.");
		}
	}


	/**
	 * Obtem a listagem de um agente sem permissao de acesso
	 * @param rede
	 */
	public static void verZeIst(SoNet rede){
		try{StringListDto dto = new StringListDto();
		new GetAllPublicationsService("zeninguem", "ist", dto).execute();
		System.out.println(dto.getlisting());
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (YouArentAFriendException e){
			System.out.println("You don't have permission to access this contents.\n"+e.reason());
			return;
		}
	}

	/**
	 * Obtem a listagem de um ganete com permissao
	 * @param rede
	 */
	public static void verMariaZe(SoNet rede){
		try{StringListDto dto = new StringListDto();
		new GetAllPublicationsService("mariazinha", "zeninguem", dto).execute();
		System.out.println(dto.getlisting());
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (YouArentAFriendException e){
			System.out.println("You don't have permission to access this contents.\n"+e.reason());
			return;
		}
	}



}



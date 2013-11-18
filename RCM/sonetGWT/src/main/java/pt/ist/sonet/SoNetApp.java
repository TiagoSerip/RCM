package pt.ist.sonet;

import jvstm.Atomic;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Individual;
import pt.ist.sonet.domain.Note;
import pt.ist.sonet.domain.Organizational;
import pt.ist.sonet.domain.Publication;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.AgentsCantVoteInTheirOwnPublicationsException;
import pt.ist.sonet.exception.AlreadyVotedException;
import pt.ist.sonet.exception.FriendAlreadyExistsException;
import pt.ist.sonet.exception.NameAlreadyExistsException;
import pt.ist.sonet.exception.PublicationIdDoesNotExistsException;
import pt.ist.sonet.exception.TargetIsAlreadyFriendException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.exception.YouArentAFriendException;

/**
 * SoNet Application. Esta class implementa a rede social
 * SoNet e executa os casos de teste fornecidos no enunciado.
 * @author ES Grupo 8
 *
 */
public class SoNetApp {

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
		scene1part1(rede);
		System.out.println("----------");
		scene1part2(rede);
		System.out.println("----------");
		scene2part1(rede);
		System.out.println("----------");
		scene2part2(rede);
		System.out.println("----------");
		scene3(rede);
		System.out.println("----------");
		scene4(rede);
		System.out.println("----------");
		scene5(rede);
		System.out.println("----------");
		scene6(rede);
		System.out.println("----------");
		listAgents(rede);
		System.out.println("----------");
		scene7(rede);
		System.out.println("----------");
		scene8(rede);
		System.out.println("----------");
		scene9(rede);
		System.out.println("----------");
		listAgents(rede);
		System.out.println("----------");
		scene10(rede);
		System.out.println("----------");
		listAgents(rede);
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
	@Atomic
	public static void scene1part1(SoNet rede){
		try{
			rede.addOrganizational(rede.createOrganizationalAgent("ist", "IST", "mail@ist.utl.pt", "fenix", "Lisboa", "Portugal", "publica"));
		}catch (NameAlreadyExistsException e){
			System.out.println("Organization '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Organization 'IST'.");

	}
	/**
	 * Tenta registar um agente organizacional com um nome ja' existente.
	 * 
	 * Resultado expectavel de erro por nome ja' em uso.
	 * @param rede
	 */
	@Atomic
	public static void scene1part2(SoNet rede){
		try{
			rede.addOrganizational(rede.createOrganizationalAgent("tecnico", "IST", "mail@ist.pt", "eIeSeIST", "Lisboa", "Portugal", "publica"));
		}catch (NameAlreadyExistsException e){
			System.out.println("Organization '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Organization 'IST'.");
	}
	
	
	/**
	 * Lista todos os agentes da SoNet, suas publicacoes e comentarios.
	 * 
	 * @param rede
	 */
	@Atomic
	public static void listAgents(SoNet rede){
		if(rede.getIndividualSet().isEmpty() && rede.getOrganizationalSet().isEmpty()){
			System.out.println("There are no registered Agents.");
			return;
		}
		System.out.println("Agent Listing (Type | username | Name | email | Location):");
		for(Individual i : rede.getIndividualSet()){
			System.out.println(i.toString());
			System.out.println(i.myPublicationsToString());
		}
		for(Organizational o : rede.getOrganizationalSet()){
			System.out.println(o.toString());
			System.out.println(o.myPublicationsToString());
		}
		System.out.println("----- End of List -----");
	}

	/**
	 * Tenta registar um novo agente idividual.
	 * 
	 * Resultado expectavel de sucesso no registo.
	 * @param rede
	 */
	@Atomic
	public static void scene2part1(SoNet rede){
		try{
			rede.addIndividual(rede.createIndividualAgent("zeninguem", "Zé Ninguém", "ze.ninguem@ist.utl.pt", "zeze", "Lisboa", "Portugal", "publica"));
		}catch (UsernameAlreadyExistsException e){
			System.out.println("Individual '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Individual 'Zé Ninguém'.");

	}

	/**
	 * Tenta registar um agente com um username ja' existente.
	 * 
	 * Resultado expectavel de erro por username ja' estar em uso.
	 * @param rede
	 */
	@Atomic
	public static void scene2part2(SoNet rede){
		try{
			rede.addIndividual(rede.createIndividualAgent("zeninguem", "Zé Ninguém", "ze.ninguem@ist.utl.pt", "zeze", "Lisboa", "Portugal", "publica"));
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
	@Atomic
	public static void scene3(SoNet rede){
		try{
			rede.addIndividual(rede.createIndividualAgent("mariazinha", "Maria Silva", "maria.silva@ist.utl.pt", "****", "Porto", "Portugal", "publica"));
		}catch (UsernameAlreadyExistsException e){
			System.out.println("Individual '"+e.getConflictingName()+"' already exists.");
			return;
		}
		System.out.println("Created Individual 'Maria Silva'.");

	}

	/**
	 * Cria uma nova nota com a agente 'mariazinha'.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	@Atomic
	public static void scene4(SoNet rede){
		try{
			Agent maria = rede.getAgentByUsername("mariazinha");
			if(maria==null)
				throw new AgentUsernameDoesNotExistsException("mariazinha");
			Note note = rede.createNote("Olá Mundo!", maria, "Olá Mundo!");
			rede.addPublications(note);
			maria.addPublications(note);
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}
		
		
		
		System.out.println("Created a new Note!");
	}

	/**
	 * Tenta votar com um agente numa das suas proprias publicacoes.
	 * 
	 * Resultado expectavel de erro por violacao de regras de negocio.
	 * @param rede
	 */
	@Atomic
	public static void scene5(SoNet rede){
		try{
			Agent maria = rede.getAgentByUsername("mariazinha");
			if(maria==null)
				throw new AgentUsernameDoesNotExistsException("mariazinha");
			Publication note = rede.getPublicationById(0);
			if(note==null)
				throw new PublicationIdDoesNotExistsException(0);
			maria.positiveVote(note, rede.getVoteLimit());
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}catch (YouArentAFriendException e){
			System.out.println("You don't have permission to post this comment.\n"+e.reason());
			return;
		}catch (AlreadyVotedException e){
			System.out.println("Agent already voted on this publication.\nAction made by "+e.getAlreadyVotedId());
			return;
		}catch (AgentsCantVoteInTheirOwnPublicationsException e){
			System.out.println("Agents can't vote on their own publications.\nAction made by "+e.getWhoCanVoteId());
			return;
		}
		System.out.println("Vote Acepted. Positive votes are now: "+rede.getAgentByUsername("mariazinha").getPublicationsSet().iterator().next().getPosVotes());

	}

	/**
	 * Tentar votar com um agente numa publicacao de outro.
	 * 
	 * Resultado expectavel de erro por falta de premissao de acesso.
	 * @param rede
	 */
	@Atomic
	public static void scene6(SoNet rede){
		try{
			Agent ze = rede.getAgentByUsername("zeninguem");
			if(ze==null)
				throw new AgentUsernameDoesNotExistsException("zeninguem");
			Publication note = rede.getPublicationById(0);
			if(note==null)
				throw new PublicationIdDoesNotExistsException(0);
			ze.positiveVote(note, rede.getVoteLimit());
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}catch (YouArentAFriendException e){
			System.out.println("You don't have permission to post this comment.\n"+e.reason());
			return;
		}catch (AlreadyVotedException e){
			System.out.println("Agent already voted on this publication.\nAction made by "+e.getAlreadyVotedId());
			return;
		}catch (AgentsCantVoteInTheirOwnPublicationsException e){
			System.out.println("Agents can't vote on their own publications.\nAction made by "+e.getWhoCanVoteId());
			return;
		}
		System.out.println("Vote Acepted. Positive votes are now: "+rede.getPublicationById(0).getPosVotes());

	}

	/**
	 * Estabelece um pedido de amizade entre dois agentes individuais.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	@Atomic
	public static void scene7(SoNet rede){
		try{
			Individual ze = (Individual)rede.getAgentByUsername("zeninguem");
			if(ze==null)
				throw new AgentUsernameDoesNotExistsException("zeninguem");
			Individual maria = (Individual)rede.getAgentByUsername("mariazinha");
			if(maria==null)
				throw new AgentUsernameDoesNotExistsException("mariazinha");
			ze.sendFriendRequest(maria);
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
	@Atomic
	public static void scene8(SoNet rede){
		try{
			Individual maria = (Individual)rede.getAgentByUsername("mariazinha");
			if(maria==null)
				throw new AgentUsernameDoesNotExistsException("mariazinha");
			if(!maria.hasAnyPendingRequest()){
				System.out.println("There are no pending friend requests.");
				return;
			}
			
			for(Individual req : maria.getPendingRequestSet()){
				if(req.getUsername().equals("zeninguem")){
					maria.acceptRequest(req);
					System.out.println("'"+maria.getName()+"' is now friends with '"+req.getName()+"'.");
					return;
				}
			}
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
	 * Tentar votar com um agente numa publicacao de outro.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	@Atomic
	public static void scene9(SoNet rede){
		try{
			Agent ze = rede.getAgentByUsername("zeninguem");
			if(ze==null)
				throw new AgentUsernameDoesNotExistsException("zeninguem");
			Publication note = rede.getPublicationById(0);
			if(note==null)
				throw new PublicationIdDoesNotExistsException(0);
			ze.positiveVote(note, rede.getVoteLimit());
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("Agent '"+e.getUsername()+"' doesn't exists.");
			return;
		}catch (PublicationIdDoesNotExistsException e){
			System.out.println("There is no Publication with ID: '"+e.getid()+"'.");
			return;
		}catch (YouArentAFriendException e){
			System.out.println("You don't have permission to post this comment.\n"+e.reason());
			return;
		}catch (AgentsCantVoteInTheirOwnPublicationsException e){
			System.out.println("Agents can't vote on their own publications.\nAction made by "+e.getWhoCanVoteId());
			return;
		}catch (AlreadyVotedException e){
			System.out.println("Agent already voted on this publication.\nAction made by "+e.getAlreadyVotedId());
			return;
		}
		System.out.println("Vote Acepted. Positive votes are now: "+rede.getPublicationById(0).getPosVotes());

	}

	/**
	 * Um agente faz um comentario numa publicacao de outro agente.
	 * 
	 * Resultado expectavel de sucesso.
	 * @param rede
	 */
	@Atomic
	public static void scene10(SoNet rede){
		try{
			Agent ze = rede.getAgentByUsername("zeninguem");
			if(ze==null)
				throw new AgentUsernameDoesNotExistsException("zeninguem");
			Publication note = rede.getPublicationById(0);
			if(note==null)
				throw new PublicationIdDoesNotExistsException(0);
			ze.commentPublication(note, "Bem-vinda Maria!");
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
		}


	}

}



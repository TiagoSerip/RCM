package pt.ist.sonet.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pt.ist.sonet.exception.*;

/**
 * SoNet e uma rede social com agentes de diferentes tipos e que podem estabelecer
 * ligacoes entre si, criar pubicacoes e comenta-las. Existem restricoes de acesso
 * a determinadas informacoes por alguns agentes.
 * 
 * @author ES Grupo 8
 */
public class SoNet extends SoNet_Base implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public SoNet() {
		super();
	}

	/**
	 * Obtem um agente individual pelo username
	 * 
	 * @param user
	 * @return Individual ou null se nao existir
	 */
	public Agent getAgentByUsername(String user) {
		for(Agent a : getAgentSet()) {
			if(a.getUsername().equalsIgnoreCase(user)) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Obtem um agente organizacional pelo nome
	 * 
	 * @param name
	 * @return Organizational ou null se nao existir
	 */
	public Agent getAgentByName(String name) {
		for(Agent a : getAgentSet()) {
			if(a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Verifica se existe um agent com um dado username
	 * 
	 * @param name
	 * @return boolean com o resultado
	 */
	public boolean hasAgentByUsername(String user) {
		return getAgentByUsername(user) != null;
	}


	/**
	 * Cria um novo agente individual na rede.
	 * Verifica a validade dos dados de utilizador de acordo com
	 * as regras de negocio estabelecidas. Lanca excepcoes caso
	 * existam dados invalidos.
	 * 
	 * @param username
	 * @param name
	 * @param email
	 * @param password
	 * @param city
	 * @param country
	 * @return Individual instacia de agente Individual criada.
	 * @throws UsernameAlreadyExistsException
	 */
	public Agent createAgentString (String user, String pass, String name, int ap, int rssi, String ip)
					throws UsernameAlreadyExistsException {
		
		if(this.hasAgentByUsername(user))
			throw new UsernameAlreadyExistsException(user);

		Agent agent = new Agent();
		agent.init(user, pass, name, ap, rssi, ip);
		return agent;
	}

	@Override
	public void addAgent(Agent someone) {
		super.addAgent(someone);
	}
	
	/**
	 * Remove um agente da base de dados da SoNet.
	 * Lanca excepcao em caso de username inexistente.
	 * 
	 * @param username
	 * @throws AgentUsernameDoesNotExistsException
	 */
	public void removeAgent(String username)
			throws AgentUsernameDoesNotExistsException {
		Agent toRemove = getAgentByUsername(username);
		if (toRemove == null)
			throw new AgentUsernameDoesNotExistsException(username);
		
		if(hasAgentByUsername(username)) {
			Agent a = toRemove;
			super.removeAgent(a);
		}
	}
	

	/**
	 * Obtem da BD a publicacao com o id indicado.
	 * 
	 * Lanca excepcao em caso de id inexistente.
	 * 
	 * @param id
	 * @return Publication instacia com o id indicado
	 * @throws PublicationIdDoesNotExistsException
	 */
	public AP getApById(int id)
			throws PublicationIdDoesNotExistsException {
		for (AP ap : getApSet()) {
			if (ap.getId() == id) {
				return ap;
			}
		}
		throw new PublicationIdDoesNotExistsException(id);
	}
	
	/**
	 * Devolve a publicacao mais recente do agente username
	 * 
	 * @param String username
	 * @return Publication
	 * @throws AgentUsernameDoesNotExistsException
	 */
	public Publication getUserLatestComment(String username) throws AgentUsernameDoesNotExistsException {
		Agent agent = this.getAgentByUsername(username);
		if (agent == null) {
			throw new AgentUsernameDoesNotExistsException(username);
		}
		if(agent.getCommentsSet().isEmpty()) {
			return null;
		}
		int id=0;
		for(Publication p : agent.getPublicationsSet()) {
			if(p.getId() > id) {
				id = p.getId();
			}
		}
		return this.getPublicationById(id);
	}

	/**
	 * Obtem o Set de publicacoes de um determinado agente.
	 * Retorna null em caso de o agente nao ter publicacoes.
	 * Lanca excepcao em caso de username inexistente.
	 * @param username
	 * @return Set<Publication> Set com as publicacoes do agente.
	 * @throws AgentUsernameDoesNotExistsException
	 */
	public Set<Publication> getUserPublicationsByUsername(String username)
			throws AgentUsernameDoesNotExistsException {
		Agent agent = this.getAgentByUsername(username);
		if (agent == null)
			throw new AgentUsernameDoesNotExistsException(username);
		return agent.getPublicationsSet();
	}

	/**
	 * Cria uma nova publicacao do tipo nota.
	 * 
	 * @param label
	 * @param creator
	 * @param text
	 * @return Note instacia de nota criada.
	 */
	public Note createNote(String label, Agent creator, String text) {
		int id = this.getPublicationIds();

		Note note = new Note(id, label, 0, 0, creator, text);
		id = id + 1;
		this.setPublicationIds(id);
		return note;
	}

	/**
	 * Cria uma nova publicacao do tipo conteudo.
	 * @param label
	 * @param creator
	 * @param url
	 * @return Content instacia de conteudo cirado.
	 */
	public Content createContent(String label, Agent creator, String url, int price) {
		int id = this.getPublicationIds();

		Content content = new Content(id, label, 0, 0, creator, url, price);
		id = id + 1;
		this.setPublicationIds(id);
		
		return content;
	}

	/**
	 * Adiciona uma nota 'a BD.
	 * @param note
	 */
	public void addNote(Note note) {
		this.addPublications(note);
	}

	/**
	 * Adiciona um conteudo 'a BD.
	 * @param content
	 */
	public void addContent(Content content) {
		this.addPublications(content);
	}

	/**
	 * Obtem um Set de comentarios de uma determianda publicaco por id
	 * de publicacao.
	 * @param id
	 * @return
	 */
	public Set<Comment> getPublicationCommentsByPublicationId(int id) {
		Publication pub = this.getPublicationById(id);
		Set<Comment> comments = pub.getCommentsSet();
		return comments;
	}

	/**
	 * Efectua um pedido de ligacao entre dois agentes.
	 * @param from
	 * @param to
	 */
	public void friendRequest(Individual from, Agent to) {
		if(!from.checkFriendLimit(this.getFriendLimit(), this.getAgentFriendNumber(from)) && !to.checkFriendLimit(this.getFriendLimit(), this.getAgentFriendNumber(to))){
			from.sendFriendRequest(to);
		}
	}

	/**
	 * Confirma um pedido de ligacao entre dois agentes do tipo Individual.
	 * @param from
	 * @param to
	 */
	public void confirmFriendRequest(Individual from, Individual to) {
		if(!from.checkFriendLimit(this.getFriendLimit(), this.getAgentFriendNumber(from)) && !to.checkFriendLimit(this.getFriendLimit(), this.getAgentFriendNumber(to))){
			to.acceptRequest(from);
		}
	}

	/**
	 * Recusa um pedido de ligacao entre dois agentes do tipo Individual.
	 * @param from
	 * @param to
	 */
	public void refuseFriendRequest(Individual from, Individual to) {
		to.refuseRequest(from);
	}

	/**
	 * Adiciona, se possivel, um voto positivo a uma publicacao
	 * 
	 * @param Agent voter - agente que vota na publicacao
	 * @param Publication pub - publicacao na qual se vai votar
	 */
	public void posVote(Agent voter, Publication pub) {
		voter.positiveVote(pub, this.getVoteLimit());		
	}

	/**
	 * Adiciona, se possivel, um voto negativo a uma publicacao
	 * 
	 * @param Agent voter - agente que vota na publicacao
	 * @param Publication pub - publicacao na qual se vai votar
	 */
	public void negVote(Agent voter, Publication pub) {
		voter.negativeVote(pub, this.getVoteLimit());
			}

	/**
	 * Comenta, se possivel, uma publicacao
	 * 
	 * @param Agent comentador
	 * @param Publication publicacao
	 * @param String comentario
	 */	
	public void commentPublication(Agent commentator, Publication pub, String comment) throws YouArentAFriendException {
		if(pub.getAgent().getPermission().canAcess(commentator, pub.getAgent()) && !pub.checkVoteLimit(this.getVoteLimit())) {
			Comment comentario = new Comment(commentator, pub, pub.generateCommentId(), comment);
			pub.commentPublication(comentario);
		}
		else { //por enquanto, o unico motivo que o leva a nao ter permissao e nao ser amigo
			throw new YouArentAFriendException(pub.getAgent().getName());
		}
	}
		
	/**
	 * Verifica se um agente Individual ja excedeu o limite de amigos
	 * 
	 * @param Individual agent - agente a verificar
	 */
	public int getAgentFriendNumber(Agent agent) {
		int friends=0;		
		friends=(agent.getAgentCount()+agent.getFriendsCount());
		return friends;
	}
	
	/**
	 * Lista todos os amigos de um dado agent
	 * 
	 * @param Individual agent - agente a verificar
	 * @return ArrayList<Agent> - Lista de Agents que sao amigo do agent
	 */
	public ArrayList<Agent> getFriendsOf(Agent agent) {
		ArrayList<Agent> list = new ArrayList<Agent>();
		
		list.addAll(agent.getAgentSet());
		list.addAll(agent.getFriendsSet());

		return list;
	}

}

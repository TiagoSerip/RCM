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
	 * gera id para point of intrest
	 * @return
	 */
	public int generatePIId(){
		int id = this.getPIId();
		this.setPIId(id+1);
		return id;
	}
	
	public int generateBoardId(){
		int id = this.getBoardId();
		this.setBoardId(id+1);
		return id;
	}
	
	public int generateMessageId() {
		int id = this.getMessageId();
		this.setMessageId(id+1);
		return id;
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
	public Agent createAgent (String user, String pass, String name, int ap, int rssi, String ip)
					throws UsernameAlreadyExistsException, ApIdDoesNotExistsException{
		
		if(this.hasAgentByUsername(user))
			throw new UsernameAlreadyExistsException(user);
		
		if(!this.hasApById(ap))
			throw new ApIdDoesNotExistsException(ap);
		AP acesspoint = getApById(ap);
		Agent agent = new Agent();
		agent.init(user, pass, name, acesspoint, rssi, ip);
		return agent;
	}
	
	public Board createBoard(Agent host, Agent guest) {		
		int id = generateBoardId();
		Board board = new Board();
		board.init(id, host, guest);
		this.addBoard(board);
		return board;
	}
	
	public Board play(Board board, Agent player, int[] jogada) throws InvalidPositionException {
		int row = jogada[1];
		int column = jogada[2];
		
		String[][] m = board.getMatrix();
		if(m[row][column] != null)
			throw new InvalidPositionException(row, column, m[row][column]);			
			
		board.setMatrixPosition(jogada, player.getUsername());
		board.setTurn(player.getUsername());
		return board;
	}
	
	public Board getBoardByUsers(Agent player1, Agent player2) {
		
		List<Board> games = new ArrayList<Board>();
		Board aux = new Board();
		
		for(Board b : this.getBoardSet()) {
			if(b.getGuest()==player1 && b.getHost()==player2) {
				games.add(b);
			}
			if(b.getGuest()==player2 && b.getHost()==player1) {
				games.add(b);
			}
		}
		
		if(games.isEmpty())
			return null;
		
		aux=games.get(0);
		for(Board b : games) {
			if(b.getId() > aux.getId())
				aux = b;
		}
		return aux;		
	}
	
	public Board getBoardById(int id) {
		for(Board b : this.getBoardSet()) {
			if(b.getId()==id)
				return b;
		}
		return null;
	}
	
	public boolean boardIsFull(Board board) {
		String[][] m = board.getMatrix();
		for(int i=0; i < 3; i++) {
			for(int j=0; j < 3; j++) {
				if (m[i][j] == null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkWinner(Board board) {
		String[][] m = board.getMatrix();
		
		return	(m[0][0] != null && m[0][0]==m[0][1] && m[0][0]==m[0][2]) ||
				(m[1][0] != null && m[1][0]==m[1][1] && m[1][0]==m[1][2]) ||
				(m[2][0] != null && m[2][0]==m[2][1] && m[2][0]==m[2][2]) ||
				(m[0][0] != null && m[0][0]==m[1][0] && m[0][0]==m[2][0]) ||
				(m[0][1] != null && m[0][1]==m[1][1] && m[0][1]==m[2][1]) ||
				(m[0][2] != null && m[0][2]==m[1][2] && m[0][2]==m[2][2]) ||
				(m[0][0] != null && m[0][0]==m[1][1] && m[0][0]==m[2][2]) ||
				(m[0][2] != null && m[0][2]==m[1][1] && m[0][2]==m[2][0]);
	}
	
	public String getWinner(Board board) {
		String[][] m = board.getMatrix();
		
		if(m[0][0] != null && m[0][0]==m[0][1] && m[0][0]==m[0][2]){ 
			return m[0][0];}
		if(m[1][0] != null && m[1][0]==m[1][1] && m[1][0]==m[1][2]){ 
			return m[1][0];}
		if(m[2][0] != null && m[2][0]==m[2][1] && m[2][0]==m[2][2]){
			return m[2][0];}
		if(m[0][0] != null && m[0][0]==m[1][0] && m[0][0]==m[2][0]){ 
			return m[0][0];}
		if(m[0][1] != null && m[0][1]==m[1][1] && m[0][1]==m[2][1]){ 
			return m[0][1];}
		if(m[0][2] != null && m[0][2]==m[1][2] && m[0][2]==m[2][2]){ 
			return m[0][2];}
		if(m[0][0] != null && m[0][0]==m[1][1] && m[0][0]==m[2][2]){ 
			return m[0][0];}
		if(m[0][2] != null && m[0][2]==m[1][1] && m[0][2]==m[2][0]){
			return m[0][2];}
		
		return null;
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
	 * Obtem um agente individual pelo username
	 * 
	 * @param user
	 * @return Individual ou null se nao existir
	 */
	public AP getApBySubnet(String subnet) {
		for(AP a : getApSet()) {
			if(a.getSubnet().equalsIgnoreCase(subnet)) {
				return a;
			}
		}
		return null;
	}
	
	public Set<PI> getAllPIFromAP(AP ap) {
		return ap.getPIs();
	}
	
	public PI getPIByName(String name) {
		for(AP a : getApSet()) {
			for(PI p : a.getPiSet()) {
				if(p.getName().equals(name))
					return p;
			}				
		}
		return null;
	}
		
	public PI getPIByID(int id) {
		for(AP a : getApSet()) {
			for(PI p : a.getPiSet()) {
				if(p.getId() == id)
					return p;
			}				
		}
		return null;
	}
	
	public boolean hasPIById(int id) {
		return getPIByID(id) != null;
	}
	
	public void removePI(PI pi, AP ap) {
		ap.removePi(pi);
	}
	
	
	public boolean hasPIByName(String name) {
		return getPIByName(name) != null;
	}
	
	public PI createPI(String name, String location, String description, String link, AP ap) 
			throws PINameAlreadyExistsException, PIIdAlreadyExistsException {
		
		if(getPIByName(name)!=null)
			throw new PINameAlreadyExistsException(name);
		
		PI pi = new PI();
		int id = this.generatePIId();
		pi.init(id, name, location, description, link, ap);
		ap.addPI(pi);
		
		return pi;		
	}

	
	/**
	 * Verifica se existe um agent com um dado username
	 * 
	 * @param name
	 * @return boolean com o resultado
	 */
	public boolean hasApById(int id) {
		return getApById(id) != null;
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
	public AP createAP (int id, String subnet, int rssi)
					throws ApIdAlreadyExistsException {
		
		if(this.hasApById(id))
			throw new ApIdAlreadyExistsException(id);

		AP ap = new AP();
		ap.init(id, subnet, rssi, 0, 0);
		return ap;
	}

	@Override
	public void addAp(AP ap) {
		super.addAp(ap);
	}
	
	/**
	 * Remove um agente da base de dados da SoNet.
	 * Lanca excepcao em caso de username inexistente.
	 * 
	 * @param username
	 * @throws AgentUsernameDoesNotExistsException
	 */
	public void removeAP(int id)
			throws AgentUsernameDoesNotExistsException {
		AP toRemove = this.getApById(id);
		if (toRemove == null)
			throw new AgentUsernameDoesNotExistsException(""+id);
		super.removeAp(toRemove);
	}
	

	/**
	 * Obtem da BD a publicacao com o id indicado.
	 * 
	 * Lanca excepcao em caso de id inexistente.
	 * 
	 * @param id
	 * @return Publication instacia com o id indicado
	 * @throws ApIdDoesNotExistsException
	 */
	public AP getApById(int id){
		for (AP ap : getApSet()) {
			if (ap.getId() == id) {
				return ap;
			}
		}

		return null;
	}
	
	/**
	 * Devolve a publicacao mais recente do agente username
	 * 
	 * @param String username
	 * @return Publication
	 * @throws AgentUsernameDoesNotExistsException
	 */
	public Comment getApLatestComment(int id) throws AgentUsernameDoesNotExistsException {
		AP ap = this.getApById(id);
		if (ap == null) {
			throw new AgentUsernameDoesNotExistsException(""+id);
		}
		if(ap.getCommentsSet().isEmpty()) {
			return null;
		}
		
		return ap.getCommentsSet().iterator().next();

	}

	/**
	 * Obtem o Set de publicacoes de um determinado agente.
	 * Retorna null em caso de o agente nao ter publicacoes.
	 * Lanca excepcao em caso de username inexistente.
	 * @param username
	 * @return Set<Publication> Set com as publicacoes do agente.
	 * @throws AgentUsernameDoesNotExistsException
	 */
	public Set<Comment> getApComments(int id)
			throws AgentUsernameDoesNotExistsException {
		AP ap = this.getApById(id);
		if (ap == null)
			throw new AgentUsernameDoesNotExistsException(""+id);
		return ap.getCommentsSet();
	}

	/**
	 * Adiciona, se possivel, um voto positivo a uma publicacao
	 * 
	 * @param Agent voter - agente que vota na publicacao
	 * @param Publication pub - publicacao na qual se vai votar
	 */
	public void posVote(Agent voter, AP ap) {
		ap.positiveVote(voter);
	}

	/**
	 * Adiciona, se possivel, um voto negativo a uma publicacao
	 * 
	 * @param Agent voter - agente que vota na publicacao
	 * @param Publication pub - publicacao na qual se vai votar
	 */
	public void negVote(Agent voter, AP ap) {
		ap.negativeVote(voter);
	}
	
	/**
	 * Adiciona, se possivel, um voto positivo a uma publicacao
	 * 
	 * @param Agent voter - agente que vota na publicacao
	 * @param Publication pub - publicacao na qual se vai votar
	 */
	public void posVotePI(Agent voter, PI pi) {
		pi.positiveVote(voter);
	}

	/**
	 * Adiciona, se possivel, um voto negativo a uma publicacao
	 * 
	 * @param Agent voter - agente que vota na publicacao
	 * @param Publication pub - publicacao na qual se vai votar
	 */
	public void negVotePI(Agent voter, PI pi) {
		pi.negativeVote(voter);
	}

	/**
	 * Comenta, se possivel, uma publicacao
	 * 
	 * @param Agent comentador
	 * @param Publication publicacao
	 * @param String comentario
	 */	
	public void commentAp(Agent agent, AP ap, String comment) {
		Comment comentario = new Comment();
		comentario.init(agent, ap, comment);
		ap.commentAp(comentario);	
		agent.commentAP(comentario);
	}
	
	/**
	 * Enviar uma private message
	 * 
	 * @param Agent sender
	 * @param Agent receiver
	 * @param String mensagem
	 */	
	public void talkTo(Agent sender, Agent receiver, String text) {
		Message msg = new Message();
		int id = generateMessageId();
		msg.init(id, sender, receiver, text);
		sender.addSentMessage(msg);
		receiver.addReceivedMessage(msg);		
	}

	public List<Message> getLastConversationWithSomeone(Agent requester, Agent otherGuy) {
		return requester.getLastConversationWithSomeone(otherGuy);		
	}
	
	public Message getAgentLastSentMsg(Agent agent) {
		Message lastMessage = new Message(); 
		while(agent.getSentMessageSet().iterator().hasNext()) {
			lastMessage = agent.getSentMessageSet().iterator().next();
		}
		return lastMessage;		
	}
	
	public Message getAgentLastReceivedMsg(Agent agent) {
		Message lastMessage = new Message(); 
		while(agent.getReceivedMessageSet().iterator().hasNext()) {
			lastMessage = agent.getReceivedMessageSet().iterator().next();
		}
		return lastMessage;		
	}
	
	public AP determineAP(String ip){
		
		for(AP ap : this.getApSet()){
			if (ip.contains(ap.getSubnet()))
					return ap;
		}
		
		return null;
	}
	
	public void updateAgentIP(Agent agent, String ip){
		
		AP ap = determineAP(ip);
		if (ap==null){
			throw new IpOutOfMeshException(ip);
		}
		agent.setAp(ap);
		agent.setIp(ip);
		
	}

	public int calculateDistanceToAP(Agent user){
		double res;
		int distance;
		
		AP ap = user.getAp();
		int pr=user.getRssi();
		int p0=ap.getRssi();
		
		res = Math.exp((pr-p0)/(-10*1.42)); //)e^((pr-p0)/(-10n) n= 2.407
		
		if(res<1)
			return 1;
		
		distance = (int) res;
		
		if(res%10>0.5)
			distance++;

		
		return distance;
		
		
	}
}

package pt.ist.sonet.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pt.ist.sonet.exception.*;

public class SoNet extends SoNet_Base implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SoNet() {
		super();
	}
	
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
	
	public Agent getAgentByUsername(String user) {
		for(Agent a : getAgentSet()) {
			if(a.getUsername().equalsIgnoreCase(user)) {
				return a;
			}
		}
		return null;
	}
	
	public Agent getAgentByName(String name) {
		for(Agent a : getAgentSet()) {
			if(a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}

	public boolean hasAgentByUsername(String user) {
		return getAgentByUsername(user) != null;
	}


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
		int row = jogada[0];
		int column = jogada[1];
		String[] v = board.getVector();
		String[][] m = new String[3][3];
		m[0][0]=v[0];
		m[0][1]=v[1];
		m[0][2]=v[2];
		m[1][0]=v[3];
		m[1][1]=v[4];
		m[1][2]=v[5];
		m[2][0]=v[6];
		m[2][1]=v[7];
		m[2][2]=v[8];
		if(m[row][column] != null)
			throw new InvalidPositionException(row, column, m[row][column]);			
		board.setMatrixPosition(jogada, player.getUsername());
		
		if(player.equals(board.getGuest()))
			board.setTurn(board.getHost().getUsername());
		if(player.equals(board.getHost()))
			board.setTurn(board.getGuest().getUsername());		
		return board;
	}
	
	public Board getBoardByUsers(Agent player1, Agent player2) {
		
		List<Board> games = new ArrayList<Board>();
		Board aux;
		
		for(Board b : this.getBoardSet()) {
			if(b.getGuest().equals(player1) && b.getHost().equals(player2)) {
				games.add(b);
			}
			if(b.getGuest().equals(player2) && b.getHost().equals(player1)) {
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

		if(this.getWinner(aux)!=null)
			return null;
		
		if(!this.boardIsFull(aux))
			return aux;
		
		return null;
	}
	
	public Board getBoardById(int id) {
		for(Board b : this.getBoardSet()) {
			if(b.getId()==id)
				return b;
		}
		return null;
	}
	
	public boolean boardIsFull(Board board) {
		String[] m = board.getVector();
		for(int i=0; i < 9; i++) {
				if (m[i] == null) {
					return false;
				}			
		}
		return true;
	}
	
	public boolean checkWinner(Board board) {
		String[] v = board.getVector();
		String[][] m = new String[3][3];
		m[0][0]=v[0];
		m[0][1]=v[1];
		m[0][2]=v[2];
		m[1][0]=v[3];
		m[1][1]=v[4];
		m[1][2]=v[5];
		m[2][0]=v[6];
		m[2][1]=v[7];
		m[2][2]=v[8];
		
		boolean res;
		res =	(m[0][0] != null && m[0][0]==m[0][1] && m[0][0]==m[0][2]) ||
				(m[1][0] != null && m[1][0]==m[1][1] && m[1][0]==m[1][2]) ||
				(m[2][0] != null && m[2][0]==m[2][1] && m[2][0]==m[2][2]) ||
				(m[0][0] != null && m[0][0]==m[1][0] && m[0][0]==m[2][0]) ||
				(m[0][1] != null && m[0][1]==m[1][1] && m[0][1]==m[2][1]) ||
				(m[0][2] != null && m[0][2]==m[1][2] && m[0][2]==m[2][2]) ||
				(m[0][0] != null && m[0][0]==m[1][1] && m[0][0]==m[2][2]) ||
				(m[0][2] != null && m[0][2]==m[1][1] && m[0][2]==m[2][0]);
		return res;
	}
	
	public String getWinner(Board board) {
		String[] v = board.getVector();
		String[][] m = {{null, null, null},{null, null, null},{null, null, null}};
		m[0][0]=v[0];
		m[0][1]=v[1];
		m[0][2]=v[2];
		m[1][0]=v[3];
		m[1][1]=v[4];
		m[1][2]=v[5];
		m[2][0]=v[6];
		m[2][1]=v[7];
		m[2][2]=v[8];
		
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

	public boolean hasApById(int id) {
		return getApById(id) != null;
	}


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

	public void removeAP(int id)
			throws AgentUsernameDoesNotExistsException {
		AP toRemove = this.getApById(id);
		if (toRemove == null)
			throw new AgentUsernameDoesNotExistsException(""+id);
		super.removeAp(toRemove);
	}
	
	public AP getApById(int id){
		for (AP ap : getApSet()) {
			if (ap.getId() == id) {
				return ap;
			}
		}

		return null;
	}
	
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

	public Set<Comment> getApComments(int id)
			throws AgentUsernameDoesNotExistsException {
		AP ap = this.getApById(id);
		if (ap == null)
			throw new AgentUsernameDoesNotExistsException(""+id);
		return ap.getCommentsSet();
	}

	public void posVote(Agent voter, AP ap) {
		ap.positiveVote(voter);
	}

	public void negVote(Agent voter, AP ap) {
		ap.negativeVote(voter);
	}
	
	public void posVotePI(Agent voter, PI pi) {
		pi.positiveVote(voter);
	}

	public void negVotePI(Agent voter, PI pi) {
		pi.negativeVote(voter);
	}

	public void commentAp(Agent agent, AP ap, String comment) {
		Comment comentario = new Comment();
		comentario.init(agent, ap, comment);
		ap.commentAp(comentario);	
		agent.commentAP(comentario);
	}

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

package pt.ist.sonet.domain;

import java.io.Serializable;
import java.util.*;

/**
 * Classe Agent que herda de Agent_Base
 */
public class Agent extends Agent_Base implements Serializable{
	private static final long serialVersionUID = 1L;

	
	/**
	 * Construtor
	 */
	public  Agent() {}

	/**
	 * Metodo auxiliar ao construtor
	 * 
	 * @param String user - username do agente
	 * @param String name - nome do agente
	 * @param String email - email do agente
	 * @param String pass - palavra passe do agente
	 * @param String city - cidade do agente
	 * @param String nation - pais do agente
	 */
	public void init(String user, String pass, String name, AP ap, int rssi, String ip) {
		setUsername(user);
		setName(name);
		setPassword(pass);
		setAp(ap);
		setRssi(rssi);
		setIp(ip);
	}   


	/**
	 * Metodo chamado quando o agente quer comentar uma dada publicacao. Verifica primeiro qual a permissao
	 * por omissao do agente associado a publicacao que se quer comentar e, caso seja possivel, comenta criando
	 * o comentario e chamando o metodo commentPublication da publicacao
	 * 
	 * @param Publication pub - publicacao que se quer comentar
	 * @param String comment - texto do comentario
	 */
	public void commentAP(Comment comment) {
		
		this.addComments(comment);
//		if(pub.getAgent().getPermission().canAcess(this, pub.getAgent())) {
//			Comment comentario = new Comment(this, pub, pub.generateCommentId(), comment);
//			pub.commentPublication(comentario);
//		}
//		else { //por enquanto, o unico motivo que o leva a nao ter permissao e nao ser amigo
//			throw new YouArentAFriendException(pub.getAgent().getName());
//		}
	}
	
	public void sendMessage(Message msg){
		this.addSentMessage(msg);
	}
	
	public List<Message> getLastConversationWithSomeone(Agent agent) {
		List<Message> sentMsg = new ArrayList<Message>();
		List<Message> receivedMsg = new ArrayList<Message>();
		List<Message> allMsg = new ArrayList<Message>();
		List<Message> allMsgSort = new ArrayList<Message>();
		
		for(Message m : this.getSentMessageSet()){
			if(m.getReceiver().equals(agent))
				sentMsg.add(m);
		}
		
		for(Message m : this.getReceivedMessageSet()){
			if(m.getSender().equals(agent))
				receivedMsg.add(m);		
		}
		
		allMsg.addAll(sentMsg);
		allMsg.addAll(receivedMsg);
		
		int[] msgIDs = new int[allMsg.size()];
		int i = 0;
		
		for(Message m : allMsg) {
			msgIDs[i] = m.getId();
			i++;
		}
		
		Arrays.sort(msgIDs);
		
		for(int x : msgIDs) {
			for(Message m : allMsg) {
				if(m.getId() == x)
					allMsgSort.add(m);
			}
		}		
		return allMsgSort;
	}


	/**
	 * Metodo que retorna informacoes do agente (nome, cidade, etc)
	 * 
	 * @return String
	 */
	public String toString() {
		return "Agent | " + this.getUsername() + " | " + this.getName() + " | " + this.getPassword() + " | " + "AP-"+this.getAp().getId() + ", " + this.getRssi()+", " + this.getIp();
	}

	
	public String viewString() {
		return this.getUsername() + ": " + this.getName() + " | " + this.getIp()+ ", RSSI:"+this.getRssi()+"dBm";
	}


}
package pt.ist.sonet.domain;

import java.io.Serializable;

import pt.ist.sonet.exception.*;

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
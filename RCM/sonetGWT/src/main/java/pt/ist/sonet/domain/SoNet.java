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
	
	public boolean hasPIByName(String name) {
		return getPIByName(name) != null;
	}
	
	public PI createPI(int id, String name, String location, String description) {
		
//		if(getPIByID(id)!=null)
//			throw new PIIdAlreadyExistsException(id);
		
//		if(getPIByName(name)!=null)
//			throw new PINameAlreadyExistsException(name);
		
		PI pi = new PI();
		pi.init(id, name, location, description);
		return pi;		
	}
	
	public void addPI(PI pi, AP ap) {
		ap.addPI(pi);
	}
	
	public void removeAPa(int id)
			throws AgentUsernameDoesNotExistsException {
		AP toRemove = this.getApById(id);
		if (toRemove == null)
			throw new AgentUsernameDoesNotExistsException(""+id);
		super.removeAp(toRemove);
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
	public AP createAP (int id, String subnet)
					throws ApIdAlreadyExistsException {
		
		if(this.hasApById(id))
			throw new ApIdAlreadyExistsException(""+id); //porque o +

		AP ap = new AP();
		ap.init(id, subnet, 0, 0);
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
		ap.positiveVote(voter);
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

}

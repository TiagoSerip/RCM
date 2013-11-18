package pt.ist.sonet.domain;

import java.io.Serializable;

import pt.ist.sonet.exception.*;
import pt.ist.sonet.service.dto.PublicationViewDto;

/**
 * Class Publication, e a super class dos varios tipos de publicacao existentes.
 * @author ES Grupo 8
 */
public abstract class Publication extends Publication_Base implements Serializable{

	protected  Publication() {}

	/**
	 * Inicializa os dados da instancia criada.
	 * @param id
	 * @param label
	 * @param pos
	 * @param neg
	 * @param creator
	 */
	protected void init(int id, String label, int pos, int neg, Agent creator) {
		setId(id);
		setLabel(label);
		setNegVotes(neg);
		setPosVotes(pos);
		setAgent(creator);
		setCommentIds(0);
		setPubVoteLimit(0);
	}

	/**
	 * Adiciona um Agent a lista de quem ja votou.
	 */
	@Override
	public void addAlreadyVoted(Agent alreadyVoted) {
		super.addAlreadyVoted(alreadyVoted);
	}

	/**
	 * Gera um id unico para cada comentario da publicacao.
	 * O id NAO e' global 'a SoNet.
	 * 
	 * @return id unico de comentario
	 */
	public int generateCommentId(){
		int id = getCommentIds();
		setCommentIds(id+1);
		return id;
	}

	/**
	 * Adiciona um comentario 'a BD.
	 * @param comment
	 */
	public void commentPublication(Comment comment) {
		this.addComments(comment);
	}

	public boolean hasComments(){
		return this.hasAnyComments();
	}

	/**
	 * Adiciona um agente 'a lista de agentes cujo acesso 'a 
	 * publicacao esta bloqueado.
	 * 
	 */
	@Override
	public void addBlockedAgent(Agent newBlocked) throws AgentAlreadyBlockedException {
		for (Agent agente : this.getBlockedAgentSet()) {
			if(agente.equals(newBlocked))
				throw new AgentAlreadyBlockedException(newBlocked.getName(), this.getId());
		}
		super.addBlockedAgent(newBlocked);
	}

	/**
	 * Remove um agente da lista de agentes com acesso bloqueado.
	 * 
	 * @see pt.ist.sonet.domain.Publication_Base#removeBlockedAgent(pt.ist.sonet.domain.Agent)
	 */
	@Override
	public void removeBlockedAgent(Agent blockedAgent) {
		for (Agent agente : this.getBlockedAgentSet()) {
			if(agente.equals(blockedAgent))
				super.removeBlockedAgent(blockedAgent);    
		}    	
	}

	/**
	 * Adiciona um voto positivo 'a publicaco em nome de um agente.
	 * Caso o votante ja' tenha votado lanca excepcao.
	 * 
	 * @param voter
	 * @throws AlreadyVotedException
	 */
	public void positiveVote(Agent voter) throws AlreadyVotedException {

		for(Agent agente : this.getAlreadyVotedSet()) {
			if(agente.equals(voter))
				throw new AlreadyVotedException(voter.getName(), this.getId());
		}
		int nrVotes = this.getPosVotes();
		nrVotes = nrVotes + 1;
		this.setPosVotes(nrVotes);
		this.addAlreadyVoted(voter);    	
	}

	/**
	 * Adiciona um voto negativo em nome de um agente.
	 * Caso o votante ja' tenha votado laca excepcao.
	 * @param voter
	 * @throws AlreadyVotedException
	 */
	public void negativeVote(Agent voter) throws AlreadyVotedException {
		for(Agent agente : this.getAlreadyVotedSet()) {
			if(agente.equals(voter))
				throw new AlreadyVotedException(voter.getName(), this.getId());
		}
		int nrVotes = this.getNegVotes();
		nrVotes = nrVotes + 1;
		this.setNegVotes(nrVotes);
		this.addAlreadyVoted(voter);    	
	}

	/**
	 * Cabecalho com a correspondencia de informacao impressa sobre a publicacao.
	 * @return string
	 */
	public String toStringHeader(){
		return "ID | Agent Name | Label | Content | Positive Votes | Negative Votes";
	}

	/**
	 * Elabora uma string com os dados da publicacao e seus comentarios.
	 * 
	 * @return string com informacao da publicacao e comentarios.
	 */
	public String toStringWithComments(){
		if(this.hasAnyComments()){
			String s = this.toString()+"\n";
			for(Comment c : getCommentsSet()){
				s=s+"\t\t"+c.toString()+"\n";
			}
			return s;
		}
		return this.toString()+"\n";
	}

	/**
	 * Verifica se a publicacao ja atingiu o limite de votos
	 * 
	 * @return boolean false se ainda nao tiver atingido o limite de votos
	 * @throws OnVoteLimitException
	 */
	public boolean checkVoteLimit(int voteLimit) throws OnVoteLimitException {
		if((this.getPosVotes()-this.getNegVotes()) <= voteLimit){
			throw new OnVoteLimitException(this.getId(), this.getAgent().getName());
		}
		return false;//significa que não está no limite
	}
	
	/**
	 * Metodo abstracto
	 */
	public abstract String getType();
	
	/**
	 * Metodo abstracto
	 */
	public abstract PublicationViewDto createDto();

}
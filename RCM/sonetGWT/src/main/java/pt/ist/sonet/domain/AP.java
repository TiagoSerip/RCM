package pt.ist.sonet.domain;

import pt.ist.sonet.exception.AlreadyVotedException;

public class AP extends AP_Base {
    

	protected  AP() {}

	/**
	 * Inicializa os dados da instancia criada.
	 * @param id
	 * @param label
	 * @param pos
	 * @param neg
	 * @param creator
	 */
	protected void init(int id, String subnet, int pos, int neg) {
		setId(id);
		setSubnet(subnet);
		setNegVotes(neg);
		setPosVotes(pos);

	}
	
	/**
	 * Adiciona um voto positivo 'a publicaco em nome de um agente.
	 * Caso o votante ja' tenha votado lanca excepcao.
	 * 
	 * @param voter
	 * @throws AlreadyVotedException
	 */
	public void positiveVote(Agent voter) throws AlreadyVotedException {

//		for(Agent agente : this.getAlreadyVotedSet()) {
//			if(agente.equals(voter))
//				throw new AlreadyVotedException(voter.getName(), this.getId());
//		}
		int nrVotes = this.getPosVotes();
		nrVotes = nrVotes + 1;
		this.setPosVotes(nrVotes);
//		this.addAlreadyVoted(voter);    	
	}

	/**
	 * Adiciona um voto negativo em nome de um agente.
	 * Caso o votante ja' tenha votado laca excepcao.
	 * @param voter
	 * @throws AlreadyVotedException
	 */
	public void negativeVote(Agent voter) throws AlreadyVotedException {
//		for(Agent agente : this.getAlreadyVotedSet()) {
//			if(agente.equals(voter))
//				throw new AlreadyVotedException(voter.getName(), this.getId());
//		}
		int nrVotes = this.getNegVotes();
		nrVotes = nrVotes + 1;
		this.setNegVotes(nrVotes);
//		this.addAlreadyVoted(voter);    	
	}
	
	/**
	 * Adiciona um comentario 'a BD.
	 * @param comment
	 */
	public void commentAp(Comment comment) {
		this.addComments(comment);
	}

	public boolean hasComments(){
		return this.hasAnyComments();
	}
    
}

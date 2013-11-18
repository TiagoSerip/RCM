package pt.ist.sonet.exception;

/**
 * Exception a ser lancada pela camada de servico para a camada de
 * aplicacao ao tentar executar uma tranferencia invalida.
 * @author ES Grupo 8
 *
 */
public class PagAmigoTransferException extends SoNetException {

	private static final long serialVersionUID = 1L;

	String from;
	String to;
	int amount;
	String description;
	
	public PagAmigoTransferException() {}
	
	public PagAmigoTransferException(String from, String to, int amount, String description) {
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.description = description;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return "You cant make this donation";
	}
	
	
	
}

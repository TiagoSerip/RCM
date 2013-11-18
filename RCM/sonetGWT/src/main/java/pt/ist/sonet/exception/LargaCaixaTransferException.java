package pt.ist.sonet.exception;

/**
 * Exception a ser lancada pela camada de servico para a camada de
 * aplicacao ao tentar executar uma tranferencia de ficheiro invalida.
 * @author ES Grupo 8
 *
 */
public class LargaCaixaTransferException extends SoNetException {

	private static final long serialVersionUID = 1L;

	String description;
	
	public LargaCaixaTransferException() {}
	
	public LargaCaixaTransferException(String description) {
		
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	
	
	
	
}

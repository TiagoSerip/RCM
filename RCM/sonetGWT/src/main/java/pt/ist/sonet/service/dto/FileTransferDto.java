package pt.ist.sonet.service.dto;

/**
 * DTO para o servico LargaCaixaFileTransfer.
 * 
 * @author ES Grupo 8
 *
 */
public class FileTransferDto {
	
	private String from;
	private String to;
	private String url;
	private Object proof;
	
	public FileTransferDto(String url, String from, String to, Object proof) {
		this.from = from;
		this.to = to;
		this.url = url;
		this.proof = proof;
	}

	/**
	 * @return the form
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @return the content url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the proof of transfer
	 */
	public Object getProof() {
		return proof;
	}
	
	
	

}

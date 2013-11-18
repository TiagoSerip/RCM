package pt.ist.sonet.exception;

public class PagaFailureException extends SoNetException {

	private static final long serialVersionUID = 1L;
	private String reason;
	
	public PagaFailureException() {
    }
	
	public PagaFailureException(String reason) {
		this.reason=reason;
    }
	
	public String getReason(){
		return reason;
	}
}
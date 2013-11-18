package pt.ist.sonet.exception;

public class ApHasNoCommentsException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private int id;
	
	public ApHasNoCommentsException(int id) {
		this.id = id;
	}
	
	public String getid() {
		return ""+this.id;
	}
	
}

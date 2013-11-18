package pt.ist.sonet.exception;

public class PublicationHasNoCommentsException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private int id;
	
	public PublicationHasNoCommentsException(int id) {
		this.id = id;
	}
	
	public String getid() {
		return ""+this.id;
	}
	
}

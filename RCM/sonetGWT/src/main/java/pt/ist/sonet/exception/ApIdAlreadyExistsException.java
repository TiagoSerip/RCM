package pt.ist.sonet.exception;

public class ApIdAlreadyExistsException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private int id;
	
	public ApIdAlreadyExistsException(int id) {
		this.id = id;
	}
	
	public int getConflictingId() {
		return this.id;
	}
	
}

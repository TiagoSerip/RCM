package pt.ist.sonet.exception;

public class PIIdAlreadyExistsException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private int id;
	
	public PIIdAlreadyExistsException(int id) {
		this.id = id;
	}
	
	public int getConflictingId() {
		return this.id;
	}

}

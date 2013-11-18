package pt.ist.largacaixa;

public class InexistentBoxContentException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String conflictingID;

	public InexistentBoxContentException(String conflictingID) {
		this.conflictingID = conflictingID;
	}
	
	public String getInexistentID() {
		return this.conflictingID;
	}

}

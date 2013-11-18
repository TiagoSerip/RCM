package pt.ist.largacaixa;

public class InexistentBoxException extends Exception {

	private static final long serialVersionUID = 1L;

	private String conflictingUsername;

	public InexistentBoxException(String username) {
		this.conflictingUsername = username;
	}
	
	public String getInexistentUsername() {
		return this.conflictingUsername;
	}

}

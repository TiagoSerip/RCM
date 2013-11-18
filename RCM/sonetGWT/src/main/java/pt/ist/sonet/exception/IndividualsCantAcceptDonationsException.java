package pt.ist.sonet.exception;

public class IndividualsCantAcceptDonationsException extends SoNetException {

	private String user;
	private static final long serialVersionUID = 1L;

	public IndividualsCantAcceptDonationsException() {
	}
	
	public IndividualsCantAcceptDonationsException(String user) {
		this.user=user;
	}
	
	public String reason() {
		return "Agent "+user+" can't accept donations;";
	}
}

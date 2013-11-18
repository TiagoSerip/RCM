package pt.ist.sonet.exception;

public class OnVoteLimitException extends SoNetException{
	
	private static final long serialVersionUID = 1L;

	private String owner;
	private int pubid;
	
	public OnVoteLimitException (int pubId, String username) {
		this.owner = username;
	}
	
	public OnVoteLimitException (){}
	
	public String getOwner() {
		return this.owner;
	}

	public int getPubid() {
		return pubid;
	}

	public void setPubid(int pubid) {
		this.pubid = pubid;
	}	

}
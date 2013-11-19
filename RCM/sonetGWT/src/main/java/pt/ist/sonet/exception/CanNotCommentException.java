package pt.ist.sonet.exception;


public class CanNotCommentException extends SoNetException {
	
	private static final long serialVersionUID = 1L;
	
	String username;
	int apId;
	String reason;
	public CanNotCommentException(String username, int apId, String reason){
		this.username=username;
		this.apId=apId;
		this.reason=reason;
		
	}


	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getApId() {
		return apId;
	}


	public void setApId(int apId) {
		this.apId = apId;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String toString(){
		return "You cannot comment on this AP (AP-"+apId+").\nReason:'"+reason;
		
	}

}

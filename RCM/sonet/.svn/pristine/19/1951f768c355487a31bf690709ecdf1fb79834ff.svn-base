package pt.ist.sonet.exception;


public class CanNotCommentException extends SoNetException {
	
	private static final long serialVersionUID = 1L;
	
	String CommentOwner;
	int pubId;
	String pubOwner;
	public CanNotCommentException(String CommentOwner, int pubId,String pubOwner){
		this.CommentOwner=CommentOwner;
		this.pubId=pubId;
		this.pubOwner=pubOwner;
		
	}
	
	public String getCommentOwner() {
		return CommentOwner;
	}
	public void setCommentOwner(String commentOwner) {
		CommentOwner = commentOwner;
	}
	public int getPubId() {
		return pubId;
	}
	public void setPubId(int pubId) {
		this.pubId = pubId;
	}

	public String getPubOwner() {
		return pubOwner;
	}

	public void setPubOwner(String pubOwner) {
		this.pubOwner = pubOwner;
	}

	public String toString(){
		return "You can not comment this publication (#"+pubId+").\nThe Agent '"+ pubOwner +"' has a permission of type amigo and you aren't friends";
		
	}

}

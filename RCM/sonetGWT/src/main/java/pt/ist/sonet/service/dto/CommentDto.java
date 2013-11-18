package pt.ist.sonet.service.dto;

import java.io.Serializable;

public class CommentDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String user;
	int pubId;
	String text;
	
	public CommentDto(String user, int pubId, String text){
		this.user = user;
		this.pubId = pubId;
		this.text = text;
		
	}
	//for serialization
	public CommentDto(){}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getPubId() {
		return pubId;
	}

	public void setPubId(int pubId) {
		this.pubId = pubId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	
}

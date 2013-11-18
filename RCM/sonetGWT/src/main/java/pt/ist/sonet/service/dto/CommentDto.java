package pt.ist.sonet.service.dto;

import java.io.Serializable;

public class CommentDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String user;
	int apId;
	String text;
	
	public CommentDto(String user, int ap, String text){
		this.user = user;
		this.apId = ap;
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

	public int getApId() {
		return apId;
	}

	public void setApId(int ApId) {
		this.apId = ApId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	
}

package pt.ist.sonet.service.dto;

public class MessageDto {

	private static final long serialVersionUID = 1L;
	String senderUsername;
	String receiverUsername;
	String text;
	
	public MessageDto(String senderUser, String receiverUser, String text){
		this.senderUsername = senderUser;
		this.receiverUsername = receiverUser;
		this.text = text;		
	}
	
	//for serialization
	public MessageDto(){}
	
	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String sender) {
		this.senderUsername = sender;
	}
	
	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiver) {
		this.receiverUsername = receiver;
	}

//	public int getMessageId() {
//		return msgId;
//	}

//	public void setMessageId(int id) {
//		this.msgId = id;
//	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}

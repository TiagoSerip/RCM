package pt.ist.sonet.domain;

public class Message extends Message_Base {
    
    public  Message() {
        super();
    }
    
    public void init(int id, Agent sender, Agent receiver, String text) {
    	setId(id);
    	setText(text);
    	setSender(sender);
    	setReceiver(receiver);
    }
    
    public String toString() {
    	return this.getSender().getUsername() + ": " + this.getText();
    }
    
}

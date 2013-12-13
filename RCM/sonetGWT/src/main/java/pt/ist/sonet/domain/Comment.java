package pt.ist.sonet.domain;

public class Comment extends Comment_Base {

	public Comment(){
		super();
	}
	
	public void init (Agent comentador, AP ap, String text) {
		setAgent(comentador);
		setAccessPoint(ap);
		setText(text);
	}

	public String toString() {
		return this.getAgent().getName() + ": " + this.getText();
	}

}

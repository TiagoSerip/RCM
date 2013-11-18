package pt.ist.largacaixa;

public class Content {

	private String id;
	
	public Content(String id) {
		this.id = id;
	}

	public String getID() {
		return this.id;
	}

	public boolean isPaid() {
		return false;
	}
	
}

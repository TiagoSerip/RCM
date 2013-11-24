package pt.ist.sonet.exception;

public class IpOutOfMeshException extends SoNetException {

	private static final long serialVersionUID = 1L;

	private String ip;
	
	public IpOutOfMeshException(String ip) {
		this.ip = ip;
	}
	//for serialization
	public IpOutOfMeshException() {
	}
	
	public String getUsername() {
		return ip;
	}
	
}

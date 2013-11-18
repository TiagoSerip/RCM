package pt.ist.sonet.service.bridge;


import pt.ist.sonet.exception.LargaCaixaTransferException;

public interface LargaCaixaServerBridge {
	
	public void transferContent(String contentID, String sourceUser, String destinationUser, Object paymentProof)
				throws LargaCaixaTransferException;

}

package pt.ist.sonet.service.bridge;


import pt.ist.largacaixa.*;
import pt.ist.sonet.exception.LargaCaixaTransferException;


public class LargaCaixaServerLocal implements LargaCaixaServerBridge{
	
	LargaCaixaLocal box = new LargaCaixaLocal();

	public void transferContent(String contentID, String sourceUser, String destinationUser, Object paymentProof)
			throws LargaCaixaTransferException{
		
		try {
			box.shareContent(contentID, sourceUser, destinationUser, paymentProof);
		} catch (InexistentBoxException e) {
			throw new LargaCaixaTransferException("Couldn't transfer content: " + 
					"no such user " + e.getInexistentUsername());
		} catch (InexistentBoxContentException e) {
			throw new LargaCaixaTransferException("Couldn't transfer content: " + 
					"no such content " + e.getInexistentID());
		} catch (PaymentProofRejectedException e) {
			throw new LargaCaixaTransferException("Couldn't transfer content: " + 
					"invalid payment proof");
		} catch (ServiceUnavailableException e) {
			throw new LargaCaixaTransferException("Couldn't transfer content: " + 
					"service is unavailable.");
		}
		
	}
}

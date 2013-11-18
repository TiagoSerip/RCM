package pt.ist.sonet.service.bridge;


import pt.ist.sonet.exception.PagAmigoTransferException;
import pt.ist.sonet.exception.PagaFailureException;

public interface PagAmigoServerBridge {
	
	public void processPayment(String from, String to, int amount, String description) throws 
		PagAmigoTransferException, PagaFailureException;
	public int checkBalance(String user)throws PagaFailureException;
}

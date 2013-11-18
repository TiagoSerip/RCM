package pt.ist.sonet.service.bridge;

import pt.ist.pagamigo.InvalidTransferException;
import pt.ist.pagamigo.PagAmigoLocal;
import pt.ist.pagamigo.ServiceUnavailableException;
import pt.ist.pagamigo.UnknownUserException;
import pt.ist.sonet.exception.PagAmigoTransferException;
import pt.ist.sonet.exception.PagaFailureException;

public class PagAmigoServerLocal implements PagAmigoServerBridge {

	private static PagAmigoLocal paga = new PagAmigoLocal();
	
	
	public void processPayment(String from, String to, int amount, String description) throws 
	PagAmigoTransferException, PagaFailureException {
		try{
		paga.transfer(from, to, amount, description);
		} catch (InvalidTransferException e) {
			throw new PagAmigoTransferException(from, to, amount, description);
		}
		catch(UnknownUserException e){
			throw new PagaFailureException("NOUSER");
		}
		catch(ServiceUnavailableException e){
			throw new PagaFailureException("NOSERVICE");
		}

	}
	
	public int checkBalance(String user)throws PagaFailureException {
		try{
			return paga.getBalance(user);
			}catch(UnknownUserException e){
				throw new PagaFailureException("NOUSER");
			}
			catch(ServiceUnavailableException e){
				throw new PagaFailureException("NOSERVICE");
			}
	}
}

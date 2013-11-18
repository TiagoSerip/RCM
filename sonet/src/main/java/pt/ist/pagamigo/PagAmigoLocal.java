package pt.ist.pagamigo;

/**
 * This class represents a simplified local version of the external service PagaAmigo.
 * The simplifications made are the following ones:
 * - The data is not persistent
 * - Every user has an account.
 * - Each user, represented by a string, starts with a initial amount of money equal to 100.
 * - It just support the money transfer operation.
 *
 * @author ES
 **/

import java.util.Map;
import java.util.HashMap;

public class PagAmigoLocal {
    private static final int INITIAL_AMOUNT_MONEY = new Integer(100);

    private Map<String, Integer> accounts;

    public PagAmigoLocal() {
	this.accounts = new HashMap<String, Integer>();
    }

    /**
     * Transfer the specified amount of money from the the first user's account to the second user's account.
     *
     * @param first the identifier of the first user
     * @param second the identifier of the second user
     * @param amount the amount of money to transfer.
     * @param description description of the transfer
     *
     * @throws InvalidTransferException if the account of the first user has less money than the specified amount of money to transfer.
     **/
    public void transfer(String first, String second, int amount, String description) throws InvalidTransferException, UnknownUserException, ServiceUnavailableException {
	int firstBalance = getBalance(first);

	if (firstBalance < amount)
	    throw new InvalidTransferException();

	setBalance(first, firstBalance - amount);
	setBalance(second, getBalance(second) + amount);

	System.out.println("Transfer of " + amount + " from " + first + " to " + second + " with description '" + description+"'.");
    }

    /**
     * Returns the balance of the user's account.
     *
     * @param user the identifier of the user
     *
     * @return the balance of the account of the specified user.
     **/
    public int getBalance(String user) throws UnknownUserException, ServiceUnavailableException{
	Integer amount = this.accounts.get(user);
	
	if (amount == null) { // first time for the specified user
	    setBalance(user, INITIAL_AMOUNT_MONEY);
	    amount = INITIAL_AMOUNT_MONEY;
	}

	return amount;
    }

    private void setBalance(String user, int amount) {
	this.accounts.put(user, amount);
    }

    public static void main(String argv[]) {
	PagAmigoLocal paga = new PagAmigoLocal();
	String user1 = "user1", user2 = "user2";
	try {
	System.out.println("amount of user1: " + paga.getBalance(user1));

	
	    paga.transfer(user1, user2, 51, "Something");
	    System.out.println("amount of user1: " + paga.getBalance(user1));
	    System.out.println("amount of user2: " + paga.getBalance(user2));
	} catch (InvalidTransferException ite) {
	    System.out.println("Could not make first transfer operation!");
	} catch (UnknownUserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ServiceUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	try {
	    paga.transfer(user1, user2, 50, "Anything");
	    System.out.println("amount of user1: " + paga.getBalance(user1));
	    System.out.println("amount of user2: " + paga.getBalance(user2));
	} catch (InvalidTransferException ite) {
	    System.out.println("Could not make first transfer operation!");
	} catch (UnknownUserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ServiceUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}
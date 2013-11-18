/**
 * 
 */
package pt.ist.sonet.service;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import pt.ist.largacaixa.InexistentBoxContentException;
import pt.ist.largacaixa.InexistentBoxException;
import pt.ist.largacaixa.LargaCaixaLocal;
import pt.ist.largacaixa.PaymentProofRejectedException;
import pt.ist.pagamigo.InvalidTransferException;
import pt.ist.pagamigo.PagAmigoLocal;
import pt.ist.pagamigo.ServiceUnavailableException;
import pt.ist.pagamigo.UnknownUserException;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.LargaCaixaTransferException;
import pt.ist.sonet.exception.PagAmigoTransferException;
import pt.ist.sonet.exception.PagaFailureException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.bridge.LargaCaixaServerBridge;
import pt.ist.sonet.service.bridge.LargaCaixaServerLocal;
import pt.ist.sonet.service.bridge.PagAmigoServerBridge;
import pt.ist.sonet.service.bridge.PagAmigoServerLocal;
/**
 * @author Grupo 8 ES
 *
 */
public class BuyContentTestCase extends SonetServiceTestCase {

	private static String ORG1_NAME = "Organization 1";
	private static String ORG1_USERNAME = "org1";
    private static String ORG1_EMAIL = "fpf@gmail.com";
    private static String ORG1_PASS = "golo";
	private static String ORG2_NAME = "Organization 2";
	private static String ORG2_USERNAME = "org2";
    private static String ORG2_EMAIL = "ist@utl.pt";
    private static String ORG2_PASS = "ist"; 
    private static String CITY = "Lisboa";
    private static String NATION = "Portugal";
    private static String PERMISSION = "publica";
    private static String TYPE = "Organizational";
    private static String IND_NAME = "Individual A";
    private static String IND_USERNAME = "ind1";
    private static String IND_EMAIL = "anacleto@a.pt";
    private static String IND_PASS = "cenas";
    private static String IND2_NAME = "Individual B";
    private static String IND2_USERNAME = "ind2";
    private static String IND2_EMAIL = "anacleto@b.pt";
    private static String IND2_PASS = "cenas";
    private static String TYPEIND = "Individual";
	private static String ORG3_NAME = "Organization 3";
	private static String ORG3_USERNAME = "org3";
    private static String ORG3_EMAIL = "irmaosAnacleto@";
    private static String ORG3_PASS = "irmaos";

	public BuyContentTestCase(String msg) {
		super(msg);
	}
		
	public BuyContentTestCase() {
		super();
	}
		
	@Override
    public void setUp() {
    	super.setUp();
    	try {
    		addAgent(TYPEIND, IND_USERNAME, IND_NAME, IND_EMAIL, IND_PASS, CITY, NATION, PERMISSION);
			addAgent(TYPEIND, IND2_USERNAME, IND2_NAME, IND2_EMAIL, IND2_PASS, CITY, NATION, PERMISSION);
			addAgent(TYPE, ORG1_USERNAME, ORG1_NAME, ORG1_EMAIL, ORG1_PASS, CITY, NATION, PERMISSION);
			addAgent(TYPE, ORG2_USERNAME, ORG2_NAME, ORG2_EMAIL, ORG2_PASS, CITY, NATION, PERMISSION);
			addAgent(TYPE, ORG3_USERNAME, ORG3_NAME, ORG3_EMAIL, ORG3_PASS, CITY, NATION, PERMISSION);
	    } catch(SoNetException e){}
	}	
	    
	/**
	 * Cria um conteudo do ORG_1 pago. Tenta comprar com o IND 
	 * estando o servico PagAmigoLocal indisponivel.
	 */
	public void testBuyWithPagAmigoLocalUnavailable() {
		    
	   	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
	   	PagAmigoServerBridge paga = new PagAmigoServerLocal();
	   	int pubID = -1;
	    	
	   	AddContentService content = new AddContentService(ORG1_USERNAME, "Pago30", "PUB_30EUR", 30);
	   	try{
	   		content.execute();
	   	}catch(SoNetException e){
	   		fail("Failed to register 30 content");
	   	}
	    	
	    try{
	    	GetMyLastPublicationService service = new GetMyLastPublicationService(ORG1_USERNAME);
	    	service.execute();
	    	pubID = service.getPublicationViewDto().getId();
	    }catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }
	    	
	    new MockUp<PagAmigoLocal>(){
	    	
	    	@Mock
	    	public void transfer(String first, String second, int amount, String description) throws ServiceUnavailableException {
	    	   throw new ServiceUnavailableException();
	    	}    
	    		 
	    };

	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND_USERNAME, pubID);
	    	
	    try{
		   	box.execute();
		}catch(LargaCaixaTransferException e){
	    	fail("LargaCaixa Service Failed.");
	    }catch(AgentUsernameDoesNotExistsException e){
	    	fail("Invalid on SoNet");
	    }catch(PagaFailureException e){
		   	assertEquals("PagAmigo wasn't unavailable.", "NOSERVICE", e.getReason());
		   	return;
		}catch(SoNetException e){
	    	fail("An unexpected thing just happened\n"+e);
	    }
	    	
	    fail("Failed to simulate PagAmigo down.");

    }
	    
    /**
	 * Cria um conteudo do ORG_1 pago. Tenta comprar com o IND 
	 * estando o servico LargaCaixaLocal indisponivel.
	 */
    public void testBuyWithLargaCaixaLocalUnavailable() {
	    	
    	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
    	PagAmigoServerBridge paga = new PagAmigoServerLocal();
    	int pubID = -1;
    	
    	AddContentService content = new AddContentService(ORG1_USERNAME, "Pago35", "PUB_35EUR", 35);
    	try{
    		content.execute();
	    }catch(SoNetException e){
	    	fail("Failed to register 35 content");
	    }
	    	
	    try{
	    	GetMyLastPublicationService service = new GetMyLastPublicationService(ORG1_USERNAME);
	    	service.execute();
	    	pubID = service.getPublicationViewDto().getId();
	    }catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }
	    	
	    new MockUp<LargaCaixaLocal>(){
	    	
	    	@Mock
	    	public void shareContent(String contentID, String sourceUser, String destinationUser, 
	    			Object paymentProof) throws pt.ist.largacaixa.ServiceUnavailableException {
	    	   throw new pt.ist.largacaixa.ServiceUnavailableException();
	    	}    
	    		 
	    };

	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND_USERNAME, pubID);
	    	
	    try{
		   	box.execute();
		} catch(LargaCaixaTransferException e){
			assertEquals("Service wasn't unavailable", "Couldn't transfer content: "+"service is unavailable.", e.getDescription());
			return;
		} catch(AgentUsernameDoesNotExistsException e){
			fail("Invalid on SoNet");
		}catch(PagaFailureException e){
		   	fail("Invalid user on PagAmigo");
		}catch(SoNetException e){
			fail("An unexpected thing just happened\n"+e);
		}
	    
	    fail("Failed to simulate LargaCaixa down.");

    }
	    
	/**
	 * Cria um conteudo do ORG_1 gratis. Tenta comprar com o IND.
	 */
	public void testBuyFreeContent() {
	    	
	   	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
	   	final PagAmigoServerBridge paga = new PagAmigoServerLocal();
	   	int pubID = -1;
	    	
	   	AddContentService content = new AddContentService(ORG1_USERNAME, "Gratis", "PUB_FREE", 0);
	   	try{
	   		content.execute();
	   	}catch(SoNetException e){
	    	fail("Failed to register free content");
	    }
	    	
	    try{
	    	GetMyLastPublicationService service = new GetMyLastPublicationService(ORG1_USERNAME);
	    	service.execute();
	    	pubID = service.getPublicationViewDto().getId();
	    }catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }
	    	
	    new Expectations(paga){
	       {
	 	   	final PagAmigoLocal pagalocal;
	        pagalocal = Deencapsulation.getField(paga, "paga");
	        Deencapsulation.invoke(pagalocal, "setBalance", IND_USERNAME, 100);
	       }
	    };
	    	
	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND_USERNAME, pubID);
	    	
	    try{
		   	box.execute();
		}catch(SoNetException e){
			fail("Failed to obtain Free content");
		}
	    		    	
	    try{
	    	assertEquals("Balance was different than expected.", 100, paga.checkBalance(IND_USERNAME));
	    }catch(PagaFailureException e){
	    	fail("Failed to obtain account balance - Service response was: "+e.getReason());
	    }
	    	
	}
	    
	/**
	 * Cria um conteudo do ORG_1 pago. Tenta comprar com o IND.
	 */
	public void testBuyPaidContent() {
	    	
	   	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
	   	final PagAmigoServerBridge paga = new PagAmigoServerLocal();
	   	int pubID = -1;
	    	
	   	AddContentService content = new AddContentService(ORG1_USERNAME, "Pago5", "PUB_5EUR", 5);
	   	try{
	   		content.execute();
	    }catch(SoNetException e){
	    	fail("Failed to register 5 content");
	    }
	    	
	    try{
	    	GetMyLastPublicationService service = new GetMyLastPublicationService(ORG1_USERNAME);
	    	service.execute();
	    	pubID = service.getPublicationViewDto().getId();
	    }catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }

	    new Expectations(paga){
	    	{
	    		final PagAmigoLocal pagalocal;
	    		pagalocal = Deencapsulation.getField(paga, "paga");
	    		Deencapsulation.invoke(pagalocal, "setBalance", IND2_USERNAME, 100);
	    	}
	    };
	    	
	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND_USERNAME, pubID);
	    	
	    try{
		   	box.execute();
		}catch(SoNetException e){
			fail("Failed to obtain Free content");
		}
	    
	    try{
	    	assertEquals("Balance was different than expected.", 95, paga.checkBalance(IND_USERNAME));
	    }catch(PagaFailureException e){
	    	fail("Failed to obtain account balance - Service response was: "+e.getReason());
	    }
	}
	    
	/**
	 * Cria um conteudo do ORG_1 pago. Tenta comprar com o IND, simulando
	 * que no PagAmigo nao existe o user.
	 */
	public void testBuyWithUnregisteredPagaRemoteUser() {
	    	
	   	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
	   	PagAmigoServerBridge paga = new PagAmigoServerLocal();
	   	int pubID = -1;
	    	
	   	try{
	   		GetMyLastPublicationService service = new GetMyLastPublicationService(ORG1_USERNAME);
	   		service.execute();
	   		pubID = service.getPublicationViewDto().getId();
	   	}catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }
	    	
	    new MockUp<PagAmigoLocal>(){

		   @Mock
		   public void transfer(String first, String second, int amount, String description) throws UnknownUserException{
			   throw new UnknownUserException();
		   }
   		};

	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND_USERNAME, pubID);
	    	
	    try{
		   	box.execute();
		}catch(AgentUsernameDoesNotExistsException e){
			fail("Invalid on SoNet");
		}catch(PagaFailureException e){
			assertEquals("Invalid user on remote service.","NOUSER", e.getReason());
			return;
		}catch(SoNetException e){
			fail("Failed to obtain content");
		}
	    	
	    fail("Failed to simulate invalid user on PagAmigo.");
	    	
	}
	    
	/**
	 * Cria um conteudo do ORG_2 pago e preco 200. Tenta comprar com o IND.
	 */
	public void testBuyContentNoFunds() {
	    	
	   	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
	   	PagAmigoServerBridge paga = new PagAmigoServerLocal();
	   	int pubID = -1;
	    	
	   	AddContentService content = new AddContentService(ORG2_USERNAME, "Caro", "PUB_200", 200);
	   	try{
	   		content.execute();
	    }catch(SoNetException e){
	    	fail("Failed to register 200 content");
	    }
	    	
	    try{
	    	GetMyLastPublicationService service = new GetMyLastPublicationService(ORG2_USERNAME);
	    	service.execute();
	    	pubID = service.getPublicationViewDto().getId();
	    }catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }
	    	
	    new MockUp<PagAmigoLocal>(){
	    	    
    	   @Mock
    	   public void transfer(String first, String second, int amount, String description) throws InvalidTransferException{
    		   throw new InvalidTransferException();
    	   }
		};

	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND_USERNAME, pubID);
	    	
	    try{
		   	box.execute();
		}catch(AgentUsernameDoesNotExistsException e){
			fail("Invalid user on SoNet");
		}catch(PagaFailureException e){
			fail("Paga Failure. Server reposnse was: "+e.getReason());
		}catch(PagAmigoTransferException e){
			assertEquals("Amount was different than expected.", 200, e.getAmount());
			return;
		}catch(SoNetException e){
			fail("Failed to obtain content");
		}
	    
	    fail("Bought a content without the necessary funds.");

	}
	    
	/**
	 * Cria um conteudo do ORG_2 pago e preco 50. Tenta comprar 3x com o IND2.
	 */
	public void testBuyContentOkOkFail() {
	    	
	  	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
	   	final PagAmigoServerBridge paga = new PagAmigoServerLocal();
	   	int pubID = -1;
	
	  	new Expectations(paga){
	   	   {
	    	final PagAmigoLocal pagalocal;
	   	    pagalocal = Deencapsulation.getField(paga, "paga");
	   	    Deencapsulation.invoke(pagalocal, "setBalance", IND2_USERNAME, 100);
	   	   }
	  	};
	    	
	    AddContentService content = new AddContentService(ORG2_USERNAME, "Meia-conta", "PUB_50", 50);
	    try{
	    	content.execute();
	    }catch(SoNetException e){
	    	fail("Failed to register 50 content");
	    }
	    	
	    try{
	    	GetMyLastPublicationService service = new GetMyLastPublicationService(ORG2_USERNAME);
	    	service.execute();
	    	pubID = service.getPublicationViewDto().getId();
	    }catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }
	    	
	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND2_USERNAME, pubID);
	    	
	    try{
		   	box.execute();
		}catch(AgentUsernameDoesNotExistsException e){
			fail("Invalid user on SoNet");
		}catch(PagaFailureException e){
			fail("Paga Failure. Server reposnse was: "+e.getReason());
		}catch(PagAmigoTransferException e){
			fail("Failed to buy for the first time.");
		}catch(SoNetException e){
			fail("Failed to obtain content");
		}
	    
	    try{
		  	box.execute();
		}catch(AgentUsernameDoesNotExistsException e){
			fail("Invalid user on SoNet");
		}catch(PagaFailureException e){
			fail("Paga Failure. Server reposnse was: "+e.getReason());
		}catch(PagAmigoTransferException e){
			fail("Failed to buy for the second time.");
		}catch(SoNetException e){
			fail("Failed to obtain content");
		}
	    
	    try{
		   	box.execute();
		}catch(AgentUsernameDoesNotExistsException e){
			fail("Invalid user on SoNet");
		}catch(PagaFailureException e){
		 	fail("Paga Failure. Server reposnse was: "+e.getReason());
		}catch(PagAmigoTransferException e){
		 	assertEquals("Balance was not 0.", 0, paga.checkBalance(IND2_USERNAME));
		 	return;
		 }catch(SoNetException e){
		 	fail("Failed to obtain content");
		 }
	    	
	    fail("Bought 3 times the content when it should fail on the third time.");

	}
	    
	/**
	 * Cria um conteudo do ORG_3 pago. Tenta comprar com o IND, simulando
	 * que nao existe o user no LargaCaixa.
	 */
	public void testBuyWithUnregisteredLargaRemoteUser() {
	    	
	   	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
	   	final PagAmigoServerBridge paga = new PagAmigoServerLocal();
	   	int pubID = -1;
	    	
	   	AddContentService content = new AddContentService(ORG3_USERNAME, "Custa 20", "PUB_20", 20);
	   	try{
	   		content.execute();
	   	}catch(SoNetException e){
	    	fail("Failed to register content");
	   	}
	    	
	    try{
	    	GetMyLastPublicationService service = new GetMyLastPublicationService(ORG3_USERNAME);
	    	service.execute();
	    	pubID = service.getPublicationViewDto().getId();
	    }catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }

	    new Expectations(paga){
	       {
	   	   	final PagAmigoLocal pagalocal;
	        pagalocal = Deencapsulation.getField(paga, "paga");
	        Deencapsulation.invoke(pagalocal, "setBalance", IND_USERNAME, 100);
	       }
	    };
	    	
	    new MockUp<LargaCaixaLocal>(){
	    	    
    	   @Mock
    	   public void shareContent(String contentID, 
                        String sourceUser, 
                        String destinationUser, 
                        Object paymentProof)
                       		 throws InexistentBoxException, 
                       		 InexistentBoxContentException,
                       		 PaymentProofRejectedException {
    		   throw new InexistentBoxException("");
    	   }
	    };

	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND_USERNAME, pubID);
	    	
	    try{
		   	box.execute();
		}catch(AgentUsernameDoesNotExistsException e){
			fail("Invalid on SoNet");
		}catch(PagaFailureException e){
			fail("Invalid user on PagAmigo");
		}catch(LargaCaixaTransferException e){
			assertEquals("Did not place a return.", 100, paga.checkBalance(IND_USERNAME));
			return;
		}catch(SoNetException e){
			fail("Failed to obtain content");
		}
	    
	    fail("Failed to simulate LargaCaixa invalid user.");

	}
	    
	/**
	 * Cria um conteudo do ORG_3 pago. Tenta comprar com o IND, simulando
	 * que nao existe o conteudo no LargaCaixa.
	 */
	public void testBuyWithInvalidContentURLRemote() {
	    	
	   	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
	   	final PagAmigoServerBridge paga = new PagAmigoServerLocal();
	   	int pubID = -1;
	    	
	   	AddContentService content = new AddContentService(ORG3_USERNAME, "Custa 10", "PUB_10", 10);
	   	try{
	   		content.execute();
	    }catch(SoNetException e){
	    	fail("Failed to register content");
	    }
	    	
	    try{
	    	GetMyLastPublicationService service = new GetMyLastPublicationService(ORG3_USERNAME);
	    	service.execute();
	    	pubID = service.getPublicationViewDto().getId();
	    }catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }
	    	
	    new Expectations(paga){
	       {
	   	   	final PagAmigoLocal pagalocal;
	        pagalocal = Deencapsulation.getField(paga, "paga");
	        Deencapsulation.invoke(pagalocal, "setBalance", IND_USERNAME, 100);
	       }
	    };
	    	
	    new MockUp<LargaCaixaLocal>(){

    	   @Mock
    	   public void shareContent(String contentID, 
                        String sourceUser, 
                        String destinationUser, 
                        Object paymentProof)
                       		 throws InexistentBoxException, 
                       		 	InexistentBoxContentException,
                       		 	PaymentProofRejectedException {
			    throw new InexistentBoxContentException("");
    		}
	    };

	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND_USERNAME, pubID);
	    
	    try{
		   	box.execute();
		}catch(AgentUsernameDoesNotExistsException e){
			fail("Invalid on SoNet");
		}catch(PagaFailureException e){
			fail("Invalid user on PagAmigo");
		}catch(LargaCaixaTransferException e){
			assertEquals("Did not place a return.", 100, paga.checkBalance(IND_USERNAME));
			return;
		}catch(SoNetException e){
			fail("Failed to obtain content");
		}
	    	
	    fail("Failed to simulate LargaCaixa invalid remote content.");

	}
	    
	/**
	 * Cria um conteudo do ORG_3 pago. Tenta comprar com o IND, simulando
	 * que o LargaCaixa rejeitou a prova de pagamento.
	 */
	public void testBuyWithInvalidPaymentProofRemote() {
	    	
	   	LargaCaixaServerBridge larga = new LargaCaixaServerLocal();
	   	final PagAmigoServerBridge paga = new PagAmigoServerLocal();
	   	int pubID = -1;
	    	
	   	AddContentService content = new AddContentService(ORG3_USERNAME, "Custa 15", "PUB_15", 15);
	   	try{
	   		content.execute();
	    }catch(SoNetException e){
	    	fail("Failed to register content");
	    }

	    try{
	    	GetMyLastPublicationService service = new GetMyLastPublicationService(ORG3_USERNAME);
	    	service.execute();
	    	pubID = service.getPublicationViewDto().getId();
	    }catch (SoNetException e){
	    	fail("failed to obtain publication from SoNet.");
	    }
	    
	    new Expectations(paga){
	       {
	    	   final PagAmigoLocal pagalocal;
	    	   pagalocal = Deencapsulation.getField(paga, "paga");
	    	   Deencapsulation.invoke(pagalocal, "setBalance", IND_USERNAME, 100);
	       }
	    };
	    	
	    new MockUp<LargaCaixaLocal>(){

    	   @Mock
    	   public void shareContent(String contentID, 
                        String sourceUser, 
                        String destinationUser, 
                        Object paymentProof)
                       		 throws InexistentBoxException, 
                       		 	InexistentBoxContentException,
                        		PaymentProofRejectedException {
			    throw new PaymentProofRejectedException();
    		}
	    };

	    ObtainLargaCaixaContentService box = new ObtainLargaCaixaContentService(paga, larga, IND_USERNAME, pubID);
	    
	    try{
		    box.execute();
		}catch(AgentUsernameDoesNotExistsException e){
			fail("Invalid on SoNet");
		}catch(PagaFailureException e){
			fail("Invalid user on PagAmigo");
		}catch(LargaCaixaTransferException e){
			assertEquals("Did not place a return.", 100, paga.checkBalance(IND_USERNAME));
			return;
		}catch(SoNetException e){
			fail("Failed to obtain content");
		}
	    
	    fail("Failed to simulate LargaCaixa proof rejection.");

	}
	    
}
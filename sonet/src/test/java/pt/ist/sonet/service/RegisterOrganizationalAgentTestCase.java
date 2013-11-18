/**
 * 
 */
package pt.ist.sonet.service;

import pt.ist.sonet.exception.NameAlreadyExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
/**
 * @author Grupo 8 ES
 *
 */
public class RegisterOrganizationalAgentTestCase extends SonetServiceTestCase {

	private static String ORG1_NAME = "FederacaoPortuguesaFutebol";
	private static String ORG1_USERNAME = "FPF";
    private static String ORG1_EMAIL = "fpf@gmail.com";
    private static String ORG1_PASS = "golo";
	private static String ORG2_NAME = "InstitutoSuperiorTecnico";
	private static String ORG2_USERNAME = "I.S.T.";
    private static String ORG2_EMAIL = "ist@utl.pt";
    private static String ORG2_PASS = "ist"; 
    private static String CITY = "Lisboa";
    private static String NATION = "Portugal";
    private static String PERMISSION = "publica";
    private static String TYPE = "Organizational";
    private static String IND_NAME = "Anacleto";
    private static String IND_USERNAME = "cleto";
    private static String IND_EMAIL = "anacleto@";
    private static String IND_PASS = "cenas";
    private static String TYPEIND = "Individual";
	private static String ORG3_NAME = "Anacleto";
	private static String ORG3_USERNAME = "IrmaosAnacletoLtd";
    private static String ORG3_EMAIL = "irmaosAnacleto@";
    private static String ORG3_PASS = "irmaos";

    
	public RegisterOrganizationalAgentTestCase(String msg) {
		super(msg);
	}
		
	public RegisterOrganizationalAgentTestCase() {
		super();
	}
		
	@Override
	public void setUp() {
	  	super.setUp();
	  	try {
	  		addAgent(TYPEIND, IND_USERNAME, IND_NAME, IND_EMAIL, IND_PASS, CITY, NATION, PERMISSION);
		} catch(SoNetException e){}
	}	  

	/**
	 * Regista o agente organizacional Anacleto que tem o mesmo nome que um agente individual ja registado
	 * Testa se o registo foi efectuado com sucesso.
	 */
	public void testRegisterOrganizationalAgentWithSameNameOfIndividual() {
	    	
	   	boolean regist = false;
	    	
	   	RegisterAgentService service = registerAgent(TYPE, ORG3_USERNAME, ORG3_NAME, ORG3_EMAIL, ORG3_PASS, CITY, NATION, PERMISSION);

	   	try {
	   		service.execute(); 
	   		regist = existsOrganizationalAgent(ORG3_USERNAME);
	   	} catch(UsernameAlreadyExistsException e) {
	    	fail("Could not add new unique agent (User Exception).");
	    } catch(NameAlreadyExistsException e) {
	   		fail("Could not add new unique agent (Name Exception).");
	   	} catch(SoNetException e) {
	   		fail("Unexpected SoNetException\n" + e);
	   	} 
	   	
	   	assertTrue("Nao registou o novo agente.", regist);
	}
	    
	    
	/**
	 * Regista o agente organizacional IST
	 * Testa se o registo foi efectuado com sucesso.
	 */
	public void testRegisterOrganizationalAgentIST() {
	    	
	   	boolean regist = false;
	   	
	   	RegisterAgentService service = registerAgent(TYPE, ORG2_USERNAME, ORG2_NAME, ORG2_EMAIL, ORG2_PASS, CITY, NATION, PERMISSION);
	    	
	   	try {
	   		service.execute(); 
	   		regist = existsOrganizationalAgent(ORG2_USERNAME);
	   	} catch(UsernameAlreadyExistsException e) {
	    	fail("Could not add new unique agent (User Exception).");
	    } catch(NameAlreadyExistsException e) {
	   		fail("Could not add new unique agent (Name Exception).");
	   	} catch(SoNetException e) {
	   		fail("Unexpected SoNetException\n" + e);
	   	} 
	    	
	   	assertTrue("Nao registou o novo agente.", regist);
	   	
	} 
	    
	/**
	 * Regista novamente o agente organizacional IST
	 * Testa se deixou efectuar o registo de um agente ja existente.
	 */
	public void testRegisterAgainOrganizationalAgentIST() {
	   	
	   	boolean regist = false;
	    	
	   	RegisterAgentService service = registerAgent(TYPE, ORG2_USERNAME, ORG2_NAME, ORG2_EMAIL, ORG2_PASS, CITY, NATION, PERMISSION);
	 
		//Act
		try{
			service.execute();
			regist = existsOrganizationalAgent(ORG2_USERNAME);
		} catch(UsernameAlreadyExistsException e) {
			assertFalse("O agente ficou registado quando ja existia um igual.", regist);
		} catch(NameAlreadyExistsException e) {
			assertFalse("O agente ficou registado quando ja existia um igual.", regist);
		} catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}
			
		//Assert
		assertFalse("O agente ficou registado quando ja existia um igual.", regist);
	}
	      
	/**
	 * Regista o agente organizacional FPF mas com um nome de organizacao ja existente
	 * Testa se deixou efectuar o registo quando ja existe uma organizacao com o mesmo nome.
	 */
	public void testRegisterOrganizationalAgentWithSameName() {
	    	
	   	Boolean  regist = false;
	    	
	   	RegisterAgentService service = registerAgent(TYPE, ORG1_USERNAME, ORG2_NAME, ORG1_EMAIL, ORG1_PASS, CITY, NATION, PERMISSION);
	 
		//Act
		try{
			
			service.execute();
			regist = existsOrganizationalAgent(ORG1_USERNAME);
				
		} catch(NameAlreadyExistsException e) {
			assertFalse("O agente ficou registado quando ja existia um com o mesmo nome.", regist);
		} catch(UsernameAlreadyExistsException e) {
			fail("Username already exists");
		} catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}
			
		//Assert
		assertFalse("O agente ficou registado quando ja existia um com o mesmo nome.", regist);
	}
	    
	/**
	 * Regista o agente organizacional FPF mas com um username ja existente
	 * Testa se deixou efectuar o registo quando ja existe uma organizacao com o mesmo username.
	 */
	public void testRegisterOrganizationalAgentWithSameUser() {
	    	
	   	Boolean  regist = false;
	   	
	   	RegisterAgentService service = registerAgent(TYPE, ORG2_USERNAME, ORG1_NAME, ORG1_EMAIL, ORG1_PASS, CITY, NATION, PERMISSION);
	 
		//Act
		try{
				
			service.execute();
			regist = existsOrganizationalAgent(ORG1_NAME);
				
		} catch(NameAlreadyExistsException e) {
			fail("Name already exists");
		} catch(UsernameAlreadyExistsException e) {
			assertFalse("O agente ficou registado quando ja existia um com o mesmo user.", regist);
		} catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}
			
		//Assert
		assertFalse("O agente ficou registado quando ja existia um com o mesmo user.", regist);
	} 
}
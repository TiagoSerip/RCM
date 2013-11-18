package pt.ist.sonet.service;


import pt.ist.sonet.exception.FriendAlreadyExistsException;
import pt.ist.sonet.exception.FriendLimitExceededException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.exception.TargetAlreadySentRequestException;
import pt.ist.sonet.exception.TargetIsAlreadyFriendException;
import pt.ist.sonet.exception.YouAlreadySentRequestException;


public class AddFriendTest extends SonetServiceTestCase{	
	
	private static String ORGANIZATIONAL = "Organizational";
	private static String INDIVIDUAL = "Individual";
	private static String USER1 = "ze";
	private static String USER2 = "maria";
	private static String USER3 = "joao";
	private static String USERORG = "ist";
	private static String NOME1 = "Zé Mário";
	private static String NOME2 = "Maria Abc";
	private static String NOME3 = "João Manuel";
	private static String NOMEORG = "IST - UTL";
	private static String EMAIL1  =	"ze@mail.pt";
	private static String EMAIL2  =	"maria@mail.pt";
	private static String EMAIL3  =	"joao@mail.pt";
	private static String EMAILORG  = "ist@mail.pt";
	private static String PASS1  =	"123456";
	private static String PASS2  =	"1234567";
	private static String PASS3  = "123";
	private static String PASSORG  = "fenix";
	private static String CIDADE  =	"Lisboa";
	private static String PAIS  =	"Portugal";
	private static String PERMISSION = "amigo";
	private static String PERMISSIONORG = "publica";	
			
	public AddFriendTest() {
		super();
	}

	public AddFriendTest(String msg) {
		super(msg);
	}

	public void setUp(){
		super.setUp();
		try{
			addAgent(ORGANIZATIONAL, USERORG, NOMEORG, EMAILORG, PASSORG, CIDADE, PAIS, PERMISSIONORG);
			
			addAgent(INDIVIDUAL, USER1, NOME1, EMAIL1, PASS1, CIDADE, PAIS, PERMISSION);

			addAgent(INDIVIDUAL, USER2, NOME2, EMAIL2, PASS2, CIDADE, PAIS, PERMISSION);

			addAgent(INDIVIDUAL, USER3, NOME3, EMAIL3, PASS3, CIDADE, PAIS, PERMISSION);
			}catch(SoNetException e){}
	}

	/**
	 * Envia pedido de amizade da Maria para o Ze
	 * Testa se o pedido ficou registado com sucesso.
	 */
	public void testSendRequestFromMariaToZe(){
		
		boolean pending = false;
		
		//Arrange		
		FriendRequestService service = friendRequest(USER2, USER1);
				
		//Act
		try{
			service.execute();
			pending = checkRequest(USER1, USER2);
		}catch(FriendAlreadyExistsException e){
			fail("Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetAlreadySentRequestException e){
			fail(e.getConflictingName()+" already sent you a friend request");
		}catch(YouAlreadySentRequestException e){
			fail("A request is already pending.");
		}catch(TargetIsAlreadyFriendException e){
			fail("You are already friends");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n"+e);
		}
		
		//Assert
		assertTrue("O pedido nao ficou registado.",pending);
	}
	
	/**
	 * Envia novo pedido de amizade da Maria para o Ze
	 * Testa se deixou efectuar novo pedido, ou recebe a devida excepção.
	 */
	public void testReSendRequestFromMariaToZe(){
		
		boolean pending = false;
		
		//Arrange		
		FriendRequestService service = friendRequest(USER2, USER1);
				
		//Act
		try{
			service.execute();
			pending = checkRequest(USER1, USER2);

		}catch(FriendAlreadyExistsException e){
			fail("Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetAlreadySentRequestException e){
			fail(e.getConflictingName()+" already sent you a friend request");
		}catch(YouAlreadySentRequestException e){
			assertFalse("Deixou efectuar novo pedido.",pending);
		}catch(TargetIsAlreadyFriendException e){
			fail("You are already friends");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n"+e);
		}
		
		//Assert
		assertFalse("Deixou efectuar novo pedido.",pending);
	}
	
	/**
	 * Aceita de amizade da Maria para o Ze
	 * Testa se a ligacao ficou registada e se o pedido foi apagado.
	 */
	public void testAcceptFromMariaToZe(){
		
		boolean pending = false;

		boolean friends = false;
		//Arrange		
		ConfirmFriendRequestService service = confirmRequest(USER2, USER1);
				
		//Act
		try{
			service.execute();
			friends = checkIsFriend(USER1, USER2);
			pending = checkRequest(USER1, USER2);

		}catch(FriendAlreadyExistsException e){
			fail("Friend: Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetIsAlreadyFriendException e){
			fail("Target: Users are already friends.");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n"+e);
		}
		
		//Assert
		assertTrue("Falhou ao aceitar pedido.",friends);
		assertFalse("O pedido nao foi apagado.",pending);
	}
	
	/**
	 * Tenta enviar um novo pedido de ligacao da maria para o Ze.
	 * Testa se o deixa efectuar novo pedido ja sendo amigos. Devendo
	 * apanhar a excepcao devida.
	 */
	public void testSendRequestToAlreadyFriendFromMariaToZe(){
		
		boolean pending = false;
		
		//Arrange		
		FriendRequestService service = friendRequest(USER2, USER1);
				
		//Act
		try{
			service.execute();
			pending=checkRequest(USER1, USER2);
		}catch(FriendAlreadyExistsException e){
			fail("Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetAlreadySentRequestException e){
			fail(e.getConflictingName()+" already sent you a friend request");
		}catch(YouAlreadySentRequestException e){
			fail("A request is already pending.");
		}catch(TargetIsAlreadyFriendException e){
			assertFalse("Deixou efectuar novo pedido ja sendo amigos.",pending);
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n"+e);
		}
		
		//Assert
		assertFalse("Deixou efectuar novo pedido ja sendo amigos.",pending);
	}
	
	/**
	 * Envia pedido de amizade do Ze para o IST
	 * Testa se o pedido foi aceite e a ligacao estabelecida.
	 */
	public void testSendRequestFromZeToIst(){
		
		boolean friends = false;
		boolean pending = false;
		
		//Arrange		
		FriendRequestService service = friendRequest(USER1, USERORG);
				
		//Act
		try{
			service.execute();
			pending=checkRequest(USER1, USERORG);
			friends=checkIsFriend(USER1, USERORG);
		}catch(FriendAlreadyExistsException e){
			fail("Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetAlreadySentRequestException e){
			fail(e.getConflictingName()+" already sent you a friend request");
		}catch(YouAlreadySentRequestException e){
			fail("A request is already pending.");
		}catch(TargetIsAlreadyFriendException e){
			fail("You are already friends");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}
		
		//Assert
		assertFalse("O pedido ficou registado.", pending);
		assertTrue("O pedido de amizade nao foi automaticamente aceite.", friends);
	}
	
	/**
	 * Envia novamente pedido de amizade do Ze para o IST
	 * Testa se o pedido e' aceite ou se apaanha a devida excepcao.
	 */
	public void testReSendRequestFromZeToIst(){
		
		boolean friends = false;
		boolean pending = false;
		
		//Arrange		
		FriendRequestService service = friendRequest(USER1, USERORG);
				
		//Act
		try{
			service.execute();
			pending=checkRequest(USER1, USERORG);
			friends=checkIsFriend(USER1, USERORG);

		}catch(FriendAlreadyExistsException e){
			assertFalse("O pedido ficou registado.",pending);
			assertFalse("O pedido de amizade nao foi novamente aceite.",friends);
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetAlreadySentRequestException e){
			fail("It should not exist a pending request.");
		}catch(YouAlreadySentRequestException e){
			fail("A request is already pending.");
		}catch(TargetIsAlreadyFriendException e){
			assertFalse("O pedido ficou registado.",pending);
			assertFalse("O pedido de amizade nao foi novamente aceite.",friends);
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}
		
		//Assert
		assertFalse("O pedido ficou registado.", pending);
		assertFalse("O pedido de amizade nao foi novamente aceite.", friends);
	}
	
	/**
	 * Envia pedido de amizade do Ze para o Joao
	 * Testa se o pedido ficou registado com sucesso.
	 */
	public void testSendRequestFromZeToJoao(){
		
		boolean pending = false;
		
		//Arrange		
		FriendRequestService service = friendRequest(USER1, USER3);
				
		//Act
		try{
			service.execute();
			pending=checkRequest(USER3, USER1);
		}catch(FriendAlreadyExistsException e){
			fail("Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetAlreadySentRequestException e){
			fail(e.getConflictingName()+" already sent you a friend request");
		}catch(YouAlreadySentRequestException e){
			fail("A request is already pending.");
		}catch(TargetIsAlreadyFriendException e){
			fail("You are already friends");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}
		
		//Assert
		assertTrue("O pedido nao ficou registado.", pending);
	}
	
	/**
	 * Rejeita o pedido de amizade do Ze para o Joao
	 * Testa se o pedido foi apagado com sucesso e se nao
	 * ficaram amigos.
	 */
	public void testRejectFromZeToJoao(){
		
		boolean friends = false;
		boolean pending = false;
		
		//Arrange		
		RejectFriendRequestService service = rejectRequest(USER1, USER3);
				
		//Act
		try{
			service.execute();
			pending=checkRequest(USER3, USER1);
			friends=checkIsFriend(USER1, USER3);
		}catch(FriendAlreadyExistsException e){
			fail("Friend: Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetIsAlreadyFriendException e){
			fail("Target: Users are already friends.");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}
		
		//Assert
		assertFalse("Falhou ao rejeitar pedido.", friends);	
		assertFalse("O pedido continuou na lista.", pending);
	}
	
	/**
	 * Envia pedido de amizade do Ze para o Joao
	 * Testa se o pedido ficou registado com sucesso.
	 */
	public void testSendAgainRequestFromZeToJoao(){
		
		boolean pending = false;
		
		//Arrange		
		FriendRequestService service = friendRequest(USER1, USER3);
				
		//Act
		try{
			service.execute();
			pending=checkRequest(USER3, USER1);

		}catch(FriendAlreadyExistsException e){
			fail("Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetAlreadySentRequestException e){
			fail(e.getConflictingName()+" already sent you a friend request");
		}catch(YouAlreadySentRequestException e){
			fail("A request is already pending.");
		}catch(TargetIsAlreadyFriendException e){
			fail("You are already friends");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}
		
		//Assert
		assertTrue("O pedido nao ficou registado.", pending);
	}
	
	/**
	 * Envia pedido de amizade do Joao para o Ze
	 * Testa se o pedido e' recusado por ja existir um pendente
	 */
	public void testSendRequestWithPendingFromJoaoToZe(){
	
		boolean friends = false;
		boolean pending = false;
		
		//Arrange		
		FriendRequestService service = friendRequest(USER3, USER1);
				
		//Act
		try{
			service.execute();
			pending=checkRequest(USER1, USER3);
			friends=checkIsFriend(USER1, USER3);

		}catch(FriendAlreadyExistsException e){
			fail("Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail(e.getUsername()+" has exceeded the friend limit.");
		}catch(TargetAlreadySentRequestException e){
			assertFalse("O pedido ficou registado.",pending);
			assertFalse("Os agentes ficaram amigos.",friends);
		}catch(YouAlreadySentRequestException e){
			fail("A request is already pending.");
		}catch(TargetIsAlreadyFriendException e){
			fail("You are already friends");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		} 
		
		//Assert
		assertFalse("O pedido ficou registado.", pending);
		assertFalse("Os agentes ficaram amigos.", friends);
	}
	
	/**
	 * Aceita pedido do Ze para o Joao tendo o Ze excedido
	 * o numero maximo de amigos possivel pela rede.
	 * Testa se o pedido continua pendente e se a ligacao nao
	 * se estabaleceu.
	 */
	public void testConfirmFromZeToJoaoExceedLimit(){
		
		boolean friends = false;
		boolean pending = false;
	
		//Arrange		
		setSoNetFriendLimit(2);
		ConfirmFriendRequestService service = confirmRequest(USER1, USER3);
	
		//Act
		try{
			service.execute();
			pending=checkRequest(USER3, USER1);
			friends=checkIsFriend(USER1, USER3);

		}catch(FriendAlreadyExistsException e){
			fail("Friend: Users are already friends.");
		}catch(FriendLimitExceededException e){
			assertFalse("Falhou ao rejeitar pedido.",friends);
			assertFalse("O pedido continuou na lista.", pending);
		}catch(TargetIsAlreadyFriendException e){
			fail("Target: Users are already friends.");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}
		
		//Assert
		assertFalse("Falhou ao rejeitar pedido.", friends);
		assertFalse("O pedido continuou na lista.", pending);
	}
	
	/**
	 * Envia um pedido da Maria para o IST tendo a Maria excedido
	 * o numero maximo de amigos possivel pela rede.
	 * Testa se o pedido nao pendente e se a ligacao nao
	 * se estabaleceu.
	 */
	public void testSendRequestFromMariaToIst(){
		
		boolean friends = false;
		boolean pending = false;
		
		//Arrange		
		FriendRequestService service = friendRequest(USER1, USERORG);
				
		//Act
		try{
			service.execute();
			pending=checkRequest(USER1, USERORG);
			friends=checkIsFriend(USER1, USERORG);

		}catch(FriendAlreadyExistsException e){
			fail("Users are already friends.");
		}catch(FriendLimitExceededException e){
			assertFalse("O pedido ficou registado.",pending);
			assertFalse("O pedido de amizade foi aceite.",friends);
		}catch(TargetAlreadySentRequestException e){
			fail(e.getConflictingName()+" already sent you a friend request");
		}catch(YouAlreadySentRequestException e){
			fail("A request is already pending.");
		}catch(TargetIsAlreadyFriendException e){
			fail("You are already friends");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n" + e);
		}	
		
		//Assert
		assertFalse("O pedido ficou registado.", pending);
		assertFalse("O pedido de amizade foi aceite.", friends);
				
	}
	
	/**
	 * Aceita pedido do Ze para o Joao com um novo limite na rede.
	 * Testa se o pedido foi eliminado e se a ligacao 
	 * se estabaleceu.
	 */
	public void testConfirmFromZeToJoaoWithNewLimit(){
		
		Boolean friends = false;
		Boolean pending = false;
		
		//Arrange		
		ConfirmFriendRequestService service = confirmRequest(USER1, USER3);
				
		//Act
		try{
			setSoNetFriendLimit(100);
			service.execute();
			pending=checkRequest(USER3, USER1);
			friends=checkIsFriend(USER1, USER3);
		}catch(FriendAlreadyExistsException e){
			fail("Friend: Users are already friends.");
		}catch(FriendLimitExceededException e){
			fail("New limit was not sucessfully applied.");
		}catch(TargetIsAlreadyFriendException e){
			fail("Target: Users are already friends.");
		}catch (SoNetException e) {
			fail("Unexpected SoNetException\n"+e);
		}
		
		//Assert
		assertTrue("Não ficaram amigos.", friends);
		assertFalse("O pedido continuou na lista.", pending);
	}
}

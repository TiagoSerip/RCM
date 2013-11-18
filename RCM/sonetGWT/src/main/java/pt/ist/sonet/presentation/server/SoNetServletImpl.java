package pt.ist.sonet.presentation.server;

import java.util.ArrayList;


import pt.ist.fenixframework.Config;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.AgentsCantVoteInTheirOwnPublicationsException;
import pt.ist.sonet.exception.AlreadyVotedException;
import pt.ist.sonet.exception.FriendAlreadyExistsException;
import pt.ist.sonet.exception.FriendLimitExceededException;
import pt.ist.sonet.exception.IndividualsCantAcceptDonationsException;
import pt.ist.sonet.exception.LargaCaixaTransferException;
import pt.ist.sonet.exception.OnVoteLimitException;
import pt.ist.sonet.exception.OrgsCantSendFriendRequestException;
import pt.ist.sonet.exception.PagAmigoTransferException;
import pt.ist.sonet.exception.PublicationIdDoesNotExistsException;
import pt.ist.sonet.exception.TargetAlreadySentRequestException;
import pt.ist.sonet.exception.TargetIsAlreadyFriendException;
import pt.ist.sonet.exception.YouAlreadySentRequestException;
import pt.ist.sonet.exception.YouArentAFriendException;
import pt.ist.sonet.presentation.client.SoNetServlet;
import pt.ist.sonet.service.AddCommentService;
import pt.ist.sonet.service.AddNoteService;
import pt.ist.sonet.service.AgentLoginService;
import pt.ist.sonet.service.AllAgentsService;
import pt.ist.sonet.service.ConfirmFriendRequestService;
import pt.ist.sonet.service.DonateService;
import pt.ist.sonet.service.FriendRequestService;
import pt.ist.sonet.service.FriendsOfAgentService;
import pt.ist.sonet.service.GetAgentPublicationsService;
import pt.ist.sonet.service.GetAllNonFriendsService;
import pt.ist.sonet.service.GetAllOrganizationalAgentsService;
import pt.ist.sonet.service.GetFriendsLastPublicationService;
import pt.ist.sonet.service.GetPublicationByIdService;
import pt.ist.sonet.service.GetPublicationCommentsService;
import pt.ist.sonet.service.ListAllService;
import pt.ist.sonet.service.NegativeVoteService;
import pt.ist.sonet.service.ObtainLargaCaixaContentService;
import pt.ist.sonet.service.PositiveVoteService;
import pt.ist.sonet.service.RejectFriendRequestService;
import pt.ist.sonet.service.SentFriendRequestsService;
import pt.ist.sonet.service.bridge.LargaCaixaServerBridge;
import pt.ist.sonet.service.bridge.LargaCaixaServerLocal;
import pt.ist.sonet.service.bridge.PagAmigoServerBridge;
import pt.ist.sonet.service.bridge.PagAmigoServerLocal;
import pt.ist.sonet.service.dto.CommentDto;
import pt.ist.sonet.service.dto.ListingDto;
import pt.ist.sonet.service.dto.PaymentDto;
import pt.ist.sonet.service.dto.PublicationDto;
import pt.ist.sonet.service.dto.PublicationListDto;
import pt.ist.sonet.service.dto.PublicationViewDto;
import pt.ist.sonet.service.dto.StringListDto;
import pt.ist.sonet.service.dto.BooleanDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SoNetServletImpl extends RemoteServiceServlet implements SoNetServlet {
	static {
		if(FenixFramework.getConfig()==null) {
			FenixFramework.initialize(new Config() {{
				domainModelPath="src/main/dml/sonet.dml";
				dbAlias = "//localhost:3306/sonetdb"; 
				dbUsername = "sonet";
				dbPassword = "s0n3t";
				//descomentar a seguinte linha para correr no eclipse sem ant
				//domainModelPath="dml/sonet.dml";
				rootClass=SoNet.class;
			}});
		}
	}
	
	private static PagAmigoServerBridge paga; 
	private static LargaCaixaServerBridge larga;
	
	/**
	 * @return PagAmigoServerBridge
	 */
	public PagAmigoServerBridge getPagaBridge(){
		return paga;
	}
	
	/**
	 * @return LargaCaixaServerBridge
	 */
	public LargaCaixaServerBridge getLargaBridge(){
		return larga;
	}
	
	public void init(String serverType) {
		if(serverType.equals("LOCAL")){
			paga = new PagAmigoServerLocal();
			larga = new LargaCaixaServerLocal();
			return;
		}
		
		//Adicionar outros casos possiveis
				
		//caso nao exista uma correspondencia assume caso LOCAL
		paga = new PagAmigoServerLocal();
		larga = new LargaCaixaServerLocal();
	}
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	/**
	 * Metodo que chama o servico utilizado para apresentar todos os agentes existentes na SoNet
	 * e todas as suas publicacoes
	 * 
	 * @return ListingDto
	 */
	public ListingDto displaySonet() {
		
		ListingDto list = new ListingDto();
		ListAllService service = new ListAllService(list);
		
		service.execute();
		
		return list;
		
	}
	
	/**
	 * Metodo que chama o servico que permite ao utilizador fazer login na SoNet
	 * 
	 * @param String username
	 * @param String password
	 * @return Boolean
	 * @throws AgentUsernameDoesNotExistsException
	 */
	public Boolean agentLogin(String username, String password) throws AgentUsernameDoesNotExistsException{
		BooleanDto dto = new BooleanDto();
		AgentLoginService service = new AgentLoginService(username, password, dto);

		service.execute();

		return dto.getValue();
	}
	
	/**
	 * Metodo que chama o servico que permite ao utilizador publicar uma nota
	 * 
	 * @param String username
	 * @param String label
	 * @param String text
	 */
	public void addNote(String username, String label, String text){
		AddNoteService service = new AddNoteService(username, label, text);
		service.execute();
	}
	
	/**
	 * Metodo que chama o servico que permite ao utilizador comentar uma publicacao
	 * 
	 * @param String username
	 * @param int pubId
	 * @param String text
	 */
	public void commentPublication(String username, int pubId, String text) throws OnVoteLimitException{
		CommentDto dto = new CommentDto(username, pubId, text);
		AddCommentService service = new AddCommentService(dto);
		service.execute();
	}
	
	/**
	 * Metodo que chama o servico utilizado para listar todos os amigos do agente associado a username
	 * 
	 * @param String username
	 * @return StringListDto
	 */
	public StringListDto getFriends(String username){
		StringListDto dto = new StringListDto();
		FriendsOfAgentService service = new FriendsOfAgentService(username, dto);
		service.execute();
		return dto;
	}
	
	/**
	 * Metodo que chama o servico utilizado para listar todos os pedidos pendentes do agente associado ao username
	 * 
	 * @param String username
	 * @return StringListDto
	 */
	public StringListDto listPendingRequests(String username){
		StringListDto dto = new StringListDto();
		SentFriendRequestsService service = new SentFriendRequestsService(username, dto);
		service.execute();
		return dto;
	}
	
	/**
	 * Metodo que chama o servico utilizado para fazer um pagamento (sob a forma de doacao)
	 * 
	 * @param String from
	 * @param String to
	 * @param int amount
	 * @param String description
	 * @throws PagAmigoTransferException
	 */
	public void makeDonation(String from, String to, int amount, String description) throws PagAmigoTransferException, AgentUsernameDoesNotExistsException, IndividualsCantAcceptDonationsException{
		new DonateService(paga, new PaymentDto(from, to, description, amount)).execute();		
	}

	/**
	 * Metodo que chama o servico utilizado para listar todos os agentes registados na SoNet
	 * 
	 * @return StringListDto
	 */
	@Override
	public StringListDto getAgents() {
		StringListDto dto = new StringListDto();
		AllAgentsService service = new AllAgentsService(dto);
		service.execute();
		return dto;
	}

	/**
	 * Metodo que chama o servico utilizado para listar todas as publicacoes de um dado agente. Recebe como
	 * parametro o username do agente que quer ver as publicacoes para se saber se tem permissao para o fazer
	 * 
	 * @param String from
	 * @param String asking
	 * @return ArrayList<PublicationDto>
	 * @throws YouArentAFriendException
	 */
	@Override
	public ArrayList<PublicationDto> getPublicationList(String from, String asking) throws YouArentAFriendException{
		PublicationListDto pubdto = new PublicationListDto();
		GetAgentPublicationsService service = new GetAgentPublicationsService(from, asking, pubdto);
		service.execute();
		return pubdto.getlisting();
	}
	
	/**
	 * Metodo que chama o servico utilizado para listar todos os agentes organizacionais registados na SoNet
	 * 
	 * @return StringListDto
	 */
	public StringListDto getOrganizationalAgents() {
		StringListDto dto = new StringListDto();
		GetAllOrganizationalAgentsService service = new GetAllOrganizationalAgentsService(dto);
		service.execute();
		return dto;
	}
	
	/**
	 * Metodo que chama o servico utilizado para listar os comentarios da publicacao com o identificador pubId
	 * 
	 * @param int pubId
	 * @return StringListDto
	 * @throws PublicationIdDoesNotExistsException
	 */
	public StringListDto getPublicationComments(int pubId) throws PublicationIdDoesNotExistsException{
		StringListDto dto = new StringListDto();
		GetPublicationCommentsService service = new GetPublicationCommentsService(pubId, dto);
		service.execute();
		return dto;
	}
	
	/**
	 * Metodo que chama o servico utilizado para votar positivamente numa publicacao
	 * 
	 * @param String user
	 * @param int pubId
	 * @throws AlreadyVotedException
	 * @throws OnVoteLimitException
	 * @throws AgentsCantVoteInTheirOwnPublicationsException
	 */
	public void positiveVote(String user, int pubId) throws AlreadyVotedException, OnVoteLimitException, AgentsCantVoteInTheirOwnPublicationsException{
		PositiveVoteService service = new PositiveVoteService(user, pubId);
		service.execute();
	}
	
	/**
	 * Metodo que chama o servico utilizado para votar negativamente numa publicacao
	 * 
	 * @param String user
	 * @param int pubId
	 * @throws AlreadyVotedException
	 * @throws OnVoteLimitException
	 * @throws AgentsCantVoteInTheirOwnPublicationsException
	 */
	public void negativeVote(String user, int pubId) throws AlreadyVotedException, OnVoteLimitException, AgentsCantVoteInTheirOwnPublicationsException{
		NegativeVoteService service = new NegativeVoteService(user, pubId);
		service.execute();
	}

	/**
	 * Metodo que chama o servico que permite ao utilizador ver publicacao pubId
	 * 
	 * @param String asking
	 * @param int pubId
	 * @throws YouArentAFriendException
	 * @throws PublicationIdDoesNotExistsException
	 * @return PublicationViewDto 
	 */
	public PublicationViewDto viewPublication(String asking, int pubId)
			throws YouArentAFriendException, PublicationIdDoesNotExistsException {
		GetPublicationByIdService service = new GetPublicationByIdService(asking, pubId);
		service.execute();
		return service.getPublication();
	}

	/**
	 * Metodo que chama o servico que permite ao utilizador obter um determinado conteudo atraves 
	 * do servico LargaCaixa
	 * 
	 * @param String user
	 * @param int pubId
	 * @throws PagAmigoTransferException
	 * @throws LargaCaixaTransferException
	 */
	@Override
	public void getContent(String user, int pubId)
			throws PagAmigoTransferException, LargaCaixaTransferException {
		
		ObtainLargaCaixaContentService service = new ObtainLargaCaixaContentService(paga, larga, user, pubId);
		service.execute();
		
	}

	/**
	 * Obtem todos os agentes da rede que ainda nao sao amigos do agente activo
	 * 
	 * @param String Username do agente activo
	 * @return StringListDto
	 */
	public StringListDto getFriendRequestAgents(String user) throws OrgsCantSendFriendRequestException, AgentUsernameDoesNotExistsException {
		StringListDto dto = new StringListDto();
		GetAllNonFriendsService service = new GetAllNonFriendsService(user, dto);
		service.execute();
		return dto;
	}
	
	/**
	 * Envia um pedido de amizade
	 * 
	 * @param String Username do agente activo
	 * @param String Username do outro agente
	 * 
	 */
	public void sendRequest(String from, String to) throws AgentUsernameDoesNotExistsException, TargetAlreadySentRequestException, TargetIsAlreadyFriendException, YouAlreadySentRequestException, FriendLimitExceededException, FriendAlreadyExistsException{
		FriendRequestService service = new FriendRequestService(from, to);
		service.execute();
	}
	
	/**
	 * Aceita um pedido de amizade
	 * 
	 * @param String Username do agente activo
	 * @param String Username do outro agente
	 * 
	 */
	public void acceptRequest(String from, String to) throws AgentUsernameDoesNotExistsException, FriendLimitExceededException {
		ConfirmFriendRequestService service = new ConfirmFriendRequestService(from, to);
		service.execute();
	}
	
	/**
	 * Recusa um pedido de amizade
	 * 
	 * @param String Username do agente activo
	 * @param String Username do outro agente
	 * 
	 */
	public void rejectRequest(String from, String to) throws AgentUsernameDoesNotExistsException {
		RejectFriendRequestService service = new RejectFriendRequestService(from, to);
		service.execute();
	}
	
	/**
	 * Devolve uma lista com a ultima publicacao de cada amigo do agente
	 * 
	 * @param String Username do agente activo
	 * @param String Username do outro agente
	 * 
	 */
	public StringListDto loadLatestPublications(String user){
		StringListDto dto = new StringListDto();
		GetFriendsLastPublicationService service = new GetFriendsLastPublicationService(user, dto);
		service.execute();
		return dto;
	}

}
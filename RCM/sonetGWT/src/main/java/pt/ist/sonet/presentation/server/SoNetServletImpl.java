package pt.ist.sonet.presentation.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.AlreadyVotedException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.IpOutOfMeshException;
import pt.ist.sonet.exception.PIIdDoesNotExistsException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.presentation.client.SoNetServlet;
import pt.ist.sonet.service.AgentLoginService;
import pt.ist.sonet.service.AgentsByApService;
import pt.ist.sonet.service.ChangeAgentPasswordService;
import pt.ist.sonet.service.GetAgentByUsernameService;
import pt.ist.sonet.service.GetAllApService;
import pt.ist.sonet.service.GetAllOtherAgentsService;
import pt.ist.sonet.service.GetAllPIFromAPService;
import pt.ist.sonet.service.GetApByIdService;
import pt.ist.sonet.service.GetApCommentsService;
import pt.ist.sonet.service.GetConversationService;
import pt.ist.sonet.service.GetPIByIdService;
import pt.ist.sonet.service.ListAllService;
import pt.ist.sonet.service.NegativeVoteService;
import pt.ist.sonet.service.PositiveVoteService;
import pt.ist.sonet.service.RegisterAgentService;
import pt.ist.sonet.service.RegisterPIService;
import pt.ist.sonet.service.SendPrivateMessageService;
import pt.ist.sonet.service.UpdateAgentInfoService;
import pt.ist.sonet.service.UpdateAgentIpService;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.ApListDto;
import pt.ist.sonet.service.dto.BooleanDto;
import pt.ist.sonet.service.dto.ListingDto;
import pt.ist.sonet.service.dto.MessageDto;
import pt.ist.sonet.service.dto.PIDto;
import pt.ist.sonet.service.dto.PIListDto;
import pt.ist.sonet.service.dto.StringListDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SoNetServletImpl extends RemoteServiceServlet implements SoNetServlet {
	static {
		try{
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	public String init(String serverType) {
		try{
			if(FenixFramework.getConfig()==null)
				return "Sem config";
			
			return "Ligado!";
		}catch(Exception e){
			e.printStackTrace();
			return "Exception";
		}
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
	
//	/**
//	 * Metodo que chama o servico que permite ao utilizador publicar uma nota
//	 * 
//	 * @param String username
//	 * @param String label
//	 * @param String text
//	 */
//	public void addNote(String username, String label, String text){
//		AddNoteService service = new AddNoteService(username, label, text);
//		service.execute();
//	}
//	
//	/**
//	 * Metodo que chama o servico que permite ao utilizador comentar uma publicacao
//	 * 
//	 * @param String username
//	 * @param int pubId
//	 * @param String text
//	 */
//	public void commentPublication(String username, int pubId, String text) throws OnVoteLimitException{
//		CommentDto dto = new CommentDto(username, pubId, text);
//		AddCommentService service = new AddCommentService(dto);
//		service.execute();
//	}
//	
//	/**
//	 * Metodo que chama o servico utilizado para listar todos os amigos do agente associado a username
//	 * 
//	 * @param String username
//	 * @return StringListDto
//	 */
//	public StringListDto getFriends(String username){
//		StringListDto dto = new StringListDto();
//		FriendsOfAgentService service = new FriendsOfAgentService(username, dto);
//		service.execute();
//		return dto;
//	}
//	
//	/**
//	 * Metodo que chama o servico utilizado para listar todos os pedidos pendentes do agente associado ao username
//	 * 
//	 * @param String username
//	 * @return StringListDto
//	 */
//	public StringListDto listPendingRequests(String username){
//		StringListDto dto = new StringListDto();
//		SentFriendRequestsService service = new SentFriendRequestsService(username, dto);
//		service.execute();
//		return dto;
//	}
//	
//	/**
//	 * Metodo que chama o servico utilizado para fazer um pagamento (sob a forma de doacao)
//	 * 
//	 * @param String from
//	 * @param String to
//	 * @param int amount
//	 * @param String description
//	 * @throws PagAmigoTransferException
//	 */
//	public void makeDonation(String from, String to, int amount, String description) throws PagAmigoTransferException, AgentUsernameDoesNotExistsException, IndividualsCantAcceptDonationsException{
//		new DonateService(paga, new PaymentDto(from, to, description, amount)).execute();		
//	}

	/**
	 * Metodo que chama o servico utilizado para listar todos os agentes registados na SoNet
	 * 
	 * @return StringListDto
	 */
	@Override
	public StringListDto getAgents(int ap) throws ApIdDoesNotExistsException{
		StringListDto dto = new StringListDto();
		AgentsByApService service = new AgentsByApService(ap, dto);
		service.execute();
		return dto;
	}
	
	@Override
	public StringListDto getAllOtherAgents(String user) throws AgentUsernameDoesNotExistsException {
		StringListDto dto = new StringListDto();
		GetAllOtherAgentsService service = new GetAllOtherAgentsService(dto, user);
		service.execute();
		return dto;
	}
	
	public AgentDto getAgent(String user) throws AgentUsernameDoesNotExistsException {
		GetAgentByUsernameService service = new GetAgentByUsernameService(user);
		service.execute();
		return service.getDto();
	}
	
	@Override
	//tirar o id do input
	public void sendPrivateMessage(String user, String otherGuy, String text) throws AgentUsernameDoesNotExistsException {
		MessageDto message = new MessageDto(user, otherGuy, text);
		SendPrivateMessageService service = new SendPrivateMessageService(message);
		service.execute();
	}
	
	/**
	 * Metodo que chama o servico utilizado para listar os comentarios da publicacao com o identificador pubId
	 * 
	 * @param int pubId
	 * @return StringListDto
	 * @throws ApIdDoesNotExistsException
	 */
	public StringListDto getPublicationComments(int pubId) throws ApIdDoesNotExistsException{
		StringListDto dto = new StringListDto();
		GetApCommentsService service = new GetApCommentsService(pubId, dto);
		service.execute();
		return dto;
	}
	
	@Override
	public StringListDto getConversation(String user, String otherGuy) throws AgentUsernameDoesNotExistsException {
		StringListDto dto = new StringListDto();
		GetConversationService service = new GetConversationService(user, otherGuy, dto);
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
	public void positiveVote(String user, int pubId) throws AlreadyVotedException{
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
	public void negativeVote(String user, int pubId) throws AlreadyVotedException{
		NegativeVoteService service = new NegativeVoteService(user, pubId);
		service.execute();
	}

	/**
	 * Metodo que chama o servico que permite ao utilizador ver publicacao pubId
	 * 
	 * @param String asking
	 * @param int pubId
	 * @throws YouArentAFriendException
	 * @throws ApIdDoesNotExistsException
	 * @return PublicationViewDto 
	 */
	public ApDto viewAp(int ApId) throws ApIdDoesNotExistsException {
		GetApByIdService service = new GetApByIdService(ApId);
		service.execute();
		return service.getPublication();
	}

	@Override
	public void addNote(String username, String label, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commentAp(String username, int apId, String text)
			throws AgentUsernameDoesNotExistsException,
			ApIdDoesNotExistsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StringListDto getApComments(int apId)
			throws ApIdDoesNotExistsException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ApListDto getApList() {
		GetAllApService service = new GetAllApService();
		service.execute();
		return service.getListing();
	}
	
	public void updateAgentProfile(AgentDto dto) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException{
	
		new UpdateAgentInfoService(dto).execute();
	}
	
	public int updateAgentIP(AgentDto dto) throws AgentUsernameDoesNotExistsException, IpOutOfMeshException{
		
		new UpdateAgentIpService(dto).execute();
		GetAgentByUsernameService service = new GetAgentByUsernameService(dto.getUsername());
		service.execute();
		AgentDto agent = service.getDto();
		return agent.getAp();
		
	}
	
	public void changeAgentPassword(AgentDto dto) throws AgentUsernameDoesNotExistsException, ApIdDoesNotExistsException{
		
		new ChangeAgentPasswordService(dto).execute();
	}
	
	public String getAgentIP(){
		String ip = this.getThreadLocalRequest().getRemoteAddr();
		
		return ip;
	}
	
public Integer loadRSSIMacOS(){
		int rssi = 1;
		Process p;
		try {
			p = Runtime.getRuntime().exec("/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport -I | grep agrCtlRSSI");
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
	
			String line = reader.readLine();

			String[] res = line.split("\\s+");
			Integer value = new Integer(res[2]);
			rssi = value.intValue();
			}
	
		catch (Exception e) {}
		
		return rssi;
		
	}

@Override
public void registerAgentProfile(AgentDto dto) throws UsernameAlreadyExistsException{
	
	new RegisterAgentService(dto).execute();
	
}

@Override
public PIListDto getPIsByAp(int apId) throws ApIdDoesNotExistsException{
	
	GetAllPIFromAPService service = new GetAllPIFromAPService(apId);
	service.execute();
	return service.getListing();
	
}

@Override
public PIDto getPIById(int id) throws PIIdDoesNotExistsException{
	
	GetPIByIdService service = new GetPIByIdService(id);
	service.execute();
	return service.getPI();
	
}

public void createPI(int ap, String name, String location, String description) throws ApIdDoesNotExistsException{
	RegisterPIService service = new RegisterPIService(new PIDto(-1, name, location, description), ap);
	service.execute();
}

}

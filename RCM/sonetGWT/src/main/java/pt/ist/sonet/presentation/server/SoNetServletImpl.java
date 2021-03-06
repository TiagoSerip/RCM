package pt.ist.sonet.presentation.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.AlreadyVotedException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.BoardDoesNotExistsException;
import pt.ist.sonet.exception.BoardIdDoesNotExistsException;
import pt.ist.sonet.exception.IpOutOfMeshException;
import pt.ist.sonet.exception.PIIdDoesNotExistsException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.presentation.client.SoNetServlet;
import pt.ist.sonet.service.AddCommentService;
import pt.ist.sonet.service.AgentLoginService;
import pt.ist.sonet.service.AgentsByApService;
import pt.ist.sonet.service.ChangeAgentPasswordService;
import pt.ist.sonet.service.CheckHasBoardService;
import pt.ist.sonet.service.CheckWinnerService;
import pt.ist.sonet.service.CreateBoardService;
import pt.ist.sonet.service.GetAgentByUsernameService;
import pt.ist.sonet.service.GetAllApService;
import pt.ist.sonet.service.GetAllOtherAgentsService;
import pt.ist.sonet.service.GetAllPIFromAPService;
import pt.ist.sonet.service.GetApByIdService;
import pt.ist.sonet.service.GetApCommentsService;
import pt.ist.sonet.service.GetBoardByUsersService;
import pt.ist.sonet.service.GetConversationService;
import pt.ist.sonet.service.GetPIByIdService;
import pt.ist.sonet.service.GetTurnService;
import pt.ist.sonet.service.GetUpdatedBoardService;
import pt.ist.sonet.service.GetWinnerService;
import pt.ist.sonet.service.IsBoardFullService;
import pt.ist.sonet.service.ListAllService;
import pt.ist.sonet.service.NegativeVoteService;
import pt.ist.sonet.service.PlayService;
import pt.ist.sonet.service.PositiveVoteService;
import pt.ist.sonet.service.RegisterAgentService;
import pt.ist.sonet.service.RegisterPIService;
import pt.ist.sonet.service.RemoveBoardService;
import pt.ist.sonet.service.SendPrivateMessageService;
import pt.ist.sonet.service.UpdateAgentInfoService;
import pt.ist.sonet.service.UpdateAgentIpService;
import pt.ist.sonet.service.VoteNegativePIService;
import pt.ist.sonet.service.VotePositivePIService;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.ApListDto;
import pt.ist.sonet.service.dto.BoardDto;
import pt.ist.sonet.service.dto.BooleanDto;
import pt.ist.sonet.service.dto.CommentDto;
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
	
	public ListingDto displaySonet() {
		
		ListingDto list = new ListingDto();
		ListAllService service = new ListAllService(list);
		
		service.execute();
		
		return list;
		
	}
	
	public Boolean agentLogin(String username, String password) throws AgentUsernameDoesNotExistsException{
		BooleanDto dto = new BooleanDto();
		AgentLoginService service = new AgentLoginService(username, password, dto);

		service.execute();

		return dto.getValue();
	}

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

	public StringListDto getAPComments(int apId) throws ApIdDoesNotExistsException{
		StringListDto dto = new StringListDto();
		GetApCommentsService service = new GetApCommentsService(apId, dto);
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
	
	@Override
	public String getTurn(int boardId) throws AgentUsernameDoesNotExistsException, BoardIdDoesNotExistsException {
		GetTurnService service = new GetTurnService(boardId);
		service.execute();
		return service.getTurn().getUsername();
	}
	
	@Override
	public void play(int boardId, String playerUser, int[] jogada) throws AgentUsernameDoesNotExistsException, BoardIdDoesNotExistsException {
		PlayService service = new PlayService(boardId, playerUser, jogada);
		service.execute();
	}
	
	@Override
	public StringListDto updateBoard(int boardId) throws BoardIdDoesNotExistsException {
		GetUpdatedBoardService service = new GetUpdatedBoardService(boardId);
		service.execute();
		//return service.getUpdatedBoard();
		return service.getBoard();

	}
	
	@Override
	public BoardDto getBoard(String player1User, String player2User) throws AgentUsernameDoesNotExistsException, BoardDoesNotExistsException {
		BoardDto dto = new BoardDto();
		GetBoardByUsersService service = new GetBoardByUsersService(player1User, player2User, dto);
		service.execute();

		return dto;
	}
	
	@Override
	public Integer createBoard(String host, String guest) throws AgentUsernameDoesNotExistsException {
		CreateBoardService service = new CreateBoardService(host, guest);
		service.execute();
		int boardId = service.getNewBoardId();
		Integer id = new Integer(boardId);
		return id;
	}
	
	@Override
	public Boolean checkWinner(int boardId) throws AgentUsernameDoesNotExistsException {
		BooleanDto dto = new BooleanDto();
		CheckWinnerService service = new CheckWinnerService(boardId, dto);
		service.execute();
		return dto.getValue();
	}
	
	@Override
	public BooleanDto checkHasBoard(String player1User, String player2User) throws AgentUsernameDoesNotExistsException {
		BooleanDto dto = new BooleanDto();
		CheckHasBoardService service = new CheckHasBoardService(player1User, player2User, dto);
		service.execute();
		return dto;
	}
	
	@Override
	public boolean boardIsFull(int boardId) throws BoardIdDoesNotExistsException {
		BooleanDto dto = new BooleanDto();
		IsBoardFullService service = new IsBoardFullService(boardId, dto);
		service.execute();
		return dto.getValue();
	}
	
	@Override
	public String getWinner(int boardId) throws AgentUsernameDoesNotExistsException, BoardIdDoesNotExistsException {
		GetWinnerService service = new GetWinnerService(boardId);
		service.execute();
		String winner = service.getWinner().getUsername();
		return winner;
	}
	
	@Override
	public void removeBoard(int boardId) throws BoardIdDoesNotExistsException {
//		RemoveBoardService service = new RemoveBoardService(boardId);
//		service.execute();
	}
	
	public void positiveVote(String user, int apId) throws AlreadyVotedException{
		PositiveVoteService service = new PositiveVoteService(user, apId);
		service.execute();
	}

	public void negativeVote(String user, int apId) throws AlreadyVotedException{
		NegativeVoteService service = new NegativeVoteService(user, apId);
		service.execute();
	}

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
		
		new AddCommentService(new CommentDto(username, apId, text)).execute();
		
	}

	@Override
	public StringListDto getApComments(int apId)
			throws ApIdDoesNotExistsException {
		StringListDto dto = new StringListDto();
		GetApCommentsService service = new GetApCommentsService(apId, dto);
		service.execute();
		return dto;
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

public void createPI(int ap, String name, String location, String description, String link) throws ApIdDoesNotExistsException{
	RegisterPIService service = new RegisterPIService(new PIDto(-1, name, location, description, link, 0, 0), ap);
	service.execute();
}


public void positiveVotePI(String user, int piId) throws AlreadyVotedException{
	VotePositivePIService service = new VotePositivePIService(user, piId);
	service.execute();
}

public void negativeVotePI(String user, int piId) throws AlreadyVotedException{
	VoteNegativePIService service = new VoteNegativePIService(user, piId);
	service.execute();
}

}

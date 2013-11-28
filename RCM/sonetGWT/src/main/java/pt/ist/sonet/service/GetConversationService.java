package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.AP;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Comment;
import pt.ist.sonet.domain.Message;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.PIListDto;
import pt.ist.sonet.service.dto.StringListDto;

public class GetConversationService extends SonetService{

	private String reqUser;
	private String otherGuyUser;
	private StringListDto conversationDto;
	
	/**
	 * Construtor
	 * 
	 * @param String agentreq - username do agente que quer ver as publicacoes
	 * @param String agentsend - username do agente detentor das publicacoes
	 * @param ListingDto
	 */
	public GetConversationService(String req, String otherGuy, StringListDto dto){
		this.conversationDto = dto;
		this.reqUser = req;
		this.otherGuyUser = otherGuy;
	}
	
	/**
	 * 
	 * Faz o envio (dispatch) do servico 
	 * 
	 * @throws SoNetException
	 */
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {
		SoNet sonet = FenixFramework.getRoot();	
		Agent requester = sonet.getAgentByUsername(reqUser);
		if(requester == null)
			throw new AgentUsernameDoesNotExistsException(reqUser);
		Agent otherGuy = sonet.getAgentByUsername(otherGuyUser);
		if(otherGuy == null)
			throw new AgentUsernameDoesNotExistsException(otherGuyUser);
		for(Message m : sonet.getLastConversationWithSomeone(requester, otherGuy)){
			conversationDto.addTolisting(m.toString());
		}
	}
	
	public StringListDto getConversation(){
		return conversationDto;
	}
}

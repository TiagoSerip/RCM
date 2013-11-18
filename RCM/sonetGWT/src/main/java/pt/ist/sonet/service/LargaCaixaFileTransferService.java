package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.LargaCaixaTransferException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.bridge.LargaCaixaServerBridge;
import pt.ist.sonet.service.dto.FileTransferDto;


/**
 * Implementa o servico de processamento de pagamentos entre dois agentes da SoNet.
 * 
 * @author ES Grupo 8
 *
 */
public class LargaCaixaFileTransferService extends SonetService{

	private FileTransferDto dto;
	private LargaCaixaServerBridge larga;
	
	/**
	 * Construtor
	 * 
	 * @param FileTransferDto dto com os dados da transferencia
	 */
	public LargaCaixaFileTransferService(LargaCaixaServerBridge larga, FileTransferDto dto) {
		this.dto = dto;	
		this.larga = larga;
	}

	/**
	 * Faz o dispatch do servico
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException 
	 * @throws LargaCaixaTransferException
	 */
	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, LargaCaixaTransferException {
		
		SoNet network = FenixFramework.getRoot();
		
		if(!network.hasAgentByUsername(dto.getFrom()))
			throw new AgentUsernameDoesNotExistsException(dto.getFrom());
		if(!network.hasAgentByUsername(dto.getTo()))
			throw new AgentUsernameDoesNotExistsException(dto.getTo());

		larga.transferContent(dto.getUrl(), dto.getFrom(), dto.getTo(), dto.getProof());
	}
	
}
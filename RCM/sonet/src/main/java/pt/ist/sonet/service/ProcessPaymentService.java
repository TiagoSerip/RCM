package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.PagAmigoTransferException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.bridge.PagAmigoServerBridge;
import pt.ist.sonet.service.dto.PaymentDto;

/**
 * Implementa o servico de processamento de pagamentos entre dois agentes da SoNet.
 * 
 * @author ES Grupo 8
 *
 */
public class ProcessPaymentService extends SonetService {
	
	private PaymentDto dto;
	private PagAmigoServerBridge paga;
	
	/**
	 * Construtor
	 * 
	 * @param String from
	 * @param String to
	 * @param Integer amount
	 * @param String description
	 */
	public ProcessPaymentService(PagAmigoServerBridge paga, PaymentDto payment) {
		this.dto = payment;	
		this.paga=paga;
	}

	/**
	 * Faz o dispatch do servico
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException 
	 * @throws PagAmigoTransferException
	 */
	@Override
	public void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException, PagAmigoTransferException {
		
		SoNet network = FenixFramework.getRoot();
		
		if(!network.hasAgentByUsername(dto.getFrom()))
			throw new AgentUsernameDoesNotExistsException(dto.getFrom());
		if(!network.hasAgentByUsername(dto.getTo()))
			throw new AgentUsernameDoesNotExistsException(dto.getTo());
		
		paga.processPayment(dto.getFrom(), dto.getTo(), dto.getAmount(), dto.getDescription());

	}

}
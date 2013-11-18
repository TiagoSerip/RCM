package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;

import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.PagAmigoTransferException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.bridge.PagAmigoServerBridge;
import pt.ist.sonet.service.dto.PaymentDto;

/**
 * Implementa o servico de ver/comprar conteudos da SoNet.
 * 
 * @author ES Grupo 8
 * 
 */
public class DonateService extends SonetService {

	private PaymentDto dto;
	private PagAmigoServerBridge paga;

	/**
	 * Construtor
	 * 
	 * @param String
	 *            from
	 * @param String
	 *            to
	 * @param Integer
	 *            amount
	 * @param String
	 *            description
	 */
	public DonateService(PagAmigoServerBridge paga, PaymentDto dto) {
		this.dto = dto;
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

		if (!network.hasAgentByUsername(dto.getFrom()))
			throw new AgentUsernameDoesNotExistsException(dto.getFrom());
		if (!network.hasAgentByUsername(dto.getTo()))
			throw new AgentUsernameDoesNotExistsException(dto.getTo());
		
		if(!network.getAgentByUsername(dto.getTo()).doIAcceptDonations())
			throw new AgentUsernameDoesNotExistsException(dto.getTo());
		
		ProcessPaymentService payment = new ProcessPaymentService(paga, dto);
		payment.dispatch();

	}
}
package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;

import pt.ist.sonet.domain.Content;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.LargaCaixaTransferException;
import pt.ist.sonet.exception.PagAmigoTransferException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.bridge.LargaCaixaServerBridge;
import pt.ist.sonet.service.bridge.PagAmigoServerBridge;
import pt.ist.sonet.service.dto.FileTransferDto;
import pt.ist.sonet.service.dto.PaymentDto;

/**
 * Implementa o servico de ver/comprar conteudos da SoNet.
 * 
 * @author ES Grupo 8
 * 
 */
public class ObtainLargaCaixaContentService extends SonetService {

	private String fromUser;
	private String toUser;
	private int pubId;
	private int price;
	private LargaCaixaServerBridge larga;
	private PagAmigoServerBridge paga;

	/**
	 * Construtor
	 * 
	 * @param PagAmigoServerBridge paga - bridge para o serviço externo
	 * @param LargaCaixaServerBridge larga - bridge para o serviço externo
	 * 
	 * @param String from
	 * @param String to
	 * @param Integer amount
	 * @param String description
	 */
	public ObtainLargaCaixaContentService(PagAmigoServerBridge paga, LargaCaixaServerBridge larga, String from, int pubID) {
		this.fromUser = from;
		this.pubId = pubID;
		this.larga=larga;
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
	public void dispatch() throws SoNetException,
			AgentUsernameDoesNotExistsException, PagAmigoTransferException {

		SoNet network = FenixFramework.getRoot();
		Content pub = (Content) network.getPublicationById(pubId);
		this.price = pub.getPrice();
		this.toUser = pub.getAgent().getUsername();

		if (!network.hasAgentByUsername(fromUser))
			throw new AgentUsernameDoesNotExistsException(fromUser);
		
		String url = pub.getURL();
		
		if(price>0){
			ProcessPaymentService payment = new ProcessPaymentService(paga, 
				new PaymentDto(fromUser, toUser, "Payment for content: "+pub.getLabel(), price));
			payment.dispatch();
		}
		
		LargaCaixaFileTransferService transfer = new LargaCaixaFileTransferService(larga,
				new FileTransferDto(url, fromUser, toUser, "Proof of Payment"));
		
		try{
			transfer.dispatch();
		} catch(LargaCaixaTransferException e){
			if(price>0){
				ProcessPaymentService payment = new ProcessPaymentService(paga, 
						new PaymentDto(toUser, fromUser, "Return for failed transfer of content: "+pub.getLabel(), price));
				payment.dispatch();
			}
			throw e;
		}
	}
}
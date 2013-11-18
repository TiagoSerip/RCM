/**
 * Servico que permite adicionar uma nota textual 'a SoNet.
 * Recebe no construtor o username do agente, a lablel e o texto.
 */
package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.Content;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;

/**
 * @author ES Grupo 8
 */
public class AddContentService extends SonetService {
	
	private String agentUser;
	private String label;
	private String url;
	private int price;
	
	/**
	 * Construtor
	 * 
	 * @param String username
	 * @param String text
	 * @param String label
	 */
	public AddContentService(String username, String label, String url, int price) {
		this.agentUser = username;
		this.label = label;
		this.url = url;
		this.price = price;
	}
	
	/**
	 * Faz o dispatch do servico
	 * 
	 * @throws SoNetException
	 * @throws AgentUsernameDoesNotExistsException 
	 */
	@Override
	protected void dispatch() throws SoNetException, AgentUsernameDoesNotExistsException {

		SoNet network = FenixFramework.getRoot();
		Agent creator = network.getAgentByUsername(agentUser);
		if(creator == null)
			throw new AgentUsernameDoesNotExistsException(agentUser);
		Content content = network.createContent(label, creator, url, price);
		network.addPublications(content);
		creator.addPublications(content);
	}
	
}

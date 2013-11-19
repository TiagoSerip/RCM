package pt.ist.sonet.presentationserver;

import jvstm.Atomic;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.service.ListAllService;
import pt.ist.sonet.service.dto.ListingDto;

/**
 * PresentationServer. Esta class implementa a rede social SoNet
 * via servicos e executa os casos de teste fornecidos no enunciado.
 * @author ES Grupo 8
 *
 */
public class PresentationServer {

	/**
	 * Executa a SoNet e efectua os testes pedidos no enunciado.
	 * @param args
	 */
	public static void main(String[] args) {
		FenixFramework.initialize(new Config() {{
			dbAlias = "//localhost:3306/sonetdb"; 
			dbUsername = "sonet";
			dbPassword = "s0n3t";
			domainModelPath="src/main/dml/sonet.dml";
			rootClass=SoNet.class;
		}});

		System.out.println("Initializing SoNet...");

		SoNet rede = startSoNet();
		listAgents(rede);
		System.out.println("----------");
		System.out.println("SoNet Terminated.");
//		extras(rede);

	}

	/**
	 * Inicializa a SoNet.
	 * 
	 * @return SoNet
	 */
	@Atomic
	public static SoNet startSoNet(){
		SoNet rede = FenixFramework.getRoot();
		return rede;
	}

	/**
	 * Lista todos os agentes da SoNet, suas publicacoes e comentarios.
	 * 
	 * @param rede
	 */
	public static void listAgents(SoNet rede){
		ListingDto dto = new ListingDto();
		new ListAllService(dto).execute();
		System.out.println(dto.getlisting());
	}


}



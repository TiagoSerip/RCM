/**
 * 
 */
package pt.ist.sonet;

import jvstm.Atomic;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.ApIdAlreadyExistsException;
import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.service.RegisterAgentService;
import pt.ist.sonet.service.RegisterApService;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.ApDto;

/**
 * Popula a SoNet
 * @author ES Grupo 8
 *
 */
public class SoNetSetup {

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
		createMeshAPs(rede);
		System.out.println("----------");
		createUserAP0(rede);
		System.out.println("SoNet Terminated.");
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


	public static void createMeshAPs(SoNet rede){
		
			int i;
			for(i=0; i<8; i++){
				try{
				ApDto ap = new ApDto(i, "192.168.10"+i+".000", 0,0 );
				new RegisterApService(ap).execute();
				}catch (ApIdAlreadyExistsException e){
					System.out.println("AP '"+e.getConflictingId()+"' already exists.");
					continue;
				}
				
				System.out.println("Created AP-"+i+".");
			}
	}

	
	public static void createUserAP0(SoNet rede){
		
			try{
				AgentDto agent = new AgentDto("ivo", "rcm5", "Ivo Pereira", 0, -34, "192.168.100.3");
				new RegisterAgentService(agent).execute();
			}catch (UsernameAlreadyExistsException e){
				System.out.println("User:'"+e.getConflictingName()+"' already exists.");
				return;
			}
			
			System.out.println("Created User: 'ivo'.");
		}

}

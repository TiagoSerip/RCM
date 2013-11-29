/**
 * 
 */
package pt.ist.sonet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.tools.javac.sym.CreateSymbols;

import jvstm.Atomic;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.*;
import pt.ist.sonet.service.AddCommentService;
import pt.ist.sonet.service.RegisterAgentService;
import pt.ist.sonet.service.RegisterApService;
import pt.ist.sonet.service.RegisterPIService;
import pt.ist.sonet.service.dto.AgentDto;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.CommentDto;
import pt.ist.sonet.service.dto.PIDto;

/**
 * Popula a SoNet
 * @author ES Grupo 8
 *
 */
public class SoNetSetup {

	static int rssi[]={-55, -55, -60, -50, -55, -60, -60, -65};
	
	
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
		createDevelopmentAPs(rede);
		System.out.println("----------");
		createUserAP0(rede);
		System.out.println("----------");
		createCommentAP0Uivo(rede);
		System.out.println("----------");
		createCommentAP8Uivo(rede);
		System.out.println("----------");
		createPIAP8(rede);
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
				createAP(rede, i, "192.168.10"+i+".", rssi[i], 0, 0);
				
			}
	}
	
	public static void createAP(SoNet rede, int id, String subnet, int rssi, int pos, int neg){
		
		try{
		ApDto ap = new ApDto(id, subnet, rssi, pos, neg);
		new RegisterApService(ap).execute();
		System.out.println("Created AP-"+id+".");

		}catch (ApIdAlreadyExistsException e){
			System.out.println("AP '"+e.getConflictingId()+"' already exists.");
		}
		

}

	public static void createDevelopmentAPs(SoNet rede){
		
		
		try{
		ApDto ap = new ApDto(8, "127.0.0.",-50 , 0, 0);
		new RegisterApService(ap).execute();
		System.out.println("Created AP-8.");
		}catch (ApIdAlreadyExistsException e){
			System.out.println("AP '"+e.getConflictingId()+"' already exists.");
		}
		
		
		
		try{
		ApDto ap = new ApDto(9, "192.168.1.",-50 ,0,0 );
		new RegisterApService(ap).execute();
		System.out.println("Created AP-9.");
		}catch (ApIdAlreadyExistsException e){
			System.out.println("AP '"+e.getConflictingId()+"' already exists.");
		}

	}

	
	public static void createUserAP0(SoNet rede){
		
			try{
				AgentDto agent = new AgentDto("ivo", "rcm5", "Ivo Pereira", 0, -34, "192.168.100.3");
				new RegisterAgentService(agent).execute();
			}catch (UsernameAlreadyExistsException e){
				System.out.println("User:'"+e.getConflictingUsername()+"' already exists.");
				return;
			}
			
			System.out.println("Created User: 'ivo'.");
	}
	
	public static void createCommentAP0Uivo(SoNet rede){
		
		try{
			CommentDto comment = new CommentDto("ivo", 0, "Teste RCM");
			new AddCommentService(comment).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("User:'"+e.getUsername()+"' doesn't exists.");
			return;
		}
		catch (ApIdDoesNotExistsException e){
			System.out.println("AP-'"+e.getid()+"' doesn't exists.");
			return;
		}
		
		System.out.println("Added Comment from 'ivo' to AP-0.");
	}
	
	public static void createCommentAP8Uivo(SoNet rede){
		
		try{
			CommentDto comment = new CommentDto("ivo", 8, "Teste RCM");
			new AddCommentService(comment).execute();
		}catch (AgentUsernameDoesNotExistsException e){
			System.out.println("User:'"+e.getUsername()+"' doesn't exists.");
			return;
		}
		catch (ApIdDoesNotExistsException e){
			System.out.println("AP-'"+e.getid()+"' doesn't exists.");
			return;
		}
		
		System.out.println("Added Comment from 'ivo' to AP-8.");
	}
	
	public static void createPIAP8(SoNet rede){
		
		try{
			PIDto dto = new PIDto(-1, "Limbo","Dentro do AP", "Teste RCM");
			new RegisterPIService(dto, 8).execute();
			System.out.println("Created PI on AP-8.");
		}
		catch (ApIdDoesNotExistsException e){
			System.out.println("AP-'"+e.getid()+"' doesn't exists.");
			return;
		}
	}

}

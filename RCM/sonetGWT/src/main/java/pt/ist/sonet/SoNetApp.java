package pt.ist.sonet;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.TimerTask;

import javax.swing.Timer;

import jvstm.Atomic;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;
import pt.ist.sonet.domain.Agent;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.service.AgentLoginService;
import pt.ist.sonet.service.dto.BooleanDto;

/**
 * SoNet Application. Esta class implementa a rede social
 * SoNet e executa os casos de teste fornecidos no enunciado.
 * @author ES Grupo 8
 *
 */
public class SoNetApp {

	
	static String username;
	static String password;
	static SoNet rede;
	
	/**
	 * Executa a SoNet e efectua os testes pedidos no enunciado.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting...");
		FenixFramework.initialize(new Config() {{
			dbAlias = "//192.168.103.1:3306/sonetdb"; 
			dbUsername = "sonet";
			dbPassword = "s0n3t";
			domainModelPath="src/main/dml/sonet.dml";
			rootClass=SoNet.class;
		}});

		System.out.println("Initializing SoNet Wifi Agent...");
		rede = startSoNet();
		readUsername();
		readPassword();
		try{
			if(!agentLogin(username, password)){
				System.out.println("Wrong Username or Password, you will be redirected to a page were you can Register a new user.");
				openWebApp();
				System.out.println("SoNet Terminated.");
				System.exit(-1);
			}
		}catch(AgentUsernameDoesNotExistsException e){
			System.out.println("Wrong Username or Password, you will be redirected to a page were you can Register a new user.");
			openWebApp();
			System.out.println("SoNet Terminated.");
			System.exit(-1);
		}
		
		System.out.println(System.getProperty("os.name"));
		if(System.getProperty("os.name").contains("Mac OS X")){
			int delay = 15*1000; //milliseconds
			  ActionListener taskPerformer = new ActionListener() {
			      public void actionPerformed(ActionEvent evt) {
			          macOSListner();
			      }
			  };
			  new Timer(delay, taskPerformer).start();
			// Launch your default web browser with ...
			  openWebApp();
			while(true){
			}
			
		}
		if(System.getProperty("os.name").contains("Linux")){
			int delay = 15*1000; //milliseconds
			  ActionListener taskPerformer = new ActionListener() {
			      public void actionPerformed(ActionEvent evt) {
			          linuxListner();
			      }
			  };
			  new Timer(delay, taskPerformer).start();
			// Launch your default web browser with ...
			  openWebApp();
			while(true){
			}
			
		}
		if(System.getProperty("os.name").contains("Windows")){
			int rssi = loadRSSIWindows();
			System.exit(1);
		}
		
		
			
		

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

	
	public static Integer loadRSSIMacOS(){
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
	
	public static Integer loadRSSILinux(){
		int rssi = 1;
		Process p;
		try {
			p = Runtime.getRuntime().exec("iwconfig wlan0");
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
	
			String line = reader.readLine();
			int i=1;
			while(i!=6){
				line = reader.readLine();
				i++;
			}
			String[] res = line.split("\\s+");
			String s = res[4];
			String parse = s.replaceAll("[^\\d]", "");
			Integer value = new Integer(parse);
			rssi = value.intValue()*-1;
			}
	
		catch (Exception e) {}
		
		return rssi;
		
	}
	
	public static Integer loadRSSIWindows(){
		int rssi = 1;
		Process p;
		try {
			
			System.out.println("Not yet available for windows.");
			return 0;
			
//			p = Runtime.getRuntime().exec("/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport -I | grep agrCtlRSSI");
//			p.waitFor();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					p.getInputStream()));
//	
//			String line = reader.readLine();
//
//			String[] res = line.split("\\s+");
//			Integer value = new Integer(res[2]);
//			rssi = value.intValue();
			}
	
		catch (Exception e) {}
		
		return rssi;
		
	}
	
	public static Boolean agentLogin(String username, String password) throws AgentUsernameDoesNotExistsException{
		BooleanDto dto = new BooleanDto();
		AgentLoginService service = new AgentLoginService(username, password, dto);

		service.execute();

		return dto.getValue();
	}
	
	public static void readUsername(){
		
	//  prompt the user to enter their name
	      System.out.println("Enter your username: ");

	      //  open up standard input
	      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	      username = null;

	      //  read the username from the command-line; need to use try/catch with the
	      //  readLine() method
	      try {
	         username = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your username, please try again.");
	         readUsername();
	      }

	}
	
	public static void readPassword(){
		
	//  prompt the user to enter their name
	      System.out.println("Enter your password: ");

	      //  open up standard input
	      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	      password = null;

	      //  read the username from the command-line; need to use try/catch with the
	      //  readLine() method
	      try {
	         password = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your password, please try again.");
	         readPassword();
	      }

	}
	
	public static void macOSListner(){
		int rssi = loadRSSIMacOS();
		if(rssi>=0){
			System.out.println("Failed to get RSSI.");
			return;
		}
		try{
			Transaction.begin();
			Agent a = rede.getAgentByUsername(username);
			a.setRssi(rssi);
			Transaction.commit();
			System.out.println("RSSI: "+rssi);
		}catch(Exception  e){
			System.out.println("Failed to push your RSSI to server. Please check your connection.");
		}
	}

	public static void linuxListner(){
		int rssi = loadRSSILinux();
		if(rssi>=0){
			System.out.println("Failed to get RSSI. ("+rssi+")");
			return;
		}
		try{
			Transaction.begin();
			Agent a = rede.getAgentByUsername(username);
			a.setRssi(rssi);
			Transaction.commit();
			System.out.println("RSSI: "+rssi);
		}catch(Exception  e){
			System.out.println("Failed to push your RSSI to server. Please check your connection.");
		}
	}
	
	public static void openWebApp(){
		 try {
			  URI url = new URI("http://192.168.1.105:8080/rcm");
				Desktop.getDesktop().browse(url);
			} catch (Exception e) {
				System.out.println("Failed to open the app web page.");
			}
	}

}



package pt.ist.sonet.presentation.client;


import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Menu extends DecoratorPanel {
		
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String LOGIN = "LOGIN";
	private static final String USER = "MY PROFILE";
	private static final String REGISTER = "REGISTER";
	private static final String LOGOUT = "LOGOUT";
	private static final String AP = "VIEW AP";
	private static final String LOGIN_ERROR = "Something went wrong while logging you in. Try again...";
	
	private String user = null; //active user
	private String ap = null; //user's AP
	private final VerticalPanel verticalPanel;
	private final Button signin;
	private final Button apView;
	
	public Menu(String user, String ap){
		
		this.user = user;
		this.ap = ap;
		this.verticalPanel = new VerticalPanel();
		
		MenuBar menu = new MenuBar(false);
		verticalPanel.add(menu);
		
		final MenuItem login = new MenuItem(LOGIN, new Command() {
		      @Override
		      public void execute() {
		    	
		      }
		   });

		menu.addItem(login);
		
		this.signin = new Button();
		this.apView = new Button(AP);
		
		verticalPanel.add(signin);
		verticalPanel.add(apView);
		
		
		if(user == null){
			this.signin.setText(LOGOUT);
		}
		else{
			this.signin.setText(LOGIN);
		}
		
		this.add(this.verticalPanel);
	}
	
	

}

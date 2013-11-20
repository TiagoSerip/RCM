package pt.ist.sonet.presentation.client;


import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Header extends DecoratorPanel {
		
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private static final String NO_USER_LOGIN = "No user logged in.";
	private static final String ACTIVE_USER = "Logged in as ";
	private static final String LOGIN_FAIL = "Wrong username or password. Try again...";
	private static final String LOGIN_OK = "You are now logged in as ";
	private static final String LOGIN_ERROR = "Something went wrong while logging you in. Try again...";
	
	private String user = null; //active user
	private String ap = null; //user's AP
	private final HorizontalPanel horizontalPanel;
	private final Label label;
	
	public Header(String user, String ap){
		
		this.user = user;
		this.ap = ap;
		this.horizontalPanel = new HorizontalPanel();
		this.label = new Label();
		
		horizontalPanel.add(label);
		
		
		if(user == null)
			this.label.setText(NO_USER_LOGIN);
		else
			this.label.setText(ACTIVE_USER+user);
		
		this.add(this.horizontalPanel);
	}
	
	

}

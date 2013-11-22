package pt.ist.sonet.presentation.client;


import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Frame;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ViewAP extends DecoratorPanel {
		
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
	private int ap = -1; //user's AP
	private final AbsolutePanel panel;
	private final Label label;
	private final Button button;
	private Frame frame;
	
	public ViewAP(String user, int ap){
		setStyleName("center");
		setSize("100%", "100%");
		
		this.user = user;
		this.ap = ap;
		this.panel = new AbsolutePanel();
		
		final Label info = new Label("View AP Menu");
		panel.add(info);
		this.label = new Label();
		button = new Button("View AP");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				info.setText("Clicou");
			}
		});
		
		panel.add(label);
		panel.add(button);
		
		if(user == null)
			this.label.setText(NO_USER_LOGIN);
		else
			this.label.setText(ACTIVE_USER+user);
		
		this.add(this.panel);
		panel.setSize("100%", "100%");
		
		frame = new Frame("https://mapsengine.google.com/map/embed?mid=zfVxo7FXwYtM.kMWYZEetIvgg");
		panel.add(frame);
		frame.setSize("640", "480");
	}

}

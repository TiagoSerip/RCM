package pt.ist.sonet.presentation.client;


import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.VerticalSplitPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Profile extends DecoratorPanel {
		
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
	
	public Profile(String user, int ap){
		setSize("100%", "100%");
		setStyleName("center");
		
		this.user = user;
		this.ap = ap;
		this.panel = new AbsolutePanel();
		
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("SoNet:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}});
		
		Label title = new Label("My Profile");
		title.setStyleName("h1");
		panel.add(title);
		
		
		this.add(this.panel);
		panel.setSize("100%", "556px");
		
		Label lblPleaseEnterYour = new Label("You can change your profile data here:");
		panel.add(lblPleaseEnterYour, 46, 65);
		
		Label lblName = new Label("Name:");
		panel.add(lblName, 46, 119);
		
		Label lblAP = new Label("AP:");
		panel.add(lblAP, 46, 158);
		
		final TextBox textBox = new TextBox();
		panel.add(textBox, 116, 103);
		textBox.setSize("171", "34");
		
		final IntegerBox apBox = new IntegerBox();
		panel.add(apBox, 117, 148);
		apBox.setSize("171", "34");
		
		Button btnSave = new Button("Save");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String name = textBox.getValue();
				int ap = apBox.getValue();
				
				if(name == null){
					dialogBox.setText("Singin Error");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("You must fill in your Username and Password.");
					dialogBox.center();
					closeButton.setFocus(true);
				}

				
				textBox.setValue(null);
				apBox.setValue(null);
				
			}
		});
		panel.add(btnSave, 342, 232);
		
		Label lblIpAddress = new Label("IP Address:");
		panel.add(lblIpAddress, 46, 196);
		
		TextBox IpBox = new TextBox();
		panel.add(IpBox, 116, 186);
		
		Label lblChangePassword = new Label("Fill the boxes bellow to change your password:");
		panel.add(lblChangePassword, 49, 297);
		
		Label lblNewPassword = new Label("New Password:");
		panel.add(lblNewPassword, 46, 349);
		
		Label lblConfirmPassword = new Label("Confirm Password:");
		panel.add(lblConfirmPassword, 46, 403);
		
		PasswordTextBox passwordConfirmTextBox = new PasswordTextBox();
		panel.add(passwordConfirmTextBox, 172, 392);
		
		PasswordTextBox passwordTextBox = new PasswordTextBox();
		panel.add(passwordTextBox, 172, 338);
		
		Button pswdButton = new Button("Confirm");
		panel.add(pswdButton, 342, 391);
		
		Label lblRssi = new Label("RSSI:");
		panel.add(lblRssi, 46, 237);
		
		IntegerBox integerBox = new IntegerBox();
		panel.add(integerBox, 116, 232);
		integerBox.setSize("171", "34");
	}
}

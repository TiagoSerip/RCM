package pt.ist.sonet.presentation.client;


import pt.ist.sonet.service.dto.AgentDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.google.gwt.core.client.GWT;
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
	private static final String LOAD_ERROR = "Something went wrong while loading your profile data. Please try again...";
	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	private String user = null; //active user
	private int ap = -1; //user's AP
	private final AbsolutePanel panel;
	
	final TextBox nameBox = new TextBox();
	final IntegerBox apBox = new IntegerBox();
	final TextBox ipBox = new TextBox();
	final IntegerBox rssiBox = new IntegerBox();
	
	public Profile(String user, int ap){
		setSize("100%", "100%");
		setStyleName("center");
		
		this.user = user;
		this.ap = ap;
		this.panel = new AbsolutePanel();
		
		
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
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
		
		panel.add(nameBox, 116, 103);
		nameBox.setSize("171", "34");
		
		panel.add(apBox, 117, 148);
		apBox.setSize("171", "34");
		
		//Load the profile data from the FF
		loadProfileData();
		
		Button btnSave = new Button("Save");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String name = nameBox.getValue();
				int ap = apBox.getValue();
				
				if(name == null){
					dialogBox.setText("Singin Error");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("You must fill in your Username and Password.");
					dialogBox.center();
					closeButton.setFocus(true);
				}

				
				nameBox.setValue(null);
				apBox.setValue(null);
				
			}
		});
		panel.add(btnSave, 342, 232);
		
		Label lblIpAddress = new Label("IP Address:");
		panel.add(lblIpAddress, 46, 196);
		

		panel.add(ipBox, 116, 186);
		
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
		

		panel.add(rssiBox, 116, 232);
		rssiBox.setSize("171", "34");
	}
	
	void loadProfileData(){
		
		sonetServlet.getAgent(user, new AsyncCallback<AgentDto>() {
			@Override
			public void onSuccess(AgentDto dto){
				nameBox.setValue(dto.getName());
				apBox.setValue(dto.getAp());
				ipBox.setValue(dto.getIp());
				rssiBox.setValue(dto.getRssi());

			}
			
			@Override
			public void onFailure(Throwable caught){
				// Show the RPC error message to the user
				dialogBox.setText("Profile Loding Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(LOAD_ERROR);
				dialogBox.center();
				closeButton.setFocus(true);
			}
		});
	}
}

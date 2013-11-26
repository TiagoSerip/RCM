package pt.ist.sonet.presentation.client;



import pt.ist.sonet.exception.UsernameAlreadyExistsException;
import pt.ist.sonet.service.dto.AgentDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Register extends DecoratorPanel {
		
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private static final String REGISTER_FAIL = "Something went wrong while creating your account. Please try again...";
	private static final String REGISTER_OK = "You can now login into your account using the form on menu bar.";
	private static final String USERNAME_FAIL = "That username is already being used. Please select another...";
	private static final String REGISTER_ERROR_TITLE = "Register Failed:";
	private static final String LOAD_ERROR = "Something went wrong while loading your profile data. Please try again...";
	private static final String IP_ERROR = "It wasn't possible to automaticaly determine your current IP address. Please update this information manualy if it has changed.";
	private static final String RSSI_ERROR = "It wasn't possible to automaticaly determine your current RSSI. Please update this information manualy if it has changed.";

	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();

	private final AbsolutePanel panel;
	
	final TextBox nameBox = new TextBox();
	final IntegerBox apBox = new IntegerBox();
	final TextBox ipBox = new TextBox();
	final IntegerBox rssiBox = new IntegerBox();
	
	public Register(){
		setSize("100%", "100%");
		setStyleName("center");
		

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
		
		Label title = new Label("Register");
		title.setStyleName("h1");
		panel.add(title);
		
		
		this.add(this.panel);
		panel.setSize("100%", "556px");
		
		Label lblPleaseEnterYour = new Label("Fill all the information bellow to create a new account:");
		panel.add(lblPleaseEnterYour, 46, 65);
		
		Label lblName = new Label("Name:");
		panel.add(lblName, 46, 119);
		
		Label lblAP = new Label("AP:");
		panel.add(lblAP, 46, 158);
		
		panel.add(nameBox, 116, 103);
		nameBox.setSize("171", "34");
		apBox.setEnabled(false);
		apBox.setText("0");
		
		panel.add(apBox, 117, 148);
		apBox.setSize("171", "34");
		
		//Load the profile data from the FF
		loadProfileData();
		
		Label lblIpAddress = new Label("IP Address:");
		panel.add(lblIpAddress, 46, 196);
		

		panel.add(ipBox, 116, 186);
		rssiBox.setText("-1");
		
		panel.add(rssiBox, 116, 226);
		final TextBox usernameBox = new TextBox();
		panel.add(usernameBox, 116, 261);
		Label lblNewPassword = new Label("Password:");
		panel.add(lblNewPassword, 46, 316);
		
		Label lblConfirmPassword = new Label("Confirm Password:");
		panel.add(lblConfirmPassword, 46, 354);
		
		final PasswordTextBox passwordTextBox = new PasswordTextBox();
		panel.add(passwordTextBox, 116, 302);
		
		final PasswordTextBox passwordConfirmTextBox = new PasswordTextBox();
		panel.add(passwordConfirmTextBox, 162, 340);
		
		Button btnSave = new Button("Register");
		panel.add(btnSave, 342, 342);

		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String name = nameBox.getValue();
				int ap = apBox.getValue();
				String ip = ipBox.getValue();
				int rssi = rssiBox.getValue();
				String username = usernameBox.getValue();
				
				if(name == null || name.equals("")){
					dialogBox.setText(REGISTER_ERROR_TITLE);
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("You must fill in the name field.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;
				}
				
				if(username == null || username.equals("")){
					dialogBox.setText(REGISTER_ERROR_TITLE);
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("You must fill in your username.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;

				}
				
				if(ip == null || ip.equals("")){
					dialogBox.setText(REGISTER_ERROR_TITLE);
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("You must fill in your current IP address.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;

				}
				
				if(rssi >= 0){
					dialogBox.setText(REGISTER_ERROR_TITLE);
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("The RSSI field must be a negative number (dBm).");
					dialogBox.center();
					closeButton.setFocus(true);
					return;

				}
				
				if(ap < 0 || ap > 7){
					dialogBox.setText(REGISTER_ERROR_TITLE);
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("The AP-ID must be between 0 and 7.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;

				}
				
				String pass = passwordTextBox.getValue();
				String confirm = passwordConfirmTextBox.getValue();
				
				if(pass.equals("") || pass == null || !pass.equals(confirm)){
					dialogBox.setText(REGISTER_ERROR_TITLE);
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("The two password fields must match.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;

				}
				
				AgentDto dto = new AgentDto(username, pass, name, ap, rssi, ip);
				
				sonetServlet.registerAgentProfile(dto, new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void v){
						// Show the RPC error message to the user
						dialogBox.setText("New Account Created!");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(REGISTER_OK);
						dialogBox.center();
						closeButton.setFocus(true);
						passwordTextBox.setValue(null);
						passwordConfirmTextBox.setValue(null);
						nameBox.setValue(null);
						apBox.setValue(0);
						usernameBox.setValue(null);
						loadProfileData();
					}
					
					@Override
					public void onFailure(Throwable caught){
						// Show the RPC error message to the user
						dialogBox.setText("Profile Update Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						dialogBox.center();
						closeButton.setFocus(true);
						if(caught instanceof UsernameAlreadyExistsException)
							serverResponseLabel.setHTML(USERNAME_FAIL);
						else
						serverResponseLabel.setHTML(REGISTER_FAIL);

					}
				});
			}
		});

		
		Label lblRssi = new Label("RSSI:");
		panel.add(lblRssi, 46, 226);
		
		
		rssiBox.setSize("171", "34");
		
		Label lblUsername = new Label("Username:");
		panel.add(lblUsername, 46, 271);
		

		
	}
	
	void loadProfileData(){
		
				loadAgentIp();
			//	loadRSSIMacOS();

	}
	
	void loadAgentIp(){
		
		sonetServlet.getAgentIP(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String ip){
				if(ip !=null){
					ipBox.setEnabled(false);
					ipBox.setValue(ip);
				}
				else{
					// Show the RPC error message to the user
					dialogBox.setText("Determine IP Failure");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML(IP_ERROR);
					dialogBox.center();
					closeButton.setFocus(true);
					
					ipBox.setEnabled(true);
					rssiBox.setEnabled(true);

				}
				

			}
			
			@Override
			public void onFailure(Throwable caught){
				// Show the RPC error message to the user
				dialogBox.setText("Determine IP Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(IP_ERROR);
				dialogBox.center();
				closeButton.setFocus(true);
				
				ipBox.setEnabled(true);
				rssiBox.setEnabled(true);
			}
		});
		
	}
	
}

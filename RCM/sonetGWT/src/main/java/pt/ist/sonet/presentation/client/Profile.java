package pt.ist.sonet.presentation.client;



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
public class Profile extends DecoratorPanel {
		
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private static final String PASS_FAIL = "Something went wrong while changing your password. Please try again...";
	private static final String PASS_OK = "You successfully changed your password.";
	private static final String UPDATE_FAIL = "Something went wrong while updating your profile data. Please try again...";
	private static final String UPDATE_OK = "You successfully updated your profile data.";
	private static final String LOAD_ERROR = "Something went wrong while loading your profile data. Please try again...";
	private static final String IP_ERROR = "It wasn't possible to automaticaly determine your current IP address. Please update this information manualy if it has changed.";
	private static final String RSSI_ERROR = "It wasn't possible to automaticaly determine your current RSSI. Please update this information manualy if it has changed.";

	
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
	
	public Profile(final String user, int ap){
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
		
		loadAgentIp();
		
		Button btnSave = new Button("Save");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String name = nameBox.getValue();
				int ap = apBox.getValue();
				String ip = ipBox.getValue();
				int rssi = rssiBox.getValue();
				
				if(name == null || name.equals("")){
					dialogBox.setText("Update Profile Error");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("You must fill in the name field.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;
				}
				
				if(ip == null || ip.equals("")){
					dialogBox.setText("Update Profile Error");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("You must fill in your current IP address.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;

				}
				
				if(rssi >= 0){
					dialogBox.setText("Update Profile Error");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("The RSSI field must be a negative number (dBm).");
					dialogBox.center();
					closeButton.setFocus(true);
					return;

				}
				
				if(ap < 0 || ap > 7){
					dialogBox.setText("Update Profile Error");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("The AP-ID must be between 0 and 7.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;

				}
				
				AgentDto dto = new AgentDto(user, null, name, ap, rssi, ip);
				
				sonetServlet.updateAgentProfile(dto, new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void v){
						// Show the RPC error message to the user
						dialogBox.setText("Profile Update");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(UPDATE_OK);
						dialogBox.center();
						closeButton.setFocus(true);

					}
					
					@Override
					public void onFailure(Throwable caught){
						// Show the RPC error message to the user
						dialogBox.setText("Profile Update Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(UPDATE_FAIL);
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
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
		
		final PasswordTextBox passwordTextBox = new PasswordTextBox();
		panel.add(passwordTextBox, 172, 338);
		
		final PasswordTextBox passwordConfirmTextBox = new PasswordTextBox();
		panel.add(passwordConfirmTextBox, 172, 392);

		Button pswdButton = new Button("Confirm");
		pswdButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String pass = passwordTextBox.getValue();
				String confirm = passwordConfirmTextBox.getValue();
				
				if(pass == null || !pass.equals(confirm)){
					dialogBox.setText("Change Password Error");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("The two password fields must match.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;

				}
				
				AgentDto dto = new AgentDto(user, pass, null, -1, -1, null);
				
				sonetServlet.changeAgentPassword(dto, new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void v){
						// Show the RPC error message to the user
						dialogBox.setText("Change Password");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(PASS_OK);
						dialogBox.center();
						closeButton.setFocus(true);
					}
					
					@Override
					public void onFailure(Throwable caught){
						// Show the RPC error message to the user
						dialogBox.setText("Change Password Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(PASS_FAIL);
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
				
				passwordTextBox.setValue(null);
				passwordConfirmTextBox.setValue(null);
			}
		});
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
				
				//loadAgentIp();
				//loadRSSIMacOS();

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
			}
		});
		
	}
	
	public void loadRSSIMacOS(){
		
		sonetServlet.loadRSSIMacOS(new AsyncCallback<Integer>() {
			@Override
			public void onSuccess(Integer rssi){
				if(rssi>0){
					fail();
					return;
				}
				rssiBox.setEnabled(false);
				rssiBox.setValue(rssi);
				
			}
			@Override
			public void onFailure(Throwable caught){
				fail();
			}
			
			void fail(){
				// Show the RPC error message to the user
				dialogBox.setText("Determine RSSI Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(RSSI_ERROR);
				dialogBox.center();
				closeButton.setFocus(true);
				
				rssiBox.setEnabled(true);
			}
		});
	}
	
}

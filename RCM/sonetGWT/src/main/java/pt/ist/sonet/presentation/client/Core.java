package pt.ist.sonet.presentation.client;




import pt.ist.sonet.service.dto.AgentDto;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Core implements EntryPoint {
		
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private static final String NO_USER_LOGIN = "No user logged in.";
	private static final String ACTIVE_USER = "Hello, ";
	private static final String LOGIN_FAIL = "Wrong username or password. Try again...";
	private static final String LOGIN_ERROR = "Something went wrong while logging you in. Try again...";
	
	private static final String IP_ERROR = "It wasn't possible to automaticaly determine your current IP address. Please update this information manualy if it has changed.";

	private static final String NOTE_ERROR = "An error occurred while posting your Text Note. Try again...";
	private static final String NOTE_OK = "Your Text Note was successfully added.";
	private static final String FRIENDS_ERROR = "An error occurred while fetching your friends. Try again...";
	private static final String FRIENDS_INFO = "Friends of ";
	private static final String PUBLICATIONS_ERROR = "An error ocurred while fetching the publications. Try again...";
	private static final String PUBLICATIONS_INFO = "Publications of ";
	private static final String PUBLICATIONS_PUBID_SELECTED = "Selected publication with id: ";
	private static final String PUBLICATIONS_NOUSER = "No user selected.";
	private static final String PUBLICATIONS_NOT_FRIEND_ERROR = "Sorry you don't have access to information of ";
	private static final String AGENTS_ERROR = "An error ocurred while fetching the agents listing. Try again...";
	private static final String NO_AGENTS = "There are no registered Agents at this moment.";
	private static final String COMMENTS_ERROR = "An error ocurred while fetching the publication comments listing. Try again...";
	private static final String COMMENTS_ID_ERROR = "There is no publication with id: ";
	private static final String DONATION_OK = "Your donation was successfully made.";
	private static final String DONATION_ERROR = "Something went wrong while processing your donation. No debit was made to your account. Please try again...";
	private static final String NO_ORGS = "There arent organizations currently registered.";
	private static final String DESCRIPTION = "Im donating because im cool.";
	private static final String POSITIVE_VOTE_ERROR = "An error occurred while posting your positive vote. Try again...";
	private static final String POSITIVE_VOTE_SUCCESS = "Your positive vote was posted!";
	private static final String NEGATIVE_VOTE_ERROR = "An error occurred while posting your negative vote. Try again...";
	private static final String NEGATIVE_VOTE_SUCCESS = "Your negative vote was posted!";
	private static final String ALREADY_VOTED = "You already voted on this publication. You can only vote once!";
	private static final String OWN_VOTE_ERROR = "You can't vote on your own publications!";
	private static final String FLAGGED_PUBLICATION_ERROR = "Votes and comments on this publication are temporarily disabled. Try again later...";
	private static final String NOTE = "Note";
	private static final String CONTENT = "Content";
	private static final String VIEW_CONTENT_INFO = "Press 'View Content' to access this content.";
	private static final String BUY_CONTENT_INFO = "Press 'Buy Content' to access this content.";
	private static final String CONTENT_OK = "This content was transfered to your LargaCaixa account!";
	private static final String CONTENT_PAYMENT_FAIL = "Your don't have enough funds to acess this content.";
	private static final String CONTENT_ERROR = "Something went wrong while processing your order. Please try again...";
	private static final String COMMENT_OK = "Your comment was posted.";
	private static final String COMMENT_ERROR = "Something went wrong while posting your comment. Try again...";
	private static final String SEND_RQST_OK = "You have sent a friend request to ";
	private static final String SEND_RQST_ERROR = "Something went wrong while sending your request. Try again...";
	private static final String SEND_RQST_ERROR_ALREADY_FRIEND = "You are already a friend of this agent.";
	private static final String SEND_RQST_ERROR_TARGET_ALREADY_SENT = "This agent has already sent you a friend request. Accept it on the Pending Requests tab.";
	private static final String SEND_RQST_ERROR_YOU_ALREADY_SENT = "You already sent this agent a friend request.";
	private static final String SEND_RQST_NO_AGENTS = "You are very social, all users are your friends already!";
	private static final String SEND_RQST_AGENTS_ERROR = "Something went wrong while fetching the users list. Try again...";
	private static final String SEND_RQST_ORG_FAIL = "Organizations can't send friend requests!\nYou should have known better...";
	private static final String ACCEPT_RQST_OK = "You are now friends with ";
	private static final String ACCEPT_RQST_ERROR = "Something went wrong while accepting this request.\nPlease try again...";
	private static final String REJECT_RQST_OK = "You rejected to be friends with ";
	private static final String REJECT_RQST_ERROR = "Something went wrong while rejecting this request.\nPlease try again...";
	private static final String FRIEND_LIMIT_ERROR = "The agent has reached the maximum number of friends allowed. He/She is too social for SoNet...";
	private static final String FRIEND_OWN_LIMIT_ERROR = "You already reached the maximum number of friends allowed.";
	private static final String LATEST_PUBS_ERROR = "Failed to load your friends' latest publications.";
	private static final String PENDING_RQST_ORG_FAIL = "Organizations automaticaly accept request, so there's nothing to show here...";
	private static final String PENDING_RQST_FAIL = "Something went wrong while fetching your pending requests. Please try again...";
	
	
	private String active = null; //active user
	private int ap = -1; //selected agent - publication view
	private String ip = null;
	
	private static final String LOGIN = "LOGIN";
	private static final String PROFILE= "MY PROFILE";
	private static final String REGISTER = "REGISTER";
	private static final String LOGOUT = "LOGOUT";
	private static final String MAP = "TAGUS MAP";
	
	private VerticalPanel verticalPanel;
	private Button signin;
	private Button map;
	private Button streaming;

	final RootPanel RootContainer = RootPanel.get("content");
	final RootPanel RootHeader = RootPanel.get("header");

	private Profile profilePanel;
	private Map viewMap;

	/**
	 * Create a remote service proxy to talk to the server-side SoNetServlet.
	 */
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);
	private Label lblusername;
	private TextBox username;
	private Label lblPassword;
	private PasswordTextBox password;
	private Label lblLoginStatus;
	private VerticalPanel verticalPanel_1;
	Button btnProfile = new Button(PROFILE);

	
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
				
		RootPanel RootMenu = RootPanel.get("leftnav");

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

			}
		});
		
		sonetServlet.init("local", new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result){
				dialogBox.setText("External Services Init OK");
				serverResponseLabel.setHTML(result);
				dialogBox.center();
				closeButton.setFocus(true);
			}
			
			@Override
			public void onFailure(Throwable caught){
				// Show the RPC error message to the user
				dialogBox.setText("External Services Init Error");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML("Failed to connect to server, some services may be unavailable.\n");
				dialogBox.center();
				closeButton.setFocus(true);
			}
		});
		
		verticalPanel_1 = new VerticalPanel();
		RootMenu.add(verticalPanel_1);
		
		lblLoginStatus = new Label("No user logged in");
		lblLoginStatus.setStyleName("h3");
		verticalPanel_1.add(lblLoginStatus);
		
		verticalPanel = new VerticalPanel();
		verticalPanel_1.add(verticalPanel);
		
		signin = new Button(LOGIN);
		signin.addClickHandler(loginHandler);
		map = new Button(MAP);
		map.setVisible(false);
		map.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootContainer.clear();
				refresh();
				RootContainer.add(new Map(active, ap));
			}
		});
		
		lblusername = new Label("Username:");
		verticalPanel.add(lblusername);
		
		username = new TextBox();
		username.setVisibleLength(17);
		verticalPanel.add(username);
		
		lblPassword = new Label("Password:");
		verticalPanel.add(lblPassword);
		
		password = new PasswordTextBox();
		verticalPanel.add(password);
		
		verticalPanel.add(signin);
		
		btnProfile.setVisible(false);
		btnProfile.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootContainer.clear();
				refresh();
				profilePanel.loadProfileData();
				RootContainer.add(profilePanel);
			}
		});
		
		btnRegister = new Button(REGISTER);
		btnRegister.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootContainer.clear();
				RootContainer.add(new Register());
			}
		});
		
		streaming = new Button("STREAMING");
		streaming.setVisible(false);
		streaming.addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
		    Window.Location.assign("http://192.168.1.105:23424/mediabrowser/");
		  }
		});
		
		verticalPanel.add(btnRegister);
		verticalPanel.add(btnProfile);
		verticalPanel.add(map);
		verticalPanel.add(streaming);
		


	}
	
	ClickHandler loginHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			if(active == null){
				final String user = username.getValue();
				String pass = password.getValue();
				
				if(user == null || pass == null){
					dialogBox.setText("Login Error");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("You must fill in your Username and Password.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;
				}

				sonetServlet.agentLogin(user, pass, new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result){
						if(!result){
							// Show the RPC error message to the user
							dialogBox.setText("Login Error");
							serverResponseLabel.addStyleName("serverResponseLabelError");
							serverResponseLabel.setHTML(LOGIN_FAIL);
							dialogBox.center();
							closeButton.setFocus(true);
							return;
						}
						active=user;
						username.setValue(null);
						password.setValue(null);
						username.setVisible(false);
						password.setVisible(false);
						lblPassword.setVisible(false);
						lblusername.setVisible(false);
						signin.setText(LOGOUT);
						lblLoginStatus.setText(ACTIVE_USER+active+"!");
						refresh();
						profilePanel = new Profile(active, ap);
						btnProfile.setVisible(true);
						map.setVisible(true);
						btnRegister.setVisible(false);
						streaming.setVisible(true);
						RootContainer.clear();
						RootContainer.add(profilePanel);
					}
					
					@Override
					public void onFailure(Throwable caught){
						// Show the RPC error message to the user
						dialogBox.setText("Login Error");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(LOGIN_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});

			}
			else{
				username.setVisible(true);
				password.setVisible(true);
				lblPassword.setVisible(true);
				lblusername.setVisible(true);
				active=null;
				ap=-1;
				signin.setText(LOGIN);
				lblLoginStatus.setText(NO_USER_LOGIN);
				btnProfile.setVisible(false);
				btnRegister.setVisible(true);
				map.setVisible(false);
				streaming.setVisible(false);
				RootContainer.clear();
				
			}
		}
	};
	private Button btnRegister;
		
	void refresh(){
		
		sonetServlet.getAgentIP(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String res){
				if(res !=null){
					ip = res;
					updateAgentIp();
				}
				else{
					// Show the RPC error message to the user
					dialogBox.setText("Determine IP Failure");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML(IP_ERROR);
					dialogBox.center();
					closeButton.setFocus(true);
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
			}
		});
		
	}
	
	void updateAgentIp(){
		
		sonetServlet.updateAgentIP(new AgentDto(active, null, null, 0, 0, ip), new AsyncCallback<Integer>() {
			@Override
			public void onSuccess(Integer res){
				ap=res;
			}
			
			@Override
			public void onFailure(Throwable caught){
				// Show the RPC error message to the user
				dialogBox.setText("Determine IP Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(IP_ERROR);
				dialogBox.center();
				closeButton.setFocus(true);
			}
		});
		
	}
	
}

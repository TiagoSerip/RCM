package pt.ist.sonet.presentation.client;




import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

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
	private static final String ACTIVE_USER = "Logged in as ";
	private static final String LOGIN_FAIL = "Wrong username or password. Try again...";
	private static final String LOGIN_OK = "You are now logged in as ";
	private static final String LOGIN_ERROR = "Something went wrong while logging you in. Try again...";
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
	private String ap = null; //selected agent - publication view

	private static final String LOGIN = "LOGIN";
	private static final String USER = "MY PROFILE";
	private static final String REGISTER = "REGISTER";
	private static final String LOGOUT = "LOGOUT";
	private static final String AP = "VIEW AP";
	
	private VerticalPanel verticalPanel;
	private Button signin;
	private Button apView;

	
	private Header headerPanel = new Header(active, ap);
	private Signin signinPanel = new Signin(active, ap);
	private ViewAP viewAPPanel = new ViewAP(active, ap);

	/**
	 * Create a remote service proxy to talk to the server-side SoNetServlet.
	 */
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel RootHeader = RootPanel.get("header");
		RootHeader.add(headerPanel);
		
		RootPanel RootMenu = RootPanel.get("leftnav");
		
		final RootPanel RootContainer = RootPanel.get("content");
		
		// Create the popup dialog box
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
		
		sonetServlet.init("local", new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result){
				
			}
			
			@Override
			public void onFailure(Throwable caught){
				// Show the RPC error message to the user
				dialogBox.setText("External Services Init Error");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML("Failed to initialize external services, some services may be unavailable.");
				dialogBox.center();
				closeButton.setFocus(true);
			}
		});
		
		verticalPanel = new VerticalPanel();
		RootMenu.add(verticalPanel);
//		MenuBar menu = new MenuBar(false);
//		verticalPanel.add(menu);
//		
//		MenuItem login = new MenuItem(LOGIN, new Command() {
//		      public void execute() {
//		    	RootContainer.add(signinPanel);
//		      }
//		   });
//		MenuItem viewAP = new MenuItem(AP, new Command() {
//		      public void execute() {
//		    	RootContainer.add(new ViewAP(active, ap));
//		      }
//		   });
//
//		menu.addItem(login);
//		menu.addItem(viewAP);
		
		this.signin = new Button(LOGIN);
		signin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootContainer.clear();
				RootContainer.add(signinPanel);
			}
		});
		this.apView = new Button(AP);
		apView.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootContainer.clear();
				RootContainer.add(new ViewAP(active, ap));
			}
		});
		
		verticalPanel.add(signin);
		verticalPanel.add(apView);
		
		
		if(active == null){
			this.signin.setText(LOGOUT);
		}
		else{
			this.signin.setText(LOGIN);
		}

	}
		
}

package pt.ist.sonet.presentation.client;




import java.util.ArrayList;

import pt.ist.sonet.exception.AgentUsernameDoesNotExistsException;
import pt.ist.sonet.exception.AlreadyVotedException;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.presentation.shared.FieldVerifier;
import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.ListingDto;
import pt.ist.sonet.service.dto.StringListDto;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SonetGWT implements EntryPoint {
		
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
	private String selected = null; //selected agent - publication view
	private int publicationId = -1; //selected publication - publication view
	private String orgSelected = null; //org selected - donate
	private String pendingSelected = null; //selected pending request user
	private String sendSelected = null; //selected send request user
	private static String serverType = null;
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
		RootPanel rootPanel = RootPanel.get("container");
		DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loading"));
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
		if(RootPanel.get("remoteServices") != null){
			serverType = "REMOTE";
		}
		else
			serverType = "LOCAL";
//		sonetServlet.init(serverType, new AsyncCallback<Void>() {
//			@Override
//			public void onSuccess(Void result){
//				
//			}
//			
//			@Override
//			public void onFailure(Throwable caught){
//				// Show the RPC error message to the user
//				dialogBox.setText("External Services Init Error");
//				serverResponseLabel.addStyleName("serverResponseLabelError");
//				serverResponseLabel.setHTML("Failed to initialize external services, some services may be unavailable.");
//				dialogBox.center();
//				closeButton.setFocus(true);
//			}
//		});
		rootPanel.get("loading").setVisible(false);
		DecoratedTabPanel decoratedTabPanel = new DecoratedTabPanel();
		decoratedTabPanel.setStyleName("center");
		rootPanel.add(decoratedTabPanel);//, 16, 107);
		decoratedTabPanel.setSize("735px", "451px");
		
		AbsolutePanel loginTab = new AbsolutePanel();
		decoratedTabPanel.add(loginTab, "Login", false);
		loginTab.setSize("722px", "409px");
		
		final Label loginUserLabel = new Label("Username:");
		loginTab.add(loginUserLabel, 29, 68);
		//loginUserLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		loginUserLabel.setSize("69px", "18px");
		final TextBox nameField = new TextBox();
		loginTab.add(nameField, 104, 62);
		nameField.setWidth("154px");
		
		Label loginPasswordlabel = new Label("Password:");
		loginTab.add(loginPasswordlabel, 29, 126);
		final PasswordTextBox pswrdtxtbxPassword = new PasswordTextBox();
		loginTab.add(pswrdtxtbxPassword, 104, 122);
		pswrdtxtbxPassword.setWidth("154px");
		final Button loginButton = new Button("Login");
		loginTab.add(loginButton, 29, 210);
		loginButton.setStyleName("sendButton");
		
		final Label loginErrorLabel = new Label("");
		loginTab.add(loginErrorLabel, 29, 183);
		loginErrorLabel.setStyleName("serverResponseLabelError");
		
		final ListBox loginPublicationsList = new ListBox();
		loginPublicationsList.setVisible(false);
		loginTab.add(loginPublicationsList, 298, 68);
		loginPublicationsList.setSize("414px", "285px");
		loginPublicationsList.setVisibleItemCount(5);
		
		final Label loginLatestInfoLabel = new Label("Your friends latest publications:");
		loginLatestInfoLabel.setVisible(false);
		loginTab.add(loginLatestInfoLabel, 298, 44);
		
		nameField.selectAll();
		
		Grid noteGrid = new Grid(3, 2);
		decoratedTabPanel.add(noteGrid, "Add Text Note", false);
		noteGrid.setSize("506px", "261px");
		
		Label noteLabelLabel = new Label("Label:");
		noteGrid.setWidget(0, 0, noteLabelLabel);
		
		Grid grid_5 = new Grid(1, 1);
		noteGrid.setWidget(0, 1, grid_5);
		
		final TextBox noteLabelTextbox = new TextBox();
		grid_5.setWidget(0, 0, noteLabelTextbox);
		
		Grid grid_6 = new Grid(1, 1);
		noteGrid.setWidget(1, 0, grid_6);
		
		Label noteTextLabel = new Label("Text:");
		grid_6.setWidget(0, 0, noteTextLabel);
		
		Grid grid_8 = new Grid(1, 1);
		noteGrid.setWidget(1, 1, grid_8);
		
		final TextBox noteTextTextbox = new TextBox();
		grid_8.setWidget(0, 0, noteTextTextbox);
		
		final Button noteButton = new Button("Add Note");
		noteGrid.setWidget(2, 0, noteButton);
		
		Grid grid_7 = new Grid(1, 1);
		noteGrid.setWidget(2, 1, grid_7);
		
		final Label noteErrorLabel = new Label("");
		noteErrorLabel.setStyleName("serverResponseLabelError");
		grid_7.setWidget(0, 0, noteErrorLabel);
		
		Grid friendGrid = new Grid(3, 2);
		decoratedTabPanel.add(friendGrid, "Friends", false);
		friendGrid.setSize("585px", "302px");
		
		Label lblPressLoadTo = new Label("Press Load to fetch your friend list.");
		friendGrid.setWidget(0, 0, lblPressLoadTo);
		
		final Label friendsOfLabel = new Label("");
		friendGrid.setWidget(1, 0, friendsOfLabel);
		
		Grid grid_4 = new Grid(1, 1);
		friendGrid.setWidget(2, 0, grid_4);
		grid_4.setWidth("307px");
		
		final ListBox friendList = new ListBox();
		grid_4.setWidget(0, 0, friendList);
		friendList.setWidth("302px");
		friendList.setVisibleItemCount(5);
		
		Button friendsButton = new Button("Load");
		friendGrid.setWidget(2, 1, friendsButton);
		
		Grid friendRequestsGrid = new Grid(1, 1);
		decoratedTabPanel.add(friendRequestsGrid, "Friend Requests", false);
		friendRequestsGrid.setSize("719px", "408px");
		
		TabPanel requestsMainTab = new TabPanel();
		friendRequestsGrid.setWidget(0, 0, requestsMainTab);
		requestsMainTab.setSize("710px", "403px");
		
		AbsolutePanel sendRequestPanel = new AbsolutePanel();
		requestsMainTab.add(sendRequestPanel, "Send Request", false);
		sendRequestPanel.setSize("696px", "346px");
		
		final ListBox sendRqstList = new ListBox();
		sendRequestPanel.add(sendRqstList, 10, 43);
		sendRqstList.setSize("320px", "202px");
		sendRqstList.setVisibleItemCount(5);
		
		Label sendRqstInfoLabel = new Label("Select a user from the users list:");
		sendRequestPanel.add(sendRqstInfoLabel, 10, 10);
		
		final Button sendRqstButton = new Button("Send Request");
		sendRequestPanel.add(sendRqstButton, 10, 283);
		
		final Label sendRqstErrorLabel = new Label("");
		sendRqstErrorLabel.setStyleName("serverResponseLabelError");
		sendRequestPanel.add(sendRqstErrorLabel, 250, 295);
		
		final Button sendRqstRefreshButton = new Button("Refresh User List");
		sendRequestPanel.add(sendRqstRefreshButton, 119, 283);
		
		AbsolutePanel pendingRequestPanel = new AbsolutePanel();
		requestsMainTab.add(pendingRequestPanel, "Pending Requests", false);
		pendingRequestPanel.setSize("696px", "346px");
		
		final ListBox pendingList = new ListBox();
		pendingRequestPanel.add(pendingList, 10, 45);
		pendingList.setSize("430px", "230px");
		pendingList.setVisibleItemCount(5);
		
		Label pendingInfoLabel = new Label("Select a request and accept or refuse it:");
		pendingRequestPanel.add(pendingInfoLabel, 10, 10);
		
		final Button pendingRefreshButton = new Button("Refresh List");
		pendingRequestPanel.add(pendingRefreshButton, 10, 306);
		
		final Button pendingAcceptButton = new Button("Accept Request");
		pendingRequestPanel.add(pendingAcceptButton, 102, 306);
		
		final Button pendingRefuseButton = new Button("Refuse Request");
		pendingRequestPanel.add(pendingRefuseButton, 217, 306);
		
		final Label pendingErrorLabel = new Label("");
		pendingErrorLabel.setStyleName("serverResponseLabelError");
		pendingRequestPanel.add(pendingErrorLabel, 332, 318);
		
		final Button listButton = new Button("List SoNet");
		//decoratedTabPanel.add(listButton, "Listar SoNet", false);
		listButton.setSize("100px", "45px");
				
		// Focus the cursor on the name field when the app loads
		listButton.setFocus(true);
		
		Grid donateGrid = new Grid(3, 2);
		decoratedTabPanel.add(donateGrid, "Donate", false);
		donateGrid.setSize("590px", "307px");
		
		Grid grid_9 = new Grid(1, 2);
		donateGrid.setWidget(0, 0, grid_9);
		
		Label donateOrganizationLabel = new Label("Select an Organization:");
		grid_9.setWidget(0, 1, donateOrganizationLabel);
		
		Grid grid_40 = new Grid(1, 1);
		donateGrid.setWidget(0, 1, grid_40);
		
		final ListBox donateList = new ListBox();
		grid_40.setWidget(0, 0, donateList);
		donateList.setWidth("249px");
		donateList.setVisibleItemCount(5);
		
		Grid grid_11 = new Grid(1, 1);
		donateGrid.setWidget(1, 0, grid_11);
		
		Label donateAmountLabel = new Label("Amount to donate:");
		grid_11.setWidget(0, 0, donateAmountLabel);
		
		final IntegerBox donateTextbox = new IntegerBox();
		donateGrid.setWidget(1, 1, donateTextbox);
		
		Grid grid_12 = new Grid(1, 2);
		donateGrid.setWidget(2, 0, grid_12);
		grid_12.setWidth("164px");
		
		final Button updateOrgsListButton = new Button("Update List");
		grid_12.setWidget(0, 1, updateOrgsListButton);
		
		final Button donateButton = new Button("Donate");
		donateButton.setEnabled(false);
		donateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(donateList.getSelectedIndex() > -1){
					orgSelected = donateList.getValue(donateList.getSelectedIndex());
				}
			}
		});
		grid_12.setWidget(0, 0, donateButton);
		

		
		final Label donateErrorLabel = new Label("");
		donateGrid.setWidget(2, 1, donateErrorLabel);
		donateGrid.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_TOP);
		
		final Grid publicationsGrid = new Grid(1, 1);
		decoratedTabPanel.add(publicationsGrid, "Publications", false);
		publicationsGrid.setSize("719px", "408px");
		
		TabPanel publicationMainTab = new TabPanel();
		publicationsGrid.setWidget(0, 0, publicationMainTab);
		publicationMainTab.setSize("710px", "403px");
		
		Grid chooseAgentGrid = new Grid(2, 2);
		publicationMainTab.add(chooseAgentGrid, "Agent", false);
		chooseAgentGrid.setSize("569px", "254px");
		
		Label chooseAgentLabel = new Label("Select an Agent");
		chooseAgentGrid.setWidget(0, 0, chooseAgentLabel);
		
		Grid grid_14 = new Grid(1, 1);
		chooseAgentGrid.setWidget(0, 1, grid_14);
		
		final ListBox publicationAgentList = new ListBox();
		grid_14.setWidget(0, 0, publicationAgentList);
		publicationAgentList.setSize("285px", "188px");
		publicationAgentList.setVisibleItemCount(5);
		
		Grid grid_13 = new Grid(1, 1);
		chooseAgentGrid.setWidget(1, 0, grid_13);
		
		Grid grid_17 = new Grid(1, 3);
		grid_13.setWidget(0, 0, grid_17);
		
		final Button publicationUpdateAgentListButton = new Button("Update List");
		grid_17.setWidget(0, 1, publicationUpdateAgentListButton);
		
		final Label publicationAgentError = new Label("");
		publicationAgentError.setStyleName("serverResponseLabelError");
		chooseAgentGrid.setWidget(1, 1, publicationAgentError);
		chooseAgentGrid.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_BOTTOM);
		chooseAgentGrid.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT);
		
		final AbsolutePanel choosePublicationGrid = new AbsolutePanel();
		publicationMainTab.add(choosePublicationGrid, "Publications", false);
		choosePublicationGrid.setSize("696px", "346px");
		
		Grid grid_10 = new Grid(1, 3);
		choosePublicationGrid.add(grid_10, 44, 287);
		grid_10.setSize("109px", "36px");
		
		final CellTable<ApDto> publicationPublicationsCell = new CellTable<ApDto>(){};
		publicationPublicationsCell.setPageSize(5);
		choosePublicationGrid.add(publicationPublicationsCell, 23, 10);
		publicationPublicationsCell.setSize("587px", "215px");
		// Add a number column to show the id.
	      Column<ApDto, Number> publicationIdColum = 
	      new Column<ApDto, Number>(new NumberCell()) {
	         @Override
	         public Integer getValue(ApDto object) {
	            return object.getId();
	         }
	      };
	      publicationIdColum.setSortable(true);
	      publicationPublicationsCell.addColumn(publicationIdColum, "ID");
	      publicationPublicationsCell.setColumnWidth(publicationIdColum, "37px");
	   // Add a text column to show the publication string.
	      TextColumn<ApDto> publicationTextColum = 
	      new TextColumn<ApDto>() {
	         @Override
	         public String getValue(ApDto object) {
	            return object.getId()+" | "+object.getPositive()+" | "+object.getNegative()+" | "+object.getSubnet();
	         }
	      };
	      publicationPublicationsCell.addColumn(publicationTextColum, "Publication");
	      // Add a selection model to handle user selection.
	      final SingleSelectionModel<ApDto> selectionModel 
	      = new SingleSelectionModel<ApDto>();
	      publicationPublicationsCell.setSelectionModel(selectionModel);
	      selectionModel.addSelectionChangeHandler(
	      new SelectionChangeEvent.Handler() {
	         public void onSelectionChange(SelectionChangeEvent event) {
	        	 ApDto selected = selectionModel.getSelectedObject();
	             publicationId = selected.getId();
	            }
	         }
	      );
	      
	    final ListDataProvider<ApDto> dataProvider = new ListDataProvider<ApDto>();
	    dataProvider.addDataDisplay(publicationPublicationsCell);
	      
		final Button publicationPublicationsUpdateButton = new Button("Update");
		grid_10.setWidget(0, 1, publicationPublicationsUpdateButton);
		
		final Label publicationPublicationSelectedLabel = new Label("");
		choosePublicationGrid.add(publicationPublicationSelectedLabel, 251, 305);
		
		final Button publicationSelectPublicationButton = new Button("Select");

		grid_10.setWidget(0, 0, publicationSelectPublicationButton);

		
		Button publicationPublicationsClearButton = new Button("Clear");

		grid_10.setWidget(0, 2, publicationPublicationsClearButton);
		
		
		final Label publicationPublicationsOfLabel = new Label("No user selected");
		choosePublicationGrid.add(publicationPublicationsOfLabel, 239, 241);
		
		final Label publicationPublicationsErrorLabel = new Label("");
		publicationPublicationsErrorLabel.setStyleName("serverResponseLabelError");
		choosePublicationGrid.add(publicationPublicationsErrorLabel, 239, 305);
		
		final SimplePager publicationPublicationsPagger = new SimplePager();
		publicationPublicationsPagger.setDisplay(publicationPublicationsCell);
		choosePublicationGrid.add(publicationPublicationsPagger, 44, 241);
		
		final Button publicationAgentSelectButton = new Button("Select");
		publicationAgentSelectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(publicationAgentList.getSelectedIndex()>-1){
					publicationAgentSelectButton.setEnabled(false);
					publicationUpdateAgentListButton.setEnabled(false);
					selected=publicationAgentList.getValue(publicationAgentList.getSelectedIndex());
					publicationPublicationsOfLabel.setText(PUBLICATIONS_INFO+selected);
					choosePublicationGrid.setVisible(true);
				}
			}
		});
		publicationAgentSelectButton.setText("Select");
		grid_17.setWidget(0, 0, publicationAgentSelectButton);
		
		Button publicationAgentClearButton = new Button("Clear");

		grid_17.setWidget(0, 2, publicationAgentClearButton);
		
		
		final AbsolutePanel publicationViewTab = new AbsolutePanel();
		publicationMainTab.add(publicationViewTab, "View", false);
		publicationViewTab.setSize("702px", "367px");
		
		publicationSelectPublicationButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(publicationId>=0){
					publicationSelectPublicationButton.setEnabled(false);
					//publicationPublicationsUpdateButton.setEnabled(false);
					publicationPublicationSelectedLabel.setText(PUBLICATIONS_PUBID_SELECTED+publicationId);
				}
			}
		});
		
		Grid grid_18 = new Grid(6, 2);
		publicationViewTab.add(grid_18, 10, 10);
		grid_18.setSize("295px", "204px");
		
		Label publicationViewTypeInfoLabel = new Label("Type");
		grid_18.setWidget(0, 0, publicationViewTypeInfoLabel);
		
		final Label publicationViewTypeLabel = new Label("empty");
		grid_18.setWidget(0, 1, publicationViewTypeLabel);
		
		Label publicationViewAuthorInfoLabel = new Label("Author");
		grid_18.setWidget(1, 0, publicationViewAuthorInfoLabel);
		
		final Label publicationViewAuthorLabel = new Label("empty");
		grid_18.setWidget(1, 1, publicationViewAuthorLabel);
		
		Label publicationViewLabelInfoLabel = new Label("Label");
		grid_18.setWidget(2, 0, publicationViewLabelInfoLabel);
		
		final Label publicationViewLabelLabel = new Label("empty");
		grid_18.setWidget(2, 1, publicationViewLabelLabel);
		
		Label publicationViewVotesInfoLabel = new Label("Votes");
		grid_18.setWidget(3, 0, publicationViewVotesInfoLabel);
		
		final Label publicationViewVotesLabel = new Label("empty");
		grid_18.setWidget(3, 1, publicationViewVotesLabel);
		
		final Label publicationViewPriceInfoLabel = new Label("Price");
		grid_18.setWidget(4, 0, publicationViewPriceInfoLabel);
		
		final Label publicationViewPriceLabel = new Label("NA");
		grid_18.setWidget(4, 1, publicationViewPriceLabel);
		
		Label publicationViewContentInfoLabel = new Label("Content");
		grid_18.setWidget(5, 0, publicationViewContentInfoLabel);
		
		final Label publicationViewContentLabel = new Label("empty");
		grid_18.setWidget(5, 1, publicationViewContentLabel);
		
		final ListBox publicationCommentList = new ListBox();
		publicationViewTab.add(publicationCommentList, 338, 10);
		publicationCommentList.setSize("350px", "180px");
		publicationCommentList.setVisibleItemCount(8);
		
		Label lblCommentText = new Label("Comment Text:");
		publicationViewTab.add(lblCommentText, 345, 196);
		
		final TextArea publicationViewCommenttextBox = new TextArea();
		publicationViewTab.add(publicationViewCommenttextBox, 441, 196);
		publicationViewCommenttextBox.setSize("237px", "76px");
		
		final Button publicationBuyButton = new Button("Buy Content");
		publicationViewTab.add(publicationBuyButton, 10, 327);
		
		Button publicationUpdateButton = new Button("Update Information");
		publicationViewTab.add(publicationUpdateButton, 104, 327);
		
		Button publicationPositiveVoteButton = new Button("Positive Vote");
		publicationViewTab.add(publicationPositiveVoteButton, 236, 327);
		
		Button publicationNegativeVoteButton = new Button("Negative Vote");
		publicationViewTab.add(publicationNegativeVoteButton, 336, 327);
		
		Button publicationCommentButton = new Button("Post Comment");
		publicationViewTab.add(publicationCommentButton, 437, 327);
		
		final Label commentPublicationErrorLabel = new Label("");
		publicationViewTab.add(commentPublicationErrorLabel, 345, 305);
		//commentPublicationErrorLabel.setSize("215px", "0px");
				
		Label headLabel = new Label("Select an option by opening a Tab");
		headLabel.setStyleName("center");
		rootPanel.add(headLabel, 280, 120);
		headLabel.setSize("208px", "18px");

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();

			}
		});
		
		final Label userSessionLabel = new Label(NO_USER_LOGIN);
		userSessionLabel.setStyleName("center");
		rootPanel.add(userSessionLabel, 560, 120);
		
		Grid logoutGrid = new Grid(1, 1);
		decoratedTabPanel.add(logoutGrid, "Logout", false);
		logoutGrid.setSize("5cm", "3cm");
		
		final Button logoutButton = new Button("Logout");
		logoutButton.setEnabled(false);
		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				active=null;
				dialogBox.setText("Session Logout");
				serverResponseLabel.removeStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML("You logged out of SoNet");
				dialogBox.center();
				closeButton.setFocus(true);
				userSessionLabel.setText(NO_USER_LOGIN);
				loginButton.setEnabled(true);
				logoutButton.setEnabled(false);
				donateList.clear();
				donateTextbox.setValue(null);
				friendList.clear();
				friendsOfLabel.setText("");
				noteLabelTextbox.setText("");
				noteTextTextbox.setText("");
				sendRqstList.clear();
				sendRqstErrorLabel.setText("");
				pendingErrorLabel.setText("");
				pendingList.clear();
			}
		});
		logoutButton.setStyleName("sendButton");
		logoutGrid.setWidget(0, 0, logoutButton);
		

		class ClearAgentSelectedHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				//Agents Tab
				publicationAgentSelectButton.setEnabled(true);
				publicationUpdateAgentListButton.setEnabled(true);
				publicationAgentList.clear();
				publicationAgentList.setSelectedIndex(-1);
				selected=null;
				//Publications List Tab
				publicationPublicationsOfLabel.setText(PUBLICATIONS_NOUSER);
				publicationSelectPublicationButton.setEnabled(true);
				publicationPublicationsUpdateButton.setEnabled(true);
				publicationPublicationsCell.setRowData(new ArrayList<ApDto>());
				publicationId=-1;
				publicationPublicationSelectedLabel.setText("");
				//Publication View Tab
				publicationViewTypeLabel.setText("");
				publicationViewLabelLabel.setText("");
				publicationViewAuthorLabel.setText("");
				publicationViewVotesLabel.setText("");
				publicationViewPriceLabel.setText("");
				publicationViewContentLabel.setText("");
				publicationCommentList.clear();

			}
				
		}

		class ClearPublicationSelectedHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				//Publications List Tab
				publicationSelectPublicationButton.setEnabled(true);
				publicationPublicationsUpdateButton.setEnabled(true);
				publicationPublicationsCell.setRowData(new ArrayList<ApDto>());
				publicationId=-1;
				publicationPublicationSelectedLabel.setText("");
				//Publication View Tab
				publicationViewTypeLabel.setText("");
				publicationViewLabelLabel.setText("");
				publicationViewAuthorLabel.setText("");
				publicationViewVotesLabel.setText("");
				publicationViewPriceLabel.setText("");
				publicationViewContentLabel.setText("");
				publicationCommentList.clear();
			}
				
		}
		
		class ClearLoginTabHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				loginPublicationsList.clear();
				loginPublicationsList.setVisible(false);
				loginLatestInfoLabel.setVisible(false);
			}
				
		}


		//handler para listagem da Sonet
		
		class ListSonetHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				requestSonetList();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					requestSonetList();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void requestSonetList() {
				// Then, we send the input to the server.
				sonetServlet.displaySonet(new AsyncCallback<ListingDto>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(ListingDto result) {
						dialogBox.setText("Remote Procedure Call");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(result.getlisting().toString());
						dialogBox.center();
						closeButton.setFocus(true);
					}
			
				});
				
			}
		}
				
		// Create a handler for the sendButton and nameField
		class AgentLoginHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				login();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					login();
				}
			}

			/**
			 * login with as an Active Agent.
			 */
			private void login() {
				// First, we validate the input.
				loginErrorLabel.setText("");
				final String textToServer = nameField.getText();
				String passToServer = pswrdtxtbxPassword.getText();
				if (!FieldVerifier.isValidUsername(textToServer)) {
					loginErrorLabel.setText("Please type in your username");
					return;
				}
				if (!FieldVerifier.isValidPassword(passToServer)) {
					loginErrorLabel.setText("Please enter your password");
					return;
				}

				// Then, we send the input to the server.
				serverResponseLabel.setText("");
				sonetServlet.agentLogin(textToServer,passToServer,
						new AsyncCallback<Boolean>() {
							public void onFailure(Throwable caught) {
								active=null;
								// Show the RPC error message to the user
								dialogBox.setText("Login Failure");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								if(caught instanceof AgentUsernameDoesNotExistsException)
									serverResponseLabel.setHTML(LOGIN_FAIL);
								else 
									serverResponseLabel.setHTML(LOGIN_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(Boolean result) {
								if(result){
								active = textToServer;
								userSessionLabel.setText(ACTIVE_USER+active);
								dialogBox.setText("Successful Login");
								serverResponseLabel.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(LOGIN_OK+textToServer);
								dialogBox.center();
								closeButton.setFocus(true);
								loginButton.setEnabled(false);
								logoutButton.setEnabled(true);
								nameField.setText(null);
								pswrdtxtbxPassword.setText(null);
								return;
								}
								active=null;
								// Show the RPC error message to the user
								dialogBox.setText("Login Failure");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(LOGIN_FAIL);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}
		
		// Create a handler for the commentPublicationButton
		class CommentPublicationHandler implements ClickHandler, KeyUpHandler{
			/**
			 * Fired when the user clicks on the commentButton.
			 */
			public void onClick(ClickEvent event){
				commentPublication();
			}
			
			/**
			 * Fired when the user types in the field.
			 */
			public void onKeyUp(KeyUpEvent keyup){
				if(keyup.getNativeKeyCode() == KeyCodes.KEY_ENTER)
					commentPublication();
			}
			private void commentPublication(){
				commentPublicationErrorLabel.setText("");
				String text = publicationViewCommenttextBox.getText();
				if(!FieldVerifier.isValidText(text)){
					commentPublicationErrorLabel.setText("Can't make an empty comment.");
					return;
				}
				if (active == null) {
					dialogBox.setText("Comment Publication Failure");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("No user logged in. Please login first.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;
				}
				if (selected==null) {
					dialogBox.setText("Comment Publication Failure");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("No Agent selected. Please select an Agent in the Agents tab and try again.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;
				}
				if (publicationId<=-1) {
					dialogBox.setText("Comment Publication Failure");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("No Publication selected. Please select an Publication in the Publications tab and try again.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;
				}
				publicationViewCommenttextBox.setText(null);
				sonetServlet.commentAp(active, publicationId, text,
						new AsyncCallback<Void>(){
					public void onFailure(Throwable caught){
						dialogBox.setText("Comment Publication Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						dialogBox.center();
						closeButton.setFocus(true);
							serverResponseLabel.setHTML(COMMENT_ERROR);

					}
					
					public void onSuccess(Void v){
						dialogBox.setText("Comment Publication");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(COMMENT_OK);
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
			}
		}
		
		// Create a handler for the noteButton
		class AddNoteHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				addNote();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addNote();
				}
			}
			/**
			 * login with as an Active Agent.
			 */
			private void addNote() {
				// First, we validate the input.
				noteErrorLabel.setText("");
				String label = noteLabelTextbox.getText();
				String text = noteTextTextbox.getText();
				if (!FieldVerifier.isValidText(label)) {
					noteErrorLabel.setText("Note's label is a required field.");
					return;
				}
				if (!FieldVerifier.isValidText(text)) {
					noteErrorLabel.setText("Note's text is a required field.");
					return;
				}
				if (active==null) {
					dialogBox.setText("Add Note Failure");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("No user logged in. Please login first.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;
				}

				// Then, we send the input to the server.
				noteLabelTextbox.setText(null);
				noteTextTextbox.setText(null);
				sonetServlet.addNote(active, label, text,
						new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								// Show the the error to the user
								dialogBox.setText("Login Failure");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(NOTE_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(Void v) {
								dialogBox.setText("A new Text Note was added!");
								serverResponseLabel.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(NOTE_OK);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
			
			
		}
		
		
		// Create a handler for the AgentList Update button
		class PublicationAgentListHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				getAgents();
			}
	
			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					getAgents();
				}
			}
			/**
			 * login with as an Active Agent.
			 */
			private void getAgents() {
				// First, we validate the input.
				noteErrorLabel.setText("");
				publicationAgentList.clear();
				if (active==null) {
					dialogBox.setText("List Publications Failure");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML("No user logged in. Please login first.");
					dialogBox.center();
					closeButton.setFocus(true);
					return;
				}
	
//				// Then, we send the input to the server.
//				sonetServlet.getAgents(new AsyncCallback<StringListDto>() {
//							public void onFailure(Throwable caught) {
//								// Show the the error to the user
//								dialogBox.setText("Login Failure");
//								serverResponseLabel.addStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(AGENTS_ERROR);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//	
//							public void onSuccess(StringListDto dto) {
//								if(dto.getlisting().isEmpty()){
//									publicationAgentError.setText(NO_AGENTS);
//									return;
//								}
//								for(String s : dto.getlisting()){									
//									publicationAgentList.addItem(s);
//								}
//							}
//						});
			}
		}
		
			
			// Create a handler for the updateCommentsList
			class CommentsHandler implements ClickHandler, KeyUpHandler {
				/**
				 * Fired when the user clicks on the sendButton.
				 */
				public void onClick(ClickEvent event) {
					getComments();
				}

				/**
				 * Fired when the user types in the nameField.
				 */
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						getComments();
					}
				}
				/**
				 * login with as an Active Agent.
				 */
				private void getComments() {
					publicationCommentList.clear();
					// First, we validate the input.
					if (active==null) {
						dialogBox.setText("View Publication Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No user logged in. Please login first.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					if (selected==null) {
						dialogBox.setText("View Publication Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No Agent selected. Please select an Agent in the Agents tab and try again.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					if (publicationId<=-1) {
						dialogBox.setText("View Publication Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No Publication selected. Please select an Publication in the Publications tab and try again.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					// Then, we send the input to the server.
					sonetServlet.getApComments(publicationId,
							new AsyncCallback<StringListDto>() {
								public void onFailure(Throwable caught) {
									// Show the the error to the user
									
									serverResponseLabel.addStyleName("serverResponseLabelError");
									if(caught instanceof ApIdDoesNotExistsException){
										serverResponseLabel.setHTML(COMMENTS_ID_ERROR+publicationId);
									}
									else serverResponseLabel.setHTML(COMMENTS_ERROR);
									dialogBox.center();
									closeButton.setFocus(true);
								}

								public void onSuccess(StringListDto dto) {
									publicationPublicationsOfLabel.setText(PUBLICATIONS_INFO+selected);
									if(dto.getlisting().isEmpty()){
										publicationCommentList.addItem("Publication has no comments. Why not adding one?");
										return;
									}
									for(String s : dto.getlisting()){
										publicationCommentList.addItem(s);
									}
								}
							});
				}
			}
			
		
			// Create a handler for the positiveVoteButton
			class PositiveVoteHandler implements ClickHandler, KeyUpHandler {
				/**
				 * Fired when the user clicks on the sendButton.
				 */
				public void onClick(ClickEvent event) {
					positiveVote();
				}

				/**
				 * Fired when the user types in the nameField.
				 */
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						positiveVote();
					}
				}
				/**
				 * Post a positive vote
				 */
				private void positiveVote() {
					// First, we validate the input.
					if (active==null) {
						dialogBox.setText("Positive Vote Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No user logged in. Please login first.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					if (selected==null) {
						dialogBox.setText("Positive Vote Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No Agent selected. Please select an Agent in the Agents tab and try again.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					if (publicationId<=-1) {
						dialogBox.setText("Positive Vote Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No Publication selected. Please select an Publication in the Publications tab and try again.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					// Then, we send the input to the server.
					sonetServlet.positiveVote(active, publicationId,
							new AsyncCallback<Void>() {
								public void onFailure(Throwable caught) {
									// Show the the error to the user
									dialogBox.setText("Positive Vote Failure");
									serverResponseLabel.addStyleName("serverResponseLabelError");
									dialogBox.center();
									closeButton.setFocus(true);
									if(caught instanceof AlreadyVotedException){
										serverResponseLabel.setHTML(ALREADY_VOTED);
										return;
									}

									else serverResponseLabel.setHTML(POSITIVE_VOTE_ERROR);
									
								}

								public void onSuccess(Void v) {
									dialogBox.setText("Positive Vote");
									serverResponseLabel.removeStyleName("serverResponseLabelError");
									serverResponseLabel.setHTML(POSITIVE_VOTE_SUCCESS);
									dialogBox.center();
									closeButton.setFocus(true);
								}
							});
				}
			}
			
			// Create a handler for the positiveVoteButton
			class NegativeVoteHandler implements ClickHandler, KeyUpHandler {
				/**
				 * Fired when the user clicks on the sendButton.
				 */
				public void onClick(ClickEvent event) {
					negativeVote();
				}

				/**
				 * Fired when the user types in the nameField.
				 */
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						negativeVote();
					}
				}
				/**
				 * Post a positive vote
				 */
				private void negativeVote() {
					// First, we validate the input.
					if (active==null) {
						dialogBox.setText("Negative Vote Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No user logged in. Please login first.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					if (selected==null) {
						dialogBox.setText("Negative Vote Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No Agent selected. Please select an Agent in the Agents tab and try again.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					if (publicationId<=-1) {
						dialogBox.setText("Negative Vote Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No Publication selected. Please select an Publication in the Publications tab and try again.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					// Then, we send the input to the server.
					sonetServlet.negativeVote(active, publicationId,
							new AsyncCallback<Void>() {
								public void onFailure(Throwable caught) {
									// Show the the error to the user
									dialogBox.setText("Negative Vote Failure");
									serverResponseLabel.addStyleName("serverResponseLabelError");
									dialogBox.center();
									closeButton.setFocus(true);
									if(caught instanceof AlreadyVotedException){
										serverResponseLabel.setHTML(ALREADY_VOTED);
										return;
									}
									else serverResponseLabel.setHTML(NEGATIVE_VOTE_ERROR);
									
								}

								public void onSuccess(Void v) {
									dialogBox.setText("Negative Vote");
									serverResponseLabel.removeStyleName("serverResponseLabelError");
									serverResponseLabel.setHTML(NEGATIVE_VOTE_SUCCESS);
									dialogBox.center();
									closeButton.setFocus(true);
								}
							});
				}
			}

			// Create a handler for the updatePublicationButton
			class PublicationViewHandler implements ClickHandler, KeyUpHandler {
				/**
				 * Fired when the user clicks on the sendButton.
				 */
				public void onClick(ClickEvent event) {
					viewPublication();
				}

				/**
				 * Fired when the user types in the nameField.
				 */
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						viewPublication();
					}
				}
				/**
				 * View a Publication.
				 */
				private void viewPublication() {
					
					// First, we validate the input.
					if (active==null) {
						dialogBox.setText("View Publications Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No user logged in. Please login first.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					if (selected==null) {
						dialogBox.setText("View Publications Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No Agent selected. Please select an Agent in the Agents tab and try again.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
					if (publicationId<=-1) {
						dialogBox.setText("View Publication Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No Publication selected. Please select an Publication in the Publications tab and try again.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}		

					// Then, we send the input to the server.
					sonetServlet.viewAp(publicationId, new AsyncCallback<ApDto>()  {
								public void onFailure(Throwable caught) {
									// Show the the error to the user
									
									dialogBox.setText("List Publications of "+selected);
									serverResponseLabel.addStyleName("serverResponseLabelError");
									
									if(caught instanceof ApIdDoesNotExistsException){
										serverResponseLabel.setHTML(COMMENTS_ID_ERROR + "_publicationId");
										return;
									}
								
								}
								
								public void onSuccess(ApDto dto) {
									publicationViewTypeLabel.setText(""+dto.getId());
									publicationViewLabelLabel.setText(dto.getSubnet());
									int resultado = dto.getPositive()-dto.getNegative();
									
									if (resultado > 0 || resultado == 0)
										publicationViewVotesLabel.setText("+"+resultado);
									else
										publicationViewVotesLabel.setText(""+resultado);
								}
							});
				}
			}
			
			
			
			// Create a handler for the AgentList Update button
			class RequestAgentListHandler implements ClickHandler, KeyUpHandler {
				/**
				 * Fired when the user clicks on the sendButton.
				 */
				public void onClick(ClickEvent event) {
					getAgents();
				}
		
				/**
				 * Fired when the user types in the nameField.
				 */
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						getAgents();
					}
				}
				/**
				 * login with as an Active Agent.
				 */
				private void getAgents() {
					// First, we validate the input.
					sendRqstErrorLabel.setText("");
					sendRqstList.clear();
					if (active==null) {
						dialogBox.setText("Friend Request Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("No user logged in. Please login first.");
						dialogBox.center();
						closeButton.setFocus(true);
						return;
					}
		
					// Then, we send the input to the server.
//					sonetServlet.getAgents(new AsyncCallback<StringListDto>() {
//								public void onFailure(Throwable caught) {
//									// Show the the error to the user
//									dialogBox.setText("Friend Request Failure");
//									serverResponseLabel.addStyleName("serverResponseLabelError");
//									dialogBox.center();
//									closeButton.setFocus(true);
//									serverResponseLabel.setHTML(SEND_RQST_AGENTS_ERROR);
//									
//								}
//		
//								public void onSuccess(StringListDto dto) {
//									if(dto.getlisting().isEmpty()){
//										sendRqstErrorLabel.setText(SEND_RQST_NO_AGENTS);
//										return;
//									}
//									for(String s : dto.getlisting()){									
//										sendRqstList.addItem(s);
//									}
//								}
//							});
				}
			}	

		//handler para listar a SoNet
		ListSonetHandler listHandler = new ListSonetHandler();
		listButton.addClickHandler(listHandler);

		//handler para listar os logins
		AgentLoginHandler loginHandler = new AgentLoginHandler();
		loginButton.addClickHandler(loginHandler);
		
		//handler para adicionar nota textual
		AddNoteHandler noteHandler = new AddNoteHandler();
		noteButton.addClickHandler(noteHandler);
		
		
		//handler para actualizar a lista de agentes
		PublicationAgentListHandler agentListHandler = new PublicationAgentListHandler();
		publicationUpdateAgentListButton.addClickHandler(agentListHandler);
		publicationAgentClearButton.addClickHandler(agentListHandler);
		
		//handler para carregar a lista de comentarios de uma publicacao
		CommentsHandler commentsHandler = new CommentsHandler();
		publicationUpdateButton.addClickHandler(commentsHandler);
		publicationSelectPublicationButton.addClickHandler(commentsHandler);

		
		//handler visualizar uma publicacao
		PublicationViewHandler viewPublication = new PublicationViewHandler();
		publicationUpdateButton.addClickHandler(viewPublication);
		publicationSelectPublicationButton.addClickHandler(viewPublication);
		
		//handler para votar positivo numa publicacao
		PositiveVoteHandler positiveVoteHandler = new PositiveVoteHandler();
		publicationPositiveVoteButton.addClickHandler(positiveVoteHandler);
		publicationPositiveVoteButton.addClickHandler(viewPublication);
		
		//handler para votar negativo numa publicacao
		NegativeVoteHandler negativeVoteHandler = new NegativeVoteHandler();
		publicationNegativeVoteButton.addClickHandler(negativeVoteHandler);
		publicationNegativeVoteButton.addClickHandler(viewPublication);
		
		//handler para comentar uma publicacao
		CommentPublicationHandler addCommentHandler = new CommentPublicationHandler();
		publicationCommentButton.addClickHandler(addCommentHandler);
		publicationCommentButton.addClickHandler(commentsHandler);
		
		
		//handler para apagar seleccao de agent
		ClearAgentSelectedHandler agentClearHandler = new ClearAgentSelectedHandler();
		publicationAgentClearButton.addClickHandler(agentClearHandler);
		logoutButton.addClickHandler(agentClearHandler);
		
		//handler para apagar seleccao de publicacao
		ClearPublicationSelectedHandler publicationClearHandler = new ClearPublicationSelectedHandler();
		publicationPublicationsClearButton.addClickHandler(publicationClearHandler);
		
		//handler para actualizar a lista de agents para enviar pedido de ligacao
		RequestAgentListHandler sendRequestListHandler = new RequestAgentListHandler();
		sendRqstRefreshButton.addClickHandler(sendRequestListHandler);
		
		//handler para apagar a listagem de ultimas publicacoes na janela de login
		ClearLoginTabHandler clearLoginTabHandler = new ClearLoginTabHandler();
		logoutButton.addClickHandler(clearLoginTabHandler);
		
		
	}
}

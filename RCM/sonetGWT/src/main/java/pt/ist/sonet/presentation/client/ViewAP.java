package pt.ist.sonet.presentation.client;


import pt.ist.sonet.service.dto.ApDto;
import pt.ist.sonet.service.dto.StringListDto;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.TextArea;


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
	private static final String AP_LOAD_ERROR = "Failed to load this AP's users. Please, check your connection and try again.";
	private static final String NO_AGENTS = "There are no users near this AP.";
	private static final String LIST_LBL = "Users connected to AP-";
	private static final String INFO = "Pick an AP on the map below and see what users are in that area.";
	private static final String CHAT_LOAD_ERROR = "Failed to load this AP's chat. Please, check your connection and try again.";
	private static final String NO_COMMENTS = "No messages on this AP's chat. Start a conversation by sending a message!";
	private static final String VOTE_FAIL = "Failed to cast your vote. Try again...";
	private static final String RATING_FAIL = "Failed to load the AP's rating. Try again...";
	private static final String EMPTY_MESSAGE = "You message cannot be empty.";

	
	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);

	
	private String user = null; //active user
	private int ap = -1; //user's AP
	private final AbsolutePanel panel;
	final ListBox listBox = new ListBox();
	final Label listLbl;
	final ListBox chatBox;
	final NumberLabel<Integer> numberLabel;
	private Timer t;

	int delay = 3000; //milliseconds
	
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	
	public ViewAP(String user, int ap){
		
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
		
		
		setStyleName("center");
		setSize("100%", "100%");
		
		this.user = user;
		this.ap = ap;
		this.panel = new AbsolutePanel();
		
		final Label title = new Label("View AP");
		title.setStyleName("h1");
		panel.add(title);
		
		Label spaceLBL = new Label(" ");
		panel.add(spaceLBL);
		
		Label info = new Label("Here you can see the users connected to this AP, chat with them and rate this location.");
		panel.add(info);
		
		this.add(this.panel);
		panel.setSize("750px", "543px");
		panel.add(listBox, 10, 83);
		listBox.setSize("445px", "191px");
		listBox.setVisibleItemCount(10);
		
		listLbl = new Label(LIST_LBL);
		listLbl.setStyleName("h3");
		panel.add(listLbl, 10, 59);
		
		Label lblApChatRoom = new Label("AP Chat Room");
		lblApChatRoom.setStyleName("h3");
		panel.add(lblApChatRoom, 10, 298);
		
		Label lblRateAp = new Label("Rate AP");
		lblRateAp.setStyleName("h3");
		panel.add(lblRateAp, 477, 83);
		
		Label lblApRating = new Label("AP rating:");
		panel.add(lblApRating, 477, 120);
		
		numberLabel = new NumberLabel<Integer>();
		panel.add(numberLabel, 541, 120);
		
		chatBox = new ListBox();
		panel.add(chatBox, 10, 325);
		chatBox.setSize("441px", "207px");
		chatBox.setVisibleItemCount(5);
		
		final TextArea textArea = new TextArea();
		panel.add(textArea, 477, 368);
		textArea.setSize("232px", "116px");
		
		Button btnSend = new Button("Send");
		btnSend.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String text = textArea.getValue();
				if(text == null || text.equals("")){
					// Show the the error to the user
					dialogBox.setText("AP's Chat Error:");
					serverResponseLabel.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML(EMPTY_MESSAGE);
					dialogBox.center();
					closeButton.setFocus(true);
					return;
				}
				textArea.setText(null);
				sendMessage(text);
				
			}
		});
		panel.add(btnSend, 662, 490);
		
		Label lblMessage = new Label("Message:");
		panel.add(lblMessage, 477, 344);
		
		Button button = new Button("+1");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {				
				positiveVote();
			}
		});
		panel.add(button, 526, 176);
		
		Button button_1 = new Button("-1");
		button_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				negativeVote();
			}
		});
		panel.add(button_1, 563, 176);
		
		Label lblVote = new Label("Vote:");
		lblVote.setStyleName("h3");
		panel.add(lblVote, 477, 176);
		
		Button btnRefreshUsers = new Button("Refresh Users");
		btnRefreshUsers.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAgents();
			}
		});
		panel.add(btnRefreshUsers, 477, 244);
		
		t = new Timer(){
			@Override
		      public void run() {
		        loadChat();
		        loadRating();
		        loadAgents();
		        this.schedule(delay);
		      }
		};
		t.run();
		
		
	}
	
	void loadAgents(){
		listLbl.setText(LIST_LBL+ap+":");
		listBox.clear();
		sonetServlet.getAgents(ap, new AsyncCallback<StringListDto>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("AP's Users Loading Error:");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(AP_LOAD_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(StringListDto dto) {
						if(dto.getlisting().isEmpty()){
							listBox.addItem(NO_AGENTS);
							return;
						}
						for(String s : dto.getlisting()){									
							listBox.addItem(s);
						}
					}
				});
	}
	
	void loadChat(){
		
		chatBox.clear();
		sonetServlet.getApComments(ap, new AsyncCallback<StringListDto>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("AP's Chat Loading Error:");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(CHAT_LOAD_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(StringListDto dto) {
						if(dto.getlisting().isEmpty()){
							chatBox.addItem(NO_COMMENTS);
							return;
						}
						for(String s : dto.getlisting()){									
							chatBox.addItem(s);
						}
					}
				});
	}
	
	void positiveVote(){
		
		sonetServlet.positiveVote(user, ap, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("AP's Rating Error:");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(VOTE_FAIL);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(Void v) {
						loadRating();
					}
				});
	}
	
	void negativeVote(){
		
		sonetServlet.negativeVote(user, ap, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("AP's Rating Error:");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(VOTE_FAIL);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(Void v) {
						loadRating();
					}
				});
	}
	
	void loadRating(){
		sonetServlet.viewAp(ap, new AsyncCallback<ApDto>() {
			public void onFailure(Throwable caught) {
				// Show the the error to the user
				dialogBox.setText("AP's Rating Error:");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(RATING_FAIL);
				dialogBox.center();
				closeButton.setFocus(true);
			}

			public void onSuccess(ApDto dto) {
				int res=dto.getPositive()-dto.getNegative();
				numberLabel.setValue(res);
				
			}
		});
	}
	
	void sendMessage(String text){
		sonetServlet.commentAp(user, ap, text, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				// Show the the error to the user
				dialogBox.setText("AP's Users Loading Error:");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(AP_LOAD_ERROR);
				dialogBox.center();
				closeButton.setFocus(true);
			}

			public void onSuccess(Void v) {
				loadChat();
				
			}
		});
	}
}

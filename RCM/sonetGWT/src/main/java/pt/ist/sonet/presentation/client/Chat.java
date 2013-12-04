package pt.ist.sonet.presentation.client;

import java.awt.event.ActionListener;

import pt.ist.sonet.service.dto.StringListDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Chat extends DecoratorPanel {	
	
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private static final String NO_AGENTS = "There are no users in the network.";
	private static final String INFO = "Choose the user with who you want to talk.";
	private static final String LIST_LBL = "These are the users in the network:";
	private static final String NO_CONVERSATION = "No past messages.";
	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	private String selected = null;
	private String messageToSend = null;
	final ListBox listBox = new ListBox();
	ListBox conversationWindow = new ListBox();
	final TextArea sendWindow = new TextArea();
	final Button sendButton = new Button("Send");
	final Button selectButton = new Button("Select");
	private String user = null; //active user
	private final AbsolutePanel panel;
	final Label listLbl;
	final Label lblConversation = new Label("Conversation");

	int delay = 3000; //milliseconds
	ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			loadConversation();		
		}
	};
	
	public Chat(String user) {
						
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
		this.panel = new AbsolutePanel();
		
		final Label title = new Label("Private Chat");
		title.setStyleName("h1");
		panel.add(title);
		
		Label spaceLBL = new Label(" ");
		
		panel.add(spaceLBL);
		
		Label info = new Label(INFO);
		panel.add(info, 0, 35);
		info.setSize("251px", "18px");
		
		this.add(this.panel);
		panel.setSize("715px", "376px");

		Grid listGrid = new Grid(3, 1);
		panel.add(listGrid);
		
		listLbl = new Label("No user selected.");
		listGrid.setWidget(1, 0, listLbl);
		
		listGrid.setWidget(2, 0, listBox);
		listBox.setSize("245px", "200px");
		listBox.setVisibleItemCount(10);	
		
		panel.add(selectButton, 194, 304);
		selectButton.setSize("57", "30");
		
		panel.add(sendWindow, 284, 294);
		sendWindow.setSize("314px", "40px");
		
		panel.add(sendButton, 614, 294);
		sendButton.setSize("57px", "40px");
		
		Button uptadeButton = new Button("Update");
		panel.add(uptadeButton, 117, 304);
		
		panel.add(conversationWindow, 284, 82);
		conversationWindow.setSize("387px", "203px");
		conversationWindow.setVisibleItemCount(5);
		
		panel.add(lblConversation, 284, 62);
		lblConversation.setSize("388px", "18px");
		
		loadAllAgents();
		
		//BOTAO UPDATE
		uptadeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAllAgents();
			}
		});
		
		//BOTAO SELECT
		selectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(listBox.getItemText(listBox.getSelectedIndex()) != null){
					selectButton.setEnabled(false);
					selected=listBox.getItemText(listBox.getSelectedIndex());
					lblConversation.setText("Conversation with "+selected);
					lblConversation.setTitle("Conversation with "+selected);
					conversationWindow.clear();
					loadConversation();
					new Timer(delay, taskPerformer).start();
				}
			}
		});
		
		//BOTAO SEND
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!sendWindow.getText().isEmpty()) {
					messageToSend = sendWindow.getText();
					sendWindow.setValue(null);
					sendMessage();
				}
			}
			
		});
	}
	
	void loadAllAgents(){
		listLbl.setText(LIST_LBL);
		listBox.clear();
		sonetServlet.getAllOtherAgents(user, new AsyncCallback<StringListDto>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading Error.");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
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
	
	void loadConversation(){
		//listBox.clear();
		sonetServlet.getConversation(user, selected, new AsyncCallback<StringListDto>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						selectButton.setEnabled(true);

					}

					public void onSuccess(StringListDto dto) {
						if(dto.getlisting().isEmpty()) {
							conversationWindow.addItem(NO_CONVERSATION);
							selectButton.setEnabled(true);
							return;
						}
						for(String s : dto.getlisting()){									
							conversationWindow.addItem(s);
						}
						selectButton.setEnabled(true);
					}
				});
	}
	
	void sendMessage(){
		//listBox.clear();
		sonetServlet.sendPrivateMessage(user, selected, messageToSend, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						selectButton.setEnabled(true);

					}

					public void onSuccess(Void v) {
						conversationWindow.clear();
						loadConversation();
						selectButton.setEnabled(true);
					}
				});
	}
}



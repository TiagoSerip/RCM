package pt.ist.sonet.presentation.client;

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
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class Chat extends DecoratorPanel {	
	
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private static final String NO_AGENTS = "There are no users in the network.";
	private static final String INFO = "Choose the user with who you want to talk.";
	private static final String LIST_LBL = "These are the users in the network:";
	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	private String selected = null;
	final ListBox listBox = new ListBox();
	final TextArea sendWindow = new TextArea();
	final Button sendButton = new Button("Send");
	final Button selectButton = new Button("Select");
	final CellTable<String> cellTable = new CellTable<String>();
	private String user = null; //active user
	private final AbsolutePanel panel;
	final Label listLbl;

	
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
		
		final Label title = new Label("PRIVATE CHAT");
		title.setStyleName("h1");
		panel.add(title);
		
		Label spaceLBL = new Label(" ");
		
		panel.add(spaceLBL);
		
		Label info = new Label(INFO);
		panel.add(info);
		
		this.add(this.panel);
		panel.setSize("100%", "623px");

		Grid listGrid = new Grid(3, 1);
		panel.add(listGrid);
		
		listLbl = new Label("No user selected.");
		listGrid.setWidget(1, 0, listLbl);
		
		listGrid.setWidget(2, 0, listBox);
		listBox.setWidth("400px");
		listBox.setVisibleItemCount(10);	
		
		panel.add(selectButton, 353, 304);
		
		panel.add(sendWindow, 0, 513);
		sendWindow.setSize("314px", "40px");
		
		panel.add(sendButton, 340, 513);
		sendButton.setSize("46px", "40px");
		
		panel.add(cellTable, 0, 348);
		cellTable.setSize("386px", "159px");
		TextColumn<String> conversationWindow = new TextColumn<String>() {
			@Override
			public String getValue(String object) {
				return object;
			}
		};
		conversationWindow.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		cellTable.addColumn(conversationWindow, "Conversation");
		
		selectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(listBox.getSelectedIndex()>-1){
					selectButton.setEnabled(false);
					selected=listBox.getValue(listBox.getSelectedIndex());					
				}
			}
		});
	}
	
	void loadAllAgents(){
		listLbl.setText(LIST_LBL);
		listBox.clear();
		sonetServlet.getAllAgents(new AsyncCallback<StringListDto>() {
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
						cellTable.setTitle("Conversation with ");
						cellTable.setRowData(dto.getlisting());
						selectButton.setEnabled(true);
					}
				});
	}
	
//	void sendMessage(){
//		//listBox.clear();
//		sonetServlet.sendPrivateMessage(user, selected, text, new AsyncCallback<StringListDto>() {
//					public void onFailure(Throwable caught) {
//						// Show the the error to the user
//						dialogBox.setText("Loading error.");
//						serverResponseLabel.addStyleName("serverResponseLabelError");
//						serverResponseLabel.setHTML(SERVER_ERROR);
//						dialogBox.center();
//						closeButton.setFocus(true);
//						selectButton.setEnabled(true);
//
//					}
//
//					public void onSuccess(StringListDto dto) {
//						cellTable.setTitle("Conversation with ");
//						cellTable.setRowData(dto.getlisting());
//						selectButton.setEnabled(true);
//					}
//				});
//	}
		
}



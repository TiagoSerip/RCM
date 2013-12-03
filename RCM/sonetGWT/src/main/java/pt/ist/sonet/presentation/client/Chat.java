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
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
		
		final Button btnSelect = new Button("Select");
		panel.add(btnSelect, 353, 304);
		
		TextArea textArea_1 = new TextArea();
		panel.add(textArea_1, 0, 513);
		textArea_1.setSize("314px", "40px");
		
		Button btnSend = new Button("Send");
		panel.add(btnSend, 340, 513);
		btnSend.setSize("46px", "40px");
		
		CellTable<Object> cellTable = new CellTable<Object>();
		panel.add(cellTable, 0, 348);
		cellTable.setSize("386px", "159px");
		
		TextColumn<Object> textColumn = new TextColumn<Object>() {
			@Override
			public String getValue(Object object) {
				return object.toString();
			}
		};
		textColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		cellTable.addColumn(textColumn, "Conversation");
		
		btnSelect.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(listBox.getSelectedIndex()>-1){
					btnSelect.setEnabled(false);
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
						dialogBox.setText("AP's Users Loading Error:");
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
	
		
}



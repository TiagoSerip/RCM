package pt.ist.sonet.presentation.client;


import pt.ist.sonet.service.dto.StringListDto;

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


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Map extends DecoratorPanel {
		
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
	private static final String LIST_LBL = "These are the users around AP-";
	private static final String INFO = "Pick an AP on the map below and see what users are in that area.";
	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);

	
	private String user = null; //active user
	private int ap = -1; //user's AP
	private final AbsolutePanel panel;
	private Grid mapGrid;
	private Image image_4;
	final ListBox listBox = new ListBox();
	final Label listLbl;
	
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	
	public Map(String user, int ap){
		
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
		
		final Label title = new Label("Tagus Map");
		title.setStyleName("h1");
		panel.add(title);
		
		Label spaceLBL = new Label(" ");
		panel.add(spaceLBL);
		
		Label info = new Label(INFO);
		panel.add(info);
		
		this.add(this.panel);
		panel.setSize("100%", "100%");

		
		mapGrid = new Grid(2, 14);
		mapGrid.setBorderWidth(0);
		panel.add(mapGrid);
		
		Image image = new Image("1_1.png");
		mapGrid.setWidget(0, 0, image);
		
		Image image_1 = new Image("1_2.png");
		mapGrid.setWidget(0, 1, image_1);
		
		Image image_6 = new Image("1_3.png");
		image_6.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAP(1);

			}
		});
		mapGrid.setWidget(0, 2, image_6);
		
		Image image_8 = new Image("1_4.png");
		mapGrid.setWidget(0, 3, image_8);
		
		Image image_10 = new Image("1_5.png");

		mapGrid.setWidget(0, 4, image_10);
		
		Image image_12 = new Image("1_6.png");
		image_12.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAP(3);

			}
		});
		mapGrid.setWidget(0, 5, image_12);
		
		Image image_5 = new Image("1_7.png");
		mapGrid.setWidget(0, 6, image_5);
		
		Image image_15 = new Image("1_8.png");
		mapGrid.setWidget(0, 7, image_15);
		
		Image image_17 = new Image("1_9.png");
		image_17.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAP(5);

			}
		});
		mapGrid.setWidget(0, 8, image_17);
		
		Image image_19 = new Image("1_10.png");
		mapGrid.setWidget(0, 9, image_19);
		
		Image image_21 = new Image("1_12.png");
		mapGrid.setWidget(0, 11, image_21);
		
		Image image_22 = new Image("1_11.png");
		mapGrid.setWidget(0, 10, image_22);
		
		Image image_3 = new Image("2_1.png");
		mapGrid.setWidget(1, 0, image_3);
		
		mapGrid.setCellPadding(0);
		mapGrid.setCellSpacing(0);
		
		image_4 = new Image("2_2.png");
		image_4.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAP(0);

			}
		});
		mapGrid.setWidget(1, 1, image_4);
		
		Image image_7 = new Image("2_3.png");
		mapGrid.setWidget(1, 2, image_7);
		
		Image image_9 = new Image("2_4.png");
		mapGrid.setWidget(1, 3, image_9);
		
		Image image_11 = new Image("2_5.png");
		image_11.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAP(2);

			}
		});
		
		mapGrid.setWidget(1, 4, image_11);
		
		Image image_13 = new Image("2_6.png");
		mapGrid.setWidget(1, 5, image_13);
		
		Image image_14 = new Image("2_7.png");
		image_14.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAP(4);

			}
		});
		mapGrid.setWidget(1, 6, image_14);
		
		Image image_16 = new Image("2_8.png");
		mapGrid.setWidget(1, 7, image_16);
		
		Image image_18 = new Image("2_9.png");
		mapGrid.setWidget(1, 8, image_18);
		
		Image image_20 = new Image("2_10.png");
		image_20.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAP(6);

			}
		});
		mapGrid.setWidget(1, 9, image_20);
		
		Image image_24 = new Image("2_11.png");
		image_24.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadAP(7);
			}
		});
		mapGrid.setWidget(1, 10, image_24);
		
		Image image_23 = new Image("2_12.png");
		mapGrid.setWidget(1, 11, image_23);
		
		Grid listGrid = new Grid(3, 1);
		panel.add(listGrid);
		
		listLbl = new Label("No AP selected.");
		listGrid.setWidget(1, 0, listLbl);
		

		listGrid.setWidget(2, 0, listBox);
		listBox.setWidth("529px");
		listBox.setVisibleItemCount(10);
		
		
	}
	
	void loadAP(int id){
		listLbl.setText(LIST_LBL+id+":");
		listBox.clear();
		sonetServlet.getAgents(id, new AsyncCallback<StringListDto>() {
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
}

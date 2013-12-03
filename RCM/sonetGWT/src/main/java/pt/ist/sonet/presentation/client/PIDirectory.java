package pt.ist.sonet.presentation.client;


import java.util.ArrayList;

import pt.ist.sonet.service.dto.PIDto;
import pt.ist.sonet.service.dto.PIListDto;
import pt.ist.sonet.service.dto.StringListDto;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextArea;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PIDirectory extends DecoratorPanel {
		
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
	private static final String PI_LOAD_ERROR = "Failed to load this PI information. Please, try again...";
	private static final String LOCATION_ERROR = "You must enter a location for the new PI.";
	private static final String AP_ID_ERROR = "The AP that you are using doesn't allow adding new PI's. Sorry...";
	private static final String NAME_ERROR = "You must enter a name for the new PI.";
	private static final String DESC_ERROR = "You must enter a description for the new PI.";

	

	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);

	
	private String user = null; //active user
	private int ap = -1; //user's AP
	private final AbsolutePanel panel;
	final Label listLbl;
	final CellTable<PIDto> pointCell;
	int selected = -1;
	
	
	final Label lblNameData;
	final Label lblLoactionData;
	final Label lblDescriptionData;
	final Label lblRatingData;
	
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	
	public PIDirectory(String user, final int ap){
		
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
		
		final Label title = new Label("Points of Interest Directory");
		title.setStyleName("h1");
		panel.add(title);
		
		Label spaceLBL = new Label(" ");
		panel.add(spaceLBL);
		
		Label info = new Label("Pick a Point of Interest in the box bellow and press 'View' to see more information.");
		panel.add(info);
		
		this.add(this.panel);
		panel.setSize("100%", "568px");
		
		listLbl = new Label("No AP selected.");
		panel.add(listLbl);

		Grid listGrid = new Grid(2, 1);
		panel.add(listGrid);
		
		pointCell = new CellTable<PIDto>(){};
		pointCell.setPageSize(5);
		listGrid.setWidget(0,0,pointCell);
		pointCell.setSize("587px", "215px");
		// Add a number column to show the id.
	      Column<PIDto, Number> pointIdColum = 
	      new Column<PIDto, Number>(new NumberCell()) {
	         @Override
	         public Integer getValue(PIDto object) {
	            return object.getId();
	         }
	      };
	      pointIdColum.setSortable(true);
	      pointCell.addColumn(pointIdColum, "ID");
	      pointCell.setColumnWidth(pointIdColum, "37px");
	   // Add a text column to show the publication string.
	      TextColumn<PIDto> pointTextColum = 
	      new TextColumn<PIDto>() {
	         @Override
	         public String getValue(PIDto object) {
	            return object.getName();
	         }
	      };
	      pointCell.addColumn(pointTextColum, "Name");
	      // Add a selection model to handle user selection.
	      final SingleSelectionModel<PIDto> selectionModel 
	      = new SingleSelectionModel<PIDto>();
	      pointCell.setSelectionModel(selectionModel);
	      selectionModel.addSelectionChangeHandler(
	      new SelectionChangeEvent.Handler() {
	         public void onSelectionChange(SelectionChangeEvent event) {
	        	 PIDto pi = selectionModel.getSelectedObject();
	             selected = pi.getId();
	        	 //publicationId = selected.getId();
	            }
	         }
	      );
	      
	    final ListDataProvider<PIDto> dataProvider = new ListDataProvider<PIDto>();
	    dataProvider.addDataDisplay(pointCell);
		
	    loadPIs();
	    
		final Button btnRefresh = new Button("Refresh");
		btnRefresh.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadPIs();
			}
			}
		);
		
		panel.add(btnRefresh);
		

		Label lblName = new Label("Name:");
		lblName.setStyleName("h3");
		panel.add(lblName, 614, 59);
		
		lblNameData = new Label("Name empty ");
		panel.add(lblNameData, 614, 83);
		
		Label lblLocation = new Label("Location:");
		lblLocation.setStyleName("h3");
		panel.add(lblLocation, 614, 118);
		
		lblLoactionData = new Label("Location empty");
		panel.add(lblLoactionData, 614, 145);
		
		Label lblDescription = new Label("Description:");
		lblDescription.setStyleName("h3");
		panel.add(lblDescription, 614, 176);
		
		lblDescriptionData = new Label("Description empty");
		panel.add(lblDescriptionData, 614, 204);
		
		Button btnPostive = new Button("+1");
		panel.add(btnPostive, 663, 314);
		
		Button btnNegative = new Button("-1");
		panel.add(btnNegative, 700, 314);
		
		Label lblRating = new Label("Rating:");
		lblRating.setStyleName("h3");
		panel.add(lblRating, 614, 246);
		
		lblRatingData = new Label("Rating empty");
		panel.add(lblRatingData, 614, 272);
		
		Label lblVote = new Label("Vote:");
		lblVote.setStyleName("h3");
		panel.add(lblVote, 614, 314);
		
		Button btnViewPi = new Button("View PI");
		btnViewPi.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(selected == -1)
					return;
				loadPI();
			}
		});
		panel.add(btnViewPi, 532, 314);
		
		Label lblAddInfo = new Label("To add a new Point of Intrest to this AP please fill the boxes bellow:");
		lblAddInfo.setStyleName("gwt-DialogBox");
		panel.add(lblAddInfo, 0, 387);
		
		Label lblAddANew = new Label("Add a New Point of Interest");
		lblAddANew.setStyleName("h3");
		panel.add(lblAddANew, 0, 360);
		
		Label lblAddName = new Label("Name:");
		panel.add(lblAddName, 36, 435);
		
		Label lblAddLocation = new Label("Location:");
		panel.add(lblAddLocation, 22, 487);
		
		Label lblAddDescription = new Label("Description:");
		panel.add(lblAddDescription, 287, 435);
		
		final TextBox boxName = new TextBox();
		panel.add(boxName, 79, 429);
		
		final TextBox boxLocation = new TextBox();
		panel.add(boxLocation, 81, 477);
		
		final TextArea boxDescription = new TextArea();
		panel.add(boxDescription, 372, 424);
		boxDescription.setSize("180px", "81px");
		
		Button btnAdd = new Button("Add PI");
		btnAdd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String name = boxName.getValue();
				String location = boxLocation.getValue();
				String description = boxDescription.getValue();
				
				if (ap < 0){
					showError(AP_ID_ERROR);
					return;
				}
				
				if( location.equals("") || location==null){
					showError(LOCATION_ERROR);
					return;

				}
				
				if( name.equals("") || name==null){
					showError(NAME_ERROR);
					return;

				}
				
				if( description.equals("") || description==null){
					showError(DESC_ERROR);
					return;

				}
					
				boxName.setValue(null);	
				boxLocation.setValue(null);	
				boxDescription.setValue(null);	
				
				addPI(ap, name, location, description);
			}
		});
		panel.add(btnAdd, 598, 475);
		
		
	}
	
	void showError(String reason){
		
		// Show the the error to the user
		dialogBox.setText("Add new PI error:");
		serverResponseLabel.addStyleName("serverResponseLabelError");
		serverResponseLabel.setHTML(reason);
		dialogBox.center();
		closeButton.setFocus(true);
		
	}
	
	void loadPIs(){
		listLbl.setText(LIST_LBL+ap+":");
		sonetServlet.getPIsByAp(ap, new AsyncCallback<PIListDto>() {
					public void onFailure(Throwable caught) {
						
						showError(AP_LOAD_ERROR+"\n(AP:"+ap+") | "+caught.getLocalizedMessage());
						caught.printStackTrace();
					}

					public void onSuccess(PIListDto dto) {
						pointCell.setRowData(dto.getlisting());
						selected=-1;
					}
				});
	}
	
	void loadPI(){
		sonetServlet.getPIById(selected, new AsyncCallback<PIDto>() {
					public void onFailure(Throwable caught) {
						showError(PI_LOAD_ERROR);
						caught.printStackTrace();
					}

					public void onSuccess(PIDto dto) {
						lblNameData.setText(dto.getName());
						lblLoactionData.setText(dto.getLocation());
						lblDescriptionData.setText(dto.getDescription());
						//lblRatingData.setText(dto.getPositive() - dto.getNegative());
					}
				});
	}
	
	void addPI(int ap, String name, String location, String description){
		
	}
	
}

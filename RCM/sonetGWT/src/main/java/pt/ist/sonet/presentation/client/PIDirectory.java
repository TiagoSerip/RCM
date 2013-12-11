package pt.ist.sonet.presentation.client;


import java.util.ArrayList;

import pt.ist.sonet.service.dto.PIDto;
import pt.ist.sonet.service.dto.PIListDto;
import pt.ist.sonet.service.dto.StringListDto;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
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
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Hyperlink;


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
	private static final String NO_AGENTS = "There are no users near this AP.";
	private static final String LIST_LBL = "These are the Points of Interest from AP-";
	private static final String INFO = "Pick an AP on the map below and see what users are in that area.";
	private static final String PI_LOAD_ERROR = "Failed to load this PI information. Please, try again...";
	private static final String LOCATION_ERROR = "You must enter a location for the new PI.";
	private static final String AP_ID_ERROR = "The AP that you are using doesn't allow adding new PI's. Sorry...";
	private static final String NAME_ERROR = "You must enter a name for the new PI.";
	private static final String DESC_ERROR = "You must enter a description for the new PI.";
	private static final String ADD_OK = "You have sucessfully created a new PI on this AP.";
	private static final String PI_CREATE_ERROR = "Failed to create a new PI. Try again...";
	private static final String LINK_ERROR = "You must enter a link for the new PI.";
	private static final String VOTE_FAIL = "Failed to cast your vote. Try again...";
	private static final String RATING_FAIL = "Failed to load the PI's rating. Try again...";
	
	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);

	
	private String user = null; //active user
	private int ap = -1; //user's AP
	private final AbsolutePanel panel;
	final Label listLbl;
	final CellTable<PIDto> pointCell;
	int selected = -1;
	
    final Label lblLocationData;
    final Label lblNameData;
    final TextArea lblDescriptionData;
    final Label lblRating;
	
    final SimplePager pointPager = new SimplePager();
    final ListDataProvider<PIDto> dataProvider = new ListDataProvider<PIDto>();
    
    HTML linkLink;
    
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
		
		Label lblInfo = new Label("Choose an option from the tab menu:");
		panel.add(lblInfo, 0, 44);
		
		TabPanel tabPanel = new TabPanel();
		panel.add(tabPanel, 0, 68);
		tabPanel.setSize("761px", "390px");
				
		this.add(this.panel);
		panel.setSize("793px", "568px");
	      // Add a selection model to handle user selection.
	      final SingleSelectionModel<PIDto> selectionModel = new SingleSelectionModel<PIDto>();
	      selectionModel.addSelectionChangeHandler( new SelectionChangeEvent.Handler() {
	         public void onSelectionChange(SelectionChangeEvent event) {
	        	 PIDto pi = selectionModel.getSelectedObject();
	             selected = pi.getId();
	        	 //publicationId = selected.getId();
	            }
	         }
	      );
		
		AbsolutePanel viewPanel = new AbsolutePanel();
		tabPanel.add(viewPanel, "View PI's", false);
		viewPanel.setSize("750px", "350px");
		
		Label info = new Label("Pick a Point of Interest in the box bellow and press 'View PI' to see more information.");
		viewPanel.add(info);
		
		listLbl = new Label("No AP selected.");
		viewPanel.add(listLbl);
		
		Grid listGrid = new Grid(2, 1);
		viewPanel.add(listGrid);
		
		pointCell = new CellTable<PIDto>(){};
		listGrid.setWidget(0,0,pointCell);
		pointCell.setSize("465px", "215px");
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
         pointCell.setSelectionModel(selectionModel);
         
         final Button btnRefresh = new Button("Refresh");
         viewPanel.add(btnRefresh, 0, 301);
         btnRefresh.addClickHandler(new ClickHandler() {
         	public void onClick(ClickEvent event) {
         		loadPIs();
         	}
         	}
         );
         dataProvider.addDataDisplay(pointCell);
         
         SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);         
         pointPager.setDisplay(pointCell);
 		 viewPanel.add(pointPager, 163, 304);
 		 pointCell.setPageSize(5);
 		 pointPager.setPageSize(5);

         
         lblDescriptionData = new TextArea();
         viewPanel.add(lblDescriptionData, 494, 136);
         lblDescriptionData.setSize("220px", "97px");
         
         Button button = new Button("+1");
         viewPanel.add(button, 655, 301);
         button.setSize("31px", "30px");
         button.addClickHandler(new ClickHandler() {
   			public void onClick(ClickEvent event) {
   				positiveVote();
   			}
   		});
         
         Button button_1 = new Button("-1");
         viewPanel.add(button_1, 692, 301);
         button_1.setSize("28px", "30px");
         button_1.addClickHandler(new ClickHandler() {
  			public void onClick(ClickEvent event) {
  				negativeVote();
  			}
  		});
         
         Label label_1 = new Label("Rating:");
         label_1.setStyleName("h3");
         viewPanel.add(label_1, 494, 310);
         label_1.setSize("58px", "21px");
         
         lblRating = new Label("0");
         viewPanel.add(lblRating, 558, 313);
         lblRating.setSize("21px", "18px");
         
         Label label_3 = new Label("Vote:");
         label_3.setStyleName("h3");
         viewPanel.add(label_3, 606, 310);
         label_3.setSize("43px", "21px");
         
         Button btnViewPi = new Button("View PI");
         btnViewPi.addClickHandler(new ClickHandler() {
 			public void onClick(ClickEvent event) {
 				if(selected == -1)
 					return;
 				loadPI();
 			}
 		});
         viewPanel.add(btnViewPi, 410, 304);
         btnViewPi.setSize("61px", "30px");
         
         Label label_9 = new Label("Name:");
         label_9.setStyleName("h3");
         viewPanel.add(label_9, 494, 28);
         label_9.setSize("62px", "21px");
         
         lblNameData = new Label();
         viewPanel.add(lblNameData, 494, 46);
         lblNameData.setSize("143px", "18px");
         
         Label label_11 = new Label("Location:");
         label_11.setStyleName("h3");
         viewPanel.add(label_11, 494, 70);
         label_11.setSize("85px", "21px");
         
         lblLocationData = new Label();
         viewPanel.add(lblLocationData, 494, 90);
         lblLocationData.setSize("182px", "18px");
         
         Label label_13 = new Label("Description:");
         label_13.setStyleName("h3");
         viewPanel.add(label_13, 494, 114);
         label_13.setSize("105px", "21px");
         
         Label lblLink = new Label("Link:");
         lblLink.setStyleName("h3");
         viewPanel.add(lblLink, 494, 243);
         
         linkLink = new HTML();
         viewPanel.add(linkLink, 494, 270);
		
		AbsolutePanel addPanel = new AbsolutePanel();
		tabPanel.add(addPanel, "Add PI", false);
		addPanel.setSize("750px", "350px");
		
		Label lblToAddA = new Label("To add a new Point of Interest to this AP please fill all the boxes bellow:");
		lblToAddA.setStyleName("gwt-DialogBox");
		addPanel.add(lblToAddA, 10, 37);
		lblToAddA.setSize("422px", "18px");
		
		Label label_5 = new Label("Add a New Point of Interest");
		label_5.setStyleName("h3");
		addPanel.add(label_5, 10, 10);
		label_5.setSize("222px", "21px");
		
		Label label_6 = new Label("Name:");
		addPanel.add(label_6, 46, 88);
		label_6.setSize("39px", "18px");
		
		Label label_7 = new Label("Location:");
		addPanel.add(label_7, 32, 127);
		label_7.setSize("53px", "18px");
		
		Label label_8 = new Label("Description:");
		addPanel.add(label_8, 297, 85);
		label_8.setSize("69px", "18px");
		
		final TextBox boxName = new TextBox();
		addPanel.add(boxName, 91, 81);
		boxName.setSize("171px", "34px");
		
		final TextBox boxLocation = new TextBox();
		addPanel.add(boxLocation, 91, 121);
		boxLocation.setSize("171px", "34px");
		
		final TextArea boxDescription = new TextArea();
		addPanel.add(boxDescription, 382, 74);
		boxDescription.setSize("281px", "93px");
		
		Label lblLink_1 = new Label("Link:");
		addPanel.add(lblLink_1, 57, 171);
		
		final TextBox linkBox = new TextBox();
		linkBox.setText("http://");
		addPanel.add(linkBox, 91, 163);
		
		Button btnAdd = new Button("Add PI");
		btnAdd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String name = boxName.getValue();
				String location = boxLocation.getValue();
				String description = boxDescription.getValue();
				String link = linkBox.getValue();
				
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
				
				if( link.equals("") || link==null){
					showError(LINK_ERROR);
					return;

				}
					
				boxName.setValue(null);	
				boxLocation.setValue(null);	
				boxDescription.setValue(null);
				linkBox.setValue(null);
				
				addPI(ap, name, location, description, link);
			}
		});

		addPanel.add(btnAdd, 607, 183);
		btnAdd.setSize("56px", "30px");
		

		
	    loadPIs();
	    viewPanel.setVisible(true);
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
						
						showError(PI_LOAD_ERROR+"\n(AP:"+ap+") | "+caught.getLocalizedMessage());
						caught.printStackTrace();
					}

					public void onSuccess(PIListDto dto) {
						//pointCell.setRowData(dto.getlisting());
						dataProvider.setList(dto.getlisting());
						pointPager.setPageSize(5);
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
						lblLocationData.setText(dto.getLocation());
						lblDescriptionData.setText(dto.getDescription());
						
						String mask1 = "\"<a href=\"";
						String link = dto.getLink();
						String mask2 = "\" target=\"_blank\">";
						String name = link;
						String mask3 = "</a>\"";
						
						linkLink.setHTML(mask1+link+mask2+name+mask3);
						lblRating.setText(""+(dto.getPos() - dto.getNeg()));								
					}
				});
	}
	
	void addPI(int ap, String name, String location, String description, String link){
		
		sonetServlet.createPI(ap, name, location, description, link, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				showError(PI_CREATE_ERROR);
				caught.printStackTrace();
			}

			public void onSuccess(Void v) {
				dialogBox.setText("Added a new PI:");
				serverResponseLabel.removeStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(ADD_OK);
				dialogBox.center();
				closeButton.setFocus(true);
			}
		});
		
	}
	
	void positiveVote(){
		
		sonetServlet.positiveVotePI(user, selected, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("PI's Rating Error:");
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
		
		sonetServlet.negativeVotePI(user, selected, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("PI's Rating Error:");
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
		sonetServlet.getPIById(selected, new AsyncCallback<PIDto>() {
			public void onFailure(Throwable caught) {
				// Show the the error to the user
				dialogBox.setText("PI's Rating Error:");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(RATING_FAIL);
				dialogBox.center();
				closeButton.setFocus(true);
			}

			public void onSuccess(PIDto dto) {
				int res=dto.getPos()-dto.getNeg();
				lblRating.setText(""+res);
				
			}
		});
	}
	
}

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
	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);

	
	private String user = null; //active user
	private int ap = -1; //user's AP
	private final AbsolutePanel panel;
	final Label listLbl;
	final CellTable<PIDto> pointCell;
	final int selected = -1;
	
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	
	public PIDirectory(String user, int ap){
		
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
		panel.setSize("100%", "100%");
		
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
	            return object.getId()+" | "+object.getName();
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
	        	 PIDto selected = selectionModel.getSelectedObject();
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
		
		FileUpload fileUpload = new FileUpload();
		panel.add(fileUpload);
		
	}
	
	void loadPIs(){
		listLbl.setText(LIST_LBL+ap+":");
		//listBox.clear();
		sonetServlet.getPIsByAp(ap, new AsyncCallback<PIListDto>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("PI Directory error:");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(AP_LOAD_ERROR+"\n(AP:"+ap+") | "+caught.getLocalizedMessage());
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();
					}

					public void onSuccess(PIListDto dto) {
						pointCell.setRowData(dto.getlisting());
					}
				});
	}
}

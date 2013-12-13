package pt.ist.sonet.presentation.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Repository extends DecoratorPanel {
		

/**
* The message displayed to the user when the server cannot be reached or
* returns an error.
*/
private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

private static final String HOMEIP = "http://192.168.1.105/rcm";
private static final String TAGUSIP = "http://192.168.102.1/rcm";

private static String INUSE;



/**
* Create a remote service proxy to talk to the server-side Greeting
* service.
*/
	
	private String user = null; //active user
	private int ap = -1; //user's AP
	private final AbsolutePanel panel;
    
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	
	public Repository(String user, final int ap, final String serverIP){
		INUSE = serverIP;
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
		
		final Label title = new Label("File Repository");
		title.setStyleName("h1");
		panel.add(title);
		
		Label lblInfo = new Label("Choose an option from the tab menu:");
		panel.add(lblInfo, 0, 44);
		
		TabPanel tabPanel = new TabPanel();
		panel.add(tabPanel, 0, 68);
		tabPanel.setSize("783px", "492px");
				
		this.add(this.panel);
		panel.setSize("793px", "568px");
		
		AbsolutePanel viewPanel = new AbsolutePanel();
		tabPanel.add(viewPanel, "Upload", false);
		viewPanel.setSize("769px", "447px");
		
		Label info = new Label("Choose a file from your computer to upload to the server's repository.");
		viewPanel.add(info, 10, 37);
                
                Label lblUploadFileTo = new Label("Upload File to the Repository");
                lblUploadFileTo.setStyleName("h3");
                viewPanel.add(lblUploadFileTo, 10, 10);
                lblUploadFileTo.setSize("309px", "21px");
                
                Frame frame_1 = new Frame("/jupload/index.html");
                viewPanel.add(frame_1, 10, 61);
                frame_1.setSize("745px", "372px");
                
	    viewPanel.setVisible(true);
		
		AbsolutePanel addPanel = new AbsolutePanel();
		tabPanel.add(addPanel, "Browse", false);
		addPanel.setSize("769px", "452px");
		
		Label lblToAddA = new Label("Here you can browse and download files from the file repository.");
		lblToAddA.setStyleName("gwt-DialogBox");
		addPanel.add(lblToAddA, 10, 37);
		lblToAddA.setSize("422px", "18px");
		
		Label lblBrowseFileRepository = new Label("Browse File Repository");
		lblBrowseFileRepository.setStyleName("h3");
		addPanel.add(lblBrowseFileRepository, 10, 10);
		lblBrowseFileRepository.setSize("222px", "21px");
		
		final Frame frame = new Frame("http://"+INUSE+"/rcm");
		addPanel.add(frame, 10, 61);
		frame.setSize("745px", "377px");
		
		Button btnRefresh = new Button("Refresh");
		btnRefresh.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setUrl("http://"+INUSE+"/rcm");
			}
		});
		addPanel.add(btnRefresh, 697, 25);
	}
	
	void showError(String reason){
		
		// Show the the error to the user
		dialogBox.setText("Repository Error:");
		serverResponseLabel.addStyleName("serverResponseLabelError");
		serverResponseLabel.setHTML(reason);
		dialogBox.center();
		closeButton.setFocus(true);
		
	}
}

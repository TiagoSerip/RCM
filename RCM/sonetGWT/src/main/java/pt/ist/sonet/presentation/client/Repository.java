package pt.ist.sonet.presentation.client;


import pt.ist.sonet.presentation.server.FileUploadServlet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Frame;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Repository extends DecoratorPanel {
		
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL()
            + "upload";
/**
* The message displayed to the user when the server cannot be reached or
* returns an error.
*/
private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

private static final String HOMEIP = "http://192.168.1.105/rcm";
private static final String TAGUSIP = "http://192.168.103.1/rcm";

private static final String INUSE = HOMEIP;



/**
* Create a remote service proxy to talk to the server-side Greeting
* service.
*/
 //private final FileUploadServlet servlet = GWT.create(FileUploadServlet.class);

	
	private String user = null; //active user
	private int ap = -1; //user's AP
	private final AbsolutePanel panel;
    
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	
	public Repository(String user, final int ap){
		
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
		
		// Create a FormPanel and point it at a service.
        final FormPanel form = new FormPanel();
        form.setAction(UPLOAD_ACTION_URL);

        // Because we're going to add a FileUpload widget, we'll need to set the
        // form to use the POST method, and multipart MIME encoding.
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
		
		final Label title = new Label("File Repository");
		title.setStyleName("h1");
		panel.add(title);
		
		Label lblInfo = new Label("Choose an option from the tab menu:");
		panel.add(lblInfo, 0, 44);
		
		TabPanel tabPanel = new TabPanel();
		panel.add(tabPanel, 0, 68);
		tabPanel.setSize("761px", "390px");
				
		this.add(this.panel);
		panel.setSize("793px", "568px");
		
		AbsolutePanel viewPanel = new AbsolutePanel();
		form.add(viewPanel);
		tabPanel.add(form, "Upload", false);
		viewPanel.setSize("750px", "244px");
		
		Label info = new Label("Choose a file from your computer to upload to the server's repository.");
		viewPanel.add(info, 10, 37);
		
		// Create a TextBox, giving it a name so that it will be submitted.
        final TextBox tb = new TextBox();
        tb.setName("textBoxFormElement");
        viewPanel.add(tb, 79, 85);
        
     // Create a FileUpload widget.
        FileUpload upload = new FileUpload();
        upload.setName("uploadFormElement");
        viewPanel.add(upload, 10, 156);

        // Add a 'submit' button.
        viewPanel.add(new Button("Submit", new ClickHandler() {
                public void onClick(ClickEvent event) {
                        form.submit();
                }
        }), 309, 148);
        
        Label lblFileName = new Label("File Name:");
        viewPanel.add(lblFileName, 10, 91);
        
        Label lblUploadFileTo = new Label("Upload File to the Repository");
        lblUploadFileTo.setStyleName("h3");
        viewPanel.add(lblUploadFileTo, 10, 10);
        lblUploadFileTo.setSize("309px", "21px");

        // Add an event handler to the form.
        form.addSubmitHandler(new FormPanel.SubmitHandler() {
                public void onSubmit(SubmitEvent event) {
                        // This event is fired just before the form is submitted. We can
                        // take this opportunity to perform validation.
                        if (tb.getText().length() == 0) {
                                Window.alert("The text box must not be empty");
                                event.cancel();
                        }
                }
        });

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
                public void onSubmitComplete(SubmitCompleteEvent event) {
                        // When the form submission is successfully completed, this
                        // event is fired. Assuming the service returned a response of type
                        // text/html, we can get the result text here (see the FormPanel
                        // documentation for further explanation).
                        showMessage(event.getResults());
                }
        });
		
		AbsolutePanel addPanel = new AbsolutePanel();
		tabPanel.add(addPanel, "Browse", false);
		addPanel.setSize("750px", "433px");
		
		Label lblToAddA = new Label("Here you can browse and download files from the file repository.");
		lblToAddA.setStyleName("gwt-DialogBox");
		addPanel.add(lblToAddA, 10, 37);
		lblToAddA.setSize("422px", "18px");
		
		Label lblBrowseFileRepository = new Label("Browse File Repository");
		lblBrowseFileRepository.setStyleName("h3");
		addPanel.add(lblBrowseFileRepository, 10, 10);
		lblBrowseFileRepository.setSize("222px", "21px");
		
		final Frame frame = new Frame(INUSE);
		addPanel.add(frame, 10, 77);
		frame.setSize("726px", "342px");
		
		Button btnRefresh = new Button("Refresh");
		btnRefresh.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				frame.setUrl(INUSE);
			}
		});
		addPanel.add(btnRefresh, 678, 37);
		
	    viewPanel.setVisible(true);
	}
	
	void showError(String reason){
		
		// Show the the error to the user
		dialogBox.setText("Repository Error:");
		serverResponseLabel.addStyleName("serverResponseLabelError");
		serverResponseLabel.setHTML(reason);
		dialogBox.center();
		closeButton.setFocus(true);
		
	}
	
	void showMessage(String message){
		
		// Show the the error to the user
		dialogBox.setText("Repository:");
		serverResponseLabel.removeStyleName("serverResponseLabelError");
		serverResponseLabel.setHTML(message);
		dialogBox.center();
		closeButton.setFocus(true);
		
	}
}

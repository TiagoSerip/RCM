package pt.ist.sonet.presentation.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pt.ist.sonet.service.dto.StringListDto;
import com.google.gwt.user.client.Timer;
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
import com.google.gwt.user.client.ui.Image;

public class Game extends DecoratorPanel {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private static final String NO_AGENTS = "There are no users in the network.";
	private static final String INFO = "Choose the user with who you want to play.";
	private static final String LIST_LBL = "These are the users in the network:";
	
	private final SoNetServletAsync sonetServlet = GWT.create(SoNetServlet.class);
	// Create the popup dialog box
	final DialogBox dialogBox = new DialogBox();
	final Button closeButton = new Button("Close");
	final HTML serverResponseLabel = new HTML();
	
	private String selected = null;
	private int[] jogada = {-1,-1};
	private int[] jogada00 = {0,0};
	private int[] jogada01 = {0,1};
	private int[] jogada02 = {0,2};
	private int[] jogada10 = {1,0};
	private int[] jogada11 = {1,1};
	private int[] jogada12 = {1,2};
	private int[] jogada20 = {2,0};
	private int[] jogada21 = {2,1};
	private int[] jogada22 = {2,2};
	private String messageToSend = null;
	private int boardId = -1;
	final ListBox listBox = new ListBox();
	ListBox conversationWindow = new ListBox();		
	Label lblGame = new Label("Game");
	final TextArea sendWindow = new TextArea();
	final Button sendButton = new Button("Send");
	final Button selectButton = new Button("Select");
	private String user = null; //active user
	private final AbsolutePanel panel;
	Button uptadeButton = new Button("Update");
	final Label listLbl;
	Button button11 = new Button(" ");
	Button button12 = new Button(" ");
	Button button13 = new Button(" ");
	Button button21 = new Button(" ");
	Button button22 = new Button(" ");
	Button button23 = new Button(" ");
	Button button31 = new Button(" ");
	Button button32 = new Button(" ");
	Button button33 = new Button(" ");
	Button gameOverButton = new Button("Game Over");
	private String username = null;

	
	//popups do jogo
	final DialogBox popUp = new DialogBox();
	final Button okButton = new Button("OK");
	final HTML gameResponse = new HTML();

	
	public Game(String user) {
		username = user;
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
		
		final Label title = new Label("TIC TAC TOE");
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
		
		panel.add(uptadeButton, 117, 304);
		
		button11.setText("");
		panel.add(button11, 335, 86);
		button11.setSize("81px", "56px");
		
		button21.setText("");
		panel.add(button21, 335, 167);
		button21.setSize("81px", "56px");
		
		button31.setText("");
		panel.add(button31, 335, 241);
		button31.setSize("81px", "56px");
		
		button12.setText("");
		panel.add(button12, 436, 86);
		button12.setSize("81px", "56px");
		
		button22.setText("");
		panel.add(button22, 436, 167);
		button22.setSize("81px", "56px");
		
		button32.setText("");
		panel.add(button32, 436, 241);
		button32.setSize("81px", "56px");
		
		button13.setText("");
		panel.add(button13, 541, 86);
		button13.setSize("81px", "56px");
		
		button23.setText("");
		panel.add(button23, 541, 167);
		button23.setSize("81px", "56px");
		
		button33.setText("");
		panel.add(button33, 541, 241);
		button33.setSize("81px", "56px");
		
		gameOverButton.setText("Game Over");
		panel.add(gameOverButton, 479, 336);
		gameOverButton.setSize("125px", "30px");
		
		panel.add(lblGame, 335, 35);
		lblGame.setSize("287px", "18px");
		
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
					lblGame.setTitle("You're playing against "+selected);
					newBoard();
				}					
			}
		});
		
		gameOverButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				////////////////////////////
			}
		});
		
		button11.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada00;
				play();
				button11.setText(username);
				button11.setEnabled(false);
				////////////////////////////
			}
		});
		
		button12.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada01;
				play();
				button12.setText(username);
				button12.setEnabled(false);
				////////////////////////////
			}
		});
		
		button13.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada02;
				play();
				button13.setText(username);
				button13.setEnabled(false);
				////////////////////////////
			}
		});
		
		button21.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada10;
				play();
				button21.setText(username);
				button21.setEnabled(false);
				////////////////////////////
			}
		});
		
		button22.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada11;
				play();
				button22.setText(username);
				button22.setEnabled(false);
				////////////////////////////
			}
		});
		
		button23.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada12;
				play();
				button23.setText(username);
				button23.setEnabled(false);
				////////////////////////////
			}
		});
		
		button31.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada20;
				play();
				button31.setText(username);
				button31.setEnabled(false);
				////////////////////////////
			}
		});
		
		button32.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada21;
				play();
				button32.setText(username);
				button32.setEnabled(false);
				////////////////////////////
			}
		});
		
		button33.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada22;
				play();
				button33.setText(username);
				button33.setEnabled(false);
				////////////////////////////
			}
		});
	}
	
	void play(){
		sonetServlet.play(boardId, user, jogada, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						

					}

					public void onSuccess(Void v) {
						////////////////////////////

					}
				});
	}
	
	void checkWinner(){
		sonetServlet.checkWinner(boardId, user, new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
												

					}

					public void onSuccess(Boolean b) {
						////////////////////////////

					}
				});
	}
	
	void getWinner(){
		sonetServlet.getWinner(boardId, user, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String b) {
						////////////////////////////

					}
				});
	}
	
	
	
	void newBoard(){
		sonetServlet.createBoard(user, selected, new AsyncCallback<Integer>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						selectButton.setEnabled(true);
						button11.setEnabled(false);
						button12.setEnabled(false);
						button13.setEnabled(false);
						button21.setEnabled(false);
						button22.setEnabled(false);
						button23.setEnabled(false);
						button31.setEnabled(false);
						button32.setEnabled(false);
						button33.setEnabled(false);
						
					}

					public void onSuccess(Integer i) {
						conversationWindow.clear();
						selectButton.setEnabled(false);
						button11.setEnabled(true);
						button12.setEnabled(true);
						button13.setEnabled(true);
						button21.setEnabled(true);
						button22.setEnabled(true);
						button23.setEnabled(true);
						button31.setEnabled(true);
						button32.setEnabled(true);
						button33.setEnabled(true);
						gameOverButton.setEnabled(true);
						boardId = i.intValue();
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
}

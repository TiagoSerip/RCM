package pt.ist.sonet.presentation.client;

import pt.ist.sonet.service.dto.BoardDto;
import pt.ist.sonet.service.dto.BooleanDto;
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
	final ListBox listBox = new ListBox();
	ListBox conversationWindow = new ListBox();		
	Label lblGame = new Label("Game");
	final TextArea sendWindow = new TextArea();
	final Button selectButton = new Button("Select");
	
	private String username = null;
	private String turn = null;
	private String winner = null;
	private boolean hasWinner = false;
	private int boardId = -1;
	private boolean haveBoard = false;
	private boolean boardIsFull = false;
	String[] vector = {null, null, null, null, null, null, null, null, null};

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
	//popups do jogo
	final DialogBox popUp = new DialogBox();
	final Button okButton = new Button("OK");
	final HTML gameResponse = new HTML();
	private Timer t;
	int delay = 500; //milliseconds
	
	
	public Game(String user) {
		
		boardId = -1;
		
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
		
		this.username = user;
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
		
		button11.setText("");
		button12.setText("");
		button13.setText("");
		button21.setText("");
		button22.setText("");
		button23.setText("");
		button31.setText("");
		button32.setText("");
		button33.setText("");
		gameOverButton.setEnabled(false);
		button11.setEnabled(false);
		button12.setEnabled(false);
		button13.setEnabled(false);
		button21.setEnabled(false);
		button22.setEnabled(false);
		button23.setEnabled(false);
		button31.setEnabled(false);
		button32.setEnabled(false);
		button33.setEnabled(false);
		
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
					winner = null;
					hasWinner = false;
					boardId = -1;
					haveBoard = false;
					boardIsFull = false;
					String[] cenas = {null, null, null, null, null, null, null, null, null};
					vector = cenas;
					hasBoard();
					
				}					
			}
		});
		
		gameOverButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.setText("Game Over.");
				serverResponseLabel.removeStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML("The game ended with a draw.");
				dialogBox.center();
				closeButton.setFocus(true);
				removeBoard();				
			}
		});
		
		button11.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada00;
				play();
				button11.setText(username);
				button11.setEnabled(false);
				buttonAction();
			}
		});
		
		button12.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada01;
				play();
				button12.setText(username);
				button12.setEnabled(false);
				buttonAction();			
			}
		});
		
		button13.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada02;
				play();
				button13.setText(username);
				button13.setEnabled(false);
				buttonAction();			
			}
		});
		
		button21.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada10;
				play();
				button21.setText(username);
				button21.setEnabled(false);
				buttonAction();			
			}
		});
		
		button22.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada11;
				play();
				button22.setText(username);
				button22.setEnabled(false);
				buttonAction();			
			}
		});
		
		button23.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada12;
				play();
				button23.setText(username);
				button23.setEnabled(false);
				buttonAction();			
			}
		});
		
		button31.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada20;
				play();
				button31.setText(username);
				button31.setEnabled(false);
				buttonAction();
			}
		});
		
		button32.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada21;
				play();
				button32.setText(username);
				button32.setEnabled(false);
				buttonAction();
			}
		});
		
		button33.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jogada = jogada22;
				play();
				button33.setText(username);
				button33.setEnabled(false);
				buttonAction();
			}
		});
	}
	
	void buttonAction() {
		checkWinner();
		if(hasWinner) {
			getWinner();
			if(winner.equals(username)) {
				dialogBox.setText("Congratulations!");
				serverResponseLabel.removeStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML("Congratulations!! You won!");
				dialogBox.center();
				closeButton.setFocus(true);
			}
			else {
				dialogBox.setText("=(");
				serverResponseLabel.removeStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML("You lost...");
				dialogBox.center();
				closeButton.setFocus(true);
			}
			removeBoard();
		}
		else {
			boardIsFull();
			if(boardIsFull) {
				dialogBox.setText("Draw.");
				serverResponseLabel.removeStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML("The game ended with a draw.");
				dialogBox.center();
				closeButton.setFocus(true);
				removeBoard();
			}
		}
	}
	
	void play(){
		sonetServlet.play(boardId, username, jogada, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.play");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();
					}

					public void onSuccess(Void v) {
					}
				});
	}
	
	//ACABADO
	void checkWinner(){
		sonetServlet.checkWinner(boardId, new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.chkw");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();

					}

					public void onSuccess(Boolean b) {
						hasWinner = b;
					}
				});
	}
	
	void boardIsFull(){
		sonetServlet.boardIsFull(boardId, new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.full");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();

					}

					public void onSuccess(Boolean b) {
						boardIsFull = b;
					}
				});
	}
	
	//ACABADO
	void getWinner(){
		sonetServlet.getWinner(boardId, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.win");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();

					}

					public void onSuccess(String w) {
						winner = w;
					}
				});
	}
	
	//FALTA ACTIVAR E DESACTIVAR BOTOES CONSOANTE O TURNO
	
	//ACABADO
	void getTurn(){
		sonetServlet.getTurn(boardId, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.trn");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();

					}

					public void onSuccess(String t) {
						turn = t;
					}
				});
	}
	
	void getBoard(){
		sonetServlet.getBoard(username, selected, new AsyncCallback<BoardDto>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.get");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();

					}

					public void onSuccess(BoardDto dto) {
						if(dto.getBoardId() > -1) {
							boardId = dto.getBoardId();
							System.out.println(boardId);
							gameOverButton.setEnabled(true);
							updateBoard();
							//startTimer();

						}

					}
				});
	}
	
	void hasBoard(){
		sonetServlet.checkHasBoard(username, selected, new AsyncCallback<BooleanDto>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.has");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();

					}

					public void onSuccess(BooleanDto b) {
							haveBoard = b.getValue().booleanValue();
							if(haveBoard) {
								System.out.println("have true");
								getBoard();
							}
							else {
								System.out.println("have false");

								newBoard();	
							}
					}

					
				});
	}
	
	void removeBoard(){
		sonetServlet.removeBoard(boardId, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.rmv");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();

					}

					public void onSuccess(Void v) {
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
						button11.setText("");
						button12.setText("");
						button13.setText("");
						button21.setText("");
						button22.setText("");
						button23.setText("");
						button31.setText("");
						button32.setText("");
						button33.setText("");
						gameOverButton.setEnabled(false);
					}
				});
	}
	
	
	
	void newBoard(){
		sonetServlet.createBoard(username, selected, new AsyncCallback<Integer>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.new");
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
						button11.setText("");
						button12.setText("");
						button13.setText("");
						button21.setText("");
						button22.setText("");
						button23.setText("");
						button31.setText("");
						button32.setText("");
						button33.setText("");
						gameOverButton.setEnabled(false);
						caught.printStackTrace();

						
					}

					public void onSuccess(Integer i) {
						selectButton.setEnabled(false); ///////////////
						button11.setEnabled(true);
						button12.setEnabled(true);
						button13.setEnabled(true);
						button21.setEnabled(true);
						button22.setEnabled(true);
						button23.setEnabled(true);
						button31.setEnabled(true);
						button32.setEnabled(true);
						button33.setEnabled(true);
						button11.setText("");
						button12.setText("");
						button13.setText("");
						button21.setText("");
						button22.setText("");
						button23.setText("");
						button31.setText("");
						button32.setText("");
						button33.setText("");
						gameOverButton.setEnabled(true);
						boardId = i.intValue();
						
						startTimer();
					}
				});
	}
	
	void updateBoard(){
		sonetServlet.updateBoard(boardId, new AsyncCallback<BoardDto>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading error.upd");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();

					}

					public void onSuccess(BoardDto dto) {
						vector = dto.getVector();

						if(vector[0] != null) {
							button11.setText(vector[0]);
							button11.setEnabled(false);
						}
						if(vector[1] != null) {
							button12.setText(vector[1]);
							button12.setEnabled(false);
						}
						if(vector[2] != null) {
							button13.setText(vector[2]);
							button13.setEnabled(false);
						}
						if(vector[3] != null) {
							button21.setText(vector[3]);
							button21.setEnabled(false);
						}
						if(vector[4] != null) {
							button22.setText(vector[4]);
							button22.setEnabled(false);
						}
						if(vector[5] != null) {
							button23.setText(vector[5]);
							button23.setEnabled(false);
						}
						if(vector[6] != null) {
							button31.setText(vector[6]);
							button31.setEnabled(false);
						}
						if(vector[7] != null) {
							button32.setText(vector[7]);
							button32.setEnabled(false);
						}
						if(vector[8] != null) {
							button33.setText(vector[8]);
							button33.setEnabled(false);
						}
						
					}
				});
	}
	
	
	//ACABADO
	void loadAllAgents(){
		listLbl.setText(LIST_LBL);
		listBox.clear();
		sonetServlet.getAllOtherAgents(username, new AsyncCallback<StringListDto>() {
					public void onFailure(Throwable caught) {
						// Show the the error to the user
						dialogBox.setText("Loading Error.age");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
						caught.printStackTrace();

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
	
	private void startTimer(){
		t = new Timer(){
			@Override
		     public void run() {
		        updateBoard();
		        getTurn();
		        if(turn == username) {
					if(vector[0] == null) {
						button11.setEnabled(true);
					}
					if(vector[1] == null) {
						button12.setEnabled(true);
					}
					if(vector[2] == null) {
						button13.setEnabled(true);
					}
					if(vector[3] == null) {
						button21.setEnabled(true);
					}
					if(vector[4] == null) {
						button22.setEnabled(true);
					}
					if(vector[5] == null) {
						button23.setEnabled(true);
					}
					if(vector[6] == null) {
						button31.setEnabled(true);
					}
					if(vector[7] == null) {
						button32.setEnabled(true);
					}
					if(vector[8] == null) {
						button33.setEnabled(true);
					}
		        }
		        else {
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
		     }
		};
		t.schedule(delay);
	
	}
}

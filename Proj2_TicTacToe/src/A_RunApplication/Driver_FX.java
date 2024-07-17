// yousef sharbi 1202057
// anas karakra 1200467
package A_RunApplication;
	
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


public class Driver_FX extends Application {
	
	//for UI
	Stage stage = new Stage();
	DropShadow shadow = new DropShadow();
	
	Text t_ai=null;
	Text t_human=null;
	Text t_round=null;
	
	Button b_0_0=new Button();
	Button b_0_1=new Button();
	Button b_0_2=new Button();
	
	Button b_1_0=new Button();
	Button b_1_1=new Button();
	Button b_1_2=new Button();
	
	Button b_2_0=new Button();
	Button b_2_1=new Button();
	Button b_2_2=new Button();
	
	Line line;
	
	int countdown_sec=3;
	// for data
	char board[][]= { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };
	String chooseRandom[]= {"Human","AI"};
	Random random = new Random();
	int randomIndex = random.nextInt(chooseRandom.length);
	
	int depth=9;
	
	int ai_score=0;
	int human_score=0;
	int round=0;
	
	int ai_i_trace=-1;
	int ai_j_trace=-1;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Create line
	        line = new Line();
	        line.setStroke(null);
	        line.setStrokeWidth(10);
	        
			// scenes
			Scene scene = new Scene(startPage());
						
			stage.setMaximized(true);
			stage.setScene(scene);
			stage.setTitle("Tic Tac Toe (AI vs Human) Using Minimax");
			stage.setIconified(true);
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/D2_Graphic_icons/app_icon.png")));
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Pages:
	
	// start page
	public BorderPane startPage() {
		BorderPane bp_startPage = new BorderPane();
		
		VBox vb=new VBox();
		// top
		HBox hb_top=new HBox();
		Image image_D3_Tic_Tac_Toe = new Image(getClass().getResource("/D3_Graphic_images/Tic_Tac_Toe.png").toExternalForm()); 
		ImageView imageView_D3_Tic_Tac_Toe = new ImageView(image_D3_Tic_Tac_Toe);
		hb_top.getChildren().add(imageView_D3_Tic_Tac_Toe);
		hb_top.setPadding(new Insets(20,0,0,245)); // top, right, bottom, left
		
		
		// bottom
		HBox hb_bottom=new HBox();
		Button b_start=new Button("Let's Play");
		b_start.setPrefSize(250, 80);
		b_start.setStyle("-fx-background-color: #036c8c; -fx-background-radius: 12; -fx-Border-color: #034e65;"
				+ "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:2;-fx-font-weight:bold;-fx-text-fill: white;");
		
		b_start.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
	          @Override
	          public void handle(MouseEvent e) {
	        	  b_start.setEffect(shadow);
	        	  b_start.setStyle("-fx-background-color: #036c8c;-fx-Border-color: white;-fx-font-size:20;"
	        			  + "-fx-Border-width:4;-fx-font-weight:bold;-fx-text-fill: white;-fx-background-radius: 14;-fx-Border-radius: 12");
	          }
	    });
		
		b_start.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
	          @Override
	          public void handle(MouseEvent e) {
	        	  b_start.setEffect(null);
	        	  b_start.setStyle("-fx-background-color: #036c8c; -fx-background-radius: 12; -fx-Border-color: #034e65;"
	    			   	  + "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:2;-fx-font-weight:bold;-fx-text-fill: white;");
	          }
	    });
		
		b_start.setOnAction(e->{
			GaussianBlur blur = new GaussianBlur(6);
			BorderPane bp_nextscene=mainPage();
			bp_nextscene.setEffect(blur);
			stage.getScene().setRoot(bp_nextscene);
			
			Stage stage_start=new Stage();
			stage_start.setTitle("Random Choose");

			stage_start.initStyle(StageStyle.UTILITY);
			stage_start.initModality(Modality.APPLICATION_MODAL);
			stage_start.initOwner(stage.getScene().getWindow());
			
			stage_start.setX(365); 
	        stage_start.setY(240); 
	        
			stage_start.setOnCloseRequest(event -> {
				Platform.runLater(() -> {
					bp_nextscene.setEffect(null);
					
					if(chooseRandom[randomIndex].equals("AI")) {
						calcMinimax(board, depth, true, true);
						updateAI();
					}
				});
				
	            
	        });
			
			VBox vb_start=new VBox();
			
				HBox hb_l=new HBox();
				Label l_start=new Label("\n"+" The game chooses who will start randomly every round between: (Human and AI)"+"\n\n\n"
										+"                                              "+"The "+chooseRandom[randomIndex]+" Will Start The Game");
				l_start.setStyle("-fx-font-size:20;-fx-font-weight:bold;-fx-text-fill: white;");
				hb_l.getChildren().add(l_start);
			
				
				HBox hb_b=new HBox();
				hb_b.setPadding(new Insets(50,0,0,270)); // top, right, bottom, left
				Button b_done_start=new Button("Done");
				b_done_start.setPrefSize(200, 40);
				b_done_start.setStyle("-fx-background-color: LAWNGREEN; -fx-background-radius: 12; -fx-Border-color: OLIVEDRAB;"
						+ "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:2;-fx-font-weight:bold;");
				
				b_done_start.addEventHandler(MouseEvent.MOUSE_ENTERED,
				        new EventHandler<MouseEvent>() {
			          @Override
			          public void handle(MouseEvent e) {
			        	  b_done_start.setEffect(shadow);
			        	  b_done_start.setStyle("-fx-background-color: LAWNGREEN;-fx-Border-color: black;-fx-font-size:20;"
			        			  + "-fx-Border-width:2;-fx-font-weight:bold;-fx-text-fill: white;-fx-background-radius: 10;-fx-Border-radius: 8");
			          }
			    });
				
				b_done_start.addEventHandler(MouseEvent.MOUSE_EXITED,
				        new EventHandler<MouseEvent>() {
			          @Override
			          public void handle(MouseEvent e) {
			        	  b_done_start.setEffect(null);
			        	  b_done_start.setStyle("-fx-background-color: LAWNGREEN; -fx-background-radius: 12; -fx-Border-color: OLIVEDRAB;"
			    			   	  + "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:2;-fx-font-weight:bold;");
			          }
			    });
				
				b_done_start.setOnAction(e1->{
					Platform.runLater(() -> {
						if(chooseRandom[randomIndex].equals("AI")) {
							calcMinimax(board, depth, true, true);				
							updateAI();
						}
						
						bp_nextscene.setEffect(null);
						stage_start.close();
						
					});
					
				});
				hb_b.getChildren().add(b_done_start);
				
			vb_start.setStyle("-fx-background-color:#034e65;");
			vb_start.getChildren().addAll(hb_l,hb_b);
			vb_start.setSpacing(20);
			
			Scene scene_choose=new Scene(vb_start, 780, 300);
			stage_start.setScene(scene_choose);
			stage_start.show();
		});
		
		hb_bottom.getChildren().add(b_start);
		hb_bottom.setPadding(new Insets(35,0,0,620)); // top, right, bottom, left
		
		vb.getChildren().addAll(hb_top,hb_bottom);
		bp_startPage.setCenter(vb);
		bp_startPage.setStyle("-fx-background-color:#034e65;");
		return bp_startPage;
	}
	
	
	// main page
	public BorderPane mainPage() {
		BorderPane bp_mainPage = new BorderPane();
		
		HBox hb=new HBox();
			// top Right
			VBox vb_right=new VBox();
			vb_right.setMaxSize(300, 230);
			vb_right.setMinSize(300, 230);
				HBox hb_right_top=new HBox();
					ImageView imageView_D2_AI = new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/ai.png").toExternalForm()));
					t_ai=new Text("\n   "+ai_score);
					t_ai.setStyle("-fx-font-size:20;-fx-font-weight:bold;");
				hb_right_top.getChildren().addAll(imageView_D2_AI,t_ai);
				
				HBox hb_right_center=new HBox();
					ImageView imageView_D2_Human = new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/human.png").toExternalForm()));
					t_human=new Text("\n   "+human_score);
					t_human.setStyle("-fx-font-size:20;-fx-font-weight:bold;");
				hb_right_center.getChildren().addAll(imageView_D2_Human,t_human);
				
				HBox hb_right_bottom=new HBox();
					t_round=new Text();
					t_round.setStyle("-fx-font-size:20;-fx-font-weight:bold;");
					t_round.setText("Round: "+round+"/5");
				hb_right_bottom.getChildren().addAll(t_round);
				
			vb_right.setPadding(new Insets(20,0,0,20)); // top, right, bottom, left
			vb_right.setSpacing(20);
			vb_right.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-Border-color: #036c8c;-fx-Border-radius: 10;-fx-Border-width:2;");
			vb_right.getChildren().addAll(hb_right_top,hb_right_center,hb_right_bottom);
		
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			//***********************************************************************************************************************************************
			
			
			// Left
			VBox vb_left=new VBox();
			
				Pane p=new Pane();
				GridPane gp=new GridPane();
				gp.setHgap(2);
				gp.setVgap(2);
					
					
					// b 0 0
					b_0_0.setStyle("-fx-background-color: white;");
					if(board[0][0]==' ') {
						b_0_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
					}
					
					b_0_0.setOnAction(e->{
						
						if(board[0][0]==' ') {
							if(chooseRandom[randomIndex].equals("Human")) {
								b_0_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
								board[0][0]='X';
							}
							else {
								b_0_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
								board[0][0]='O';
							}
							if(depth>0) {
								depth--;
							}
							
							// check state of game
							if(checkStateOfGame(board)!=2) {
								Platform.runLater(() -> {
									finishRound();
									
								});
							}
							// Ai turn
							else {
								Platform.runLater(() -> {
									if(chooseRandom[randomIndex].equals("Human")) {
										calcMinimax(board, depth, false, true);
									}
									else {
										calcMinimax(board, depth, true, true);
									}
									
									updateAI();
									
									// check state of game
									if(checkStateOfGame(board)!=2) {
										finishRound();
									}
									
								});
								
							}
							
						}
						
						
						
						
						
					});
					
					
					// b 0 1
					b_0_1.setStyle("-fx-background-color: white;");
					if(board[0][1]==' ') {
						b_0_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
					}
					
					b_0_1.setOnAction(e->{
						
						if(board[0][1]==' ') {
							
							// human turn
							if(chooseRandom[randomIndex].equals("Human")) {
								b_0_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
								board[0][1]='X';
							}
							else {
								b_0_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
								board[0][1]='O';
							}
							if(depth>0) {
								depth--;
							}
							
							// check state of game
							if(checkStateOfGame(board)!=2) {
								finishRound();
							}
							// Ai turn
							else {
								Platform.runLater(() -> {
									if(chooseRandom[randomIndex].equals("Human")) {
										calcMinimax(board, depth, false, true);
									}
									else {
										calcMinimax(board, depth, true, true);
									}
									
									updateAI();
									
									// check state of game
									if(checkStateOfGame(board)!=2) {
										finishRound();
									}
									
								});
								
							}
						}						
						
					});
					
					
					// b 0 2
					b_0_2.setStyle("-fx-background-color: white;");
					if(board[0][2]==' ') {
						b_0_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
					}
					b_0_2.setOnAction(e->{
						
						
						if(board[0][2]==' ') {
							if(chooseRandom[randomIndex].equals("Human")) {
								b_0_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
								board[0][2]='X';
							}
							else {
								b_0_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
								board[0][2]='O';
							}
							if(depth>0) {
								depth--;
							}
							
							// check state of game
							if(checkStateOfGame(board)!=2) {
								finishRound();
							}
							// Ai turn
							else {
								Platform.runLater(() -> {
									if(chooseRandom[randomIndex].equals("Human")) {
										calcMinimax(board, depth, false, true);
									}
									else {
										calcMinimax(board, depth, true, true);
									}
									
									updateAI();
									
									// check state of game
									if(checkStateOfGame(board)!=2) {
										finishRound();
									}
									
								});
								
							}
						}
					
						
						
					});
					
					
					// b 1 0
					b_1_0.setStyle("-fx-background-color: white;");
					if(board[1][0]==' ') {
						b_1_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
					}
					b_1_0.setOnAction(e->{
						
					
						if(board[1][0]==' ') {
							if(chooseRandom[randomIndex].equals("Human")) {
								b_1_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
								board[1][0]='X';
							}
							else {
								b_1_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
								board[1][0]='O';
							}
							if(depth>0) {
								depth--;
							}
							
							// check state of game
							if(checkStateOfGame(board)!=2) {
								finishRound();
							}
							// Ai turn
							else {
								Platform.runLater(() -> {
									if(chooseRandom[randomIndex].equals("Human")) {
										calcMinimax(board, depth, false, true);
									}
									else {
										calcMinimax(board, depth, true, true);
									}
									
									updateAI();
									
									// check state of game
									if(checkStateOfGame(board)!=2) {
										finishRound();
									}
									
								});
								
							}
						}
					
						
						
						
					});
					
					
					// b 1 1
					b_1_1.setStyle("-fx-background-color: white;");
					if(board[1][1]==' ') {
						b_1_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
					}
					b_1_1.setOnAction(e->{
						
					
						if(board[1][1]==' ') {
							if(chooseRandom[randomIndex].equals("Human")) {
								b_1_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
								board[1][1]='X';
							}
							else {
								b_1_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
								board[1][1]='O';
							}
							if(depth>0) {
								depth--;
							}
							
							// check state of game
							if(checkStateOfGame(board)!=2) {
								finishRound();
							}
							// Ai turn
							else {
								Platform.runLater(() -> {
									if(chooseRandom[randomIndex].equals("Human")) {
										calcMinimax(board, depth, false, true);
									}
									else {
										calcMinimax(board, depth, true, true);
									}
									
									updateAI();
									
									// check state of game
									if(checkStateOfGame(board)!=2) {
										finishRound();
									}
									
								});
								
							}
						}
					
						
						
					});
					
					
					// b 1 2
					b_1_2.setStyle("-fx-background-color: white;");
					if(board[1][2]==' ') {
						b_1_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
					}
					b_1_2.setOnAction(e->{
						
				
						if(board[1][2]==' ') {
							if(chooseRandom[randomIndex].equals("Human")) {
								b_1_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
								board[1][2]='X';
							}
							else {
								b_1_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
								board[1][2]='O';
							}
							if(depth>0) {
								depth--;
							}
							
							// check state of game
							if(checkStateOfGame(board)!=2) {
								finishRound();
							}
							// Ai turn
							else {
								Platform.runLater(() -> {
									if(chooseRandom[randomIndex].equals("Human")) {
										calcMinimax(board, depth, false, true);
									}
									else {
										calcMinimax(board, depth, true, true);
									}
									
									updateAI();
									
									// check state of game
									if(checkStateOfGame(board)!=2) {
										finishRound();
									}
									
								});
								
							}
						}
					
						
						
					});
					
					 
					// b 2 0
					b_2_0.setStyle("-fx-background-color: white;");
					if(board[2][0]==' ') {
						b_2_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
					}
					b_2_0.setOnAction(e->{
						
					
						if(board[2][0]==' ') {
							if(chooseRandom[randomIndex].equals("Human")) {
								b_2_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
								board[2][0]='X';
							}
							else {
								b_2_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
								board[2][0]='O';
							}
							if(depth>0) {
								depth--;
							}
							
							// check state of game
							if(checkStateOfGame(board)!=2) {
								finishRound();
							}
							// Ai turn
							else {
								Platform.runLater(() -> {
									if(chooseRandom[randomIndex].equals("Human")) {
										calcMinimax(board, depth, false, true);
									}
									else {
										calcMinimax(board, depth, true, true);
									}
									
									updateAI();
									
									// check state of game
									if(checkStateOfGame(board)!=2) {
										finishRound();
									}
									
								});
								
							}
						}
					
						
						
					});
					
					
					// b 2 1
					b_2_1.setStyle("-fx-background-color: white;");
					if(board[2][1]==' ') {
						b_2_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
					}
					b_2_1.setOnAction(e->{
						
				
						if(board[2][1]==' ') {
							if(chooseRandom[randomIndex].equals("Human")) {
								b_2_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
								board[2][1]='X';
							}
							else {
								b_2_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
								board[2][1]='O';
							}
							if(depth>0) {
								depth--;
							}
							
							// check state of game
							if(checkStateOfGame(board)!=2) {
								finishRound();
							}
							// Ai turn
							else {
								Platform.runLater(() -> {
									if(chooseRandom[randomIndex].equals("Human")) {
										calcMinimax(board, depth, false, true);
									}
									else {
										calcMinimax(board, depth, true, true);
									}
									
									updateAI();
									
									// check state of game
									if(checkStateOfGame(board)!=2) {
										finishRound();
									}
									
								});
								
							}
						}
						
						
						
					});
					
					 
					// b 2 2
					b_2_2.setStyle("-fx-background-color: white;");
					if(board[2][2]==' ') {
						b_2_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
					}
					b_2_2.setOnAction(e->{
					
						if(board[2][2]==' ') {
							if(chooseRandom[randomIndex].equals("Human")) {
								b_2_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
								board[2][2]='X';
							}
							else {
								b_2_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
								board[2][2]='O';
							}
							if(depth>0) {
								depth--;
							}
							
							// check state of game
							if(checkStateOfGame(board)!=2) {
								finishRound();
							}
							// Ai turn
							else {
								Platform.runLater(() -> {
									if(chooseRandom[randomIndex].equals("Human")) {
										calcMinimax(board, depth, false, true);
									}
									else {
										calcMinimax(board, depth, true, true);
									}
									
									updateAI();
									
									// check state of game
									if(checkStateOfGame(board)!=2) {
										finishRound();
									}
									
								});
								
							}
						}
					
						
						
					});
					
				gp.add(b_0_0,0,0);
				gp.add(b_0_1,0,1);
				gp.add(b_0_2,0,2);
				
				gp.add(b_1_0,1,0);
				gp.add(b_1_1,1,1);
				gp.add(b_1_2,1,2);
				
				gp.add(b_2_0,2,0);
				gp.add(b_2_1,2,1);
				gp.add(b_2_2,2,2);
				gp.setStyle("-fx-background-color:black;-fx-Border-color: black;-fx-Border-width:3;");
				
			p.getChildren().addAll(gp,line);	
			vb_left.getChildren().add(p);	
			vb_left.setStyle("-fx-background-color:#034e65;");
			vb_left.setPadding(new Insets(170,0,0,0)); // top, right, bottom, left
			
		hb.getChildren().addAll(vb_left,vb_right);
		hb.setSpacing(250);
		hb.setPadding(new Insets(20,0,0,525)); // top, right, bottom, left
		
		
		
		
		
		//***********************************************************************************************************************************************
		

			
		bp_mainPage.setCenter(hb);
		
		bp_mainPage.setStyle("-fx-background-color:#034e65;");
		return bp_mainPage;
	}

	
	//////////////////////////////////////////////////////////////////////////////////
	// Methods:
	
	
	public int calcMinimax(char board[][], int depth, boolean isMaxmizing, boolean isParent) {

		if (depth == 0 || checkStateOfGame(board) != 2) {
			return checkStateOfGame(board);
		}

		if (isMaxmizing) { // x time
			int finalScore = Integer.MIN_VALUE; // to save the max value for x
			int i_res = 0;
			int j_res = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == ' ') {
						board[i][j] = 'X';
						int score = calcMinimax(board, depth - 1, false, false);
						board[i][j] = ' ';
						if (score > finalScore) {
							finalScore = score;
							i_res = i;
							j_res = j;
						}
					}

				}
			}
			if (isParent) {
				if(chooseRandom[randomIndex].equals("Human")) {
					board[i_res][j_res] = 'O';
				}
				else {
					board[i_res][j_res] = 'X';
				}
				
				ai_i_trace=i_res;
				ai_j_trace=j_res;
			}
			return finalScore;
		} else {
			int finalScore = Integer.MAX_VALUE;// to save the max value for o
			int i_res = 0;
			int j_res = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == ' ') {
						board[i][j] = 'O';
						int score = calcMinimax(board, depth - 1, true, false);
						board[i][j] = ' ';
						if (score < finalScore) {
							finalScore = score;
							i_res = i;
							j_res = j;

						}
					}

				}
			}
			if (isParent) {
				if(chooseRandom[randomIndex].equals("Human")) {
					board[i_res][j_res] = 'O';
				}
				else {
					board[i_res][j_res] = 'X';
				}
				
				ai_i_trace=i_res;
				ai_j_trace=j_res;
			}
			
			return finalScore;
		}

	}

	public int checkStateOfGame(char board[][]) {
		// 1: X win
		//-1: O win
		// 0: tie
		// 2: continue game
		
		//-------------------------------------------------------------------------------------------
		// Win:
		// in case row:
		
			// Case X:
			if((board[0][0]==board[0][1] && board[0][1]==board[0][2] && board[0][2]=='X') ||
			   (board[1][0]==board[1][1] && board[1][1]==board[1][2] && board[1][2]=='X') ||
			   (board[2][0]==board[2][1] && board[2][1]==board[2][2] && board[2][2]=='X')) {
				return 1;
			} 
			// Case O:
			else if((board[0][0]==board[0][1] && board[0][1]==board[0][2] && board[0][2]=='O') ||
					(board[1][0]==board[1][1] && board[1][1]==board[1][2] && board[1][2]=='O') ||
					(board[2][0]==board[2][1] && board[2][1]==board[2][2] && board[2][2]=='O')) {
				return -1;
			}
			
			
		// in case column:
			
			// Case X:
			else if((board[0][0]==board[1][0] && board[1][0]==board[2][0] && board[2][0]=='X') ||
			   (board[0][1]==board[1][1] && board[1][1]==board[2][1] && board[2][1]=='X') ||
			   (board[0][2]==board[1][2] && board[1][2]==board[2][2] && board[2][2]=='X')) {
				return 1;
			}
			// Case O:
			else if((board[0][0]==board[1][0] && board[1][0]==board[2][0] && board[2][0]=='O') ||
			        (board[0][1]==board[1][1] && board[1][1]==board[2][1] && board[2][1]=='O') ||
			        (board[0][2]==board[1][2] && board[1][2]==board[2][2] && board[2][2]=='O')) {
				return -1;
			}
			
			
		// in case diagonal:
			
			// Case X:
			else if((board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[2][2]=='X') ||
					(board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[2][0]=='X')) {
				return 1;
			}
			// Case O:
			else if((board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[2][2]=='O') ||
					(board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[2][0]=='O')) {
				return -1;
			}
			
		//-------------------------------------------------------------------------------------------	
		// Tie:
			
			else if(checkTie(board)) {
				return 0;
			}
			
		//-------------------------------------------------------------------------------------------	
		// Continue game:	
			else {
				return 2;
			}
		
		
	    
	    
	}
	
	public boolean checkTie(char board[][]) {
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(board[i][j]==' ') {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public void updateAI() {
		
		Platform.runLater(() -> {
			if(depth>0) {
				depth--;
			}
		});
		
		
		if(chooseRandom[randomIndex].equals("AI")) {
			if(ai_i_trace==0 && ai_j_trace==0) {
				b_0_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
			}
			else if(ai_i_trace==0 && ai_j_trace==1) {
				b_0_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
			}
			else if(ai_i_trace==0 && ai_j_trace==2) {
				b_0_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
			}
			else if(ai_i_trace==1 && ai_j_trace==0) {
				b_1_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
			}
			else if(ai_i_trace==1 && ai_j_trace==1) {
				b_1_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
			}
			else if(ai_i_trace==1 && ai_j_trace==2) {
				b_1_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
			}
			else if(ai_i_trace==2 && ai_j_trace==0) {
				b_2_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
			}
			else if(ai_i_trace==2 && ai_j_trace==1) {
				b_2_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
			}
			else if(ai_i_trace==2 && ai_j_trace==2) {
				b_2_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/x.png").toExternalForm())));
			}
		}
		else {
			if(ai_i_trace==0 && ai_j_trace==0) {
				b_0_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
			}
			else if(ai_i_trace==0 && ai_j_trace==1) {
				b_0_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
			}
			else if(ai_i_trace==0 && ai_j_trace==2) {
				b_0_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
			}
			else if(ai_i_trace==1 && ai_j_trace==0) {
				b_1_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
			}
			else if(ai_i_trace==1 && ai_j_trace==1) {
				b_1_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
			}
			else if(ai_i_trace==1 && ai_j_trace==2) {
				b_1_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
			}
			else if(ai_i_trace==2 && ai_j_trace==0) {
				b_2_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
			}
			else if(ai_i_trace==2 && ai_j_trace==1) {
				b_2_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
			}
			else if(ai_i_trace==2 && ai_j_trace==2) {
				b_2_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/o.png").toExternalForm())));
			}
		}
		
		
	}
	
	public void createNextRound() {
		
		GaussianBlur blur = new GaussianBlur(6);
		BorderPane bp_nextscene=mainPage();
		bp_nextscene.setEffect(blur);
		stage.getScene().setRoot(bp_nextscene);
		
		
		Stage stage_res=new Stage();
		
		stage_res.initStyle(StageStyle.TRANSPARENT);
		stage_res.initModality(Modality.NONE);
		stage_res.initOwner(stage.getScene().getWindow());
		
		stage_res.setX(0); 
		stage_res.setY(400); 
        
		VBox vb_t=new VBox();
		Text t_res=new Text();
		t_res.setStyle("-fx-fill: #FFD700; -fx-stroke: #FFFFFF; -fx-stroke-width: 1.5; -fx-font-size: 40;-fx-font-weight: bold;");
		
		if(checkStateOfGame(board)==1) {
			if(chooseRandom[randomIndex].equals("AI")) {
				t_res.setText("AI Win");
				stage_res.setX(680); 
			}
			else {
				t_res.setText("Human Win"); 
				stage_res.setX(640); 
			}
		}
		else if(checkStateOfGame(board)==-1) {
			if(chooseRandom[randomIndex].equals("Human")) {
				t_res.setText("AI Win");
				stage_res.setX(680); 
			}
			else {
				t_res.setText("Human Win"); 
				stage_res.setX(640); 
			}
		}
		else {
			t_res.setText("Tie"); 
			stage_res.setX(720); 
		}
		
		
		
		
		Text t_sec=new Text("");
		t_sec.setStyle("-fx-fill: #FFFFFF; -fx-stroke: black; -fx-stroke-width: 0.5; -fx-font-size: 20;-fx-font-weight: bold;");
		vb_t.getChildren().addAll(t_res,t_sec);
		vb_t.setSpacing(230);
		vb_t.setStyle("-fx-background-color:transparent;");
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> {
                    
			Platform.runLater(() -> {
				t_sec.setText("next round in: " + countdown_sec);
				countdown_sec--;   
	        });
			
			if(countdown_sec==0) {
				Platform.runLater(() -> {
					line.setStroke(null);
		            bp_nextscene.setEffect(null);
		            clearBoard();
		            Random r=new Random();
		    		randomIndex = r.nextInt(chooseRandom.length);
		    		depth=9;
		    		if(chooseRandom[randomIndex].equals("AI")) {
		    			calcMinimax(board, depth, true, true);	
		    			updateAI();
		    		}
		            stage_res.close();
		            
		            countdown_sec=3;
		        });
			}
	        
                    
        }));
		
		
		
			
		
        timeline.setCycleCount(4);
        timeline.play();
		
        
        
        
		Scene scene_choose=new Scene(vb_t, 780, 340);
		scene_choose.setFill(null);
		stage_res.setScene(scene_choose);
		stage_res.show();
	}

	public void clearBoard() {
		// clear array
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board.length;j++) {
				board[i][j]=' ';
			}
		}
		
		Platform.runLater(() -> {
			depth=9;
			ai_i_trace=-1;
			ai_j_trace=-1;
		});
		
		// clear UI
		b_0_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
		b_0_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
		b_0_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
		b_1_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
		b_1_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
		b_1_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
		b_2_0.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
		b_2_1.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
		b_2_2.setGraphic(new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/init.png").toExternalForm())));
		
	}

	public void finishGame() {
		line.setStroke(null);
		GaussianBlur blur = new GaussianBlur(6);
		BorderPane bp_nextscene=mainPage();
		bp_nextscene.setEffect(blur);
		stage.getScene().setRoot(bp_nextscene);
		
		Stage stage_finish=new Stage();
		if(ai_score==human_score) {
			stage_finish.setTitle("Tie");
		}
		else if(ai_score>human_score) {
			stage_finish.setTitle("Game Over");
		}
		else if(human_score>ai_score) {
			stage_finish.setTitle("Win Game");
		}
		

		stage_finish.initStyle(StageStyle.UTILITY);
		stage_finish.initModality(Modality.APPLICATION_MODAL);
		stage_finish.initOwner(stage.getScene().getWindow());
		
		stage_finish.setX(365); 
		stage_finish.setY(240); 
        
		stage_finish.setOnCloseRequest(event -> {
			stage.getScene().setRoot(startPage());
			
			Platform.runLater(() -> {
				clearBoard();
				round=0;
				t_round.setText("Round: "+round+"/5");
				ai_score=0;
				t_ai.setText("\n   "+ai_score);
				human_score=0;
				t_human.setText("\n   "+human_score);
				countdown_sec=3;
	        });
			
        });
		
		VBox vb_finish=new VBox();
		
			// top
			HBox hb_top=new HBox();
			Text t_res=new Text("The Game Result: ");
			t_res.setStyle("-fx-font-size:20;-fx-font-weight:bold;-fx-fill: white;");
			hb_top.getChildren().add(t_res);
			hb_top.setPadding(new Insets(10,0,0,10)); // top, right, bottom, left
			
			// center
			HBox hb_center=new HBox();
				
				// left
				VBox vb_center_left=new VBox();
					ImageView iv_ai=new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/ai.png").toExternalForm()));
					Text t_ai_res=new Text("\n"+"     "+ai_score);
					t_ai_res.setStyle("-fx-font-size:20;-fx-font-weight:bold;-fx-fill: white;");
				vb_center_left.getChildren().addAll(iv_ai,t_ai_res);
				
				// center
				ImageView iv_line=new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/verical_line.png").toExternalForm()));
				
				// right
				VBox vb_center_right=new VBox();
					ImageView iv_human=new ImageView(new Image(getClass().getResource("/D2_Graphic_icons/human.png").toExternalForm()));
					Text t_human_res=new Text("\n"+"     "+human_score);
					t_human_res.setStyle("-fx-font-size:20;-fx-font-weight:bold;-fx-fill: white;");
				vb_center_right.getChildren().addAll(iv_human,t_human_res);
				
			hb_center.setPadding(new Insets(0,0,0,230)); // top, right, bottom, left
			hb_center.getChildren().addAll(vb_center_left,iv_line,vb_center_right);
			hb_center.setSpacing(10);
			
			
			// bottom
			HBox hb_b=new HBox();
			hb_b.setPadding(new Insets(50,0,0,270)); // top, right, bottom, left
			Button b_restart=new Button("Restart Game");
			b_restart.setPrefSize(200, 40);
			b_restart.setStyle("-fx-background-color: LAWNGREEN; -fx-background-radius: 12; -fx-Border-color: OLIVEDRAB;"
					+ "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:2;-fx-font-weight:bold;");
			
			b_restart.addEventHandler(MouseEvent.MOUSE_ENTERED,
			        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		        	  b_restart.setEffect(shadow);
		        	  b_restart.setStyle("-fx-background-color: LAWNGREEN;-fx-Border-color: black;-fx-font-size:20;"
		        			  + "-fx-Border-width:2;-fx-font-weight:bold;-fx-text-fill: white;-fx-background-radius: 10;-fx-Border-radius: 8");
		          }
		    });
			
			b_restart.addEventHandler(MouseEvent.MOUSE_EXITED,
			        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		        	  b_restart.setEffect(null);
		        	  b_restart.setStyle("-fx-background-color: LAWNGREEN; -fx-background-radius: 12; -fx-Border-color: OLIVEDRAB;"
		    			   	  + "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:2;-fx-font-weight:bold;");
		          }
		    });
			
			b_restart.setOnAction(e->{
				stage_finish.close();
				
				Platform.runLater(() -> {
					clearBoard();
					round=0;
					t_round.setText("Round: "+round+"/5");
					ai_score=0;
					t_ai.setText("\n   "+ai_score);
					human_score=0;
					t_human.setText("\n   "+human_score);
					countdown_sec=3;
		        });
				
				bp_nextscene.setEffect(blur);
				stage.getScene().setRoot(bp_nextscene);
				
				Stage stage_start=new Stage();
				stage_start.setTitle("Random Choose");

				stage_start.initStyle(StageStyle.UTILITY);
				stage_start.initModality(Modality.APPLICATION_MODAL);
				stage_start.initOwner(stage.getScene().getWindow());
				
				stage_start.setX(365); 
		        stage_start.setY(240); 
		        
				stage_start.setOnCloseRequest(event -> {
					bp_nextscene.setEffect(null);
					
					if(chooseRandom[randomIndex].equals("AI")) {
						calcMinimax(board, depth, true, true);
						updateAI();
					}
		            
		        });
				
				VBox vb_start=new VBox();
				
					HBox hb_l=new HBox();
					Label l_start=new Label("\n"+" The game chooses who will start randomly every round between: (Human and AI)"+"\n\n\n"
											+"                                              "+"The "+chooseRandom[randomIndex]+" Will Start The Game");
					l_start.setStyle("-fx-font-size:20;-fx-font-weight:bold;-fx-text-fill: white;");
					hb_l.getChildren().add(l_start);
				
					
					HBox hb_bottom=new HBox();
					hb_bottom.setPadding(new Insets(50,0,0,270)); // top, right, bottom, left
					Button b_done_start=new Button("Done");
					b_done_start.setPrefSize(200, 40);
					b_done_start.setStyle("-fx-background-color: LAWNGREEN; -fx-background-radius: 12; -fx-Border-color: OLIVEDRAB;"
							+ "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:2;-fx-font-weight:bold;");
					
					b_done_start.addEventHandler(MouseEvent.MOUSE_ENTERED,
					        new EventHandler<MouseEvent>() {
				          @Override
				          public void handle(MouseEvent e) {
				        	  b_done_start.setEffect(shadow);
				        	  b_done_start.setStyle("-fx-background-color: LAWNGREEN;-fx-Border-color: black;-fx-font-size:20;"
				        			  + "-fx-Border-width:2;-fx-font-weight:bold;-fx-text-fill: white;-fx-background-radius: 10;-fx-Border-radius: 8");
				          }
				    });
					
					b_done_start.addEventHandler(MouseEvent.MOUSE_EXITED,
					        new EventHandler<MouseEvent>() {
				          @Override
				          public void handle(MouseEvent e) {
				        	  b_done_start.setEffect(null);
				        	  b_done_start.setStyle("-fx-background-color: LAWNGREEN; -fx-background-radius: 12; -fx-Border-color: OLIVEDRAB;"
				    			   	  + "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:2;-fx-font-weight:bold;");
				          }
				    });
					
					b_done_start.setOnAction(e1->{
						if(chooseRandom[randomIndex].equals("AI")) {
							calcMinimax(board, depth, true, true);				
							updateAI();
						}
						
						bp_nextscene.setEffect(null);
						stage_start.close();
					});
					hb_bottom.getChildren().add(b_done_start);
					
				vb_start.setStyle("-fx-background-color:#034e65;");
				vb_start.getChildren().addAll(hb_l,hb_bottom);
				vb_start.setSpacing(20);
				
				Scene scene_choose=new Scene(vb_start, 780, 300);
				stage_start.setScene(scene_choose);
				stage_start.show();
				
			});
			
			hb_b.getChildren().add(b_restart);
				
		

			
		vb_finish.setStyle("-fx-background-color:#034e65;");
		vb_finish.getChildren().addAll(hb_top,hb_center,hb_b);
		vb_finish.setSpacing(20);
		
		Scene scene_choose=new Scene(vb_finish, 780, 350);
		stage_finish.setScene(scene_choose);
		stage_finish.show();
		
	}
	
	public void finishRound() {
		
		if(checkStateOfGame(board)!=0) {
			line.setStroke(Color.YELLOW);
			createLine();  
		}
		
		
		if(checkStateOfGame(board)==1) {
			if(chooseRandom[randomIndex].equals("AI")) {
				Platform.runLater(() -> {
					ai_score++;
					t_ai.setText("\n   "+ai_score);
				});
			}
			else {
				Platform.runLater(() -> {
					human_score++;
					t_human.setText("\n   "+human_score);
				});	
			}
		}
		else if(checkStateOfGame(board)==-1) {
			if(chooseRandom[randomIndex].equals("Human")) {
				Platform.runLater(() -> {
					ai_score++;
					t_ai.setText("\n   "+ai_score);
				});
			}
			else {
				Platform.runLater(() -> {
					human_score++;
					t_human.setText("\n   "+human_score);
				});	
			}
		}
		
		Platform.runLater(() -> {
			
			round++;
			t_round.setText("Round: "+round+"/5");
			
			if(round==5 || ai_score==3 || human_score==3) {
				finishGame();
			}
			else {
				createNextRound();
				if(human_score>ai_score) {
					t_ai.setStyle("-fx-font-size:20;-fx-font-weight:bold;-fx-fill: #FF0000;");
					t_human.setStyle("-fx-font-size:20;-fx-font-weight:bold;-fx-fill: #7FFF00;");
				}
				else if(human_score<ai_score) {
					t_ai.setStyle("-fx-font-size:20;-fx-font-weight:bold;-fx-fill: #7FFF00;");
					t_human.setStyle("-fx-font-size:20;-fx-font-weight:bold;-fx-fill: #FF0000;");
				}
			}
			
		});
		
		
		
	
		
		
		
	}

	public void createLine() {
		if(checkStateOfGame(board)!=2 && checkStateOfGame(board)!=0) {
			// row
			if(board[0][0]==board[0][1] && board[0][1]==board[0][2]) {
				line.startXProperty().bind(b_0_0.layoutXProperty().add(b_0_0.widthProperty().divide(2)));
		        line.startYProperty().bind(b_0_0.layoutYProperty().add(b_0_0.heightProperty().divide(2)));
		        
		        line.endXProperty().bind(b_0_2.layoutXProperty().add(b_0_2.widthProperty().divide(2)));
		        line.endYProperty().bind(b_0_2.layoutYProperty().add(b_0_2.heightProperty().divide(2)));
			}
			else if(board[1][0]==board[1][1] && board[1][1]==board[1][2]) {
				line.startXProperty().bind(b_1_0.layoutXProperty().add(b_1_0.widthProperty().divide(2)));
		        line.startYProperty().bind(b_1_0.layoutYProperty().add(b_1_0.heightProperty().divide(2)));
		        
		        line.endXProperty().bind(b_1_2.layoutXProperty().add(b_1_2.widthProperty().divide(2)));
		        line.endYProperty().bind(b_1_2.layoutYProperty().add(b_1_2.heightProperty().divide(2)));
			}
			else if(board[2][0]==board[2][1] && board[2][1]==board[2][2]) {
				line.startXProperty().bind(b_2_0.layoutXProperty().add(b_2_0.widthProperty().divide(2)));
		        line.startYProperty().bind(b_2_0.layoutYProperty().add(b_2_0.heightProperty().divide(2)));
		        
		        line.endXProperty().bind(b_2_2.layoutXProperty().add(b_2_2.widthProperty().divide(2)));
		        line.endYProperty().bind(b_2_2.layoutYProperty().add(b_2_2.heightProperty().divide(2)));
			}
			// colmun
			else if(board[0][0]==board[1][0] && board[1][0]==board[2][0]) {
				line.startXProperty().bind(b_0_0.layoutXProperty().add(b_0_0.widthProperty().divide(2)));
		        line.startYProperty().bind(b_0_0.layoutYProperty().add(b_0_0.heightProperty().divide(2)));
		        
		        line.endXProperty().bind(b_2_0.layoutXProperty().add(b_2_0.widthProperty().divide(2)));
		        line.endYProperty().bind(b_2_0.layoutYProperty().add(b_2_0.heightProperty().divide(2)));
			}
			else if(board[0][1]==board[1][1] && board[1][1]==board[2][1]) {
				line.startXProperty().bind(b_0_1.layoutXProperty().add(b_0_1.widthProperty().divide(2)));
		        line.startYProperty().bind(b_0_1.layoutYProperty().add(b_0_1.heightProperty().divide(2)));
		        
		        line.endXProperty().bind(b_2_1.layoutXProperty().add(b_2_1.widthProperty().divide(2)));
		        line.endYProperty().bind(b_2_1.layoutYProperty().add(b_2_1.heightProperty().divide(2)));
			}
			else if(board[0][2]==board[1][2] && board[1][2]==board[2][2]) {
				line.startXProperty().bind(b_0_2.layoutXProperty().add(b_0_2.widthProperty().divide(2)));
		        line.startYProperty().bind(b_0_2.layoutYProperty().add(b_0_2.heightProperty().divide(2)));
		        
		        line.endXProperty().bind(b_2_2.layoutXProperty().add(b_2_2.widthProperty().divide(2)));
		        line.endYProperty().bind(b_2_2.layoutYProperty().add(b_2_2.heightProperty().divide(2)));
			}
			// diagonal
			else if(board[0][0]==board[1][1] && board[1][1]==board[2][2]) {
				line.startXProperty().bind(b_0_0.layoutXProperty().add(b_0_0.widthProperty().divide(2)));
		        line.startYProperty().bind(b_0_0.layoutYProperty().add(b_0_0.heightProperty().divide(2)));
		        
		        line.endXProperty().bind(b_2_2.layoutXProperty().add(b_2_2.widthProperty().divide(2)));
		        line.endYProperty().bind(b_2_2.layoutYProperty().add(b_2_2.heightProperty().divide(2)));
			}
			else if(board[0][2]==board[1][1] && board[1][1]==board[2][0]) {
				line.startXProperty().bind(b_0_2.layoutXProperty().add(b_0_2.widthProperty().divide(2)));
		        line.startYProperty().bind(b_0_2.layoutYProperty().add(b_0_2.heightProperty().divide(2)));
		        
		        line.endXProperty().bind(b_2_0.layoutXProperty().add(b_2_0.widthProperty().divide(2)));
		        line.endYProperty().bind(b_2_0.layoutYProperty().add(b_2_0.heightProperty().divide(2)));
			}
			
		}
        
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}

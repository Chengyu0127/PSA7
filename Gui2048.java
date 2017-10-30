//------------------------------------------------------------------//
// Gui2048.java                                                     //
//                                                                  //
// GUI Driver for 2048                                             //
//                                                                  //
// Author:  Ujjwal Gulecha		                             //
// Date:    11/09/16                                                //
//------------------------------------------------------------------//
//Chengyu Chen 03/02/2017
// This class is created to show the visual image on the computer of the 
// game 2048.And it sets the movement of the board related to the keyboard.


import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private static final int TILE_WIDTH = 106;

    private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
    private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
                                                 //(128, 256, 512)
    private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
                                                  //(1024, 2048, Higher)

    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
    private static final Color COLOR_2 = Color.rgb(238, 228, 218);
    private static final Color COLOR_4 = Color.rgb(237, 224, 200);
    private static final Color COLOR_8 = Color.rgb(242, 177, 121);
    private static final Color COLOR_16 = Color.rgb(245, 149, 99);
    private static final Color COLOR_32 = Color.rgb(246, 124, 95);
    private static final Color COLOR_64 = Color.rgb(246, 94, 59);
    private static final Color COLOR_128 = Color.rgb(237, 207, 114);
    private static final Color COLOR_256 = Color.rgb(237, 204, 97);
    private static final Color COLOR_512 = Color.rgb(237, 200, 80);
    private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    private static final Color COLOR_OTHER = Color.BLACK;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
                        // For tiles >= 8

    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
                       // For tiles < 8
  /** Add your own Instance Variables here */

    private GridPane pane;
    private Rectangle[][] cells;
    private Text[][] text;
    private int index;
    private Text score;
    private int count;

    @Override
    public void start(Stage primaryStage)
    {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
        // Set the spacing between the Tiles
        pane.setHgap(8.5); 
        pane.setVgap(8.5);
        Scene scene = new Scene(pane);// add the pane to the scene
        primaryStage.setTitle("Gui2048");
        ArrayList<Color> color = new ArrayList<Color>();//created a list of the color
        color.add(COLOR_EMPTY);
        color.add(COLOR_2);
        color.add(COLOR_4);
        color.add(COLOR_8);
        color.add(COLOR_16);
        color.add(COLOR_32);
        color.add(COLOR_64);
        color.add(COLOR_128);
        color.add(COLOR_256);
        color.add(COLOR_512);
        color.add(COLOR_1024);
        color.add(COLOR_2048);
        int size = board.GRID_SIZE;
        text = new Text[size][];
        cells = new Rectangle[size][];//create a 2D rectangles 
        int[][] grid = board.getGrid();
       for( int i =0; i< size;i++){
           cells[i]=new Rectangle[size];//create a new rectangle on each cell
           text[i] = new Text[size];
        }
        for(int i=0; i<size;i++){
           for(int j=0; j<size;j++){
              int value = grid[i][j];
              cells[i][j] = new Rectangle(TILE_WIDTH,TILE_WIDTH );
              text[i][j] = new Text();
        
          if( value == 0) {
             text[i][j].setText("");//set the text to empty when the value is 0
          }
          if (value != 0) {
              text[i][j].setText(""+value+"");//set the text to the corresponding value of the grid
          }    
          if (value < 8) {
             text[i][j].setFill(COLOR_VALUE_DARK);//set a darker color if the value of the tile is smaller than 8
              }
          if (value >= 8){
             text[i][j].setFill(COLOR_VALUE_LIGHT);//set a lighter color if the value of the tile is greater than 8
          }
          if (value ==0) {
               cells[i][j].setFill(color.get(0));
              
           }
          if (value!=0 && value <= 2048) {

              index =(int)(Math.log10(value)/Math.log10(2));//get the index of the Arraylist
              cells[i][j].setFill(color.get(index));

           }
          if (value >2048 ) {//set the tile that is bigger than 2048 to black
             cells[i][j].setFill(COLOR_OTHER);
          }

              text[i][j].setFont(Font.font("Times New Roman",FontWeight.BOLD,50)); 
              pane.setHalignment(text[i][j], HPos.CENTER);//set the text to the center
              pane.add(cells[i][j],j,i+1,1,1);//add the rectangle to the grid
              pane.add(text[i][j],j,i+1,1,1);//add the text onto the grid

           }
        } 
	     Text someText = new Text();
        someText.setText("2048");//place the name 2048 on the top left
	     someText.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.ITALIC, 30));
	     someText.setFill(Color.BLACK);
        pane.setHalignment(someText, HPos.CENTER);
        pane.add(someText,0,0,2,1);
        score = new Text();
        score.setText("Score: "+board.getScore());//put the score on the top right
        score.setFont(Font.font("Times New Roman",FontWeight.BOLD,25));
        pane.setHalignment(score, HPos.CENTER);
        score.setFill(Color.BLACK);
        pane.add(score,2,0,2,1);//add the score onto the pane 
        primaryStage.setScene(scene);
        primaryStage.show();//show the stage
        scene.setOnKeyPressed( new KeyHandler());// listen to key event handler


    }
    
      class KeyHandler implements EventHandler<KeyEvent>{ 
          @Override
         public void handle(KeyEvent e){
          if (count ==1) {
             return;
          }
          if (board.isGameOver()==true){//when the game is over
             Text GameOver = new Text();
             GameOver.setText("Game Over!");//add the Game over on the window
             GameOver.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.ITALIC, 80));
             GameOver.setFill(COLOR_VALUE_DARK);
             Rectangle rectangle = new Rectangle();
             rectangle.widthProperty().bind(pane.widthProperty());
             rectangle.heightProperty().bind(pane.heightProperty());
             rectangle.setFill(COLOR_GAME_OVER);
             pane.add(rectangle,0,0,4,5);
             pane.add(GameOver,0,0,4,5);//in the middle of the window
             pane.setHalignment(rectangle, HPos.CENTER);
             pane.setValignment(rectangle, VPos.CENTER);
             pane.setHalignment(GameOver, HPos.CENTER);
             pane.setValignment(GameOver, VPos.CENTER);
             count++;
             return;

           }
          if ( e.getCode() == KeyCode.S) {//if the key pressed is s then save the board.
             try {
              board.saveBoard(outputBoard);
                  } catch (IOException ex) { 
              System.out.println("saveBoard threw an Exception");
           }
              System.out.println(board);//print out the board after saving it
          }
          if( e.getCode()== KeyCode.UP && board.canMove(Direction.UP)== true) {
              board.move(Direction.UP);//move up when the user pressed up and the board can be moved up
              board.addRandomTile();//add a tile after you make the movement
              System.out.println("Moving" + "<UP>");// print out the direction on the terminal
              changeRectangle();//call the method that update the board
         }
          if( e.getCode() == KeyCode.DOWN && board.canMove(Direction.DOWN)== true) {
              board.move(Direction.DOWN);//move down when the user pressed up and the board can be moved up
              board.addRandomTile();//add a tile after you make the movement
              System.out.println("Moving" + "<DOWN>");
              changeRectangle();//call the method that update the board
         }
          if( e.getCode() == KeyCode.LEFT && board.canMove(Direction.LEFT)== true) {
              board.move(Direction.LEFT);//move left when the user pressed up and the board can be moved up
              board.addRandomTile();//add the tile after making the movement
              System.out.println("Moving" + "<LEFT>");
              changeRectangle();
         }
          if( e.getCode() == KeyCode.RIGHT && board.canMove(Direction.RIGHT)== true) {
              board.move(Direction.RIGHT);//move right when the user pressed right key 
              board.addRandomTile();
              System.out.println("Moving" + "<RIGHT>");
              changeRectangle();
         }
         if (e.getCode() == KeyCode.R){//the board will rotate clockwise every time I press r
              board.flip(3);
              changeRectangle();
          }
         }
      }


       
        /** Add your Code for the GUI Here */



    /** Add your own Instance Methods Here */
     public void changeRectangle(){
        pane.getChildren().clear();//clear everything on the pane
        Text someText = new Text();
        someText.setText("2048");//place the name 2048 on the top left
	     someText.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.ITALIC, 30));
	     someText.setFill(Color.BLACK);
        pane.setHalignment(someText, HPos.CENTER);
        pane.add(someText,0,0,2,1);//add the title to the board
        score = new Text();
        score.setText("Score: "+board.getScore());//put the score on the top right
        score.setFont(Font.font("Times New Roman",FontWeight.BOLD,25));
        pane.setHalignment(score, HPos.CENTER);
        score.setFill(Color.BLACK);
        pane.add(score,2,0,2,1);//add the score onto the pane 
        ArrayList<Color> color = new ArrayList<Color>();//created a list of the color
        color.add(COLOR_EMPTY);//add all the color to the arraylist
        color.add(COLOR_2);
        color.add(COLOR_4);
        color.add(COLOR_8);
        color.add(COLOR_16);
        color.add(COLOR_32);
        color.add(COLOR_64);
        color.add(COLOR_128);
        color.add(COLOR_256);
        color.add(COLOR_512);
        color.add(COLOR_1024);
        color.add(COLOR_2048);
        int size = board.GRID_SIZE;
       cells = new Rectangle[size][];//create a 2D rectangles
       text = new Text[size][];
        int[][] grid = board.getGrid();
      for( int i =0; i< size;i++){
         cells[i]=new Rectangle[size];
           text[i] = new Text[size];
        }
        for(int i=0; i<size;i++){
           for(int j=0; j<size;j++)
           {
 
              int value = grid[i][j];
             cells[i][j] = new Rectangle(TILE_WIDTH ,TILE_WIDTH );
              text[i][j] = new Text();
        
          if( value == 0) {
             text[i][j].setText("");//set the text to empty when the value is 0
          }
          if (value != 0) {
              text[i][j].setText(""+value+"");//set the text to the corresponding value of the grid
          }    
          if (value < 8) {
             text[i][j].setFill(COLOR_VALUE_DARK);//set a darker color if the value of the tile is smaller than 8
              }
          if (value >= 8){
             text[i][j].setFill(COLOR_VALUE_LIGHT);//set a lighter color if the value of the tile is greater than 8
          }
          if (value ==0) {
               cells[i][j].setFill(color.get(0));
              
           }
          if (value!=0) {

              index =(int)(Math.log10(value)/Math.log10(2));//get the index of the Arraylist
              cells[i][j].setFill(color.get(index));

           }
              
              text[i][j].setFont(Font.font("Times New Roman",FontWeight.BOLD,50)); 
              pane.setHalignment(text[i][j], HPos.CENTER);//set the text to the center
              pane.add(cells[i][j],j,i+1,1,1);//add the rectangle to the grid
              pane.add(text[i][j],j,i+1,1,1);//add the text onto the grid
              pane.setHalignment(score, HPos.CENTER);

           }
        }
       
}
          
        
           
        
        
     


    






    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(inputBoard, new Random());
            else
                board = new Board(boardSize, new Random());
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }
}

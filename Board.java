//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  Ujjwal Gulecha, Alan Kuo                             //
// Date:    01/22/17                                                //
//------------------------------------------------------------------//
//Chengyu Chen 02/06/2017 cs8bwane
//This Board class is the fundamental board where tilles will be placed
//and mover around. The instance variable like NUM_START_TILES is the
//number of the tiles that will be there when the game start. The GRID_SIZE
//is the size of the board. grid 2D array is how the board will be constructed.

/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

public class Board {
  public final int NUM_START_TILES = 2;
  public final int TWO_PROBABILITY = 90;
  public int GRID_SIZE;
  
  
  private final Random random;
  private int[][] grid;
  private int score;
  private int saveScore;
  private int[][] undoGrid;
  private int count;
  
  // TODO PSA3
  /* Constructs a fresh board with random tiles
   * @param random a random number
   * @param boardSize the size of the board that you want to make
   * */
  public Board(int boardSize, Random random) {
    this.random = random; 
    GRID_SIZE = boardSize;
    this.score = 0;
    this.grid=new int[GRID_SIZE][GRID_SIZE]; //Initialize grid with the size GRID_SIZE.
    for ( int i=0 ; i < NUM_START_TILES; i++ ) {
      addRandomTile(); //This method should run a "NUM_START_TILES"(this is an int) times.
    }
  }
  
  // TODO PSA3
  /* Construct a board based off of an input file
   * @param random a random number
   * @param inputBoard: an existed saved board 
   * */
  public Board(String inputBoard, Random random) throws IOException {
    this.random = random; // FIXME
    Scanner input = new Scanner(new File(inputBoard));
    GRID_SIZE = input.nextInt();
    score = input.nextInt();
    this.grid=new int[GRID_SIZE][GRID_SIZE];
    while (input.hasNextInt()) {
      for (int row =0;row<GRID_SIZE;row++) {
        for (int column=0;column<GRID_SIZE;column++){
          grid[row][column] = input.nextInt();
          
          
        }
      }
    }
  }
  
  // TODO PSA3
  /*Saves the current board to a file
   * @param outputBoard: the name of the file that you want to save all your board's
   * informations to.
   * */
  public void saveBoard(String outputBoard) throws IOException {
    PrintWriter writer = new PrintWriter(outputBoard);
    writer.println(GRID_SIZE);
    writer.println(score);
    for ( int row=0; row<GRID_SIZE;row++){
      for(int column=0; column<GRID_SIZE;column++) {
        writer.print(grid[row][column]+" "); 
      }
      writer.println();
    }
    writer.close();
  }
  
  // TODO PSA3
  /* Adds a random tile (of value 2 or 4) to a
   * random empty space on the board
   * */
  public void addRandomTile() {
    int count = 0;
    for (int row = 0; row< GRID_SIZE; row++) {
      for (int column = 0; column<GRID_SIZE; column++) {
        if( grid[row][column]==0) {
          count += 1 ;
        }
      }
    }
    if (count==0) {
      return;//return when there is no empty space
    }
    else {
      int location = random.nextInt(count); 
      int value = random.nextInt(100);
      for ( int row = 0; row< GRID_SIZE;row++) {
        for ( int column = 0; column<GRID_SIZE;column++) {
          if ( grid[row][column]==0) {
            location-- ;
            
          if (location==-1 ){
            if( value < TWO_PROBABILITY) {
              grid[row][column]=2; 
              
            }
            
            else {
              grid[row][column]=4;
            }
             
            }
          } 
        }
      }
    }
  }
  
  
  
  
  // TODO PSA3
  /**Flip the board horizontally or vertically
    * Rotate the board by 90 degrees clockwise or 90 degrees counter-clockwise.
    * @param change, depending on the value of change, it will perform different filps.
    * */
  public void flip(int change) {
    //Tips: Look up your picture lab
    //Flipping method is the same
    int limit = GRID_SIZE/2;
    if (change ==1) {
      for (int row=0; row<GRID_SIZE; row++) {
        for (int column =0; column<limit; column++) {
          int original = grid[row][column];
          int result = grid[row][grid[row].length-1-column];
          grid[row][column] = result;
          grid[row][grid[row].length-1-column] = original;
          
          
        }
      }
    }
    if (change ==2) {
      for (int row=0; row<limit; row++) {
        for (int column =0; column<GRID_SIZE; column++) {
          int original = grid[row][column];
          int result = grid[grid[row].length-1-row][column];
          grid[row][column] = result;
          grid[grid[row].length-1-row][column] = original;
          
          
        }
      }
    }
    
    if (change==3) {
      int[][] temp = new int[GRID_SIZE][GRID_SIZE];
      for ( int row=0; row<GRID_SIZE; row++) {
        for ( int column =0; column< GRID_SIZE ; column++) {
          temp[column][grid[row].length-row-1]= grid[row][column];
        }
      }
      for ( int row=0; row<GRID_SIZE; row++) {
        for ( int column =0; column< GRID_SIZE ; column++) {
          
          grid[row][column] = temp[row][column];
          
        }
      }
    }
    
    if (change==4) {
      int[][] temp = new int[GRID_SIZE][GRID_SIZE];
      for ( int row=0; row<GRID_SIZE; row++) {
        for ( int column =0; column<GRID_SIZE ; column++) {
          temp[grid[row].length-1-column][row]=  grid[row][column];            
        }
      }
      for ( int row=0; row<GRID_SIZE; row++) {
        for ( int column =0; column< GRID_SIZE ; column++) {
          
          grid[row][column] = temp[row][column];
        }
      }
    }
    if (change==5) {
      int[][] temp = new int[GRID_SIZE][GRID_SIZE];
      for ( int row=0; row<GRID_SIZE; row++) {
        for ( int column =0; column<GRID_SIZE ; column++) {
          temp[grid[row].length-1-column][grid[row].length-1-row]=  grid[row][column];            
        }
      }
      for ( int row=0; row<GRID_SIZE; row++) {
        for ( int column =0; column< GRID_SIZE ; column++) {
          
          grid[row][column] = temp[row][column];
        }
      }
    }
  }
  
  //Complete this method ONLY if you want to attempt at getting the extra credit
  //Returns true if the file to be read is in the correct format, else return
  //false
  public static boolean isInputFileCorrectFormat(String inputFile) {
    //The try and catch block are used to handle any exceptions
    //Do not worry about the details, just write all your conditions inside the
    //try block
    try {
      //write your code to check for all conditions and return true if it satisfies
      //all conditions else return false
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  
  
  
  /* This method will return true if the board can be moved
   * false if the board can not moved
   * @param direction: the direction that you want the tile to be move to
   * */
  public boolean canMove(Direction direction) {
    if ( direction.equals(Direction.LEFT)) {
      return canMoveLeft();
    }
    if ( direction.equals(Direction.RIGHT)) {
      return canMoveRight();
    }
    if ( direction.equals(Direction.UP)) {
      return canMoveUp();
    }
    if ( direction.equals(Direction.DOWN)) {
      return canMoveDown();
    }
    return true;
    
  }
  
  /* method determine whether the board can move
   * to the left or not.
   * @return true if it can, false if it can not move to
   * the left.
   * */
  
  private boolean canMoveLeft(){
    for ( int row=0; row<GRID_SIZE; row++) {
      for ( int column =1; column< GRID_SIZE ; column++){
        if( grid[row][column] != 0 
             && grid[row][column] == grid[row][column-1]) {
          return true;
        }
        else if( grid[row][column] != 0 && grid[row][column-1] ==0) {
          return true;
        }
      }
    }
    
    return false;      
  }
  
  /* method determine whether the board can move
   * to the right or not.
   * @return true if it can, false if it can not move to
   * the right.
   * */
  private boolean canMoveRight(){
    for ( int row=0; row<GRID_SIZE; row++) {
      for ( int column =0; column< GRID_SIZE-1 ; column++){
        if( grid[row][column] != 0 && grid[row][column] == grid[row][column+1]) {
          return true;
        }
        else if( grid[row][column] != 0 && grid[row][column+1] ==0) {
          return true;
        }
      }
    }
    
    return false;      
  }
  
  /* method determine whether the board can move up or not.
   * @return true if it can, false if it can not move up.
   * */
  private boolean canMoveUp(){
    for ( int row=1; row<GRID_SIZE; row++) {
      for ( int column =0; column< GRID_SIZE ; column++){
        if( grid[row][column] != 0 && grid[row-1][column] == grid[row][column]){
          return true;
        }
        else if( grid[row][column] != 0 && grid[row-1][column] ==0) {
          return true;
        }
      }
    }
    return false;
  }
  
  /* method determine whether the board can move down or not.
   * @return true if it can, false if it can not move down.
   * */
  private boolean canMoveDown(){
    for ( int row=0; row<GRID_SIZE-1; row++) {
      for ( int column =0; column< GRID_SIZE ; column++){
        if( grid[row][column] != 0 && grid[row][column] == grid[row+1][column]) {
          return true;
        }
        else if( grid[row][column] != 0 && grid[row+1][column] ==0) {
          return true;
        }
      }
    }
    
    return false; 
  }
  
  
  
  /*Performs a move Operation
   * @return true if the board move to the direction indicated
   * @param direction: the direction player want the tile to be move to.
   * */
  public boolean move(Direction direction) {
    if ( !canMove(direction) ) 
      return false;
    
    if (direction.equals(Direction.LEFT)) {
      moveLeft();
    }
    if (direction.equals(Direction.RIGHT)) {
      moveRight();
    }
    if (direction.equals(Direction.UP)) {
      moveUp();
    }
    if (direction.equals(Direction.DOWN)) {
      moveDown();
    }
    
    return true;
  }
  
  
  
  /* method that move the tile to the left
   * */
  public void moveLeft(){
    
    for ( int row=0; row<GRID_SIZE; row++) {
      for ( int column =1; column< GRID_SIZE ; column++){  
        if( grid[row][column-1] ==0 && grid[row][column] !=0 ){
          //compare with the previous number 
          grid[row][column-1]=grid[row][column];
          grid[row][column] =0;
          //shift if the current number is nonzero but the previous one is zero.
          column = 0;
          
        }
      }
    }   
    for ( int row=0; row<GRID_SIZE; row++) {
      for ( int column =1; column< GRID_SIZE ; column++){      
        
        if( grid[row][column] !=0 && (grid[row][column] == grid[row][column-1])) {
          //if the two adjacent number are the same combine them
          int value = grid[row][column] + grid[row][column-1];
          grid[row][column-1]=value;
          grid[row][column]=0;
          this.score += value;        
        }
      }                                                                                                                              
    }
    for ( int row=0; row<GRID_SIZE; row++) {
      for ( int column =1; column< GRID_SIZE ; column++){    
        if( grid[row][column] !=0 && grid[row][column-1] ==0 ){
          //compare with the previous number.
          grid[row][column-1]=grid[row][column];
          grid[row][column] =0;//shift if the current number is nonzero but the previous one is zero.
          column = 0;
        }
      }
    }   
    
    
    
  }
  
  
  
  
  
  /* method that move the tile to the right
   * */
  public void moveRight(){
    for ( int row=0; row<GRID_SIZE; row++) {
      for ( int column =0; column< GRID_SIZE-1 ; column++){  
        if( grid[row][column] !=0 && grid[row][column+1] ==0 ){//compare with the next number 
          int value = grid[row][column]+grid[row][column+1];
          grid[row][column+1]=value;
          grid[row][column] =0;//shift if the current number is nonzero but the next one is zero.
          column = -1;
        }
      }
    }               
    for ( int row=0; row<GRID_SIZE; row++) {
      for ( int column =GRID_SIZE-2; column>-1 ; column--){      
        
        if( grid[row][column] == grid[row][column+1]) {//if the two adjacent number are the same combine them
          int value = grid[row][column]+grid[row][column+1];
          grid[row][column+1]=value;
          grid[row][column]=0;
          this.score += value;        
        }
      }                                                                                                                              
    }
    for ( int row=0; row<GRID_SIZE; row++) {
      for ( int column =0; column< GRID_SIZE-1 ; column++){    
        if( grid[row][column] !=0 && grid[row][column+1] ==0 ){//compare with the next number 
          int value = grid[row][column]+grid[row][column+1];
          grid[row][column+1]=value;
          grid[row][column] =0;//shift if the current number is nonzero but the next one is zero.
          column=-1;
        }
      }
    }        
    
    
  }
  
  /* method that move the tile up.
   * */
  public void moveUp(){
    
    for ( int column =0; column< GRID_SIZE ; column++) {
      for ( int row=1; row<GRID_SIZE; row++){  
        if( grid[row][column] !=0 && grid[row-1][column] ==0 ){//compare with the previous' row number 
          grid[row-1][column]=grid[row][column];
          grid[row][column] =0;//shift if the current number is nonzero but the previous one is zero.
          row = 0;
        }
      }
    }               
    for ( int column =0; column< GRID_SIZE ; column++) {
      for ( int row=1; row<GRID_SIZE; row++){      
        
        if( grid[row][column] !=0 && grid[row][column] == grid[row-1][column]) {
          //if the two adjacent number are the same combine them
          int value = grid[row][column]+grid[row-1][column];
          grid[row-1][column]=value;
          grid[row][column]=0;
          this.score += value;        
        }
      }                                                                                                                              
    }
    for (int column =0; column< GRID_SIZE ; column++) {
      for ( int row=1; row<GRID_SIZE; row++){    
        if( grid[row][column] !=0 && grid[row-1][column] ==0 ){//compare with the previous row's number.
          grid[row-1][column]=grid[row][column];
          grid[row][column] =0;//shift if the current number is nonzero but the previous one is zero.
          row = 0;
        }
      }
    }        
    
    
    
  }
  
  
  /* method that move the tile down.
   * */
  public void moveDown(){
    
    for ( int column =0; column< GRID_SIZE ; column++) {
      for ( int row=0; row<GRID_SIZE-1; row++){  
        if( grid[row][column] !=0 && grid[row+1][column] ==0 ){//compare with the next row number 
          grid[row+1][column]=grid[row][column];
          grid[row][column] =0;//shift if the current number is nonzero but the next one is zero.
          row = -1;
        }
      }
    }               
    for (  int column =0; column< GRID_SIZE ; column++) {
      for (int row=GRID_SIZE-2; row>-1 ; row--){      
        
        if( grid[row][column] !=0 && grid[row+1][column] == grid[row][column]) {
          //if the two adjacent number are the same combine them
          int value = grid[row][column]+grid[row+1][column];
          grid[row+1][column]=value;
          grid[row][column]=0;
          this.score += value;        
        }
      }                                                                                                                              
    }
    for ( int column =0; column< GRID_SIZE ; column++) {
      for ( int row=0; row<GRID_SIZE-1; row++){    
        if( grid[row][column] !=0 && grid[row+1][column] ==0 ){//compare with the number in the next row.
          grid[row+1][column]=grid[row][column];
          grid[row][column] =0;//shift if the current number is nonzero but the next one is zero.
          row = -1;
        }
      }
    }          
  }
  
  
  
  
  
  
  /* return true if the board can no longer be moved
   * meaning the game is over
   * false if the tile can still move inside the board.
   * */
  public boolean isGameOver() {
    if (canMove(Direction.LEFT)== false && canMove(Direction.UP)==false 
          && canMove(Direction.RIGHT)==false && canMove(Direction.DOWN)==false) {
      return true;
    }
    return false;
  }
  
  
  /*method that expand the board.
   * */
  public void expand() {
    int[][] resultArray = new int[GRID_SIZE+1][GRID_SIZE+1];//create a new board
    //that have a size 1 more greater than the original board
    for ( int row = 0; row< GRID_SIZE; row++) {
      for (int column = 0; column < GRID_SIZE; column++) {
        resultArray[row][column] = grid[row][column];
        //copy the grid onto the new array
      }
    }
    GRID_SIZE++;
    grid = new int[GRID_SIZE][GRID_SIZE];//create a new grid array 
    for ( int row = 0; row< GRID_SIZE; row++) {
      for (int column = 0; column < GRID_SIZE; column++) {
        grid[row][column] = resultArray[row][column];
      }
    }
  }


   /*method that save the board.
    * */
  public void saveBoard() {
    undoGrid = new int[GRID_SIZE][GRID_SIZE];
    saveScore = score;
    count = 0;
    for ( int row = 0; row< GRID_SIZE; row++) {
      for (int column = 0; column < GRID_SIZE; column++) {
        undoGrid[row][column] = grid[row][column];
      }
    }
  }
  

  /*method that revert back to the previous
   * board when called.
   * */

  public void undo() {
    if( count == 1) {
      return;}
    
    else {
      count++;
      
      for ( int row = 0; row< GRID_SIZE; row++) {
        for (int column = 0; column < GRID_SIZE; column++) {
          grid[row][column] = undoGrid[row][column];
          //copy the previous board to the 2D grid.
        }
      }
    }
    score = saveScore;
  }
  
  
  
  // Return the reference to the 2048 Grid
  public int[][] getGrid() {
    return grid;
  }
  
  // Return the score
  public int getScore() {
    return score;
  }
  
  @Override
  public String toString() {
    StringBuilder outputString = new StringBuilder();
    outputString.append(String.format("Score: %d\n", score));
    for (int row = 0; row < GRID_SIZE; row++) {
      for (int column = 0; column < GRID_SIZE; column++)
        outputString.append(grid[row][column] == 0 ? "    -" :
                              String.format("%5d", grid[row][column]));
      
      outputString.append("\n");
    }
    return outputString.toString();
  }
}

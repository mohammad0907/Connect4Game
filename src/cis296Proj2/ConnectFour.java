
package cis296Proj2;

/**
 *
 * @author rashe
 */
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;




public class ConnectFour extends Application {
    

    private boolean red = true;
    private Pane rootChip = new Pane();
    private char [][] board = new char[6][7];
    private Text turn = new Text("Red Turn");
    private  Alert alert = new Alert(AlertType.NONE,  
                              "default Dialog",ButtonType.OK); 
     
     // holds the root pane
     private Parent game() {
       Pane root = new Pane();   
       root.getChildren().addAll(Grid());
       root.getChildren().add(rootChip);
       turn.setTranslateY(7.2 * (80 + 5) + 80 / 4);
       turn.setTranslateX(270);
       turn.setFill(Color.RED);
       turn.setStyle("-fx-font: 24 arial;");
       root.getChildren().add(turn);
      
       // populates the the 2D board with . (means empty)
       for( int i = 0; i < 6; i++){
           for(int j = 0; j < 7; j++){
               board[i][j] = '.';
           }
       }
       
        return root;
    }
      
          
    // creates the grid
     private List<Pane> Grid() {
         // columns
        List<Pane>columns = new ArrayList();
          
        // each column pane  has a rectangles
        for(int i = 0; i < 7; i++){
            Pane column = new Pane();
            column.setTranslateX(i * 85 + 20);
            Shape rect = new Rectangle(81, 7.5 * 80);
            rect.setFill(Color.BLUE);
                     
            Button button = new Button ("Drop Disc");
                  
            button.setTranslateY(6.2 * 85 + 20);
            button.setTranslateX(7);
                   
            final int columnNums = i;
            button.setOnMouseClicked(e -> setDisc(columnNums));
            column.getChildren().add(rect);
            column.getChildren().add(button);
            // each column pane has 6 disc empty chip slots 
            for(int j = 0; j < 6; j++){
                Circle circle = new Circle(40);
                circle.setFill(Color.WHITE);
                circle.setCenterX(40);
                circle.setCenterY(40);
                        
                circle.setTranslateY(j * (85) + 20);
                        
                column.getChildren().add(circle);   
               } 
                columns.add(column);
             }
               
             return columns;  
    }
       // sets Discs in right positon    
       private void setDisc(int column){
           
           int row  = 5 ;
          Circle discCircle = new Circle(35);
          // loop to find the empty row in the column clicked
           for(int i = 5; i >= 0; i--){
           if(board[row][column] == '.'){
               break;
           }else {
               row--;
           }
       }
           // when it doesnt find anything, it returns nothing
        if (row < 0){
            
            return;
        }
        
        
       // red disc creation
        if(red){
            board[row][column] = 'r';
            discCircle.setFill(Color.RED);
            turn.setText("Black Turn");
            turn.setFill(Color.BLACK);
            // check if the move created a winning streak
            winCheck( row ,column,"red");
            
        }
        // for black creation
        else{
            board[row][column] = 'b';
            discCircle.setFill(Color.BLACK);
            turn.setText("Red Turn");
            turn.setFill(Color.RED);
            // check if the move created a winning streak
            winCheck( row,column,  "black");
           
        }
        
     
        // places the new created cirlce on top of the white circle
        discCircle.setCenterX(40);
        discCircle.setCenterY(40);
        
        rootChip.getChildren().add(discCircle);
       discCircle.setTranslateX(column * 85 + 20 );
        discCircle.setTranslateY(row * 85  + 20);
        red = !red; // keeps track of turn
        
        
           
       }
       
       // finds if win occured
       public void winCheck(int row, int column, String player){
           // horizontal, vertical, rigth diagnal and left diagnal check 
           if(checkHorizontal(row, player)){
               System.out.println("Winner: " + player);
               alert.setTitle("Win");
               alert.setContentText("Winner: " + player);
               alert.show();
           }else if(checkVertical(column, player)){
               System.out.println("Winner: " + player);
               alert.setTitle("Win");
               alert.setContentText("Winner: " + player);
               alert.show();
           }else if(checkRigthDiagnal(row, column, player)){
                System.out.println("Winner: " + player);
                alert.setTitle("Win");
               alert.setContentText("Winner: " + player);
               alert.show();
           }else if(checkLeftDiagnal(row, column, player)){
               System.out.println("Winner: " + player);
               alert.setTitle("Win");
               alert.setContentText("Winner: " + player);
               alert.show();
           }else{
               // to see if the board is full
               checkFull();
           }
           
       }
       
       // horizontal win check 
       public boolean checkHorizontal(int row, String player){
           
           String horiz = new String(board[row]); // sets horiz the the whole row 
          
           String win;
           
           // sets the winning streak
           if(player.equals("red")){
               win = "rrrr";
           }else{
               win = "bbbb";
           }
           
           // if the streak contain in horiz, then return true 
           if(horiz.contains(win)){
               return true;
           }else{
               return false;
           }
           
           
       }
       
       
       // vertical win check
       public boolean checkVertical(int column, String player){
           String vertical = ""; 
           String win;
           
           // cretes a string cotain all the values in the column passed
           for(int i = 0; i < 6; i++){
               vertical = vertical + board[i][column];
           }
           
            // sets the winning streak
           if(player.equals("red")){
               win = "rrrr";
           }else{
               win = "bbbb";
           }
           
           // if the streak contain in vertical, then return true
           if(vertical.contains(win)){
               return true;
           }else{
               return false;
           }
       }
       
       // checks for right diagnal win
       public boolean checkRigthDiagnal(int row, int column, String player){
           String rightDiag = "";
           String win;
          
           int topRow = row;
           int topColumn = column;
           
           // finds top most spot rigth diagonally from the curren spot 
           for(int i = 0; i < 6; i++){
               
               if((topRow <= 0 || topColumn <0) || (topRow >6  || topColumn >= 6) ) {
                   break;
               }else{
                   topRow--;
                   topColumn++;
               }
               
               
           }
           
          
           // from the top spot goes down diagnally to to the end of board and creates a string containing the values
           for(int i = 0; i <6 ; i++){
               if(topRow < 6 && topColumn >= 0){
                   rightDiag = rightDiag + board[topRow][topColumn];
                   topRow++;
                   topColumn--;
               }
           }
           
           
          
           // creates winning streak
            if(player.equals("red")){
               win = "rrrr";
           }else{
               win = "bbbb";
           }
           // if the streak contain in horiz, then return true
           if(rightDiag.contains(win)){
               return true;
           }else{
               return false;
           }
           
       }
       
       // left diagnal check 
       public boolean checkLeftDiagnal(int row, int column, String player){
           String leftDiag = "";
           String win;
          
           int topRow = row;
           int topColumn = column;
            // finds top most spot left diagonally from the curren spot 
           for(int i = 0; i < 6; i++){
               
               if((topRow <= 0 || topColumn <=0) || (topRow >6  || topColumn > 6) ) {
                   break;
               }else{
                   topRow--;
                   topColumn--;
               }
               
               
           }
           
         
           
           // from the top spot goes down diagnally to to the end of board and creates a string containing the values
           for(int i = 0; i <6 ; i++){
               if(topRow < 6 && topColumn < 7){
                   leftDiag = leftDiag + board[topRow][topColumn];
                   topRow++;
                   topColumn++;
               }
           }
           
           
          
           // creates winning streak
            if(player.equals("red")){
               win = "rrrr";
           }else{
               win = "bbbb";
           }
           
            // if the streak contain in horiz, then return true
           if(leftDiag.contains(win)){
               return true;
           }else{
               return false;
           }
       }
       
       // checks if the board is full 
       public void checkFull(){
           String rows; 
           boolean full = false;
           // checks if each row is full, if one row is not full than loop breaks
           for(int i = 0; i < 6; i++){
               rows = new String(board[i]);
               if(rows.contains(".")){
                  full = false;
                  break; 
               }else{
                   full = true;
               }
           }
           
           if(full){
               System.out.println("Draw");
               
               alert.setContentText("Draw");
               alert.show();
           }
       }
       
   
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //creates the window and calls the game function 
        stage.setScene(new Scene(game(), 640, 640));
        stage.show();
    }
    
}
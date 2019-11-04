
package javaapplication23;

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
     
     
     private Parent Game() {
       Pane root = new Pane();   
       root.getChildren().addAll(Grid());
       root.getChildren().add(rootChip);
       turn.setTranslateY(7.2 * (80 + 5) + 80 / 4);
       turn.setTranslateX(270);
       turn.setFill(Color.RED);
       turn.setStyle("-fx-font: 24 arial;");
       root.getChildren().add(turn);
      
       
       for( int i = 0; i < 6; i++){
           for(int j = 0; j < 7; j++){
               board[i][j] = '.';
           }
       }
       
        return root;
    }
      
          
        
     private List<Pane> Grid() {
        List<Pane>columns = new ArrayList();
               
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
           
       private void setDisc(int column){
           
           int row  = 5 ;
          Circle discCircle = new Circle(35);
           for(int i = 5; i >= 0; i--){
           if(board[row][column] == '.'){
               break;
           }else {
               row--;
           }
       }

        if (row < 0){
            
            return;
        }
        
        
       
        if(red){
            board[row][column] = 'r';
            discCircle.setFill(Color.RED);
            turn.setText("Black Turn");
            turn.setFill(Color.BLACK);
            winCheck( row ,column,"red");
            
        }
        else{
            board[row][column] = 'b';
            discCircle.setFill(Color.BLACK);
            turn.setText("Red Turn");
            turn.setFill(Color.RED);
            winCheck( row,column,  "black");
           
        }
        
     
  
        discCircle.setCenterX(40);
        discCircle.setCenterY(40);
        
        rootChip.getChildren().add(discCircle);
       discCircle.setTranslateX(column * 85 + 20 );
        discCircle.setTranslateY(row * 85  + 20);
        red = !red;
        
        
           
       }
       
       public void winCheck(int row, int column, String player){
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
               checkFull();
           }
           
       }
       
       public boolean checkHorizontal(int row, String player){
           
           String horiz = new String(board[row]);
          
           String win;
           
           if(player.equals("red")){
               win = "rrrr";
           }else{
               win = "bbbb";
           }
           
           if(horiz.contains(win)){
               return true;
           }else{
               return false;
           }
           
           
       }
       
       public boolean checkVertical(int column, String player){
           String vertical = ""; 
           String win;
           
           for(int i = 0; i < 6; i++){
               vertical = vertical + board[i][column];
           }
           
           
           if(player.equals("red")){
               win = "rrrr";
           }else{
               win = "bbbb";
           }
           
           if(vertical.contains(win)){
               return true;
           }else{
               return false;
           }
       }
       
       public boolean checkRigthDiagnal(int row, int column, String player){
           String rightDiag = "";
           String win;
          
           int topRow = row;
           int topColumn = column;
           for(int i = 0; i < 6; i++){
               
               if((topRow <= 0 || topColumn <0) || (topRow >6  || topColumn >= 6) ) {
                   break;
               }else{
                   topRow--;
                   topColumn++;
               }
               
               
           }
           
          
           
           for(int i = 0; i <6 ; i++){
               if(topRow < 6 && topColumn >= 0){
                   rightDiag = rightDiag + board[topRow][topColumn];
                   topRow++;
                   topColumn--;
               }
           }
           
           
          
           
            if(player.equals("red")){
               win = "rrrr";
           }else{
               win = "bbbb";
           }
           
           if(rightDiag.contains(win)){
               return true;
           }else{
               return false;
           }
           
       }
       
       public boolean checkLeftDiagnal(int row, int column, String player){
           String leftDiag = "";
           String win;
          
           int topRow = row;
           int topColumn = column;
           for(int i = 0; i < 6; i++){
               
               if((topRow <= 0 || topColumn <=0) || (topRow >6  || topColumn > 6) ) {
                   break;
               }else{
                   topRow--;
                   topColumn--;
               }
               
               
           }
           
         
           
           
           for(int i = 0; i <6 ; i++){
               if(topRow < 6 && topColumn < 7){
                   leftDiag = leftDiag + board[topRow][topColumn];
                   topRow++;
                   topColumn++;
               }
           }
           
           
          
           
            if(player.equals("red")){
               win = "rrrr";
           }else{
               win = "bbbb";
           }
           
           if(leftDiag.contains(win)){
               return true;
           }else{
               return false;
           }
       }
       
       public void checkFull(){
           String rows; 
           boolean full = false;
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
           }
       }
       
   
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(Game(), 640, 640));
        stage.show();
    }
    
}
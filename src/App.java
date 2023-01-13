
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

   
   /** 
    * @param args
    */
   public static void main(String[] args) {
      launch(args); // create a App object and call its start method
   }

   
   /** 
    * @param primaryStage
    * @throws Exception catture le eccezione del loadImplementation
    */
   @Override
   public void start(Stage primaryStage) throws Exception {
      
      try{
         
         // loads LoginWindow.fxml and configures the LoginWindowController
         Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/LoginWindow.fxml"));

         Scene scene = new Scene(root); // attach scene graph to scene
         primaryStage.setTitle("Lettore Musicale"); // displayed in window's title bar
         primaryStage.setScene(scene); // attach scene to stage
         primaryStage.show(); // display the stage
         primaryStage.setResizable(false);// not resizable stage


      }catch ( Exception e ){
         System.out.println(e.getMessage());
         e.printStackTrace();
         System.exit(0);
      }
   }
}
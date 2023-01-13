
package controllerFXML;

import java.net.URL;
import java.util.ResourceBundle;

import controller.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;



public class LoginWindow  implements  Initializable{


    @FXML private TextField signUpEmailTextField ;
    
    @FXML private TextField signUpUtenteIDTextfield ;

    @FXML private TextField signUpPasswordTextfield ;

    @FXML private TextField loginPasswordTextfield ;

    @FXML private TextField loginEmailTextfield ;

    @FXML private AnchorPane scenePane ;

    @FXML private Button loginButton; 

    @FXML private Button signinButton; 

    @FXML private Hyperlink link ;
    
    @FXML private TabPane tabPane;

    private Controller controller;

    private InterfacciaController interfacciaController;

    
    /** 
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        controller = new Controller();

        interfacciaController = controller;

        redirectToLoginView();

    }

    private void redirectToLoginView() {
        
        link.setOnMouseClicked(ev -> {
            tabPane.getSelectionModel().select(0);
        });
    }


    
    /** 
     * @param ev
     * @throws IOException
     */
    @FXML
    public void loginPressed(ActionEvent ev) throws IOException{
        
        String emailUtente = loginEmailTextfield.getText();
        String passwordUtente  = loginPasswordTextfield.getText();

        // check correct format textfield 
        if( loginEmailTextfield == null  ){
            //display allert
            controller.displayAllert(AlertType.INFORMATION, "Inserimento Errato", "Non hai inserito nessuna email o password");
            
        }
        else {

            if( loginEmailTextfield.getText().isEmpty()  ){
                //display allert
                controller.displayAllert(AlertType.INFORMATION, "Inserimento Errato", "Non hai inserito nessuna email o password");
            }
            else{
                
                if ( controller.validate(emailUtente) )
                {
                    //devo in qualche evitare errori nel apllciativo, se l'email non è valida esce questo errore
                    //Caused by: java.lang.NullPointerException: Cannot invoke "model.Utente.setutenteID(String)" because "this.loggedUtente" is null

                    if( controller.login(emailUtente, passwordUtente) ){

                        //creare un utente che ha le info dell'utente loggato
                        controller.displayAllert(AlertType.INFORMATION, "Login effetuato con successo", "Premi ok per continuare");
                        
                        //cambiare scena tramite controller 
                        interfacciaController.switchToHomeScene( (Stage)signUpPasswordTextfield.getScene().getWindow(), controller );
    

                    }else{
                        controller.displayAllert(AlertType.INFORMATION," Utente non Identificato", "Premi ok per riprovare");
                    }
                

                }
                else{
                    
                    //display allert
                    controller.displayAllert(AlertType.INFORMATION," Formato email Errata", "Premi ok per riprovare");
                }
            }   
        } 

    }  


    
    /** 
     * @param ev
     */
    @FXML
    private void signinPressed(ActionEvent ev) 
    {
        String emailUtente = signUpEmailTextField.getText();
        String passwordUtente  = signUpPasswordTextfield.getText();
        String utenteID = signUpUtenteIDTextfield.getText();

        // check correct format textfield 
        if( loginEmailTextfield == null || passwordUtente == null || utenteID == null  ){

            //display allert
            controller.displayAllert(AlertType.INFORMATION, "Inserimento Errato", "I campi non possono essere Vuoti");
            
        }
        else {

            if( signUpEmailTextField.getText().isEmpty() || signUpPasswordTextfield.getText().isEmpty() || signUpUtenteIDTextfield.getText().isEmpty() ){
                //display allert
                controller.displayAllert(AlertType.INFORMATION, "Inserimento Errato", "I campi non possono essere Vuoti");
            }
            else{
                
                if ( controller.validate(emailUtente)  )
                {
                    if ( controller.signIn(emailUtente, passwordUtente, utenteID) ){
                        
                        controller.displayAllert(AlertType.INFORMATION, "SignIn effettuato con successo", "Ti indirizzo alla pagina login per efettuare l'accesso");
                        
                        tabPane.getSelectionModel().select(0);

                    }else{
                        controller.displayAllert(AlertType.INFORMATION, "Utente o email esistente", "Premi ok per riprovare");
                    }
                }
                else{
                    
                    //display allert
                    controller.displayAllert(AlertType.INFORMATION, "Formato Email Errata", "Premi ok per riprovare");
                }
            }   
        } 

    }  

}


package controllerFXML;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.Controller;
import controller.InterfacciaController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.geometry.Insets;


public class AccountWindow implements Initializable {

    @FXML private Label nomeUtenteLabel;

    @FXML private Button insertAlbumButton;

    @FXML private TextField accountIDTextField;

    @FXML private TextField cognomeTextfield;

    @FXML private TextField emailTextField;

    @FXML private TextField nomeTextfield;

    @FXML private TextField passwordTextfield;

    @FXML private DatePicker datePicker;

    @FXML private Button homeButton;

    @FXML private Button modEmailButton;

    @FXML private Button modIDButton;

    @FXML private Button modInfoUtenteButton;

    @FXML private Button modPasswordButton;

    @FXML private ChoiceBox<String> tipoAccountChoiceBox;

    private Controller controller;
    private InterfacciaController interfacciaController;
    private String [] typeAcount = {"artista","ascoltatore"};

    public AccountWindow ( Controller controller ){
        
        this.interfacciaController = controller;

        this.controller = controller;
        
    }


    
    /** 
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize ( URL arg0, ResourceBundle arg1 ){
        
        
        tipoAccountChoiceBox.getItems().addAll(typeAcount);

        nomeUtenteLabel.setText(controller.getUtenteLogged().getUtenteID());

        hideInsertMusicButton ( );

        accountIDTextField.setText(controller.getUtenteLogged().getUtenteID());
        
        cognomeTextfield.setText(controller.getUtenteLogged().getcognomeUtente());

        emailTextField.setText(controller.getUtenteLogged().getemailUtente());

        nomeTextfield.setText(controller.getUtenteLogged().getNomeUtente());

        
    }

    
    /** 
     * @param event
     */
    @FXML
    void insertAlbumButtonPressed(ActionEvent event) {

        interfacciaController.switchToInsertMusicScene( (Stage)insertAlbumButton.getScene().getWindow(), controller);
    }

    
    /** 
     * @param event
     */
    @FXML
    void homeButtonPressed(ActionEvent event) {

        interfacciaController.switchToHomeScene( (Stage)homeButton.getScene().getWindow(), controller);
    }

    
    /** 
     * @param event
     */
    @FXML
    void modEmailButtonPressed(ActionEvent event) {

        if( emailTextField == null  ){
            //display allert
            controller.displayAllert(AlertType.INFORMATION, "meh", "meh null meh");
            
        }
        else {

            if( emailTextField.getText().isEmpty()  ){
                //display allert
                controller.displayAllert(AlertType.INFORMATION, "meh", "meh empty meh");
            }
            else{
                
                if( controller.updateEmailUtenteInDB( emailTextField.getText()) > 0)
                {
                    controller.displayAllert(AlertType.INFORMATION, "Email Aggiornata", "Tutto apposto email aggiornata con " +emailTextField.getText() );
                }
                else{
                    controller.displayAllert(AlertType.INFORMATION, "Errore Update email", "Email gia esistente");
                }

            }   
        } 
    }

    
    /** 
     * @param event
     */
    @FXML
    void modIDButtonPressed(ActionEvent event) {

        if( accountIDTextField == null  ){
            //display allert
            controller.displayAllert(AlertType.INFORMATION, "meh", "meh null meh");
            
        }
        else {

            if( accountIDTextField.getText().isEmpty()  ){
                //display allert
                controller.displayAllert(AlertType.INFORMATION, "meh", "meh empty meh");
            }
            else{
                
                if( controller.updateAccountIDinDB( accountIDTextField.getText()) > 0)
                {
                    controller.displayAllert(AlertType.INFORMATION, "Id Aggiornato", "Tutto apposto id aggiornato con" +accountIDTextField.getText() );
                }
                else{
                    controller.displayAllert(AlertType.INFORMATION, "Errore Update userid", "Nome ID gia esistente");
                }

            }   
        }  
    }

    
    /** 
     * @param event
     */
    @FXML
    void modInfoUtenteButtonPressed(ActionEvent event) {

        
        if( nomeTextfield == null  || cognomeTextfield == null  || datePicker.getValue() == null || tipoAccountChoiceBox.getValue() == null){
            //display allert
            controller.displayAllert(AlertType.INFORMATION, "meh", "ci sono dei campi vuoti, no bene");
            
        }
        else {

            if( nomeTextfield.getText().isEmpty() || cognomeTextfield.getText().isEmpty() ){
                //display allert
                controller.displayAllert(AlertType.INFORMATION, "meh", "ci sono dei campi vuoti, no bene");
            }
            else{
                
                controller.updateTableUtenteInfoUtente (nomeTextfield.getText(),cognomeTextfield.getText(),tipoAccountChoiceBox.getValue() ,datePicker.getValue().toString());
                controller.displayAllert(AlertType.INFORMATION, "All good", "Informazioni Aggiornate");
                
            }   
        }

        hideInsertMusicButton();
    }

    
    /** 
     * @param event
     */
    @FXML
    void modPasswordButtonPressed(ActionEvent event) {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Modifica Password");
    
        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
    
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
    
        TextField from = new TextField();
        from.setPromptText("Vecchia Password");
        TextField to = new TextField();
        to.setPromptText("Nuova Password");
        
        gridPane.add(from, 0, 0);
        gridPane.add(new Label("To:"), 1, 0);
        gridPane.add(to, 2, 0);
        
        dialog.getDialogPane().setContent(gridPane);
    
        // Request focus on the username field by default.
        Platform.runLater(() -> from.requestFocus());
        
        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType ) {

                if( controller.checkPassworUtente( from.getText(), to.getText()) > 0 ) 
                {   
                    controller.displayAllert(AlertType.INFORMATION, "Password Aggiornata", "Tutto apposto password aggiornata " );

                    return new Pair<>(from.getText(), to.getText());
                }
                else
                {  
                    controller.displayAllert( AlertType.CONFIRMATION, "Password Errata","La verifica della password non è andata a buon fine");

                }
                
            }

            return null;
        });
    
        Optional<Pair<String, String>> result = dialog.showAndWait();
    
        result.ifPresent(pair -> {
            System.out.println("From=" + pair.getKey() + ", To=" + pair.getValue());
        });
    }

    private void hideInsertMusicButton ( ){
        
        if ( (controller.getUtenteLogged()).gett_utente().compareTo("artista") == 0 ){
            insertAlbumButton.setVisible(true);
        }else{
            insertAlbumButton.setVisible(false);
        }
    }


}

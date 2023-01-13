package controllerFXML;

import java.net.URL;
import java.util.ResourceBundle;

import controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.*;



public class InsertMusicWindow implements Initializable {

    @FXML private Button checkAlbumButton;

    @FXML private Button checkSongButton;

    @FXML private ChoiceBox<String> choiceBoxTipoTraccia;

    @FXML private ColorPicker choiceColor;

    @FXML private TextField durataSongText;

    @FXML private TextField genereCanzone;

    @FXML private Button homeButton;

    @FXML private Button insertNewAlbumButton;

    @FXML private ListView<Traccia> listViewCanzoniInserite;

    @FXML private Label nomeAlbumLabel;

    @FXML private TextField nomeAlbumText;

    @FXML private TextField nomeCanzoneText;

    @FXML private Label nomeCasaDiscoLabel;

    @FXML private Label colorAlbumLabel;

    @FXML private TextField nomeCasaDiscoText;

    @FXML private DatePicker datePickerAlbum;
    
    private Controller controller;
    private InterfacciaController interfacciaController;


    private String [] choiceTipoTraccia = {"Originale","Remastering","Cover"};

    
    public InsertMusicWindow ( Controller controller ){
        this.controller = controller;
        this.interfacciaController = controller;
    }

    
    /** 
     * @param arg0
     * @param arg1
     */
    public void initialize ( URL arg0, ResourceBundle arg1 ){
        
        choiceBoxTipoTraccia.getItems().addAll(choiceTipoTraccia);

        controller.setMaxValueTracciaID();

        controller.getSelectedAlbum().setalbumID(-1);


    }

    
    /** 
     * @param event
     */
    @FXML //aggiorna le label con le info dell'album
    void checkAlbumButtonPressed(ActionEvent event) {
        
        //checkAlbumButton.setBackground(new Background( new BackgroundFill(Paint.valueOf(myColor) , null, null)));
        
        if( nomeCasaDiscoText == null  || nomeAlbumText == null || datePickerAlbum == null){
            //display allert
            controller.displayAllert(AlertType.INFORMATION, "meh", "meh null meh");
            
        }
        else {

            if( nomeCasaDiscoText.getText().isEmpty()  || nomeAlbumText.getText().isEmpty() ){
                //display allert
                controller.displayAllert(AlertType.INFORMATION, "meh", "meh empty meh");
            }
            else{
                
                controller.getSelectedAlbum().setalbumID( controller.getMaxIDvaluesInDB(1));

                if( controller.getSelectedAlbum().getalbumID() < 0 ) controller.displayAllert(AlertType.INFORMATION, "ERRORE", "Errore nel caricamento albumid");
                else{


                    controller.getSelectedAlbum().setartistaID(controller.getUtenteLogged().getUtenteID());
                    controller.getSelectedAlbum().setcasaDiscografica(nomeCasaDiscoText.getText());
                    controller.getSelectedAlbum().settitoloAlbum(nomeAlbumText.getText());
                    controller.getSelectedAlbum().setcoloreCopertina(choiceColor.getValue().toString());
    
                    nomeCasaDiscoLabel.setText(nomeCasaDiscoText.getText());
                    nomeAlbumLabel.setText(nomeAlbumText.getText());
                    colorAlbumLabel.setBackground(new Background( new BackgroundFill(Paint.valueOf(choiceColor.getValue().toString()) , null, null)));
                }

            }   
        }
    
    }
    
    
    
    /** 
     * @param event
     */
    @FXML //insert songs in the list view listViewCanzoniInserite
    void checkSongButtonPressed(ActionEvent event) {

        if( controller.getSelectedAlbum().getalbumID() < 0 ){
            controller.displayAllert(AlertType.INFORMATION, "Album non inserito", "Devi inserire prima un album per procedere all'inserimento delle tracce");
        }else{

            if( nomeCanzoneText == null  || genereCanzone == null || durataSongText == null || choiceBoxTipoTraccia == null ){
                //display allert
                controller.displayAllert(AlertType.INFORMATION, "meh", "meh null meh");
                
            }
            else {
    
                if( !nomeCanzoneText.getText().isEmpty()  || !genereCanzone.getText().isEmpty() || !durataSongText.getText().isEmpty() ){
    
    
                    //la durata deve avere la seguente regex 00:00:00 e deve essere Durata < '01:00:00' AND Durata > '00:01:00'
                    if( controller.isDurationFormat(durataSongText.getText() ) ){
                        

                        controller.getSelectedTraccia().settracciaID(controller.getMaxTracciaIDvalues());

                        if ( controller.getSelectedTraccia().gettracciaID() < 0 )  controller.displayAllert(AlertType.INFORMATION, "ERRORE", "Errore nel caricamento tracciaid");
                        else{

                            controller.getSelectedTraccia().setartistaID( controller.getUtenteLogged().getUtenteID() );
                            controller.getSelectedTraccia().setalbumID(controller.getSelectedAlbum().getalbumID());
                            controller.getSelectedTraccia().setversione(datePickerAlbum.getValue().toString());
                            controller.getSelectedTraccia().settitolo(nomeCanzoneText.getText());
                            controller.getSelectedTraccia().setgenere( genereCanzone.getText());
                            controller.getSelectedTraccia().setdurata( durataSongText.getText() );
                            controller.getSelectedTraccia().setcodT_Originale(0);
                            controller.getSelectedTraccia().sett_Traccia(choiceBoxTipoTraccia.getValue());

                            insertSongInListView( new Traccia( controller.getSelectedTraccia() ));
                        }
    
    
                    }else controller.displayAllert(AlertType.INFORMATION, "Tipo dirata non valido", "la durata deve avere la seguente regex 00:00:00 e deve essere Durata < '01:00:00' AND Durata > '00:01:00'");
                }else{
    
                    //display allert
                    controller.displayAllert(AlertType.INFORMATION, "meh", "meh empty meh");
                }
        
            }
        }

    }

    
    /** 
     * @param event
     */
    @FXML
    void homeButtonPressed(ActionEvent event) {
        interfacciaController.switchToHomeScene((Stage)homeButton.getScene().getWindow(), controller);
    }

    
    /** 
     * @param event
     */
    @FXML //inserisce prima l'album nel db poi le tracce 
    void insertNewAlbumButtonPressed(ActionEvent event) {

        if(controller.getSelectedAlbum().getalbumID() < 0 || listViewCanzoniInserite.getItems().isEmpty() ){

            controller.displayAllert(AlertType.INFORMATION, "Errore nell'inserimento album", "Non è stato inserito nessun album o l'album inserito è privo di tracce");

        }else{
            
            if( controller.insertAlbumDB( controller.getSelectedAlbum() )>0 ){

                if(controller.insertTracceDB( listViewCanzoniInserite.getItems()) >0){

                    controller.displayAllert(AlertType.INFORMATION, "Album Inserito Correttamente", "Ti rindirizzo alla pagina Home");
                    interfacciaController.switchToHomeScene((Stage)insertNewAlbumButton.getScene().getWindow(), controller);
                }else{
                    controller.displayAllert(AlertType.INFORMATION, "Errore nell'inserimento album", "Errore Nell'inserimento dati database");
                }

            }else{
                controller.displayAllert(AlertType.INFORMATION, "Errore nell'inserimento album", "Errore Nell'inserimento dati database");
            }

            
        }

    }


    
    /** 
     * @param traccia
     */
    private void insertSongInListView ( Traccia traccia){

    

        //se la lista è vuota procedo all'inserimento
        if(listViewCanzoniInserite.getItems().isEmpty() ){
            
            listViewCanzoniInserite.getItems().add(traccia);
            controller.incrementmaxValueTracciaID();
        }
        else{ //devo controllare se la traccia è gia stata inserita cosi chè non la inserisca di nuovo

            String titoloTraccia = traccia.gettitolo();

            
            boolean anyMatch = listViewCanzoniInserite.getItems().stream()
                                                            .map( Traccia::gettitolo )
                                                            .anyMatch( titolo -> titolo.equals( titoloTraccia ) );
                                                         

            if( anyMatch ){
                controller.displayAllert(AlertType.INFORMATION,"Traccia gia esistente","Non puoi inserire una traccia con titolo gia esistente" );
            }else{
                listViewCanzoniInserite.getItems().add(traccia);
                controller.incrementmaxValueTracciaID();
            }

        }
    }
}

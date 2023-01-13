package controllerFXML;


//import from SceneBuilder

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.control.Slider;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javafx.fxml.FXML;


//import from personal classes
import model.*;
import java.net.URL;

import java.text.DecimalFormat;

import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.collections.ObservableList;

import controller.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

//import for lo slider interattivo
import javafx.util.Duration;


public class HomeWindow implements Initializable {

    @FXML private Button backSecondButton;

    @FXML private Label currentDuration;

    @FXML private Button findSongButton;

    @FXML private TextField findSongTextField;

    @FXML private Hyperlink nomeArtistaLink;

    @FXML private Hyperlink nomeCanzoneLink;

    @FXML private Label labelColorSong;

    @FXML private ListView<Traccia> listViewTracce;

    @FXML private ListView<Utente> listViewUser;

    @FXML private ChoiceBox<String> menuUtenteChoiceBox;

    @FXML private Slider musicSlider;

    @FXML private Button nextSecondButton;

    @FXML private Button playButton;

    @FXML private Label totalDuration;

    @FXML private Slider volumeSlider;

    @FXML private Button muteButton;


    //oggetti per la litaview e altre funzionalita
    private ObservableList<Utente> userList = FXCollections.observableArrayList();
    private ObservableList<Traccia> tracciaList = FXCollections.observableArrayList();

    private String [] choiceBarAccountMenu = {"Account","Exit","Profilo"};
    private Controller controller;
    private InterfacciaController interfacciaController;

    private Duration totalTime;
    private Boolean playing = false;
    private final DecimalFormat formatter = new DecimalFormat("00.00");

    public HomeWindow ( Controller controller ){

        this.controller = controller;
        
        this.interfacciaController = controller;
    }
    
    
    /** 
     * @param arg0
     * @param arg1
     */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		menuUtenteChoiceBox.getItems().addAll(choiceBarAccountMenu);
		menuUtenteChoiceBox.setOnAction(this::getChoiceAccount);
        currentDuration.setTextFill(Color.WHITE);
        totalDuration.setTextFill(Color.WHITE);
        setListviewTracce();
        
        setListviewUser();

        listViewUser.setOrientation(Orientation.HORIZONTAL);

        //set on action clicchi su una canzone e te la inserisce nella barra lettore musicale
        listViewUser.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue, oldValue , newValue) -> {
                displayUserWindow( newValue );
            }
        );

        //set on action clicchi su una canzone e te la inserisce nella barra lettore musicale
        listViewTracce.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue, oldValue , newValue) -> {
                displayInfoTraccia( newValue );
            }
        );

        musicSlider.valueProperty().addListener(
            (ObservableValue, oldValue , newValue) -> {

                musicSlider.valueProperty().setValue( newValue );
                currentDuration.setText(String.valueOf(formatter.format( newValue.doubleValue() )));
            }
                
        );
        

	}

    public void setListviewUser ( ){

        
        //inserisco la lista nella ObservableList tracceList
        userList.setAll( controller.getListUtente(null) );

        //Inserisco la lista ObservableList tarccelist nella listView
        listViewUser.setItems( userList );

    }

    public void setListviewTracce ( ){
        
        //inserisco la lista nella ObservableList tracceList
        tracciaList.setAll( controller.getListTracce(null) );

        //Inserisco la lista ObservableList tarccelist nella listView
        listViewTracce.setItems( tracciaList );
    }


    @FXML void backSecondPressed(ActionEvent event) {
        Duration currentTime = Duration.seconds(musicSlider.getValue());

        musicSlider.setValue( currentTime.subtract(Duration.seconds(15)).toSeconds() );
    }

    @FXML void nextSecondButtonPressed(ActionEvent event) {
        Duration currentTime = Duration.seconds(musicSlider.getValue());

        musicSlider.setValue( currentTime.add(Duration.seconds(15)).toSeconds() );
    }

    @FXML void muteButtonPressed (ActionEvent event )
    {
        controller.displayAllert(AlertType.INFORMATION, "Traccia Senza Audio", "Non posso aumentare il volume con tracce senza audio");
    }

    @FXML void findSongButtonPressed(ActionEvent event) {


        userList.setAll( controller.getListUtente( findSongTextField.getText()) );
        listViewUser.setItems(userList);

        tracciaList.setAll( controller.getListTracce(findSongTextField.getText()) );
        listViewTracce.setItems( tracciaList);


    }


    @FXML void playButtonPressed(ActionEvent event) {
        
        
        playing = ! playing; 

        //aggiungere alla lista ascolti , riprodotta
        if (playing ){
            
            playButton.setText("Pause");
            controller.displayAllert(AlertType.INFORMATION, "Traccia Senza Audio", "Non posso avviare la traccia");
        }
        else{
            playButton.setText("Play");
            controller.displayAllert(AlertType.INFORMATION, "Traccia Senza Audio", "Non posso avviare la traccia");
        }
    }

    @FXML void nomeArtistaLinkPressed(ActionEvent event) {

        controller.setSelectedUtente( nomeArtistaLink.getText() );
        interfacciaController.switchToProfiloArtistaScene((Stage)nomeArtistaLink.getScene().getWindow(), controller);
    }

    @FXML void nomeCanzoneLinkPressed(ActionEvent event) {
        //se si preme sul link della canzone allora non ce nessun cambio scena
        // bensi la list view cambiera e verrano proiettate tutte le canzoni   
        // che fanno parte dell'album della canzone selezionata ( copiato spudoratamente da spotify xD)

        tracciaList.setAll( controller.getListeTracceByAlbum( ) );
        listViewTracce.setItems( tracciaList);

    }


    
    /** 
     * @param traccia
     */
    //Aggiorna i valori dello slider e l'immagine link che porta al profilo Artista
    // in base alla canzone selezionata
    private void displayInfoTraccia ( Traccia traccia )
    {
        
        if (traccia != null )
        {
            //inizializzo lalbum di appartenenza della traccia e la traccia selezionata nel controller 
            controller.setSelectedAlbum( traccia );
            controller.setSelectedTraccia ( traccia);
            
            //inizializzo le informazioni necessarie per il lettore musicale
            totalTime = Duration.minutes(traccia.getdurata());
            totalDuration.setText( String.valueOf(formatter.format(Math.floor(totalTime.toSeconds()))));
            musicSlider.setMax((totalTime.toSeconds()));

            //inizializzo l'hyperlink della traccia e il nome dell'artista
            nomeCanzoneLink.setText(traccia.gettitolo());
            nomeArtistaLink.setText(traccia.getartistaID());
            
            //imposto il colore dell'album di appartenenza della canzone cliccata
            labelColorSong.setBackground(new Background( new BackgroundFill(Paint.valueOf(controller.getSelectedAlbum().getcoloreCopertina()) , null, null)));
            
            //inserisco nel database che l'utente ha ascoltato questa canzone
            controller.insertAscoltiDB( );

        }
        else
        {
            totalTime = Duration.minutes(0);
            totalDuration.setText( String.valueOf(formatter.format(Math.floor(totalTime.toSeconds()))));
            musicSlider.setMax(0);
        }

    }


    
    /** 
     * @param utente
     */
    private void displayUserWindow ( Utente utente ){

        controller.setSelectedUtente( utente );

        if( controller.getSelectedUtente().gett_utente().compareTo("artista") == 0){

            interfacciaController.switchToProfiloArtistaScene((Stage)findSongButton.getScene().getWindow(), controller);

        }else interfacciaController.switchToProfiloAscoltatoreScene((Stage)findSongButton.getScene().getWindow(), controller);
        

    }


    
    /** 
     * @param event
     */
    private void getChoiceAccount(ActionEvent event) {
		
		String myChoice = menuUtenteChoiceBox.getValue();
		
        System.out.println("Processing switching scene");
        switch(myChoice) {
            case "Account":
                interfacciaController.switchToAccountScene((Stage)menuUtenteChoiceBox.getScene().getWindow(), controller);
            break;

            case "Exit":
                interfacciaController.exitApp();
            break;

            case "Profilo":
                controller.setSelectedUtente( controller.getUtenteLogged());
                interfacciaController.switchToProfiloAscoltatoreScene((Stage)menuUtenteChoiceBox.getScene().getWindow(), controller);

            break;

            default:
                return;
            
        }

	}

    
    /** 
     * @param controller
     */
    public void setController ( Controller controller ){
        this.controller = controller;
    }

}

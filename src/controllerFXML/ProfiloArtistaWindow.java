package controllerFXML;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Album;
import model.Traccia;

//IMPORTANTE PRIMA DI CAMBIARE SCENA O CHIUDERE L'APPLICATIVO BISOGNA MODIFICARE I DATI SUL DB
//RIGUARDANTI I FOLLOW , NON PUO ESSERE FATTO DURANTE L'APPLICAZIONE PERCHE L'UTENTE PUO UNFOLLOWARE E FOLLOWARE SPAMMANDO
public class ProfiloArtistaWindow implements Initializable {


    @FXML
    private Button followButton;

    @FXML
    private Button homeButton;

    @FXML
    private Label labelNumFollowers;

    @FXML
    private Label labelNumFollowing;

    @FXML
    private Label labelOrarioPrefertio;

    @FXML
    private ListView<String> listViewFollowers;

    @FXML
    private ListView<String> listViewFollowing;

    @FXML
    private ListView<Album> listViiewAlbum;

    @FXML
    private ListView<Traccia> listViiewCanzoni;

    @FXML
    private ListView<String> listViiewCanzoniPopolari;

    @FXML
    private Label numAscoltatoriTotaliLabel;

    @FXML
    private Label selectedUtenteLabel;

    private Controller controller ;
    private InterfacciaController interfacciaController;

    private boolean follow;

    private ObservableList<String> followersList = FXCollections.observableArrayList();
    private ObservableList<String> followingList = FXCollections.observableArrayList();

    private ObservableList<String> popularSong = FXCollections.observableArrayList();

    private ObservableList<Traccia> listSong = FXCollections.observableArrayList();
    private ObservableList<Album> listAlbum = FXCollections.observableArrayList();

    public ProfiloArtistaWindow ( Controller controller ){
        this.controller = controller;
        this.interfacciaController = controller;
    }

    
    /** 
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize (URL arg0, ResourceBundle arg1 ){

        hideFollowButton ( );

        labelOrarioPrefertio.setText( controller.getOrarioPreferitoByUser(controller.getOrarioPreferitoByUser(controller.getSelectedUtente().getUtenteID())));

        selectedUtenteLabel.setText(controller.getSelectedUtente().getUtenteID());

        numAscoltatoriTotaliLabel.setText(controller.getTotalAscolti( controller.getSelectedUtente().getUtenteID()));

        listViewFollowers.setOrientation(Orientation.HORIZONTAL);
        listViewFollowing.setOrientation(Orientation.HORIZONTAL);
        
        setTextFollowButton( );

        setItemsInAscoltiListView();

        setItemsInFollowersListView();

        setItemsInFollowingListView();

        setItemsInSongListView();

        setItemsInAlbumListView();
    }


    
    /** 
     * @param event
     */
    @FXML
    void followButtonPressed(ActionEvent event) {

        //aggiungere alla lista ascolti , riprodotta
        if ( follow ){
            
            followButton.setText("UnFollow");
            // inserire nella lista followers del selected utente
            //inserire nella lista following del logged utente
            controller.followUser();

            setItemsInFollowersListView();
            
            follow = false ;

        }
        else{
            followButton.setText("Follow");
            controller.unFollowUser();

            follow = true;
            setItemsInFollowersListView();

        }
    }

    
    /** 
     * @param event
     */
    @FXML
    void homeButtonPressed(ActionEvent event) {

        controller.insertFollowInDB( );
        interfacciaController.switchToHomeScene((Stage)homeButton.getScene().getWindow(), controller);
    }

    private void setTextFollowButton ( ){


        //se è gia seguito bisogna settare il bottone in unfollow else follow
        //in questa funzione potrei inizializzare anche l'oggetto follow
        if( controller.checkFollowedUser( ) ){

            followButton.setText("Unfollow");
            follow = false;

        }else{
            follow = true;
            followButton.setText("Follow");
        }

    }

    public void setItemsInFollowersListView ()
    {

        followersList.setAll( controller.getSelectedUserFollowers());

        listViewFollowers.setItems( followersList );

        System.out.println("FollowersListView populated");

        labelNumFollowers.setText(String.valueOf(controller.getNumSelectedUserFollowers()));
        
    }
    
    public void setItemsInFollowingListView ()
    {

        followingList.setAll( controller.getSelectedUserFollowing());

        listViewFollowing.setItems( followingList );

        System.out.println("FollowingListView populated");

        labelNumFollowing.setText(String.valueOf(controller.getNumSelectedUserFollowing()));
    }   

    public void setItemsInAscoltiListView ( )
    {

        //prelevo le tracce piu ascoltate

        List<String> ascoltiArtista = controller.getAscoltiSongByArtista( selectedUtenteLabel.getText() );

        if( ascoltiArtista != null ){

            popularSong.setAll(ascoltiArtista);

            listViiewCanzoniPopolari.setItems( popularSong );
            
            System.out.println("AscoltiListView populated");

        }else{
            System.out.println("Ouston abbiamo un problema");
        }

        
    }

    public void setItemsInAlbumListView ( )
    {
        List<Album> album = controller.getListAlbumsByArtista(controller.getSelectedUtente().getUtenteID());

        if( album != null ){
            listAlbum.setAll( album );

            listViiewAlbum.setItems( listAlbum );
    
            System.out.println("DiscografiaView populated");
        }else{
            System.out.println("Ouston abbiamo un problema");
        }
    }

    public void setItemsInSongListView ( ){
        List<Traccia> tracce = controller.getListTracceByArtista(controller.getSelectedUtente().getUtenteID());

        if( tracce != null ){
            listSong.setAll( tracce );

            listViiewCanzoni.setItems( listSong );

            System.out.println("CanzoniView populated");
        }else{
            System.out.println("Ouston abbiamo un problema");
        }

    }

    private void hideFollowButton ( ){

        if( controller.getUtenteLogged().getUtenteID().compareToIgnoreCase( controller.getSelectedUtente().getUtenteID() )== 0 ){

            followButton.setVisible(false);
        }
    }

    
    /** 
     * @param controller
     */
    public void setController ( Controller controller ){
        this.controller = controller;
    }
}

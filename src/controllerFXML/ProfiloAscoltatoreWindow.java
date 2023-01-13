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
import model.Utente;


public class ProfiloAscoltatoreWindow implements Initializable{

    @FXML
    private Button followButton;

    @FXML
    private ListView<Utente> listViewArtistiPref;

    @FXML
    private ListView<String> listViewBraniPref;

    @FXML
    private ListView<String> listViewFollowers;

    @FXML
    private ListView<String> listViewFollowing;

    @FXML
    private Button homeButton;

    @FXML
    private Label labelNumeroFollower;

    @FXML
    private Label labelNumeroFollowing;

    @FXML
    private Label labelOrarioPiuAscoltato;

    @FXML
    private Label selectedUtenteLabel;

    private Controller controller ;
    private InterfacciaController interfacciaController;
    private boolean follow;

    private ObservableList<String> followersList = FXCollections.observableArrayList();
    private ObservableList<String> followingList = FXCollections.observableArrayList();

    private ObservableList<String> popularSong = FXCollections.observableArrayList();

    private ObservableList<Utente> popularArtisti = FXCollections.observableArrayList();

    public ProfiloAscoltatoreWindow ( Controller controller ){
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

        listViewArtistiPref.setOrientation(Orientation.HORIZONTAL);
        listViewFollowers.setOrientation(Orientation.HORIZONTAL);
        listViewFollowing.setOrientation(Orientation.HORIZONTAL);

        labelOrarioPiuAscoltato.setText( controller.getOrarioPreferitoByUser(controller.getSelectedUtente().getUtenteID()));

        selectedUtenteLabel.setText(controller.getSelectedUtente().getUtenteID());

        setTextFollowButton( );

        setItemsInBraniPrefListView();

        setItemsInFollowersListView();

        setItemsInFollowingListView();

        setItemsInArtistiListView ( );
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

        labelNumeroFollower.setText(String.valueOf(controller.getNumSelectedUserFollowers()));
        
    }
    
    public void setItemsInFollowingListView ()
    {

        followingList.setAll( controller.getSelectedUserFollowing());

        listViewFollowing.setItems( followingList );

        System.out.println("FollowingListView populated");

        labelNumeroFollowing.setText(String.valueOf(controller.getNumSelectedUserFollowing()));
    }   

    public void setItemsInBraniPrefListView ( )
    {

        //prelevo le tracce piu ascoltate

        List<String> braniPref = controller.getTraccePrefByUser( selectedUtenteLabel.getText() );

        if( braniPref != null ){

            popularSong.setAll(braniPref);

            listViewBraniPref.setItems( popularSong );
            
            System.out.println("AscoltiListView populated");

        }else{
            System.out.println("Ouston abbiamo un problema");
        }

        
    }

    private void hideFollowButton ( ){

        if( controller.getUtenteLogged().getUtenteID().compareToIgnoreCase( controller.getSelectedUtente().getUtenteID() )== 0 ){

            followButton.setVisible(false);
        }
    }

    public void setItemsInArtistiListView ( ){

        //prelevo le tracce piu ascoltate

        List<Utente> ascoltiArtista = controller.getArtistiPrefByUser( selectedUtenteLabel.getText() );

        if( ascoltiArtista != null ){

            popularArtisti.setAll(ascoltiArtista);

            listViewArtistiPref.setItems( popularArtisti );
            
            System.out.println("AscoltiListView populated");

        }else{
            System.out.println("Ouston abbiamo un problema");
        }
    }

    
    /** 
     * @param controller
     */
    public void setController ( Controller controller ){
        this.controller = controller;
    }
}

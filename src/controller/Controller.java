package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import controllerFXML.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import model.*;
import dao.*;


public class Controller implements InterfacciaController{
    
    private Utente selectedUtente;
    private Utente loggedUtente ;

    private Traccia selectedTraccia;
    private Album selectedAlbum;

    private Follow selectedUserFollow;
    private int maxValueTracciaID;

    private ConnectionDAO connectionDAO;


    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private List<Traccia> listTraccia;
    private List<Utente> listUtente;
    private List<Album> listAlbum;
    private List<Traccia> tracciaInseriteInAlbum;

    public Controller ( ){ 

        this.connectionDAO = new ConnectionDAO();

    };


    
    /** 
     * @param currentStage
     * @param controller
     */
    public void switchToHomeScene ( Stage currentStage, Controller controller ){

        //changin scene to account
        try {

            //carico i dati che verra utilizzati nel HomeWindow.fxml
            setListAlbum();

            setListTracce();

            setListUtente();

            //carico il controller che verra utilizzato dal file fxml HomeWindow.fxml
            HomeWindow homeWindow = new HomeWindow(controller);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/HomeWindow.fxml"));

            loader.setController(homeWindow);

            Parent root = loader.load();

            Scene scene = new Scene(root);

            currentStage.setScene(scene);

            System.out.println("Processing changing scene to HomeWindow...");
            
            currentStage.setOnCloseRequest(e -> exitApp());

            currentStage.show();

            System.out.println("Processing gone good");
            
        }catch ( IOException ex ){
            ex.printStackTrace();
            System.out.println("Processing gone Wrong "+ ex.getMessage());
        } 
    }

    
    /** 
     * @param currentStage
     * @param controller
     */
    public void switchToProfiloArtistaScene ( Stage currentStage, Controller controller ){

        //changin scene to account
        try {

            setFollow( );

            //carico il controller che verra utilizzato dal file fxml ProfiloWindow.fxml
            ProfiloArtistaWindow profiloWindow = new ProfiloArtistaWindow(controller);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ProfiloArtistaWindow.fxml"));

            loader.setController(profiloWindow);

            Parent root = loader.load();

            Scene scene = new Scene(root);

            currentStage.setScene(scene);

            System.out.println("Processing changing scene to AccountWindow...");
            currentStage.setOnCloseRequest(e -> exitApp());
            currentStage.show();
            
            System.out.println("Processing gone good");
            
        }catch ( IOException ex ){
            ex.printStackTrace();
            System.out.println("Processing gone Wrong "+ ex.getMessage());
        } 
    }

    
    /** 
     * @param currentStage
     * @param controller
     */
    public void switchToInsertMusicScene ( Stage currentStage, Controller controller ){
        //changin scene to account
        try {
          //carico il controller che verra utilizzato dal file fxml InsertMusicWindow.fxml

            System.out.println("Processing changing scene to InsertMusicWindow...");

            controller.setSelectedTraccia();
            controller.setSelectedAlbum();

            InsertMusicWindow InsertMusicWindow = new InsertMusicWindow(controller);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/InsertMusicWindow.fxml"));

            System.out.println("Processing...");

            //inizializzo il controller della finestra HomeWindow 
            loader.setController( InsertMusicWindow );
            

            System.out.println("Processing...");
            Parent root = loader.load();



            Scene scene = new Scene(root);

            currentStage.setScene(scene);
            currentStage.setOnCloseRequest(e -> exitApp());
            System.out.println("Processing...");

            currentStage.show();
            
            System.out.println("Processing gone good");
            
        }catch ( IOException ex ){
            ex.printStackTrace();
            System.out.println("Processing gone Wrong "+ ex.getMessage());
        }      
    }   

    
    /** 
     * @param currentStage
     * @param controller
     */
    public void switchToProfiloAscoltatoreScene ( Stage currentStage, Controller controller ){

        //changin scene to account
        try {

            setFollow();
            //carico il controller che verra utilizzato dal file fxml ProfiloWindow.fxml
            ProfiloAscoltatoreWindow profiloWindow = new ProfiloAscoltatoreWindow(controller);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ProfiloAscoltatoreWindow.fxml"));

            loader.setController(profiloWindow);

            Parent root = loader.load();

            Scene scene = new Scene(root);

            currentStage.setScene(scene);

            System.out.println("Processing changing scene to ProfiloPersonaleWIndow...");
            currentStage.setOnCloseRequest(e -> exitApp());
            currentStage.show();
            
            System.out.println("Processing gone good");
            
        }catch ( IOException ex ){
            ex.printStackTrace();
            System.out.println("Processing gone Wrong "+ ex.getMessage());
        } 
    }

    public void exitApp ( ){

        // messaggio quitting the app 
        System.out.println("Processing quitting app...");

        //aggiornare valori nel db


        //deallocare tutto il necessario 
        connectionDAO.closeConnection();

        Platform.exit();

        System.out.println("Processing gone good");
    }

    
    /** 
     * @param currentStage
     * @param controller
     */
    public void switchToAccountScene ( Stage currentStage, Controller controller){
                //changin scene to account
        try {

            //carico il controller che verra utilizzato dal file fxml HomeWindoq.fxml
            AccountWindow accountWindow = new AccountWindow(controller);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/AccountWindow.fxml"));

            loader.setController(accountWindow);

            Parent root = loader.load();

            Scene scene = new Scene(root);

            currentStage.setScene(scene);

            System.out.println("Processing changing scene to AccountWindow...");

            currentStage.setOnCloseRequest(e -> exitApp());

            currentStage.show();
            
            System.out.println("Processing gone good");
            
        }catch ( IOException ex ){
            ex.printStackTrace();
            System.out.println("Processing gone Wrong "+ ex.getMessage());
        } 
    }

    //setters

    public void setListUtente ( ){

        this.listUtente = connectionDAO.getUtenteDAO().getListUtente();

        if ( listUtente.isEmpty() ) { System.out.println ( "Ouston abbiamo un probema");}

    }

    public void setListTracce ( ){


        this.listTraccia = connectionDAO.getTracciaDAO().getTableTraccia(null);
        
        if(listTraccia.isEmpty()){
            System.out.println("Ouston c'è un problema");
        }

    }

    public void setListAlbum ( ){
        

        this.listAlbum = connectionDAO.getAlbumDAO().getListAlbum(null);

        if(listAlbum.isEmpty()){
            System.out.println("Ouston c'è un problema");
        }

    }

    public void setFollow ( ){

        this.selectedUserFollow = new Follow( selectedUtente.getUtenteID() );
        this.selectedUserFollow.setFollowing(connectionDAO.getFollowDAO().getFollowDB( selectedUtente.getUtenteID(), 1));
        this.selectedUserFollow.setFollowers( connectionDAO.getFollowDAO().getFollowDB( selectedUtente.getUtenteID(), 0));
    }

    public void setMaxValueTracciaID (  ){
        
        this.maxValueTracciaID = getMaxIDvaluesInDB(0);
    }

    
    /** 
     * @param selectedTraccia
     */
    public void setSelectedAlbum ( Traccia selectedTraccia ){

        //cerco l album di appartenenza della traccia selezionata e me lo salvo 
        this.selectedAlbum =  getListAlbums(  selectedTraccia.getalbumID()  ).get(0) ;
        
    }

    public void setSelectedTraccia ( ){
        this.selectedTraccia = new Traccia();
    }

    public void setSelectedAlbum (  ){

        this.selectedAlbum = new Album();
        
    }

    
    /** 
     * @param traccia
     */
    public void setSelectedTraccia ( Traccia traccia ){
        this.selectedTraccia = traccia;
    }

    
    /** 
     * @param utente
     */
    public void setSelectedUtente  ( Utente utente ){
        this.selectedUtente =  utente ;
    }

    
    /** 
     * @param utenteID
     */
    public void setSelectedUtente ( String utenteID){

        for( Utente u : listUtente ){

            if ( u.getUtenteID().compareToIgnoreCase(utenteID.toLowerCase()) == 0 ){
                this.selectedUtente = new Utente(u);
            }
        }
        
    }

    
    /** 
     * @param traccia
     */
    public void setListTracceSelected ( Traccia traccia ){

        tracciaInseriteInAlbum.add( new Traccia(traccia) );
    }

    
    /** 
     * @param utenteID
     * @return List<String>
     */
    //getters

    public List<String> getAscoltiSongByArtista ( String utenteID ){

        return connectionDAO.getAscoltiDAO().getAscoltiSongByArtista( utenteID );

    }
    
    
    /** 
     * @param utenteID
     * @return String
     */
    public String getTotalAscolti ( String utenteID ){
        
        return String.valueOf(connectionDAO.getAscoltiDAO().getTotalAscolti( utenteID ));

    }

    
    /** 
     * @param utenteid
     * @return String
     */
    public String getOrarioPreferitoByUser (String utenteid ){

        return connectionDAO.getAscoltiDAO().getOrarioPreferitoByUser(utenteid);

    }   

    
    /** 
     * @param utenteid
     * @return List<String>
     */
    public List<String> setArtistiPrefByUserDB ( String utenteid ){
        
        return connectionDAO.getAscoltiDAO().getArtistiPrefByUser( utenteid); 

    }
    
    
    /** 
     * @param utenteid
     * @return List<String>
     */
    public List<String> getTraccePrefByUser ( String utenteid ){

        return connectionDAO.getAscoltiDAO().getTraccePrefInBD( utenteid); 


    }


    
    /** 
     * @return List<String>
     */
    public List<String> getSelectedUserFollowers ( ){
        return selectedUserFollow.getFollowers();
    }

    
    /** 
     * @return List<String>
     */
    public List<String>  getSelectedUserFollowing ( ){
        return selectedUserFollow.getFollowing();
    }


    
    /** 
     * @param val
     * @return int
     */
    public int getMaxIDvaluesInDB ( int val ){
        //val == 0 traccia ; val == 1 album

        if( val == 0 ){

            return connectionDAO.getTracciaDAO().getMaxIDvaluesInTable (  ) +1;

        }else{

            return connectionDAO.getAlbumDAO().getMaxIDvaluesInTable() +1 ;

        }
    }   

    
    /** 
     * @return Traccia
     */
    public Traccia getSelectedTraccia ( ){
        return selectedTraccia;
    }


    
    /** 
     * @return Album
     */
    public Album getSelectedAlbum ( ){
        return selectedAlbum;
    }

    
    /** 
     * @return int
     */
    public int getMaxTracciaIDvalues ( ){
        return maxValueTracciaID;
    }

    
    /** 
     * @param key
     * @return List<Utente>
     */
    public List<Utente> getListUtente ( String key ){
        
        if ( key == null ){ 
            return listUtente;
        }

        Predicate<Utente> filteringUsers = 
        t -> (t.getUtenteID().toLowerCase().matches("(.*)" + key.toLowerCase() +"(.*)") );

        List<Utente> users = listUtente.stream()
                                    .filter( filteringUsers )
                                    .collect(Collectors.toList());
        
        return users;
    }
   
    
    /** 
     * @return Utente
     */
    public Utente getSelectedUtente ( ){
        return selectedUtente;
    }

    
    /** 
     * @return Utente
     */
    public Utente getUtenteLogged ( ){
        return loggedUtente;
    }

    
    /** 
     * @param key
     * @return List<Traccia>
     */
    public List<Traccia> getListTracce ( String key ){
        
        if( key == null ){
            return listTraccia;
        }

        Predicate<Traccia> filteringSongs = 
            t -> (t.gettitolo().toLowerCase().matches("(.*)" + key.toLowerCase() +"(.*)") || t.getartistaID().toLowerCase().matches("(.*)" + key.toLowerCase() +"(.*)")  );

        List<Traccia> songs = listTraccia.stream()
                                    .filter( filteringSongs )
                                    .collect(Collectors.toList());
        
        return songs;
    }

    
    /** 
     * @return List<Traccia>
     */
    public List<Traccia> getListeTracceByAlbum ( ){

        Predicate<Traccia> filteringSongs = 
        t -> (t.getalbumID() == selectedAlbum.getalbumID() );

        List<Traccia> songs = listTraccia.stream()
                                    .filter( filteringSongs )
                                    .collect(Collectors.toList());
        
        return songs;
    }

    
    /** 
     * @param key
     * @return List<Album>
     */
    public List<Album> getListAlbums ( int key  ){

        if ( key == 0 ){ 
            return listAlbum;
        }

        Predicate<Album> filteringAlbums = 
        t -> (t.getalbumID() == key);

        List<Album> albums = listAlbum.stream()
                                      .filter( filteringAlbums )
                                      .collect(Collectors.toList());
        
        return albums;
    }

    
    /** 
     * @param key
     * @return List<Album>
     */
    public List<Album> getListAlbumsByArtista ( String key  ){

        if ( key == null ){ 
            return listAlbum;
        }

        Predicate<Album> filteringAlbums = 
        t -> (t.getartistaID().toLowerCase().matches("(.*)" + key.toLowerCase() +"(.*)") );

        List<Album> albums = listAlbum.stream()
                                    .filter( filteringAlbums )
                                    .collect(Collectors.toList());


        return albums;
    }

    
    /** 
     * @param key
     * @return List<Traccia>
     */
    public List<Traccia> getListTracceByArtista ( String key ){
        if( key == null ){
            return listTraccia;
        }

        Predicate<Traccia> filteringSongs = 
            t -> ( t.getartistaID().toLowerCase().matches("(.*)" + key.toLowerCase() +"(.*)")  );

        List<Traccia> songs = listTraccia.stream()
                                    .filter( filteringSongs )
                                    .collect(Collectors.toList());
        


        return songs;
    }


    
    /** 
     * @return int
     */
    public int getNumSelectedUserFollowers( ){
        return selectedUserFollow.getFollowers().size();
    }

    
    /** 
     * @return int
     */
    public int getNumSelectedUserFollowing( ){
        return selectedUserFollow.getFollowing().size();
    }

    
    /** 
     * @param utenteid
     * @return List<Utente>
     */
    public List<Utente> getArtistiPrefByUser ( String utenteid){
        
        List<String> ascolti = setArtistiPrefByUserDB(utenteid);

        if ( ascolti.isEmpty() ) return new ArrayList<Utente>();

        List<Utente> listArtistiPref = new ArrayList<>(listUtente);

        Iterator<Utente> artistiIterator = listArtistiPref.iterator();

        //posso utilizzare anche lo stream map per creare un codice diverso ho deciso di usare questo metodo 

        boolean find = false;

        while (artistiIterator.hasNext() ){
            String selectedUser = artistiIterator.next().getUtenteID();

            for( String u : ascolti ){
                //System.out.println(" +"+selectedUser+" ?"+u+" --> "+find);
                if ( selectedUser.compareToIgnoreCase( u ) == 0 && !find){
                    find = true;
                    //System.out.println( "-"+selectedUser +" / " + u +" --> "+ find);
                }
            }

            if ( !find ) artistiIterator.remove();
            find = false;
        }
        
        return listArtistiPref;
    
    }


    
    /** 
     * @param oldPassword
     * @param newPassword
     * @return int
     */
    //---------

    public int checkPassworUtente ( String oldPassword, String newPassword ){


        if( loggedUtente.getkeyPassword().compareTo(oldPassword) != 0 ) return 0;

        loggedUtente.setkeyPassword(newPassword);

        if ( connectionDAO.getUtenteDAO().updatePasswordUtenteInDB( loggedUtente.getemailUtente(), newPassword) > 0 ) return 1;

        return 0;

    }

    public void insertFollowInDB ( ){

        if ( connectionDAO.getFollowDAO().insertFollowInDB(  selectedUserFollow ) > 0 ){
            System.out.println("Processing insert Follow item in followers gone good :D");

        }else{ 
            System.out.println("Processing insert Follow item in followers gone bad :C");
        }
 
    }

    
    /** 
     * @param Email
     * @param password
     * @return boolean
     */
    public boolean login ( String Email , String password){
        
        if( connectionDAO.getUtenteDAO().findUtente(Email, null) ){

            if( connectionDAO.getUtenteDAO().checkPassworUtente(Email, password) ){

                this.loggedUtente = connectionDAO.getUtenteDAO().getUtente(Email);
                return true;

            }else{

                this.loggedUtente.setutenteID("utenteNonIdentificato");
                return false;
            }
        }
        
        return false;

    }

    
    /** 
     * @param emailUtente
     * @param passwordUtente
     * @param utenteID
     * @return boolean
     */
    public boolean signIn (String emailUtente, String passwordUtente, String utenteID ){

        
        if ( ! connectionDAO.getUtenteDAO().findUtente(emailUtente, utenteID) ){
                
            connectionDAO.getUtenteDAO().signIn(emailUtente, passwordUtente, utenteID);

            return true; 

        }else{

            return false;
        }

    }


    
    /** 
     * @param email
     * @return int
     */
    public int updateEmailUtenteInDB ( String email){

        int result = connectionDAO.getUtenteDAO().updateEmailUtenteInDB(loggedUtente.getUtenteID(), email );

        if( result > 0){
            loggedUtente.setemailUtente( email );
        }
        return result;

    }
    
    
    /** 
     * @param accountID
     * @return int
     */
    public int updateAccountIDinDB ( String accountID ){


        int result = connectionDAO.getUtenteDAO().updateAccountIDinDB(loggedUtente.getemailUtente(), accountID );

        if( result > 0){
            loggedUtente.setutenteID(accountID);
        }

        return result;

    }

    
    /** 
     * @param nome
     * @param cognome
     * @param tipoAccount
     * @param birthDate
     * @return int
     */
    public int updateTableUtenteInfoUtente ( String nome ,String cognome ,String tipoAccount ,String birthDate ){

    
        Utente tmpUtente = new Utente();
        
        tmpUtente.setutenteID(loggedUtente.getUtenteID());
        tmpUtente.setnomeUtente(nome);
        tmpUtente.setcognomeUtente(cognome);
        tmpUtente.setdataNascita(birthDate);
        tmpUtente.sett_utente(tipoAccount);

        int result = connectionDAO.getUtenteDAO().updateInfoUtente( tmpUtente );
        if( result > 0){

            loggedUtente.setnomeUtente(nome);
            loggedUtente.setcognomeUtente(cognome);
            loggedUtente.setdataNascita(birthDate);
            loggedUtente.sett_utente(tipoAccount);
        }

        return result;


    }


    
    /** 
     * @param album
     * @return int
     */
    public int insertAlbumDB ( Album album ){

        return connectionDAO.getAlbumDAO().insertAlbumInDB( new Album(album) );

    }

    
    /** 
     * @param tracce
     * @return int
     */
    public int insertTracceDB ( List<Traccia> tracce ){

        return connectionDAO.getTracciaDAO().insertTracceInDB(tracce);
    }

    public void insertAscoltiDB ( ){

        connectionDAO.getAscoltiDAO().insertAscoltiDB( selectedTraccia, loggedUtente);

    }
    


    
    /** 
     * @return boolean
     */
    public boolean checkFollowedUser ( ){

        String loggedUser = loggedUtente.getUtenteID();

            
        boolean anyMatch = selectedUserFollow.getFollowers()
                                             .stream()
                                             .anyMatch( utenteID -> utenteID.toLowerCase().equals( loggedUser.toLowerCase() ) );
                                                     

        if( anyMatch ){
            return true;
        }else{
            return false;
        }

    }

    public void unFollowUser ( ){
        

        Iterator<String> selectedFollow = selectedUserFollow.getFollowers().iterator();


        while ( selectedFollow.hasNext()  ) {
  
            String loggedUser = selectedFollow.next();
            
            if ( loggedUser.compareToIgnoreCase(loggedUtente.getUtenteID().toLowerCase()) == 0 )
                selectedFollow.remove();

            
        }

    }

    public void followUser ( ){
        
        selectedUserFollow.getFollowers().add( loggedUtente.getUtenteID() );
    }


    public void incrementmaxValueTracciaID (  ){
        this.maxValueTracciaID = maxValueTracciaID+1;
    }

    
    /** 
     * @param emailStr
     * @return boolean
     */
    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    
    /** 
     * @param type
     * @param title
     * @param message
     */
    public void displayAllert ( AlertType type, String title, String message) 
    {
        Alert alert = new Alert(type);
        alert.setTitle ( title );
        alert.setContentText( message );
        alert.showAndWait( );
    }
    
    
    /** 
     * @param date
     * @return String
     */
    public String formatDate( String date ){

        String[] newdate = date.split(":|/|-");
        return newdate[2] + "/" + newdate[1]  + "/" +  newdate[0];
    }

    
    
    /** 
     * @param duration
     * @return boolean
     */
    public boolean isDurationFormat( String duration ){
        
        return duration.matches("^[0-0][0-1]:[0-0][0-0]:[0-0][0-0]|[0-0][0-0]:[0-5][0-9]:[0-5][0-9]$");
        
    }
}


package dao;


import java.sql.*;
import java.util.*;

import model.Ascolti;
import model.Traccia;
import model.Utente;


public class AscoltiDAO {

    private Connection link;


    public AscoltiDAO ( Connection link) {
        
        this.link = link ;

    }

    
    /** 
     * @param traccia
     * @param utente
     */
    public void insertAscoltiDB (Traccia traccia , Utente utente){
        
        Ascolti ascolto;
        try
        {
            ascolto = new Ascolti(java.time.LocalTime.now().getHour() , traccia );

            System.out.println("Processing insert nuovo ascolto in Ascolti....");

            String query = "insert into ascolti ( ascoltatoreid, Ora, tracciaid, albumid, versione, artistaid) values "; 

            /* 
            NON FUNZIONA PERCHE la colonna "ora" è di tipo fasciaoraria ma l'espressione è di tipo character varying
            PreparedStatement statement = link.prepareStatement("insert into ascolti ( ascoltatoreid, Ora, tracciaid, albumid, versione, artistaid) "+
                                                                            "values (? , ?, ? , ?, ?, ? )"); 
            
            statement.setString(1, utente.getUtenteID());
            statement.setString(2, ascolto.getFasciaOraria());
            statement.setInt(3, ascolto.gettracciaID());
            statement.setInt(4, ascolto.getalbumID());
            statement.setString(5, ascolto.getversione());
            statement.setString(6, ascolto.getartistaID());
            */

            query = query + "('" + utente.getUtenteID() +"','" + ascolto.getFasciaOraria() +"','" + ascolto.gettracciaID( ) + "','"   ; 
            query = query + ascolto.getalbumID() + "','" + formatDate(ascolto.getversione()) + "','" + ascolto.getartistaID() + "');";

            PreparedStatement statement = link.prepareStatement(query); 

            if( (statement.executeUpdate()) > 0 ) {
                System.out.println("Processing insert nuovo ascolto in traccia gone good :D");

            }else System.out.println("Processing insert nuovo ascolto in traccia gone bad :C");

        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Errore nell'inserimento traccia in Ascolti");
        }
    }

    
    /** 
     * @param utenteID
     * @return int
     */
    public int getTotalAscolti ( String utenteID ){

        try{
        PreparedStatement statement = link.prepareStatement("select count(artistaid) from ascolti where artistaid = ?"); 

        System.out.println("Loading max Ascolti  in a ascolti table...");
        statement.setString(1, utenteID );

        ResultSet resultSet = statement.executeQuery();
                   
        System.out.println("Processing..."); 

        if( resultSet.next() )
        {
            System.out.println("Maximum values loaded");
            return resultSet.getInt(1);

        }else{
            System.out.println("Error occured loading max Ascolti  :C");
            return 0;
        }

        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("max Ascolti Loading error occur");
            return 0;
        
        }    
    }

    
    /** 
     * @param utenteid
     * @return List<String>
     */
    public List<String> getAscoltiSongByArtista ( String utenteid ){
        try
        {

            System.out.println("Loading Ascolti of " + utenteid + " ....");

            ResultSet resultSet; 

            List<String> ascolti = new ArrayList<>();

            PreparedStatement statement = link.prepareStatement(" Select titoloTraccia from traccepiuascoltatebyartista() where artistaid = ?"); 
            
            statement.setString(1, utenteid );

            System.out.println("Loading estrazione Tracce Ascoltate ....");
            
            resultSet = statement.executeQuery();

            System.out.println("Processing...");

            while( resultSet.next() )
            {

                ascolti.add( resultSet.getString(1) );
            }   

            System.out.println("Processing estrazione Tracce Ascoltate gone good...");

            return ascolti;

        }
        catch ( SQLException e ){

            System.out.println("Errore nella ricerca nel Database");
            return new ArrayList<>();
        }
    }

    
    /** 
     * @param userid
     * @return String
     */
    public String getOrarioPreferitoByUser ( String userid ){

        try
        {
            String orario = new String("Non Definito");

            System.out.println("Loading Orario con piu ascolti of " + userid + " ....");

            ResultSet resultSet; 

            PreparedStatement statement = link.prepareStatement(" Select * from orarioConpiuAscolti( ? )"); 
            
            statement.setString(1, userid );

            System.out.println("Loading estrazione Orario con piu ascolti ....");
            
            resultSet = statement.executeQuery();

            System.out.println("Processing...");
            
            while( resultSet.next() )
            {

                orario = resultSet.getString(1) ; 
            }  

            System.out.println("Processing estrazione Orario con piu ascolti gone good...");

            return orario;

        }
        catch ( SQLException e ){

            System.out.println("Errore nella ricerca nel Database");
            return null;
        }
    }

    
    /** 
     * @param userID
     * @return List<String>
     */
    public List<String> getArtistiPrefByUser ( String userID ){
        try
        {

            System.out.println("Loading Arist pref of " + userID + " ....");

            ResultSet resultSet; 

            List<String> ascolti = new ArrayList<>();

            PreparedStatement statement = link.prepareStatement(" select distinct artistaid from ascolti where ascoltatoreid = ?"); 
            
            statement.setString(1, userID );

            System.out.println("Loading estrazione Arist pref ....");
            
            resultSet = statement.executeQuery();

            System.out.println("Processing...");

            while( resultSet.next() )
            {

                ascolti.add( resultSet.getString(1) );
            }   

            System.out.println("Processing estrazione Arist pref gone good...");

            return ascolti;

        }
        catch ( SQLException e ){

            System.out.println("Errore nella ricerca nel Database");
            return new ArrayList<>();
        }
    }

    
    /** 
     * @param userID
     * @return List<String>
     */
    public List<String> getTraccePrefInBD ( String userID ){
        try
        {

            System.out.println("Loading Arist pref of " + userID + " ....");

            ResultSet resultSet; 

            List<String> ascolti = new ArrayList<>();

            PreparedStatement statement = link.prepareStatement(" select titoloTraccia  from traccePiuAscoltate() where LOWER(ascoltatore) = LOWER(?)"); 
            
            statement.setString(1, userID );

            System.out.println("Loading estrazione Arist pref ....");
            
            resultSet = statement.executeQuery();

            System.out.println("Processing...");

            while( resultSet.next() )
            {

                ascolti.add( resultSet.getString(1) );
            }   

            System.out.println("Processing estrazione Arist pref gone good...");

            return ascolti;

        }
        catch ( SQLException e ){

            System.out.println("Errore nella ricerca nel Database");
            return new ArrayList<>();
        }
    }

    
    /** 
     * @param date
     * @return String
     */
    public String formatDate( String date ){

        String[] newdate = date.split(":|/|-");
        return newdate[2] + "/" + newdate[1]  + "/" +  newdate[0];
    }

}
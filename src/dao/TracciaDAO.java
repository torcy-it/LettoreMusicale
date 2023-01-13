package dao;

import java.sql.*;
import java.util.*;


import model.Traccia;

public class TracciaDAO {

    private Connection link;

    public TracciaDAO ( Connection link )  {


        this.link = link;

    }

    
    /** 
     * @param query
     * @return List<Traccia>
     */
    public List<Traccia> getTableTraccia ( String query )
    {
        
        try 
        {
            if( query == null || query.isEmpty() ) query = "select * from traccia";


            System.out.println("Loading Song....");

            Statement statement = link.createStatement();

            ResultSet resultSet;

            List<Traccia> songList = new ArrayList<>();

            String result = null;
            
            //Statement selecStatement = connessione.createStatement();
            System.out.println("Processing...");

            resultSet = statement.executeQuery(query);
            
            System.out.println("Processing...");
            
            while( resultSet.next() )
            {
            
                result = String.valueOf(resultSet.getInt(1)) + ";";
                
                result = result  + resultSet.getString(2)+ ";";

                result = result + String.valueOf(resultSet.getInt(3)) + ";";

                result = result  + resultSet.getString(4)+ ";";

                result = result  + resultSet.getString(5)+ ";";

                result = result  + resultSet.getString(6)+ ";";

                result = result + resultSet.getString(7)+ ";";

                result = result +  String.valueOf(resultSet.getInt(8)) + ";";

                result = result  + resultSet.getString(9)+ ";";


                songList.add(new Traccia ( result ));
     

            }   
            

            System.out.println("Processing Loading Song gone good");

            return songList;
        
            

        }catch ( SQLException e  ){

            System.out.println("Loading song gone bad");
            e.printStackTrace();

            return null;
        }

        
    }

    
    /** 
     * @param stringToFind
     * @return List<Traccia>
     */
    public List<Traccia> getTracciaByPersonalQuery ( String stringToFind )
    {

        String query = "Select * from traccia "+
                        "where LOWER(titolo) like lower('%"+ stringToFind +"%') or LOWER(artistaid)  like lower('%" + stringToFind +"') or  LOWER(genere) = lower('%" + stringToFind + "') ;";


        try
        {
            return getTableTraccia ( query );
        }
        catch ( IllegalStateException e ){

            System.out.println("Errore nella ricerca nel Database");
            return null;
        }
    }

    
    /** 
     * @param tracce
     * @return int
     */
    public int insertTracceInDB ( List<Traccia> tracce ){

        //(tracciaid, titolo, durata, genere, t_traccia,  albumid, versione, artistaid)
        //durata must be '00:01:33'
        String query = "insert into Traccia (tracciaid, titolo, durata, genere, t_traccia,  albumid, versione, artistaid) values ";

        

        for ( Traccia t : tracce ){

            query = query + "('" + t.gettracciaID() +"','" + t.gettitolo() +"','" + t.minsInString( t.getdurata() ) + "','"   ; 
            query = query + t.getgenere() + "','" + t.gett_Traccia() + "','" + t.getalbumID() + "','" + t.getversione() + "','" + t.getartistaID() + "'),";

        }

        query = query.substring(0, query.length() - 1);
        query = query + ";";

        //System.out.println(query);

        try
        {

            System.out.println("Processing insertnuovo traccia in traccia....");


            PreparedStatement statement = link.prepareStatement(query); 
         
            if( (statement.executeUpdate()) > 0 ) {
                System.out.println("Processing insert traccia in traccia gone good :D");
                return 1;

            }else {
                System.out.println("Processing insert traccia in traccia gone bad :C");
                return 0;
            }
        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Errore nell'inserimento traccia in traccia");
            return 0;
        }
    }
    
    
    /** 
     * @return int
     */
    public int getMaxIDvaluesInTable (  ){
        
        try 
        {   

            PreparedStatement statement = link.prepareStatement("SELECT MAX(tracciaid) FROM traccia "); 

            System.out.println("Loading Maximun Values in a TracciaDAO table...");

            ResultSet resultSet = statement.executeQuery();
                       
            System.out.println("Processing..."); 

            if( resultSet.next() )
            {
                System.out.println("Maximum values loaded");
                return resultSet.getInt(1);

            }else{
                System.out.println("Error occured loading Maximum values :C");
                return 0;
            }

        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Maximum values Loading error occur");
            return 0;
        
        }    
        
    }
}

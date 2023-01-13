package dao;


import java.sql.*;
import java.util.*;

import model.Follow;

public class FollowDAO {

    private Connection link;
    public FollowDAO ( Connection link ) {

        this.link = link ;
        
    }

    
    /** 
     * @param UtenteID
     * @param key
     * @return List<String>
     */
    public List<String> getFollowDB ( String UtenteID ,int key ){
        

        String query;

        if( key == 1 ){
            //following
            query = "select utente1 from followers where LOWER(utente2) = LOWER(?) ";
        }else{
            //followers
            query = "select utente2 from followers where LOWER(utente1) = LOWER(?) ";
        }


        try
        {
   
            List<String> following = new ArrayList<>();

            System.out.println("Loading Follow of " + UtenteID + " ....");
            ResultSet resultSet; 
            PreparedStatement statement = link.prepareStatement(query); 
            
            statement.setString(1, UtenteID);

            System.out.println("Processing...");

            resultSet = statement.executeQuery();
            
            while( resultSet.next() )
            {

                following.add( resultSet.getString(1) );
            }   

            System.out.println("Following of " + UtenteID +" loaded ...");

            return following;

        }
        catch ( SQLException e ){

            System.out.println("Errore nella ricerca following nel Database" + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    
    /** 
     * @param selectedFollow
     * @return int
     */
    public int insertFollowInDB ( Follow selectedFollow ){

        try
        {
            
            System.out.println("Processing insert Follow in followers....");

            //selected Follow solo i followers
            List<String> followers = selectedFollow.getFollowers();

            System.out.println("Scelgo gli utente che non sono stati inseriti in followers....");
            ridurreListFollowers(followers, selectedFollow.getUtenteID());

            if( !followers.isEmpty() ){

                String query = "insert into followers ( utente1 , utente2 ) values "; 
                //carica nel db

                System.out.println("Processing Uploading data Followers of  "+ selectedFollow.getUtenteID() + " in followers..");
                for ( String t : followers ){

                    query = query + "('" + selectedFollow.getUtenteID() +"','" + t +  "'),";
        
                }
        
                query = query.substring(0, query.length() - 1);
                query = query + ";";

                System.out.println( "? "+query );

                PreparedStatement statement = link.prepareStatement(query); 

                if( (statement.executeUpdate()) > 0 ) {
                    System.out.println("Processing insert Followers of  "+ selectedFollow.getUtenteID() + " in followers gone good :D");
                    return 1;
    
                }else{
                    System.out.println("Processing insert Followers of  "+ selectedFollow.getUtenteID() + " in followers gone bad :C");
                    return 0;
                }
                
                
            }

            return 0;
        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Errore nell'inserimento traccia in Ascolti");
            return 0;
        }
        

    }

    
    /** 
     * @param follow
     * @param utenteID
     */
    public void ridurreListFollowers ( List<String> follow, String utenteID ){

        Iterator<String> selectFollowers = follow.iterator();

        List<String> totalSelectedFollowers = getFollowDB(utenteID, 2);

        System.out.println( "FOLLOWERS RIDUZIONE of "+utenteID+"\n\n" );

        while (selectFollowers.hasNext() ) {
            
            String selectedUser = selectFollowers.next();

            System.out.println(  "- "+selectedUser  );
            System.out.println(  );
            for( String followingString : totalSelectedFollowers ) {
            
                if ( selectedUser.compareToIgnoreCase( followingString ) == 0 ){
                    selectFollowers.remove();
                }

                System.out.println( "+ "+followingString );

            }
            
        }

        for( String f : follow ) {

            System.out.println( "$ "+ f );

        }
    }
}

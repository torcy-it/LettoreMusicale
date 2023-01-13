package dao;

import java.sql.*;
import java.util.*;

import model.Album;


public class AlbumDAO {
    private Connection link;

    public AlbumDAO ( Connection link)  {

        this.link = link;
    }

    
    /** 
     * @param query
     * @return List<Album>
     */
    public List<Album> getListAlbum ( String query )
    {
        try 
        {
            if( query == null ) query = "select * from album";

            System.out.println("Loading Album....");

            ResultSet resultSet;

            Statement statement = link.createStatement();

            List<Album> albumList = new ArrayList<>();

            String result = null;
            
            //Statement selecStatement = connessione.createStatement();
            System.out.println("Processing...");

            resultSet = statement.executeQuery(query);
            
            System.out.println("Processing...");
            
            while( resultSet.next() )
            {
            
                result = resultSet.getString(1) +";";
                
                result = result  + resultSet.getString(2)+";";

                result = result  + resultSet.getString(3)+";";

                result = result  + resultSet.getString(4)+";";

                result = result  + resultSet.getString(5)+";";

                albumList.add( new Album(result) );
    
            }   
            

            System.out.println("Processing Loading Album gone good");

            return albumList;
        
            

        }catch ( SQLException e  ){

            System.out.println("Loading Album gone bad");
            e.printStackTrace();

            return null;
        }       
    }

    
    /** 
     * @param album
     * @return int
     */
    //setttablealbum
    public int insertAlbumInDB ( Album album ){

        try
        {
            System.out.println("Processing insertnuovo album in album....");

            PreparedStatement statement = link.prepareStatement("insert into ALBUM (albumid, Titolo , ColoreCopertina, Casa_discografica, artistaid ) values (? , ? , ?, ?, ?)"); 
            
            statement.setInt(1, album.getalbumID());
            statement.setString(2, album.gettitoloAlbum());
            statement.setString(3, album.getcoloreCopertina());
            statement.setString(4, album.getcasaDiscografica());
            statement.setString(5, album.getartistaID());
            

            if( (statement.executeUpdate()) > 0 ) {
                System.out.println("Processing insert album in album gone good :D");
                return 1;

            }else {
                System.out.println("Processing insert album in album gone bad :C");
                return 0;
            }
        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Errore nell'inserimento album in album");
            return 0;
        }
    }

    
    /** 
     * @return int
     */
    public int getMaxIDvaluesInTable (  ){
        
        try 
        {   

            PreparedStatement statement = link.prepareStatement("SELECT MAX(albumid) FROM album "); 

            System.out.println("Loading Maximun Values in a AlbumDAO table...");

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

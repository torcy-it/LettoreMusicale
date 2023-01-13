package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import model.Utente;

public class UtenteDAO {

    private Connection link;

    public UtenteDAO ( Connection link) {

        this.link = link;

    }


    
    /** 
     * @param email
     * @param key_password
     * @param utenteID
     */
    public void signIn ( String email , String key_password , String utenteID)
    {

        try
        {
            System.out.println("Processing insertnuovo utente in utente....");

            PreparedStatement statement = link.prepareStatement("insert into utente (email , utenteid , key_password ) values (? , ? , ?)"); 
            
            statement.setString(1, email);
            statement.setString(2,utenteID);
            statement.setString(3, key_password);
            
            if( (statement.executeUpdate()) > 0 ) {
                System.out.println("Processing insert nuovo utente in utente gone good :D");

            }else System.out.println("Processing insert nuovo utente in utente gone bad :C");

        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Errore nell'inserimento utente in utente " + e.getMessage());
        }
        
        
    }

    
    /** 
     * @param email
     * @param utenteid
     * @return boolean
     */
    public boolean findUtente ( String email, String utenteid ){


        try 
        {
            PreparedStatement statement;

            if ( utenteid == null ){
                statement = link.prepareStatement("Select * from utente where LOWER(email) = LOWER(?) "); 

                statement.setString(1, email);
            }else{
                statement = link.prepareStatement("Select * from utente where LOWER(email) = LOWER(?) or LOWER(utenteid) = LOWER(?)"); 

                statement.setString(1, email);
                statement.setString(2, utenteid);                
            }

            System.out.println("Trying to Find User by Email or ID...");

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Processing..."); 

            if( resultSet.next() )
            {
                System.out.println("Utente Trovato ");
                return true;
            }else{
                System.out.println("Utente non Trovato ");
                
                return false;
            }

        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Error occur while Find User by Email or ID");
            return false;
        }

        
    }

    
    /** 
     * @param email
     * @param passwString
     * @return boolean
     */
    public boolean checkPassworUtente ( String email, String passwString )
    {

        try 
        {

            PreparedStatement statement = link.prepareStatement("Select key_password from utente where LOWER(email) = LOWER(?) "); 

            statement.setString(1, email);

            System.out.println("Checking Password of user with: "+email+" ...");

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Processing..."); 

            if( resultSet.next() )
            {
                if( passwString.compareTo(resultSet.getString(1)) == 0 )
                
                System.out.println("Password corretta");
                return true;
                
            }else{
                System.out.println("Password Non corretta");
                
                return false;
            }

        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Error occur while checking Password of user with: "+email+" ...");
            return false;
        }

        
    }

    
    /** 
     * @param emailUtente
     * @return Utente
     */
    public Utente getUtente ( String emailUtente )
    {   
        Utente utente = new Utente();
        utente.setutenteID("UtenteNonIdentificato");


        try 
        {

            PreparedStatement statement = link.prepareStatement("Select * from utente where LOWER ( email ) = LOWER ( ? ) "); 

            statement.setString(1, emailUtente);

            String result = null;

            System.out.println("Loading Logged User...");

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Processing..."); 

            if( resultSet.next() )
            {
                result =  resultSet.getString(1)+ ";";

                result = result  + resultSet.getString(2)+ ";";

                result = result  + resultSet.getString(3)+ ";";

                result = result + resultSet.getString(4)+ ";";

                result = result + resultSet.getString(5)+ ";";

                result = result +  resultSet.getString(6)+ ";";

                result = result +  resultSet.getString(7)+ ";";

                result = result +  resultSet.getString(8)+ ";";

                System.out.println("Logged User loaded ");

                utente = new Utente(result);

            }else{
                System.out.println("Error occured loading the  Logged User :C");
                return null;
            }


            if( utente.getUtenteID().compareTo("UtenteNonIdentificato") != 0 )
            {
                System.out.println("Login Effetuato con successo");
                return utente;
    
            }else{
                System.out.println("Errore Utente Non Identificato");
                return null;   
            }

        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Logged User Loading error occur");
            return null;
        
        }
     
    }

    
    /** 
     * @param email
     * @param newUtenteID
     * @return int
     */
    public int updateAccountIDinDB ( String email, String newUtenteID ){

        try
        {
            System.out.println("Processing update AccountID....");

            PreparedStatement statement = link.prepareStatement("UPDATE utente "+
                                                                        "SET utenteid = ?"+
                                                                        "WHERE email = ?"); 

            statement.setString(1, newUtenteID);
            statement.setString(2, email);
            
            
            if( (statement.executeUpdate()) > 0 ) {
                System.out.println("Processing update AccountID gone good :D");
                return 1;

            }else{
                System.out.println("Processing update AccountID gone bad :C");
                return 0;
            } 

        }catch ( SQLException e ){

            
            e.printStackTrace();
            System.out.println("Errore nell'update AccountID");
            return 0;
        } 
    }

    
    /** 
     * @param utenteID
     * @param newEmail
     * @return int
     */
    public int updateEmailUtenteInDB ( String utenteID, String newEmail ){

        try
        {
            System.out.println("Processing update Email ....");

            PreparedStatement statement = link.prepareStatement("UPDATE utente "+
                                                                        "SET email = ? "+
                                                                        "WHERE utenteid = ?"); 

            
            statement.setString(1, newEmail);
            statement.setString(2, utenteID);
            
            if( (statement.executeUpdate()) > 0 ) {
                System.out.println("Processing update Email gone good :D");
                return 1;

            }else{
                System.out.println("Processing update Email gone bad :C");
                return 0;
            }
        }catch ( SQLException e ){

            e.printStackTrace();

            System.out.println("Errore nell'update Email");
            return 0;
        } 
    }

    
    /** 
     * @param email
     * @param pass
     * @return int
     */
    public int updatePasswordUtenteInDB ( String email , String pass ){

        try
        {
            System.out.println("Processing update password....");

            PreparedStatement statement = link.prepareStatement("UPDATE utente "+
                                                                        "SET key_password = ?"+
                                                                        "WHERE email = ?"); 

            statement.setString(1, pass);
            statement.setString(2, email);
  
            
            if( (statement.executeUpdate()) > 0 ) {
                System.out.println("Processing update password gone good :D");
                return 1;
            }else System.out.println("Processing update password gone bad :C");

            return 0;
            
        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Errore nell'update password");
            return 0;
        } 
    }

    
    /** 
     * @param utente
     * @return int
     */
    public int updateInfoUtente ( Utente utente ){

        try
        {
            System.out.println("Processing update utente .... "+utente.getUtenteID());
            
            PreparedStatement statement = link.prepareStatement("UPDATE utente "+
                                                                    "SET nome = '"+utente.getNomeUtente()+"' , cognome = '"+utente.getcognomeUtente()+"'"+
                                                                    " , datanascita = '"+utente.getdataNascita()+"' , t_utente = '"+utente.gett_utente()+"'"+
                                                                    "WHERE utenteid = '"+utente.getUtenteID()+"'"); 

            System.out.println("Processing .... ");

            if( (statement.executeUpdate()) > 0 ) {
                
                System.out.println("Processing update utente gone good :D");
                return 1;

            }else System.out.println("Processing update utente gone bad :C");

            return 0;
        }catch ( SQLException e ){

            e.printStackTrace();
            System.out.println("Errore update utente in utente");
            return 0;
        }
    }

    
    /** 
     * @return List<Utente>
     */
    public List<Utente> getListUtente ( ){
        try 
        {
            System.out.println("Loading Users....");

            Statement statement = link.createStatement();

            ResultSet resultSet;

            List<Utente> utenti = new ArrayList<>();

            String result = null;
            
            //Statement selecStatement = connessione.createStatement();
            System.out.println("Processing...");

            resultSet = statement.executeQuery("select * from utente");
            
            System.out.println("Processing...");
            
            while( resultSet.next() )
            {
            
                result =  resultSet.getString(1)+ ";";

                result = result  + resultSet.getString(2)+ ";";

                result = result  + resultSet.getString(3)+ ";";

                result = result + resultSet.getString(4)+ ";";

                result = result + resultSet.getString(5)+ ";";

                result = result +  resultSet.getString(6)+ ";";

                result = result +  resultSet.getString(7)+ ";";

                result = result +  resultSet.getString(8)+ ";";

                utenti.add( new Utente(result) );

            }   
            

            System.out.println("Processing Loading users gone good");

            return utenti;
        

        }catch ( SQLException e  ){

            System.out.println("Loading users gone bad");
            e.printStackTrace();

            return null;
        }
    }
}

package database;

import java.sql.*;

public class DataBasePg {

	private Connection connection = null;

	private String nome = "postgres";

	private String password = "admin";

	private String url = "jdbc:postgresql://localhost:5432/lettoreMusicaleDB";

	private String driver = "org.postgresql.Driver";

	String failed = "DB Connection failed";

	String success = "DB Connection succesful";

    private Boolean connectedToDatabase  = false;
   

    public DataBasePg ( )  
    {
        
        try
        {
            
			setConnection(DriverManager.getConnection(getUrl(), getNome(), password));

            if(connection!=null)
            {
                System.out.println(success);
                connectedToDatabase = true;
            }else{
                System.out.println(failed);
                System.exit(0);
            }
            

        }catch ( SQLException sqlException )
        {
            System.out.println( failed + sqlException.getMessage());
            sqlException.printStackTrace();
            System.exit(0);
        }

    }
    

	
    /** 
     * @return String
     */
    public String getNome() {
		return nome;
	}

	
    /** 
     * @return String
     */
    public String getDriver() {
		return driver;
	}

	
    /** 
     * @return String
     */
    public String getUrl() {
		return url;
	}

    
    /** 
     * @return boolean
     */
    public boolean isConnectedDB ( ){
        return connectedToDatabase;
    }

    
    /** 
     * @return Connection
     */
    public Connection getConnection ( ){
        return connection;
    }    

    
    /** 
     * @param nome
     */
    public void setNome(String nome) {
		this.nome = nome;
	}

	
    /** 
     * @param url
     */
    public void setUrl(String url) {
		this.url = url;
	}

	
    /** 
     * @param connection
     */
    public void setConnection(Connection connection) {
		this.connection = connection;
	}

    
    /** 
     * @param password
     */
    public void setPassword ( String password ){
        this.password = password;
    }

    public void closeConnection ( ){
        try{
            System.out.println("Connessione Chiusa con successo");
            this.connection.close();
        }catch(SQLException sqlException){

            System.out.println( "Chiusura connessione gone wrong" + sqlException.getMessage());
            sqlException.printStackTrace();
        }
        
    }

}
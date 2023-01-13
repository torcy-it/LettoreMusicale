package dao;

import database.DataBasePg;

public class ConnectionDAO {

    private DataBasePg dataBasePg;
    private UtenteDAO utenteDAO;
    private AlbumDAO albumDAO;
    private TracciaDAO tracciaDAO;
    private FollowDAO followDAO;
    private AscoltiDAO ascoltiDAO;

    public ConnectionDAO ( ){

        this.dataBasePg = new DataBasePg();
        
        this.utenteDAO = new UtenteDAO( dataBasePg.getConnection());

        this.albumDAO = new AlbumDAO( dataBasePg.getConnection());

        this.tracciaDAO = new TracciaDAO( dataBasePg.getConnection());

        this.followDAO = new FollowDAO( dataBasePg.getConnection());

        this.ascoltiDAO = new AscoltiDAO( dataBasePg.getConnection() );
    }
    
    
    /** 
     * @param utenteDAO
     */
    public void setUtenteDAO( UtenteDAO utenteDAO ){
        this.utenteDAO =  utenteDAO;
    }

    
    /** 
     * @param albumDAO
     */
    public void setAlbumDAO( AlbumDAO albumDAO){
        this.albumDAO =  albumDAO;;
    }

    
    /** 
     * @param tracciaDAO
     */
    public void setTracciaDAO( TracciaDAO tracciaDAO){
        this.tracciaDAO =  tracciaDAO;
    }

    
    /** 
     * @param followDAO
     */
    public void setFollowDAO( FollowDAO followDAO){
        this.followDAO =  followDAO;
    }

    
    /** 
     * @param ascoltiDAO
     */
    public void setAscoltiDAO( AscoltiDAO ascoltiDAO){
        this.ascoltiDAO =  ascoltiDAO;
    }
    
    
    /** 
     * @return UtenteDAO
     */
    public UtenteDAO getUtenteDAO(){
        return utenteDAO;
    }

    
    /** 
     * @return AlbumDAO
     */
    public AlbumDAO getAlbumDAO(){
        return albumDAO;
    }

    
    /** 
     * @return TracciaDAO
     */
    public TracciaDAO getTracciaDAO(){
        return tracciaDAO;
    }

    
    /** 
     * @return FollowDAO
     */
    public FollowDAO getFollowDAO(){
        return followDAO;
    }

    
    /** 
     * @return AscoltiDAO
     */
    public AscoltiDAO getAscoltiDAO(){
        return ascoltiDAO;
    }

    public void closeConnection ( ){
        dataBasePg.closeConnection();
    }

}

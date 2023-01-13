package model;


public class Ascolti {

    private String fasciaOraria;
    private String artistaID;
    private int tracciaID;
    private String versione;
    private int albumID;
    
    public Ascolti ( ) {};
    
    public Ascolti ( String infoAscolti )
    {
        String[] word = infoAscolti.trim().split(";");

        this.fasciaOraria = word[0];
        this.artistaID = word[2];
        this.tracciaID = Integer.parseInt(word[3]);
        this.versione = word[4];
        this.albumID = Integer.parseInt(word[5]);
    }

    public Ascolti ( int orario , Traccia traccia){

        this.fasciaOraria = getFasciaOraria( orario );
        this.artistaID = traccia.getartistaID();
        this.tracciaID = traccia.gettracciaID();
        this.versione = traccia.getversione();
        this.albumID = traccia.getalbumID();
    }


    
    /** 
     * @return String
     */
    @Override
    public String toString ( )
    {
        return fasciaOraria +" " + artistaID  +" " + versione  +" "+ tracciaID +" " + albumID  ;
    }

    
    /** 
     * @param orario
     * @return String
     */
    public String getFasciaOraria ( int orario )
    {
        if ( orario < 8 ){
            return  "Notte";

        }else if ( orario > 8 && orario < 12 ){
            return "Mattina";

        }else if(orario > 12 && orario < 18 ){
            return "Pomeriggio";

        }else{
            return "Sera";
        }
    }


    
    /** 
     * @return String
     */
    public String getFasciaOraria ( )
    {
        return fasciaOraria;
    }

    
    /** 
     * @return int
     */
    public int gettracciaID ( )
    {
        return tracciaID;
    }

    
    /** 
     * @return String
     */
    public String getartistaID ( )
    {
        return artistaID;
    }

    
    /** 
     * @return int
     */
    public int getalbumID ( )
    {
        return albumID ;
    }

    
    /** 
     * @return String
     */
    public String getversione ( )
    {
        return versione;
    }



}   

package model;

public class Traccia {

    
    private int tracciaID;
	private String artistaID;
	private int albumID;
	private String versione;

	private String titolo;
	private String genere;

	private int durata; 

    private int codT_Originale ;
	private String t_Traccia;

    public Traccia () {}

    public Traccia ( String infoTraccia )
    {
        String[] word = infoTraccia.trim().split(";");

        this.tracciaID = Integer.parseInt(word[0]);
        this.artistaID = word[1];
        this.albumID = Integer.parseInt(word[2]);
        this.versione = formatDate(word[3]);
        this.titolo = word[4];
        this.genere = word[5];
        this.durata = toMins(word[6]); 
        this.codT_Originale = Integer.parseInt(word[7]);
        this.t_Traccia = word[8];

        
    }

    public Traccia( Traccia traccia ){

        this.tracciaID = traccia.gettracciaID();
        this.artistaID = traccia.getartistaID();
        this.albumID = traccia.getalbumID();
        this.versione = formatDate(traccia.getversione());
        this.titolo = traccia.gettitolo();
        this.genere = traccia.getgenere();
        this.durata = traccia.getdurata(); 
        this.codT_Originale = traccia.getcodT_Originale();
        this.t_Traccia = traccia.gett_Traccia();
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString ( )
    {
        return String.format("%s %40s %40s %40s %40s",titolo, artistaID , durata, versione  , genere,  t_Traccia );
    }

    
    /** 
     * @param tracciaID
     */
    //(tracciaid, titolo, durata, genere, t_traccia,  albumid, versione, artistaid)


    public void settracciaID ( int tracciaID )
    {
        this.tracciaID = tracciaID;
    }

    
    /** 
     * @param artistaID
     */
    public void setartistaID ( String artistaID )
    {
        this.artistaID = artistaID;
    }

    
    /** 
     * @param albumID
     */
    public void setalbumID ( int albumID)
    {
        this.albumID = albumID;
    }

    
    /** 
     * @param versione
     */
    public void setversione ( String versione)
    {
        this.versione = versione;
    }

    
    /** 
     * @param titolo
     */
    public void settitolo ( String titolo )
    {
        this.titolo = titolo;
    }

    
    /** 
     * @param genere
     */
    public void setgenere ( String genere)
    {
        this.genere = genere;
    }

    
    /** 
     * @param durata
     */
    public void setdurata ( String durata)
    {
        this.durata = toMins( durata );
    }

    
    /** 
     * @param codT_Originale
     */
    public void setcodT_Originale ( int codT_Originale)
    {
        this.codT_Originale = codT_Originale;
    }

    
    /** 
     * @param t_Traccia
     */
    public void sett_Traccia ( String t_Traccia)
    {
        this.t_Traccia = t_Traccia;
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

    
    /** 
     * @return String
     */
    public String gettitolo ( )
    {
        return titolo;
    }

    
    /** 
     * @return String
     */
    public String getgenere ( )
    {
        return genere;
    }

    
    /** 
     * @return int
     */
    public int getdurata ( )
    {
        return durata;
    }

    
    /** 
     * @return int
     */
    public int getcodT_Originale ( )
    {
        return codT_Originale;
    }

    
    /** 
     * @return String
     */
    public String gett_Traccia ( )
    {
        return t_Traccia ;
    }

    
    /** 
     * @param s
     * @return int
     */
    public static int toMins(String s) {

        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
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
     * @param mins
     * @return String
     */
    public String minsInString ( int mins ){

        int hour = mins/60 ;
        int minutes = mins%60;

        return String.format("%02d:%02d:00",hour,minutes);
    }
}

package model;

public class Album {
    
    private int albumID;
    private String artistaID;
    private String titoloAlbum;
    private String coloreCopertina;
    private String casaDiscografica;

    public Album () {}

    public Album (String infoAlbum ) {

        String[] word = infoAlbum.trim().split(";");

        this.albumID = Integer.parseInt(word[0]);
        this.artistaID = word[1];
        this.titoloAlbum = word[2];
        this.coloreCopertina = word[3];
        this.casaDiscografica = word[4];
    }  

    public Album ( Album album ){
        this.albumID = album.getalbumID();
        this.artistaID = album.getartistaID();
        this.titoloAlbum = album.gettitoloAlbum();
        this.coloreCopertina = album.getcoloreCopertina();
        this.casaDiscografica = album.getcasaDiscografica();
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString ( )
    {
        return artistaID + " " + titoloAlbum + " " + casaDiscografica;
    }

    
    /** 
     * @return int
     */
    public int getalbumID ( )
    {
        return albumID;
    }

    
    /** 
     * @return String
     */
    public String getartistaID ( )
    {
        return artistaID;
    }

    
    /** 
     * @return String
     */
    public String gettitoloAlbum ( )
    {
        return titoloAlbum;
    }

    
    /** 
     * @return String
     */
    public String getcoloreCopertina ( )
    {
        return coloreCopertina;
    }

    
    /** 
     * @return String
     */
    public String getcasaDiscografica ( )
    {
        return casaDiscografica;
    }

    
    /** 
     * @param albumID
     */
    public void setalbumID ( int albumID )
    {
        this.albumID = albumID;
    }

    
    /** 
     * @param artistaID
     */
    public void setartistaID ( String artistaID )
    {
        this.artistaID = artistaID;
    }

    
    /** 
     * @param titoloAlbum
     */
    public void settitoloAlbum ( String titoloAlbum)
    {
        this.titoloAlbum = titoloAlbum;
    }

    
    /** 
     * @param coloreCopertina
     */
    public void setcoloreCopertina ( String coloreCopertina)
    {
        this.coloreCopertina = coloreCopertina;
    }

    
    /** 
     * @param casaDiscografica
     */
    public void setcasaDiscografica ( String casaDiscografica)
    {
        this.casaDiscografica = casaDiscografica;
    }



}
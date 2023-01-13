package model;

// la classe utente è una super cclasse che estende ascoltatori e astisti 
// dal databse è possibile creare una lista di array ( è solo un idea per adesso ) 
// ascoltatori [] e artisti[] in base al tipo di utente che è scritto nel db
// per esempio se t.utente di utente nel db è uguale a artista verra aggiunto nel array aristi 

public class Utente 
{

    private String utenteID;
    private String nomeUtente; 
    private String cognomeUtente; 
    private String keyPassword;
    private String dataNascita;
    private String dataIscrizione;
    private String t_utente;
    private String emailUtente;

    public Utente  ( ){ }

    public Utente ( String infoUtente ){


        String[] word = infoUtente.trim().split(";");
        
        this.utenteID = word[0];
        this.keyPassword = word[1];
        this.nomeUtente = word[2];
        this.cognomeUtente = word [3];
        this.dataNascita = word[4];
        this.emailUtente = word[5];
        this.dataIscrizione = word[6];
        this.t_utente = word[7];
    
        
    }

    public Utente ( Utente utente ){
        this.utenteID = utente.getUtenteID();
        this.keyPassword = utente.getkeyPassword();
        this.nomeUtente = utente.getNomeUtente();
        this.cognomeUtente = utente.getcognomeUtente();
        this.dataNascita = utente.getdataNascita();
        this.emailUtente = utente.getemailUtente();
        this.dataIscrizione = utente.getdataIscrizione();
        this.t_utente = utente.gett_utente();
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString ( )
    {
        return utenteID;
    }
    
    
    /** 
     * @return String
     */
    public String getUtenteID ( )
    {
        return utenteID;
    }
    
    /** 
     * @return String
     */
    public String getNomeUtente ( )
    {
        return nomeUtente;
    }

    
    /** 
     * @return String
     */
    public String getcognomeUtente ( )
    {
        return cognomeUtente;
    }

    
    /** 
     * @return String
     */
    public String getkeyPassword ( )
    {
        return keyPassword;
    }
    
    
    /** 
     * @return String
     */
    public String getdataNascita ( )
    {
        return dataNascita;
    }
    
    
    /** 
     * @return String
     */
    public String getdataIscrizione ( )
    {
        return dataIscrizione;
    }

    
    /** 
     * @return String
     */
    public String gett_utente ( )
    {
        return t_utente;
    }

    
    /** 
     * @return String
     */
    public String getemailUtente ( )
    {
        return emailUtente;
    }

    
    /** 
     * @param utenteID
     */
    public void setutenteID (String utenteID )
    {
        this.utenteID = utenteID;
    }

    
    /** 
     * @param nomeUtente
     */
    public void setnomeUtente (String nomeUtente)
    {
        this.nomeUtente = nomeUtente ;
    }

    
    /** 
     * @param cognomeUtente
     */
    public void setcognomeUtente (String cognomeUtente )
    {
        this. cognomeUtente = cognomeUtente;
    }

    
    /** 
     * @param keyPassword
     */
    public void setkeyPassword (String keyPassword )
    {
        this.keyPassword = keyPassword ;
    }

    
    /** 
     * @param sos
     */
    public void setdataNascita (String sos )
    {   
        System.out.println(sos);
        this.dataNascita =  sos;
    }

    
    /** 
     * @param dataIscrizione
     */
    public void setdataIscrizione (String dataIscrizione )
    {
        
        this.dataIscrizione = dataIscrizione ;
    }

    
    /** 
     * @param t_utente
     */
    public void sett_utente (String t_utente)
    {
        this.t_utente = t_utente;
    }

    
    /** 
     * @param emailUtente
     */
    public void setemailUtente (String emailUtente )
    {
        this.emailUtente = emailUtente;
    }

}

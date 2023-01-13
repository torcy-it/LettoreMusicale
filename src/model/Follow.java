
package model;

import java.util.*;

public class Follow {


    private String utenteID;
    private List <String> followers;
    private List <String> following;

    public Follow ( String utenteID ){

        this.utenteID = utenteID;

    }
    public Follow ( ){}
    
    /** 
     * @param followers
     */
    public void setFollowers ( List<String> followers )
    {
        this.followers = followers;
        
    }

    
    /** 
     * @param following
     */
    public void setFollowing ( List<String> following )
    {
        this.following = following;
    }

    
    /** 
     * @return List<String>
     */
    public List <String> getFollowing ( )
    {
        return following;
    }

    
    /** 
     * @return List<String>
     */
    public List <String> getFollowers ( )
    {
        return followers;
    }

    public void setUtenteID ( ){

    }


    
    /** 
     * @return String
     */
    public String getUtenteID ( ){
        return utenteID;
    }


}

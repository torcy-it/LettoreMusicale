
package model;

import java.util.*;

public class Follow {


    private String utenteID;
    private List <String> followers;
    private List <String> following;

    public Follow ( String utenteID ){

        this.utenteID = utenteID;

    }
    
    
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

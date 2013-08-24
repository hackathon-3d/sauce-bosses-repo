package com.r0adkll.sparc.pillalarm.server;

/**
 * Created by r0adkll on 8/24/13.
 */
public class UserSession {

    /*******************************************
     *
     * Singleton Methods
     *
     */

    // Singleton instance
    private static UserSession instance;

    /**
     * Get the UserSession Singleton
     * @return      the singleton interface
     */
    public static UserSession getSession(){
        if(instance == null) instance = new UserSession();
        return instance;
    }

    /******************************************/


    /*******************************************
     *
     * Variables
     *
     */



    /*******************************************
     *
     * Session Methods
     *
     */

}

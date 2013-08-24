package com.r0adkll.sparc.pillalarm.server;

import android.content.Context;

import com.r0adkll.deadskunk.utils.FileUtils;
import com.r0adkll.deadskunk.utils.Utils;
import com.r0adkll.sparc.pillalarm.server.model.Prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r0adkll on 8/24/13.
 */
public class UserSession {
    private static final String TAG = "USER_SESSION";

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

    private ArrayList<Prescription> mPrescriptions = new ArrayList<Prescription>();

    public UserSession(){

    }

    /*******************************************
     *
     * Session Methods
     *
     */

    public void addPrescription(Prescription pres){
        mPrescriptions.add(pres);
    }

    public void removePrescription(Prescription pres){
        mPrescriptions.remove(pres);
    }

    public ArrayList<Prescription> getPrescriptions(){
        return mPrescriptions;
    }

    /**
     * Save the user to disk
     * @param ctx   application context
     * @return      result of the operation (IO_FAIL, IO_SUCCESS)
     */
    public synchronized int savePrescriptions(Context ctx){
        long pre = System.currentTimeMillis();
        int result = FileUtils.IO_FAIL;
        if(mPrescriptions != null){
            result = FileUtils.writeObjectToInternal(ctx, "PRESC_CACHE", mPrescriptions);
            long post = System.currentTimeMillis();
            Utils.log(TAG, "[" + result + "] User Data cached in " + (post - pre));
        }
        return result;
    }

    /**
     * Load the user data from the disk
     * @param ctx   application context
     * @return      result of the operation (IO_FAIL, IO_SUCCESS)
     */
    public synchronized int loadPrescriptions(Context ctx){
        long pre = System.currentTimeMillis();
        int result = FileUtils.IO_FAIL;

        ArrayList<Prescription> cacheUser = (ArrayList<Prescription>) FileUtils.readObject(ctx, "PRESC_CACHE");
        if(cacheUser != null){
            mPrescriptions = new ArrayList<Prescription>(cacheUser);
            result = FileUtils.IO_SUCCESS;
            long post = System.currentTimeMillis();
            Utils.log(TAG, "[" + result + "] User Data loaded in "  + (post - pre));
        }
        return result;
    }

}

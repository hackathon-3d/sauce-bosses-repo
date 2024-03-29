package com.r0adkll.sparc.pillalarm;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.r0adkll.sparc.pillalarm.server.UserSession;
import com.r0adkll.sparc.pillalarm.ui.HomeFragment;

public class PillAlarm extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pillalarm);
        getActionBar().setIcon(R.drawable.ic_launcher);

        // Load saved prescriptions
        UserSession.getSession().loadPrescriptions(this);



        if(savedInstanceState == null){

            // Show the homefragment
            HomeFragment home = HomeFragment.createInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, home, "HOME")
                    .commit();

        }


    }
    
}

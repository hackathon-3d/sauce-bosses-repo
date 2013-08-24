package com.r0adkll.sparc.pillalarm.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.r0adkll.sparc.pillalarm.R;
import com.r0adkll.sparc.pillalarm.server.UserSession;

/**
 * Created by r0adkll on 8/24/13.
 */
public class DrugViewFragment extends Fragment {


    /*******************************************
     *
     * Static Initializers
     *
     */

    /**
     * Create a new instance of this fragment
     * @return      the newly created fragment
     */
    public static DrugViewFragment createInstance(){
        DrugViewFragment frag = new DrugViewFragment();
        return frag;
    }


    /*******************************************
     *
     * Variables
     *
     */

    /*******************************************
     *
     * Lifecycle methods
     *
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drugview, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }

}

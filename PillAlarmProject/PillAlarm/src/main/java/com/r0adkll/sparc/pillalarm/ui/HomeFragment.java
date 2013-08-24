package com.r0adkll.sparc.pillalarm.ui;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.r0adkll.sparc.pillalarm.R;

/**
 * Created by r0adkll on 8/23/13.
 */
public class HomeFragment extends Fragment{

    /*******************************************
     *
     * Static Initializers
     *
     */

    /**
     * Create a new instance of this fragment
     * @return      the newly created fragment
     */
    public static HomeFragment createInstance(){
        HomeFragment frag = new HomeFragment();
        return frag;
    }


    /*******************************************
     *
     * Variables
     *
     */

    private ListView mList;
    private TextView mNoItemsText;


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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Load Views
        mList = (ListView) getActivity().findViewById(R.id.prescription_list);
        mNoItemsText = (TextView) getActivity().findViewById(R.id.no_prescription_message);

        // Attempt to load saved prescription information

        // TODO - this until data loading
        showEmptyText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                // Start process to create new prescription item
                AddNewDialog diag = AddNewDialog.createInstance();
                diag.show(getFragmentManager(), "ADD_NEW_PRESC");

                diag.setAddActionListener(new AddNewDialog.OnAddActionListener() {
                    @Override
                    public void onImageCapture(Bitmap image) {
                        // Go Forward
                    }

                    @Override
                    public void onSkipClick() {
                        // Go Forward
                    }

                    @Override
                    public void onCancelClick() {
                        // Do jack nothing
                    }
                });

                return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_home, menu);
    }

    /*******************************************
     *
     * Helper Methods
     *
     */

    private void showEmptyText(){
        mNoItemsText.setVisibility(View.VISIBLE);
    }

    private void hideEmptyText(){
        mNoItemsText.setVisibility(View.INVISIBLE);
    }




}

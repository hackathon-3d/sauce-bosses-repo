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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.r0adkll.sparc.pillalarm.R;
import com.r0adkll.sparc.pillalarm.adapters.ScheduleListAdapter;
import com.r0adkll.sparc.pillalarm.server.model.Schedule;
import com.slidinglayer.SlidingLayer;

import java.util.ArrayList;
import java.util.List;

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

    private SlidingLayer mSlideLayer;
    private ListView mList, mScheduleList;
    private TextView mNoItemsText;

    private EditText mEtName, mEtDose, mEtQuantity;
    private Button mAdd;

    private List<Schedule> mSchedules = new ArrayList<Schedule>();
    private ScheduleListAdapter mAdapter;

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
        mSlideLayer = (SlidingLayer) getActivity().findViewById(R.id.slide_layer);

        View layout = getActivity().getLayoutInflater().inflate(R.layout.layout_prescription_form, null, false);
        mSlideLayer.addView(layout);
        mSlideLayer.setStickTo(SlidingLayer.STICK_TO_RIGHT);


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
                        openNewPrescriptionLayer();
                    }

                    @Override
                    public void onSkipClick() {
                        // Go Forward
                        openNewPrescriptionLayer();
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

    /**
     *
     */
    public void openNewPrescriptionLayer(){
        // Create new scheduling
        Schedule addItem = new Schedule();
        mSchedules = new ArrayList<Schedule>();
        mSchedules.add(addItem);

        // Re-Create adapter
        mAdapter = new ScheduleListAdapter(getActivity(), R.layout.layout_schedule_item, mSchedules);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Schedule sched = (Schedule) mList.getItemAtPosition(i);

                if(sched.isAddItem){

                    // Show the new Sched dialog
                    AddNewScheduleDialog diag = AddNewScheduleDialog.createInstance();
                    diag.setAddActionListener(new AddNewScheduleDialog.OnAddActionListener() {
                        @Override
                        public void onScheduleOk(Schedule newSched) {
                            // Remove the add item
                            mSchedules.remove(sched);

                            // add to list
                            mSchedules.add(newSched);

                            // notify list
                            mAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelClick() {
                            // DO NOTHIZNANS
                        }
                    });

                }


            }
        });

        // Now show sliding layer
        mSlideLayer.openLayer(true);

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

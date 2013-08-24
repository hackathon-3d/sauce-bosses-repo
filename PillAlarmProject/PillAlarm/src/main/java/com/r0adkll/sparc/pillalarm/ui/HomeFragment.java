package com.r0adkll.sparc.pillalarm.ui;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.deadskunk.utils.DialogFactory;
import com.r0adkll.deadskunk.utils.Utils;
import com.r0adkll.sparc.pillalarm.R;
import com.r0adkll.sparc.pillalarm.adapters.PrescriptionListAdapter;
import com.r0adkll.sparc.pillalarm.adapters.ScheduleListAdapter;
import com.r0adkll.sparc.pillalarm.server.APIClient;
import com.r0adkll.sparc.pillalarm.server.PillAlarmResponseHandler;
import com.r0adkll.sparc.pillalarm.server.UserSession;
import com.r0adkll.sparc.pillalarm.server.model.Drug;
import com.r0adkll.sparc.pillalarm.server.model.Prescription;
import com.r0adkll.sparc.pillalarm.server.model.Schedule;
import com.slidinglayer.SlidingLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by r0adkll on 8/23/13.
 */
public class HomeFragment extends Fragment {

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

    private EditText mEtName, mEtDose, mEtQuantity, mEtTag;
    private Button mAdd;
    private View mSetDateView;
    private TextView mDateTextView;

    private Date chosenDate;

    private List<Prescription> mPrescriptions = new ArrayList<Prescription>();
    private List<Schedule> mSchedules = new ArrayList<Schedule>();
    private ScheduleListAdapter mAdapter;
    private PrescriptionListAdapter mPrescAdapter;

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

        // Save Prescriptions
        UserSession.getSession().savePrescriptions(getActivity());

        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get Prescriptions
        mPrescriptions = UserSession.getSession().getPrescriptions();



        // Load Views
        mList = (ListView) getActivity().findViewById(R.id.prescription_list);
        mNoItemsText = (TextView) getActivity().findViewById(R.id.no_prescription_message);
        mSlideLayer = (SlidingLayer) getActivity().findViewById(R.id.slide_layer);
        mPrescAdapter = new PrescriptionListAdapter(getActivity(), R.layout.layout_prescription_item, mPrescriptions);
        mList.setAdapter(mPrescAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prescription prescription = mPrescriptions.get(i);

                DrugViewFragment dvf = DrugViewFragment.createInstance(prescription);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, dvf, "DRUG_VIEW")
                        .addToBackStack(null)
                        .commit();


            }
        });

        if(!mPrescriptions.isEmpty()){
            hideEmptyText();
        }else{
            showEmptyText();
        }


        View layout = getActivity().getLayoutInflater().inflate(R.layout.layout_prescription_form, null, false);
        mScheduleList = (ListView) layout.findViewById(R.id.scheduling_list);
        mEtName = (EditText) layout.findViewById(R.id.et_name);
        mEtDose = (EditText) layout.findViewById(R.id.et_dose);
        mEtQuantity = (EditText) layout.findViewById(R.id.et_quantity);
        mEtTag = (EditText) layout.findViewById(R.id.tag);
        mSetDateView = layout.findViewById(R.id.start_date_container);
        mDateTextView = (TextView) layout.findViewById(R.id.tv_startdate);
        mSetDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show DatePicker Dialog
                DatePickerDialog diag = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        chosenDate = new GregorianCalendar(i, i2, i3).getTime();
                        mDateTextView.setText(android.text.format.DateFormat.format("MM-dd-yyyy", chosenDate));
                    }
                }, 2013, 8, 24);
                diag.show();
            }
        });


        mAdd = (Button) layout.findViewById(R.id.submit);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = mEtTag.getText().toString().trim();
                String name = mEtName.getText().toString().trim();
                String dose = mEtDose.getText().toString().trim();

                int quantity = -1;
                try{
                    quantity = Integer.valueOf(mEtQuantity.getText().toString());
                } catch (NumberFormatException e){}

                List<Schedule> scheds = new ArrayList<Schedule>(mSchedules);
                scheds.remove(mSchedules.size()-1);

                // Verify Creation
                if(name.isEmpty()){
                    DialogFactory.createAlertDialog(getActivity(), "Please enter a drug name.", "Error");
                    return;
                }else if(dose.isEmpty()){
                    DialogFactory.createAlertDialog(getActivity(), "Please enter a drug dose.", "Error");
                    return;
                }else if(quantity == -1){
                    DialogFactory.createAlertDialog(getActivity(), "Please enter a valid quantity.", "Error");
                    return;
                }else if(scheds.isEmpty()){
                    DialogFactory.createAlertDialog(getActivity(), "Please enter a drug schedule.", "Error");
                    return;
                }else if(chosenDate == null){
                    DialogFactory.createAlertDialog(getActivity(), "Please select the starting date of your prescription", "Error");
                    return;
                }

                // Create Perscription object
                final Prescription prescript = new Prescription(tag, name, dose, quantity, chosenDate, scheds);

                // Make request to get prescription drug info
                APIClient.getRequest(name.toLowerCase(), null, new PillAlarmResponseHandler(){

                    @Override
                    public void onRequestSuccess(JSONObject data) {

                        try {
                            Utils.log(getTag(), "RESPONSE: " + data.toString(2));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Create and apply drug info
                        Drug info = new Drug(data);
                        prescript.setDrugInfo(info);
                        if(getActivity() != null)
                            UserSession.getSession().savePrescriptions(getActivity());
                    }

                    @Override
                    public void onRequestFailure(String message) {
                        Utils.log(getTag(), "ERROR: " + message);
                    }
                });

                // Generate reminder events

                mPrescriptions.add(prescript);
                UserSession.getSession().savePrescriptions(getActivity());
                mPrescAdapter.notifyDataSetChanged();
                hideEmptyText();

                mSlideLayer.closeLayer(true);
            }
        });

        mSlideLayer.addView(layout);
        mSlideLayer.setStickTo(SlidingLayer.STICK_TO_RIGHT);
        mSlideLayer.setOnInteractListener(new SlidingLayer.OnInteractListener() {
            @Override
            public void onOpen() {
                getActivity().getActionBar().hide();

            }

            @Override
            public void onClose() {
                getActivity().getActionBar().show();
            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });

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
//                AddNewDialog diag = AddNewDialog.createInstance();
//                diag.show(getFragmentManager(), "ADD_NEW_PRESC");
//
//                diag.setAddActionListener(new AddNewDialog.OnAddActionListener() {
//                    @Override
//                    public void onImageCapture(Bitmap image) {
//                        // Go Forward
//                        openNewPrescriptionLayer();
//                    }
//
//                    @Override
//                    public void onSkipClick() {
//                        // Go Forward
//                        openNewPrescriptionLayer();
//                    }
//
//                    @Override
//                    public void onCancelClick() {
//                        // Do jack nothing
//                    }
//                });


                openNewPrescriptionLayer();

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

        // Re-set views
        mEtName.getText().clear();
        mEtQuantity.getText().clear();
        mEtDose.getText().clear();
        mEtTag.getText().clear();
        chosenDate = null;
        mDateTextView.setText("Prescription start date.");

        // Re-Create adapter
        mAdapter = new ScheduleListAdapter(getActivity(), R.layout.layout_schedule_item, mSchedules);
        mScheduleList.setAdapter(mAdapter);
        mScheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Schedule sched = mSchedules.get(i);

                Utils.log(getTag(), "Schedule Click: " + sched.toString());

                if(sched.isAddItem){

                    // Get quantity
                    int quant = -1;
                    try{
                        quant = Integer.valueOf(mEtQuantity.getText().toString());
                    } catch (NumberFormatException e){

                    }

                    if(quant != -1){

                        // Show the new Sched dialog
                        AddNewScheduleDialog diag = AddNewScheduleDialog.createInstance(quant);
                        diag.setAddActionListener(new AddNewScheduleDialog.OnAddActionListener() {
                            @Override
                            public void onScheduleOk(Schedule newSched) {
                                // Remove the add item
                                //mSchedules.remove(sched);

                                // add to list
                                mSchedules.add(mSchedules.size()-1, newSched);

                                // notify list
                                mAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelClick() {
                                // DO NOTHIZNANS
                            }
                        });
                        diag.show(getFragmentManager(), "ADD_SCHEDULE");

                    }else{
                        DialogFactory.createAlertDialog(getActivity(), "Please enter a quantity first.", "Error");
                    }

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
        mNoItemsText.setVisibility(View.GONE);
    }




}

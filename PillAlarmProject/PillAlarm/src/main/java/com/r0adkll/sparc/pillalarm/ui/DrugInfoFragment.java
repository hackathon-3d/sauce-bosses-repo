package com.r0adkll.sparc.pillalarm.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.r0adkll.sparc.pillalarm.R;
import com.r0adkll.sparc.pillalarm.server.model.Drug;
import com.r0adkll.sparc.pillalarm.server.model.Prescription;

/**
 * Created by r0adkll on 8/24/13.
 */
public class DrugInfoFragment extends Fragment {
    public static DrugInfoFragment createInstance(Prescription prescript){
        DrugInfoFragment frag = new DrugInfoFragment();
        frag.data = prescript;
        return frag;
    }

    public static final int WARNING = 0;
    public static final int SIDEEFFECTS = 1;
    public static final int PRECAUTIONS = 2;


    private Prescription data;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState != null){
            data = (Prescription) savedInstanceState.getSerializable("DATA");
        }


        if(data != null){



        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_druginfo, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("DATA", data);
        super.onSaveInstanceState(outState);
    }
}

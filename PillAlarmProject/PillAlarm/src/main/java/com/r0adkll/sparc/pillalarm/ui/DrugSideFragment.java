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

/**
 * Created by r0adkll on 8/24/13.
 */
public class DrugSideFragment extends Fragment {

    public static DrugSideFragment createInstance(Drug drug){
        DrugSideFragment frag = new DrugSideFragment();
        frag.data = drug;
        return frag;
    }

    public static final int WARNING = 0;
    public static final int SIDEEFFECTS = 1;
    public static final int PRECAUTIONS = 2;


    private Drug data;
    private TextView content;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState != null){
            data = (Drug) savedInstanceState.getSerializable("DATA");
        }

        content = (TextView) getActivity().findViewById(R.id.drug_sideeffects_text);

        if(data != null){
            if(data.side_effects.isEmpty())
                content.setText("We're sorry, but content for this drug is not available yet.");
            else
                content.setText(data.side_effects);


        }else{
            content.setText("We're sorry, but content for this drug is not available yet.");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_drug_sideeffects, container, false);
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

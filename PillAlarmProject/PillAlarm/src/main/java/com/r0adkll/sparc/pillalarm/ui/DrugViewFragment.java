package com.r0adkll.sparc.pillalarm.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.r0adkll.sparc.pillalarm.R;
import com.r0adkll.sparc.pillalarm.server.model.Prescription;

import java.util.ArrayList;
import java.util.List;

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
    public static DrugViewFragment createInstance(Prescription presc){
        DrugViewFragment frag = new DrugViewFragment();
        frag.mPrescription = presc;
        return frag;
    }


    /*******************************************
     *
     * Variables
     *
     */

    private Prescription mPrescription;

    private ViewPager mPager;
    private PagerTitleStrip mTitleStrip;

    /*******************************************
     *
     * Lifecycle methods
     *
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            mPrescription = (Prescription) savedInstanceState.getSerializable("DATA");
        }

        mPager = (ViewPager) getActivity().findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(4);
        mTitleStrip = (PagerTitleStrip) getActivity().findViewById(R.id.tabs);

        // Create list of fragemnts to display
        List<Fragment> frags = new ArrayList<Fragment>();
        frags.add(DrugInfoFragment.createInstance(mPrescription));
        frags.add(DrugWarningFragment.createInstance(mPrescription.getDrugInfo()));
        frags.add(DrugSideFragment.createInstance(mPrescription.getDrugInfo()));
        frags.add(DrugPrecautionFragment.createInstance(mPrescription.getDrugInfo()));

        // Setup fragment pager adapter

        FragPagerAdapter adapter = new FragPagerAdapter(getChildFragmentManager(), frags);
        mPager.setAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drugview, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // inflater.inflate(R.menu.fragment_home, menu);
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
        outState.putSerializable("DATA", mPrescription);
        super.onSaveInstanceState(outState);
    }

    /**
     * Fragment pager adapter
     */
    public class FragPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> frags;

        public FragPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            frags = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return frags.get(i);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "INFO";
                case 1:
                    return "WARNING";
                case 2:
                    return "SIDE EFFECTS";
                case 3:
                    return "PRECAUTIONS";
                default:
                    return "UH OH";
            }
        }
    }

}

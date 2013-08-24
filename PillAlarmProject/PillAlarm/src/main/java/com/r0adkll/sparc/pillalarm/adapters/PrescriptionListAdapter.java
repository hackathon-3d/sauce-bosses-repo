package com.r0adkll.sparc.pillalarm.adapters;

import android.content.Context;
import android.view.View;

import com.r0adkll.deadskunk.adapters.BetterListAdapter;
import com.r0adkll.sparc.pillalarm.server.model.Prescription;

import java.util.List;

/**
 * Created by r0adkll on 8/24/13.
 */
public class PrescriptionListAdapter extends BetterListAdapter<Prescription> {


    /**
     * Constructor
     *
     * @param context            application context
     * @param textViewResourceId the item view id
     * @param objects            the list of objects
     */
    public PrescriptionListAdapter(Context context, int textViewResourceId, List<Prescription> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public ViewHolder createHolder(View view) {
        return null;
    }

    @Override
    public void bindHolder(ViewHolder holder, int position) {

    }

    public static class PrescriptViewHolder extends ViewHolder{

    }
}

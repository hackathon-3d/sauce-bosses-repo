package com.r0adkll.sparc.pillalarm.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.deadskunk.adapters.BetterListAdapter;
import com.r0adkll.sparc.pillalarm.R;
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
        PrescriptViewHolder holder = new PrescriptViewHolder();
        holder.title = (TextView) view.findViewById(R.id.title);
        holder.desc = (TextView) view.findViewById(R.id.description);
        holder.tag = (TextView) view.findViewById(R.id.tag);
        return holder;
    }

    @Override
    public void bindHolder(ViewHolder holder, int position) {
        PrescriptViewHolder pvh = (PrescriptViewHolder) holder;
        Prescription data = getItem(position);

        // Bind data
        pvh.title.setText(data.getName() + " - " + data.getDose() + " mg");
        pvh.desc.setText(data.getQuantity() + " pills ");
        if(!data.getTag().isEmpty()){
            pvh.tag.setVisibility(View.VISIBLE);
            pvh.tag.setText("#"+data.getTag());
        }else
            pvh.tag.setVisibility(View.GONE);

    }

    public static class PrescriptViewHolder extends ViewHolder{
        TextView title, desc, tag;
    }
}

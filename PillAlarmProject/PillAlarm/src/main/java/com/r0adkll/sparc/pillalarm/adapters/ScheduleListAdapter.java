package com.r0adkll.sparc.pillalarm.adapters;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.r0adkll.deadskunk.adapters.BetterListAdapter;
import com.r0adkll.sparc.pillalarm.R;
import com.r0adkll.sparc.pillalarm.server.model.Schedule;

import java.util.List;

/**
 * Created by r0adkll on 8/24/13.
 */
public class ScheduleListAdapter extends BetterListAdapter<Schedule> {


    /**
     * Constructor
     *
     * @param context            application context
     * @param textViewResourceId the item view id
     * @param objects            the list of objects
     */
    public ScheduleListAdapter(Context context, int textViewResourceId, List<Schedule> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public ViewHolder createHolder(View view) {
        SchedViewHolder holder = new SchedViewHolder();
        holder.icon = (ImageView) view.findViewById(R.id.icon);
        holder.title = (TextView) view.findViewById(R.id.title);
        return holder;
    }

    @Override
    public void bindHolder(ViewHolder holder, int position) {
        SchedViewHolder svh = (SchedViewHolder) holder;
        Schedule data = getItem(position);

        // Bind data
        if(!data.isAddItem){
            svh.icon.setVisibility(View.GONE);
            svh.title.setText(data.getAmount() + " pills / " + data.getFrequency() + " hours / " + data.getDuration() + " days");
        }else{
            svh.icon.setVisibility(View.VISIBLE);
            svh.title.setText("Add schedule");
        }
    }


    public static class SchedViewHolder extends ViewHolder{
        ImageView icon;
        TextView title;
    }

}

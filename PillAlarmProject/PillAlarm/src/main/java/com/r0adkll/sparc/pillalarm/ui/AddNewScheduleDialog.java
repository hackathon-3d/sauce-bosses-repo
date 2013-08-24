package com.r0adkll.sparc.pillalarm.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.r0adkll.sparc.pillalarm.R;
import com.r0adkll.sparc.pillalarm.server.model.Schedule;

/**
 * Created by r0adkll on 8/24/13.
 */
public class AddNewScheduleDialog extends DialogFragment {

    /**
     * Create a new instance of this dialog fragment
     * @return      shits weak.
     */
    public static AddNewScheduleDialog createInstance(){
        return new AddNewScheduleDialog();
    }

    /*************************************************
     *
     * Variables
     *
     */

    private EditText mAmt, mFreq, mDur;
    private OnAddActionListener listener;
    private InputMethodManager imm;

    /*************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.Theme_PillAlarm_Dialog);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.layout_addschedule_dialog, null, false);
        mAmt = (EditText) layout.findViewById(R.id.field_amount);
        mFreq = (EditText) layout.findViewById(R.id.field_frequency);
        mDur = (EditText) layout.findViewById(R.id.field_duration);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int amt = Integer.valueOf(mAmt.getText().toString());
                        int freq = Integer.valueOf(mFreq.getText().toString());
                        int dur = Integer.valueOf(mDur.getText().toString());

                        Schedule newSched = new Schedule(amt, freq, dur);

                        // Kill the Keyboard
                        imm.hideSoftInputFromWindow(mAmt.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(mFreq.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(mDur.getWindowToken(), 0);

                        // KILL THIS BITCH AGAIN!!!!!
                        if(listener != null) listener.onScheduleOk(newSched);

                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Kill this bitch
                        // Kill the Keyboard
                        imm.hideSoftInputFromWindow(mAmt.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(mFreq.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(mDur.getWindowToken(), 0);

                        dialogInterface.dismiss();
                        if(listener != null) listener.onCancelClick();
                    }
                })
                .create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                imm.showSoftInput(mAmt, 0);
            }
        });
        return dialog;
    }

    public void setAddActionListener(OnAddActionListener listener){
        this.listener = listener;
    }


    public static interface OnAddActionListener{
        public void onScheduleOk(Schedule newSched);
        public void onCancelClick();
    }

}

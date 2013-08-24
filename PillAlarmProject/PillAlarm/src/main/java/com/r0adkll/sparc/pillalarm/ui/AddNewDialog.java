package com.r0adkll.sparc.pillalarm.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.r0adkll.deadskunk.utils.IntentUtils;
import com.r0adkll.sparc.pillalarm.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

/**
 * Created by r0adkll on 8/24/13.
 */
public class AddNewDialog extends DialogFragment {

    public static AddNewDialog createInstance(){
        return new AddNewDialog();
    }


    /*******************
     *
     * Constants and shit
     *
     */
    private static final int CAMERA_CAPTURE = 1000;

    /*************************************************
     *
     * Variables
     *
     */

    private ImageView captureImage;
    private OnAddActionListener listener;

    /*************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.Theme_PillAlarm_Dialog);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_CAPTURE){
            if(resultCode == Activity.RESULT_OK){

                // Capture successful load cache image with picasso
                Bitmap image = (Bitmap) data.getExtras().get("data");
                if(image != null){
                    if(listener != null) listener.onImageCapture(image);
                }

            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.layout_addnew_dialog, null, false);
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(layout)
                .setPositiveButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // KILL THIS BITCH AGAIN!!!!!
                        dialogInterface.dismiss();
                        if(listener != null) listener.onSkipClick();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Kill this bitch
                        dialogInterface.dismiss();
                        if(listener != null) listener.onCancelClick();
                    }
                })
                .create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setAddActionListener(OnAddActionListener listener){
        this.listener = listener;
    }


    public static interface OnAddActionListener{
        public void onImageCapture(Bitmap image);
        public void onSkipClick();
        public void onCancelClick();
    }
}

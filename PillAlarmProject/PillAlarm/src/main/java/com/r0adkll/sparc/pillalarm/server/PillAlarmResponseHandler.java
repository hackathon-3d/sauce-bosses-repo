package com.r0adkll.sparc.pillalarm.server;

import com.r0adkll.deadskunk.network.JsonHttpResponseHandler;
import com.r0adkll.deadskunk.utils.Utils;

import org.json.JSONObject;

/**
 * Created by r0adkll on 8/24/13.
 */
public abstract class PillAlarmResponseHandler extends JsonHttpResponseHandler{

    @Override
    public void onSuccess(JSONObject response) {
        Utils.log("PARH", response.toString());

        super.onSuccess(response);


        String error = response.optString("error", "SUCCESS");
        if(error.equalsIgnoreCase("SUCCESS")){
            onRequestSuccess(response);
        }else{
            onRequestFailure(error);
        }

    }

    @Override
    public void onFailure(Throwable e, JSONObject errorResponse) {
        Utils.log("PARH", "FAILED API REQUEST");
        super.onFailure(e, errorResponse);
        onRequestFailure(e.getMessage());
    }

    public abstract void onRequestSuccess(JSONObject data);
    public abstract void onRequestFailure(String message);
}

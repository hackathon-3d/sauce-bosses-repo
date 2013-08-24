package com.r0adkll.sparc.pillalarm.server.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by r0adkll on 8/24/13.
 */
public class Drug implements Serializable{

    public String name;
    public String warning;
    public String side_effects;
    public String precautions;

    public Drug(JSONObject json){
        name = json.optString("name");
        warning = json.optString("warning");
        side_effects = json.optString("side_effects");
        precautions = json.optString("precautions");
    }

}

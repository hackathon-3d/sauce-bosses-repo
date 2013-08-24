package com.r0adkll.sparc.pillalarm.server.model;

import android.content.Context;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by r0adkll on 8/24/13.
 */
public class Prescription implements Serializable{

    private String tag;
    private String name;
    private String dose;
    private int quantity;
    private Date startDate;
    private List<Schedule> scheduling;
    private Drug info;

    /**
     * Constructor
     *
     * @param name
     * @param dose
     * @param quantity
     * @param startDate
     */
    public Prescription(String tag, String name, String dose, int quantity, Date startDate, List<Schedule> scheduling){
        this.tag = tag;
        this.name = name;
        this.dose = dose;
        this.quantity = quantity;
        this.startDate = startDate;
        this.scheduling = scheduling;
    }

    public String getTag(){ return tag; }
    public String getName(){ return name; }
    public String getDose(){ return dose; }
    public int getQuantity() { return quantity; }
    public Date getStartDate(){ return startDate; }
    public List<Schedule> getScheduling(){ return  scheduling; }

    public Drug getDrugInfo(){ return info; }
    public void setDrugInfo(Drug drug){
        info = drug;
    }

    public void generateReminderEvents(Context ctx){

        // Based on prescription info, schedule notification timers


    }



}

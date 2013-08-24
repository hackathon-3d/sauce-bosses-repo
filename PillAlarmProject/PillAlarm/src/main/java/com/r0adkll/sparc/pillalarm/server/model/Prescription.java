package com.r0adkll.sparc.pillalarm.server.model;

import java.util.Date;
import java.util.List;

/**
 * Created by r0adkll on 8/24/13.
 */
public class Prescription {

    private String name;
    private String dose;
    private int quantity;
    private Date startDate;
    private List<Schedule> scheduling;

    /**
     * Constructor
     *
     * @param name
     * @param dose
     * @param quantity
     * @param startDate
     */
    public Prescription(String name, String dose, int quantity, Date startDate, List<Schedule> scheduling){
        this.name = name;
        this.dose = dose;
        this.quantity = quantity;
        this.startDate = startDate;
        this.scheduling = scheduling;
    }

    public String getName(){ return name; }
    public String getDose(){ return dose; }
    public int getQuantity() { return quantity; }
    public Date getStartDate(){ return startDate; }
    public List<Schedule> getScheduling(){ return  scheduling; }



}

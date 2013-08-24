package com.r0adkll.sparc.pillalarm.server.model;

/**
 * Created by r0adkll on 8/24/13.
 */
public class Schedule {

    /********
     * Variables
     */

    int amount;
    int frequency;
    int duration;
    public boolean isAddItem;

    /**
     * Constructr
     * @param amt       the schedule pill amount
     * @param freq      the frequency of the pill taking in hours
     * @param dur       the duration of taking pills in days
     */
    public Schedule(int amt, int freq, int dur){
        amount = amt;
        frequency = freq;
        duration = dur;
        isAddItem = false;
    }

    /**
     * This constructor forces the object to be an add item
     */
    public Schedule(){
        isAddItem = true;
    }

    public int getAmount(){ return amount; }
    public int getFrequency(){ return frequency; }
    public int getDuration(){ return duration; }

}

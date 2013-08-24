package com.r0adkll.sparc.pillalarm.server.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

import com.r0adkll.deadskunk.utils.Utils;
import com.r0adkll.sparc.pillalarm.utils.NotificationReceiver;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        // Based on prescription info, schedule notification timers
        for(Schedule sched: scheduling){

            // Compute rate
            long rate = TimeUnit.MILLISECONDS.convert(sched.frequency, TimeUnit.HOURS);

            Calendar today = Calendar.getInstance();
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            start.set(Calendar.HOUR_OF_DAY, 8);

            long firstAlarm = start.getTimeInMillis();
            start.add(Calendar.DAY_OF_YEAR, sched.duration);
            long endtime = start.getTimeInMillis();

            Intent intent = new Intent("com.r0adkll.sparc.pillalarm.POST_NOTIFICATION");
            intent.putExtra("title", "Time to take " + name);
            intent.putExtra("message", "Its time to take " + dose + " mg of " + name);
            intent.putExtra("end_time", endtime);

            PendingIntent pi = PendingIntent.getBroadcast(ctx, 1000, intent, 0);
            am.setRepeating(AlarmManager.RTC_WAKEUP, firstAlarm, rate, pi);

        }

    }



}

package fr.android.app.androidproject.Events;

import java.sql.Date;

public class Event {

    private int id;
    private String name;
    private Date date;
    private float location_latitude;
    private float location_longitude;

    public Event (int id, String name, Date date, float location_latitude, float location_longitude) {
        super();
        this.id = id;
        this.name = name;
        this.date = date;
        this.location_latitude = location_latitude;
        this.location_longitude = location_longitude;
    }

    /* Getters and Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(float location_latitude) {
        this.location_latitude = location_latitude;
    }

    public float getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(float location_longitude) {
        this.location_longitude = location_longitude;
    }

}

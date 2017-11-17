package fr.android.app.androidproject.Events;

import java.util.Date;

public class Event {

    private int id;
    private String name;
    private String date;
    private String building;
    private static int cpt;

    public Event (String name, String date, String building) {
        super();
        this.id = cpt++;
        this.name = name;
        this.date = date;
        this.building = building;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

}

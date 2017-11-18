package fr.android.app.androidproject.PostEvents;

import java.util.ArrayList;

public class PostEvent {

    private int id;
    private String name;
    private String date;
    private String note;
    private ArrayList<byte[]> pictures;
    private static int cpt;

    public PostEvent(String name, String date, String note, ArrayList<byte[]> pictures) {
        super();
        this.id = cpt++;
        this.name = name;
        this.date = date;
        this.note = note;
        this.pictures = pictures;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<byte[]> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<byte[]> pictures) {
        this.pictures = pictures;
    }

}

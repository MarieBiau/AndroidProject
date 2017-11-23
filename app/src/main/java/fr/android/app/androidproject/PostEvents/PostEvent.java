package fr.android.app.androidproject.PostEvents;

public class PostEvent {

    private int id;
    private String name;
    private String date;
    private String note;
    private byte[] pictures;

    public PostEvent(int id, String name, String date, String note, byte[] pictures) {
        super();
        this.id = id;
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

    public byte[] getPictures() {
        return pictures;
    }

    public void setPictures(byte[] pictures) {
        this.pictures = pictures;
    }

}

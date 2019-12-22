package com.example.tourmate.pojos;

public class Moment {

    private String  momentID;
    private String  eventID;
    private String  downloadUrl;

    public Moment() {
    }

    public Moment(String momentID, String eventID, String downloadUrl) {
        this.momentID = momentID;
        this.eventID = eventID;
        this.downloadUrl = downloadUrl;
    }

    public String getMomentID() {
        return momentID;
    }

    public void setMomentID(String momentID) {
        this.momentID = momentID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}

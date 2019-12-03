package com.example.tourmate;

public class TourmateEvent {

    private String eventId;
    private String eventName;
    private String departure;
    private String destination;
    private Double eventbudget;
    private String startDate;
    private String endDate;

    public TourmateEvent() {
        //required by firebase
    }

    public TourmateEvent(String eventId, String eventName, String departure, String destination, Double eventbudget, String startDate, String endDate) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.departure = departure;
        this.destination = destination;
        this.eventbudget = eventbudget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getEventbudget() {
        return eventbudget;
    }

    public void setEventbudget(Double eventbudget) {
        this.eventbudget = eventbudget;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

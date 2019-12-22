package com.example.tourmate.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate.pojos.TourmateEvent;
import com.example.tourmate.repos.DB_Repositiry;

import java.util.List;

public class EventViewModel extends ViewModel {

    private DB_Repositiry db_repositiry;
    public MutableLiveData<List<TourmateEvent>> eventListLD;
    public MutableLiveData<TourmateEvent> eventDetilsLD = new MutableLiveData<>();

    public EventViewModel() {
        db_repositiry = new DB_Repositiry();
        eventListLD = db_repositiry.eventListLD;
    }
    public void saveEvent(TourmateEvent event){
        db_repositiry.saveNewEventToFirebaseDB(event);
    }
    public void DeleteEvent(TourmateEvent event){
        db_repositiry.deleteEventFromDB(event);
    }
    public void UpdateEvent(TourmateEvent event){
        db_repositiry.UpdateEventToDB(event);
    }
    public void getEventDetails(String eventID){
         eventDetilsLD = db_repositiry.getEventByEventID(eventID);
    }
}

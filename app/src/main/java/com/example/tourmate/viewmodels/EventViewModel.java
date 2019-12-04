package com.example.tourmate.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate.pojos.TourmateEvent;
import com.example.tourmate.repos.DB_Repositiry;

import java.util.List;

public class EventViewModel extends ViewModel {

    private DB_Repositiry db_repositiry;
    public MutableLiveData<List<TourmateEvent>> eventListLD;

    public EventViewModel() {
        db_repositiry = new DB_Repositiry();
        eventListLD = db_repositiry.eventListLD;
    }
    public void saveEvent(TourmateEvent event){
        db_repositiry.saveNewEventToFirebaseDB(event);
    }
}

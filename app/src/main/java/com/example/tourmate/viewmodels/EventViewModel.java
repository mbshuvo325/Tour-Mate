package com.example.tourmate.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.tourmate.pojos.TourmateEvent;
import com.example.tourmate.repos.DB_Repositiry;

public class EventViewModel extends ViewModel {

    private DB_Repositiry db_repositiry;

    public EventViewModel() {
        db_repositiry = new DB_Repositiry();
    }
    public void saveEvent(TourmateEvent event){
        db_repositiry.saveNewEventToFirebaseDB(event);
    }
}

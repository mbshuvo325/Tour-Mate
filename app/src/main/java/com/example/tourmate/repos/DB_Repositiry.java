package com.example.tourmate.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate.pojos.TourmateEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DB_Repositiry {
    private FirebaseUser firebaseUser;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;
    public MutableLiveData<List<TourmateEvent>> eventListLD;
    public MutableLiveData<TourmateEvent> eventDetilsLD;

    public DB_Repositiry(){

        eventListLD = new MutableLiveData<>();
        eventDetilsLD = new MutableLiveData<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(firebaseUser.getUid());
        eventRef = userRef.child("MyEvent");
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<TourmateEvent> events = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    events.add(d.getValue(TourmateEvent.class));
                }
                eventListLD.postValue(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveNewEventToFirebaseDB(TourmateEvent event){
        String eventId = eventRef.push().getKey();
        event.setEventId(eventId);
        eventRef.child(eventId).setValue(event);
    }

    public void deleteEventFromDB(TourmateEvent eventPojo){

        final String eventID = eventPojo.getEventId();
        eventRef.child(eventID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference expenseRef = userRef.child("Expense");
                expenseRef.child(eventID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void UpdateEventToDB(TourmateEvent eventpojo){

        String eventId = eventpojo.getEventId();
        eventpojo.setEventId(eventId);
        eventRef.child(eventId).setValue(eventpojo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public MutableLiveData<TourmateEvent> getEventByEventID(String eventID){
        eventRef.child(eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TourmateEvent eventpojo = dataSnapshot.getValue(TourmateEvent.class);
                eventDetilsLD.postValue(eventpojo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return eventDetilsLD;
    }

    public void addMoreBudgetToDB(String eventID,double amount){
        eventRef.child(eventID).child("eventbudget").setValue(amount).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
}

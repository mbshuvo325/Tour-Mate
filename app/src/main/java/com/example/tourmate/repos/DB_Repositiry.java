package com.example.tourmate.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate.pojos.TourmateEvent;
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

    public DB_Repositiry(){

        eventListLD = new MutableLiveData<>();
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

}

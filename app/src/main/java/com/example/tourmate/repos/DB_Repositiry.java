package com.example.tourmate.repos;

import com.example.tourmate.pojos.TourmateEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DB_Repositiry {
    private FirebaseUser firebaseUser;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;

    public DB_Repositiry(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(firebaseUser.getUid());
        eventRef = userRef.child("MyEvent");
    }

    public void saveNewEventToFirebaseDB(TourmateEvent event){
        String eventId = eventRef.push().getKey();
        event.setEventId(eventId);

        eventRef.child(eventId).setValue(event);
    }

}

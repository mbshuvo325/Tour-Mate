package com.example.tourmate.repos;

import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate.pojos.Moment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MomentRepository {
    private FirebaseUser firebaseUser;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference momentsRef;
    public MutableLiveData<List<Moment>> momentLD = new MutableLiveData<>();

    private String DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_DOWNLOADS).getPath();

    public MomentRepository() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(firebaseUser.getUid());
        momentsRef = userRef.child("Moment");
    }
    public void addNewMoment(Moment moment){
        String momentID = momentsRef.push().getKey();
        moment.setMomentID(momentID);
        momentsRef.child(moment.getEventID()).child(momentID).setValue(moment);
    }

    public MutableLiveData<List<Moment>> getAllMoments(String eventID){
        momentsRef.child(eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Moment> moments = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    moments.add(d.getValue(Moment.class));
                }
                momentLD.postValue(moments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return momentLD;
    }
    public void deleteImageFromBD(Moment moment){
        String momentID = moment.getMomentID();
        momentsRef.child(moment.getEventID())
                .child(momentID)
                .removeValue();

        //Delete also firebaseStrogae

        FirebaseStorage  mFirebaseStorage = FirebaseStorage.getInstance();
        StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(moment.getDownloadUrl());



        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                /// Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
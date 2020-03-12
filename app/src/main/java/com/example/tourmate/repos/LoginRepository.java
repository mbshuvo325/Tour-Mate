package com.example.tourmate.repos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate.MainActivity;
import com.example.tourmate.pojos.UserProfileINFO;
import com.example.tourmate.viewmodels.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginRepository {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    DatabaseReference rootRef;
    DatabaseReference  userRef;
    DatabaseReference userInfo;
    private MutableLiveData<LoginViewModel.AuthenticationState> stateLiveData;
    private MutableLiveData<String> errormessage = new MutableLiveData<>();
    public MutableLiveData<UserProfileINFO> userInfoLD = new MutableLiveData<>();

    public LoginRepository(MutableLiveData<LoginViewModel.AuthenticationState> stateLiveData){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        this.stateLiveData = stateLiveData;
    }

    public MutableLiveData<LoginViewModel.AuthenticationState> loginFirebaseUser(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    firebaseUser = firebaseAuth.getCurrentUser();
                    stateLiveData.postValue(LoginViewModel.AuthenticationState.AUTHENTICATED);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                stateLiveData.postValue(LoginViewModel.AuthenticationState.UNAUTHENTICATED);
                errormessage.postValue(e.getLocalizedMessage());
            }
        });
        return stateLiveData;
    }

    ///register user

    public MutableLiveData<LoginViewModel.AuthenticationState> registerFirebaseUser(UserProfileINFO userReg){
        firebaseAuth.createUserWithEmailAndPassword(userReg.getUserEmail(),userReg.getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    firebaseUser = firebaseAuth.getCurrentUser();
                    stateLiveData.postValue(LoginViewModel.AuthenticationState.AUTHENTICATED);
                    rootRef = FirebaseDatabase.getInstance().getReference();
                    Log.i(TAG, "onComplete: "+firebaseUser.getUid());
                    userRef = rootRef.child(firebaseUser.getUid());
                    userInfo =userRef.child("userInfo");
                    String userId = firebaseUser.getUid();
                    userReg.setUesrID(userId);
                    userInfo.setValue(userReg).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                stateLiveData.postValue(LoginViewModel.AuthenticationState.UNAUTHENTICATED);
                errormessage.postValue(e.getLocalizedMessage());
            }
        });
        return stateLiveData;
    }
    ///register user End


    public FirebaseUser getFirebaseUser(){

        return firebaseUser;
    }
    public MutableLiveData<String > getErrormessage(){
        return errormessage;
    }
}

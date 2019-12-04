package com.example.tourmate.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate.MainActivity;
import com.example.tourmate.viewmodels.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRepository {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private MutableLiveData<LoginViewModel.AuthenticationState> stateLiveData;
    private MutableLiveData<String> errormessage = new MutableLiveData<>();

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

    public MutableLiveData<LoginViewModel.AuthenticationState> registerFirebaseUser(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

    ///register user End


    public FirebaseUser getFirebaseUser(){

        return firebaseUser;
    }
    public MutableLiveData<String > getErrormessage(){
        return errormessage;
    }
}

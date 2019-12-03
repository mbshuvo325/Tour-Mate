package com.example.tourmate.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate.repos.LoginRepository;

public class LoginViewModel extends ViewModel {

    public enum AuthenticationState{
        AUTHENTICATED,UNAUTHENTICATED
    }
    private LoginRepository repository;
    public MutableLiveData<AuthenticationState> stateLiveData;
    public MutableLiveData<String > errormessage = new MutableLiveData<>();

    public LoginViewModel(){
        stateLiveData = new MutableLiveData<>();
        repository = new LoginRepository(stateLiveData);
        errormessage = repository.getErrormessage();
        if (repository.getFirebaseUser() != null){
            stateLiveData.postValue(AuthenticationState.AUTHENTICATED);
        }else {
            stateLiveData.postValue(AuthenticationState.UNAUTHENTICATED);
        }
    }
    public void loginuser(String email, String password){
       stateLiveData =  repository.loginFirebaseUser(email, password);
    }

}

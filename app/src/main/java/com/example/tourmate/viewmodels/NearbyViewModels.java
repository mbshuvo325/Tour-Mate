package com.example.tourmate.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate.nearbyPlace.NearbyResponseBody;
import com.example.tourmate.repos.NearbyRepository;

public class NearbyViewModels extends ViewModel {

    private NearbyRepository repository;

    public NearbyViewModels(){
        repository = new NearbyRepository();
    }
    public MutableLiveData<NearbyResponseBody> getResponseFromRepository(String endUrl){
        return repository.getResponse(endUrl);
    }
}

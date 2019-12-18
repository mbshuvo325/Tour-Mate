package com.example.tourmate.repos;

import androidx.lifecycle.MutableLiveData;

import com.example.tourmate.helper.RetrofitClient;
import com.example.tourmate.nearbyPlace.NearbyResponseBody;
import com.example.tourmate.serviceAPi.NearByserviceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NearbyRepository {

    private MutableLiveData<NearbyResponseBody> nearbyLD = new MutableLiveData<>();

    public MutableLiveData<NearbyResponseBody> getResponse(String endUrl){

        NearByserviceApi serviceApi = RetrofitClient.getClientForNearbyPlace()
                .create(NearByserviceApi.class);

        serviceApi.getNearByResponse(endUrl).enqueue(new Callback<NearbyResponseBody>() {
            @Override
            public void onResponse(Call<NearbyResponseBody> call, Response<NearbyResponseBody> response) {

                if (response.isSuccessful()){
                    nearbyLD.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NearbyResponseBody> call, Throwable t) {

            }
        });
        return nearbyLD;
    }
}

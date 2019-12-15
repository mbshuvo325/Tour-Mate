package com.example.tourmate.repos;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.example.tourmate.currentWeather.CurrentWeatherResponsebody;
import com.example.tourmate.helper.RetrofitClient;
import com.example.tourmate.serviceAPi.WeatherServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class weather_Repository {
    public MutableLiveData<CurrentWeatherResponsebody> currentResponseLD =
            new MutableLiveData<>();

    public MutableLiveData<CurrentWeatherResponsebody> getCurrentWeather(Location location, String unit,String APiKey){
        WeatherServiceApi serviceAPi = RetrofitClient.getClient()
                .create(WeatherServiceApi.class);

        String endUrl = String.format("data/2.5/weather?lat=%f&lon=%f&&units=%s&appid=%s",
                location.getLatitude(),location.getLongitude(),unit,APiKey);
        serviceAPi.getCurrentWeather(endUrl)
                .enqueue(new Callback<CurrentWeatherResponsebody>() {
                    @Override
                    public void onResponse(Call<CurrentWeatherResponsebody> call, Response<CurrentWeatherResponsebody> response) {
                        if (response.isSuccessful()){

                           CurrentWeatherResponsebody responsebody = response.body();
                           currentResponseLD.postValue(responsebody);
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentWeatherResponsebody> call, Throwable t) {

                    }
                });
        return currentResponseLD;
    }
}

package com.example.tourmate.repos;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.example.tourmate.currentWeather.CurrentWeatherResponsebody;
import com.example.tourmate.forcastweather.ForcastWeatherResponseBody;
import com.example.tourmate.helper.RetrofitClient;
import com.example.tourmate.serviceAPi.WeatherServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class weather_Repository {
    public MutableLiveData<CurrentWeatherResponsebody> currentResponseLD =
            new MutableLiveData<>();

    public MutableLiveData<ForcastWeatherResponseBody> forcastResponseLD =
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

    public MutableLiveData<ForcastWeatherResponseBody> getForcastWeathe(Location location,String unit,String Apikey){
        WeatherServiceApi serviceAPi = RetrofitClient.getClient()
                .create(WeatherServiceApi.class);
        String endurl = String.format("data/2.5/forecast/daily?lat=%f&lon=%f&cnt=7&unit=%s&appid=%s"
        ,location.getLatitude(),location.getLongitude(),unit,Apikey);
        serviceAPi.getForcastWeather(endurl).enqueue(new Callback<ForcastWeatherResponseBody>() {
            @Override
            public void onResponse(Call<ForcastWeatherResponseBody> call, Response<ForcastWeatherResponseBody> response) {

                forcastResponseLD.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ForcastWeatherResponseBody> call, Throwable t) {

            }
        });
        return forcastResponseLD;
    }
}

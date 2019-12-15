package com.example.tourmate.viewmodels;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate.currentWeather.CurrentWeatherResponsebody;
import com.example.tourmate.repos.weather_Repository;

public class WeatherViewModel extends ViewModel {
    private weather_Repository weatherRepository;

    public WeatherViewModel(){
        weatherRepository = new weather_Repository();
    }

    public MutableLiveData<CurrentWeatherResponsebody> getCurrentWeather(Location location,String unit,String APiKey){
        return weatherRepository.getCurrentWeather(location,unit,APiKey);
    }
}

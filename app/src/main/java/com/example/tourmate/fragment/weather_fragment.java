package com.example.tourmate.fragment;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate.MainActivity;
import com.example.tourmate.R;
import com.example.tourmate.adapter.WeatherAdapter;
import com.example.tourmate.currentWeather.CurrentWeatherResponsebody;
import com.example.tourmate.forcastweather.ForcastList;
import com.example.tourmate.forcastweather.ForcastWeatherResponseBody;
import com.example.tourmate.helper.EventUtils;
import com.example.tourmate.viewmodels.LocationViewModel;
import com.example.tourmate.viewmodels.WeatherViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.PrimitiveIterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class weather_fragment extends Fragment {
    private LocationViewModel locationViewModel;
    private WeatherViewModel weatherViewModel;
    private String unit = "metric";
    private TextView cityname,description,locdate,templow,humidity,preasure;
    private RecyclerView forcastRV;
    private BottomSheetBehavior mBottomSheetBehavior;
    private Location currentlocation;
    private ImageView weatherIcon;
    public weather_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        weatherViewModel = ViewModelProviders.of(getActivity()).get(WeatherViewModel.class);
        locationViewModel.getDeviceCurrentLocation();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityname = view.findViewById(R.id.location_name);
        locdate = view.findViewById(R.id.location_date);
        description = view.findViewById(R.id.location_description);
        templow = view.findViewById(R.id.location_tempLow);
        humidity = view.findViewById(R.id.location_humidity);
        preasure = view.findViewById(R.id.location_preasure);
        forcastRV = view.findViewById(R.id.forcatRV);

        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        final ImageView buttonExpand = view.findViewById(R.id.button_expand);

        buttonExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( mBottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED)
                {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    buttonExpand.setImageResource(R.drawable.ic_arrow_up_24dp);

                }
                else
                {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    buttonExpand.setImageResource(R.drawable.ic_arrow_down_24dp);
                }

            }
        });

        locationViewModel.locationLD.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {

                Double Latitude = location.getLatitude();
                Double Longitude = location.getLongitude();

                initilizeCurrentWeather(location);
                initilizeForcastWeather(location);
                /*try {
                    convertLatLongToStreetAddress(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            }
        });

    }

    private void initilizeForcastWeather(Location location) {

        String Apikey = getString(R.string.weather_APi_key1);
        weatherViewModel.getForcastWeather(location,unit,Apikey)
                .observe(this, new Observer<ForcastWeatherResponseBody>() {
                    @Override
                    public void onChanged(ForcastWeatherResponseBody response) {
                        List<ForcastList> forecastLists = response.getList();

                        WeatherAdapter forcastRVAdapter = new WeatherAdapter(getActivity(), forecastLists,unit);
                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                        forcastRV.setLayoutManager(llm);
                        forcastRV.setAdapter(forcastRVAdapter);
                    }
                });
    }

    private void initilizeCurrentWeather(Location location) {
        String APiKey = getString(R.string.weather_APi_key1);
        weatherViewModel.getCurrentWeather(location,unit,APiKey)
                .observe(this, new Observer<CurrentWeatherResponsebody>() {
            @Override
            public void onChanged(CurrentWeatherResponsebody responsebody) {
                double temp = responsebody.getMain().getTemp();
                String city = responsebody.getName();
                String date = EventUtils.getFormattedDate(responsebody.getDt());
                int hudmmidity = responsebody.getMain().getHumidity();
                int pressurev = responsebody.getMain().getPressure();
                String weatherStat = responsebody.getWeather().get(0).getDescription();

                cityname.setText(city);
                locdate.setText(date);
                description.setText(weatherStat);
                templow.setText((Math.round((temp)))+""+EventUtils.DEGREE+EventUtils.UNIT_CELCIUS_SYMBOL);
                humidity.setText((hudmmidity)+""+"%");
                preasure.setText((pressurev)+""+"hpa");
            }
        });
        //weatherViewModel.getCurrentWeather(location,unit,APiKey);
    }

    private void convertLatLongToStreetAddress(Location location) throws IOException {
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                location.getLongitude(),1);
        String address = addressList.get(0).getAddressLine(0);
        cityname.setText(address);

    }

}

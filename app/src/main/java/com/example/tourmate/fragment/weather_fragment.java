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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate.MainActivity;
import com.example.tourmate.R;
import com.example.tourmate.viewmodels.LocationViewModel;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class weather_fragment extends Fragment {
    private LocationViewModel locationViewModel;

    private TextView latlong,addressTV;

    public weather_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        locationViewModel.getDeviceCurrentLocation();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        latlong = view.findViewById(R.id.latlong);
        addressTV = view.findViewById(R.id.streetAddress);
        locationViewModel.locationLD.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {

                Double Latitude = location.getLatitude();
                Double Longitude = location.getLongitude();
                Toast.makeText(getActivity(), ""+Latitude+Longitude, Toast.LENGTH_SHORT).show();
                latlong.setText(String.valueOf(Latitude)+""+String.valueOf(Longitude));

                try {
                    convertLatLongToStreetAddress(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void convertLatLongToStreetAddress(Location location) throws IOException {
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                location.getLongitude(),1);
        String address = addressList.get(0).getAddressLine(0);
        addressTV.setText(address);

    }

}

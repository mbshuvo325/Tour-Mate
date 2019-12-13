package com.example.tourmate.fragment;


import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tourmate.R;
import com.example.tourmate.viewmodels.LocationViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class location_fragment extends Fragment {

    private LocationViewModel locationViewModel;


    public location_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        locationViewModel.getDeviceCurrentLocation();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationViewModel.locationLD.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                final TextView loc = view.findViewById(R.id.location_latlong);
                loc.setText(""+location.getLatitude()+""+location.getLongitude());
            }
        });
    }
}

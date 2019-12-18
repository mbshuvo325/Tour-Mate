package com.example.tourmate.fragment;


import android.location.Location;
import android.media.Session2Command;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate.R;
import com.example.tourmate.nearbyPlace.NearbyResponseBody;
import com.example.tourmate.nearbyPlace.Result;
import com.example.tourmate.viewmodels.LocationViewModel;
import com.example.tourmate.viewmodels.NearbyViewModels;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class location_fragment extends Fragment implements OnMapReadyCallback {

    private LocationViewModel locationViewModel;
    private GoogleMap googleMap;
    private String Type = "";
    private Button resturentbtn,atmbtn;
    private Location myLocation = null;
    private NearbyViewModels nearbyViewModel;


    public location_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        nearbyViewModel = ViewModelProviders.of(getActivity()).get(NearbyViewModels.class);
        locationViewModel.getDeviceCurrentLocation();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.nearby_map);
        mapFragment.getMapAsync(this);
        resturentbtn = view.findViewById(R.id.resturent);
        atmbtn = view.findViewById(R.id.atm);

        locationViewModel.locationLD.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {

                if (location == null){
                    return;
                }
                myLocation = location;
                LatLng currentposition = new LatLng(location.getLatitude(),location.getLongitude());

                if (googleMap != null){
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentposition,14f));
                }
            }
        });

        resturentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                googleMap.clear();
                Type = "restaurant";
                getNearByResponseData();
            }
        });

        atmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                googleMap.clear();
                Type = "atm";
                getNearByResponseData();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMyLocationEnabled(true);
        locationViewModel.getDeviceCurrentLocation();
    }

    public void getNearByResponseData(){

        String Apikey = getString(R.string.nearby_APi_key1);
        String endUrl = String.format("place/nearbysearch/json?location=%f,%f&radius=1500&type=%s&key=%s",
                myLocation.getLatitude(),
                myLocation.getLongitude(),
                Type,
               Apikey);
        Toast.makeText(getActivity(), "btn click", Toast.LENGTH_SHORT).show();
        nearbyViewModel.getResponseFromRepository(endUrl)
        .observe(this, new Observer<NearbyResponseBody>() {
            @Override
            public void onChanged(NearbyResponseBody nearbyResponseBody) {

                List<Result> results = nearbyResponseBody.getResults();

                for (Result r : results){
                    double lat = r.getGeometry().getLocation().getLat();
                    double lng = r.getGeometry().getLocation().getLng();
                    String name = r.getName();
                    LatLng latLng = new LatLng(lat,lng);
                    googleMap.addMarker(new MarkerOptions()
                            .title(name)
                            .position(latLng));
                }

            }
        });

    }
}

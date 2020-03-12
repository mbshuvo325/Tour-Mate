package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tourmate.R;
import com.example.tourmate.fragment.Event_List;
import com.example.tourmate.pojos.TourmateEvent;
import com.example.tourmate.viewmodels.LocationViewModel;
import com.example.tourmate.viewmodels.LoginViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawer;
    public static String eventID;
    private TourmateEvent tourmateEvent;
    private NavController navController;
    private boolean isExit = false;
    private boolean isBack = false;
    private LocationViewModel locationViewModel;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /// iSlocationPermissionGranted();
        tourmateEvent = new TourmateEvent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);


        final BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(NavListener);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ///
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.event_List:
                        isExit = true;
                        bottomNav.setVisibility(View.GONE);
                        break;

                    case R.id.login_Fragment:
                        isExit = true;
                        bottomNav.setVisibility(View.GONE);
                        break;

                    case R.id.event_details_fragment:
                        /// bottomNav.getMenu().findItem(R.id.event_details_fragment).setChecked(true);
                        bottomNav.setVisibility(View.VISIBLE);
                        isBack = true;
                        isExit = false;
                        break;
                    /*case R.id.bottom_camera:
                        bottomNav.setVisibility(View.VISIBLE);
                        isBack = true;
                        isExit = false;
                        break;*/

                    default:
                        bottomNav.setVisibility(View.GONE);
                        isExit = false;
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.event_List);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_profile:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.profile_fragment);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_weather:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.weather_fragment);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_location:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.location_fragment);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_Compas:
                loginViewModel.getLogoutUser();
                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.login_Fragment);
                drawer.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Bundle bundle = new Bundle();
                    bundle.putString("eid",eventID);

                    switch (menuItem.getItemId()) {
                        case R.id.bottom_home:
                            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment)
                                    .navigate(R.id.event_List);
                            break;
                        case R.id.bottom_camera:
                            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment)
                                    .navigate(R.id.gallery_fragment,bundle);
                            // dispatchCameraIntent();
                            break;

                        case R.id.bottom_search:
                            break;
                    }
                    return true;
                }
            };

}
package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.tourmate.fragment.Event_List;
import com.example.tourmate.pojos.TourmateEvent;
import com.example.tourmate.viewmodels.LocationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String eventID;
    private TourmateEvent tourmateEvent;
    private NavController navController;
    private boolean isExit = false;
    private boolean isBack = false;
    private LocationViewModel locationViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // iSlocationPermissionGranted();
        tourmateEvent = new TourmateEvent();

        eventID = tourmateEvent.getEventId();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);

       final BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(NavListener);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
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

        ///

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.nav_home:
               Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.event_List);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_profile:
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.profile_fragment);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_weather:
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.weather_fragment);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_location:
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.location_fragment);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_Compas:
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.compas_fragment);
                drawer.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            final Bundle bundle = new Bundle();
            bundle.putString("id",eventID);
            switch (menuItem.getItemId()){
                case R.id.bottom_home:
                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment)
                            .navigate(R.id.event_List);
                    break;
                case R.id.bottom_camera:
                    break;
                case R.id.bottom_search:
                    break;
            }
            return true;
        }
    };


    /*@RequiresApi(api = Build.VERSION_CODES.M)
    private boolean iSlocationPermissionGranted(){
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},111);
            return false;

        }
        return true ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

            locationViewModel.getDeviceCurrentLocation();
        }
    }*/
}

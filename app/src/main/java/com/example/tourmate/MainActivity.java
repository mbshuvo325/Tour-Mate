package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
}

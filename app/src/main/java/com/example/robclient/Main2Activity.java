package com.example.robclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Main2Activity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GalleryFragmet()).commit();
            navigationView.setCheckedItem(R.id.viewgallery);
        }

    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.viewgallery:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GalleryFragmet()).commit();
                break;
            case R.id.locations:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LocationFragment()).commit();
                Intent mapsIntent=new Intent(Main2Activity.this,MapsActivity.class);
                startActivity(mapsIntent);

                break;
            case R.id.testimonials:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TestimonialFragment()).commit();
                break;
            case R.id.contactus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CommunicationFragment()).commit();

                break;
            case R.id.nav_send:
                Toast.makeText(this,"Send",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this,"Share",Toast.LENGTH_LONG).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


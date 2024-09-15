package com.technogenis.autocurtainwindow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technogenis.autocurtainwindow.fragment.HomeFragment;
import com.technogenis.autocurtainwindow.fragment.HumidityFragment;
import com.technogenis.autocurtainwindow.start.LoginScreen;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    NavigationView navMenu;
    ActionBarDrawerToggle toggle;
    DrawerLayout drayerLayout;

    TextView tv_result;
    FragmentManager fragmentManager;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        navMenu=findViewById(R.id.navMenu);
        drayerLayout=findViewById(R.id.drawerlayout);

        tv_result=findViewById(R.id.tv_result);

            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new HomeFragment()).commit();


        toggle=new ActionBarDrawerToggle(this,drayerLayout,toolbar,R.string.app_name,R.string.app_name);
        drayerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fragmentManager = getSupportFragmentManager();

        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.menuHumidity){
                    fragment = new HumidityFragment();
                    drayerLayout.closeDrawer(GravityCompat.START);

                }
                else if(item.getItemId() == R.id.menuTemp){
                    fragment = new HumidityFragment();
                    drayerLayout.closeDrawer(GravityCompat.START);
                }

                else if(item.getItemId() == R.id.menuWin){
                    fragment = new HumidityFragment();
                    drayerLayout.closeDrawer(GravityCompat.START);
                }

                else if(item.getItemId() == R.id.menuCurtain){
                    fragment = new HumidityFragment();
                    drayerLayout.closeDrawer(GravityCompat.START);
                }

                else if(item.getItemId() == R.id.menuRoom){
                    fragment = new HumidityFragment();
                    drayerLayout.closeDrawer(GravityCompat.START);
                }

                else if(item.getItemId() == R.id.menuHistory){
                    fragment = new HumidityFragment();
                    drayerLayout.closeDrawer(GravityCompat.START);
                }

                else if(item.getItemId() == R.id.menu_logout){
                    fragment = new HumidityFragment();
                    drayerLayout.closeDrawer(GravityCompat.START);
                }
                else if(item.getItemId() == R.id.menu_logout){
                    Intent logIntent=new Intent(MainActivity.this,LoginScreen.class);
                    startActivity(logIntent);
                    finish();
                    Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                    drayerLayout.closeDrawer(GravityCompat.START);
                }

                if (fragment != null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_frame, fragment);
                    transaction.addToBackStack(null); // Optional: to add fragment to back stack
                    transaction.commit();
                }

                drayerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }
}
package com.vn.assignment_adr2.Screen;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.vn.assignment_adr2.R;

public class TrangChu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_trang_chu);

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        abdt.setDrawerIndicatorEnabled(true);
        abdt.syncState();
        drawerLayout.addDrawerListener(abdt);

        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(item -> {
            Fragment fragment = new QuanLyFragment();
            if (item.getItemId() == R.id.nav_gioi_thieu){
                toolbar.setTitle(R.string.gioi_thieu);
                fragment = new GioiThieuFragment();
            } else if (item.getItemId() == R.id.nav_logout) {
                finish();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();
            drawerLayout.close();
            return true;
        });

        toolbar.setTitle(R.string.quan_ly);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, new QuanLyFragment())
                .commit();
        drawerLayout.close();
    }
}
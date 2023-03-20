package com.example.grocerease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    public static final String FIRSTBARCODEKEY = "firstBarcode";
    public static final String SECONDBARCODEKEY = "secondBarcode";
    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.home);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // Select back this page, so no changes
                // By default, this code block should not be activated on reselect
                Log.i("mainNavigation", "Home button pressed, this shouldn't happen");
                return true;
            case R.id.scan:
                // Start the scan page when the scan button is pressed
                Log.i("mainNavigation", "Scan button pressed");
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
                return true;
            case R.id.profile:
                Intent intent1 = new Intent(MainActivity.this, DatabaseAddActivity.class);
                startActivity(intent1);
                return true;
        }
        return false;
    }
}



//{
//    "id" : 12345678,
//    "name" : marinara sauce 1
//    "calories": 5
//    "protein": 10
//    "carbohydrates": 20
//    "sugar" : 5
//        }
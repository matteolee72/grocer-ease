package com.example.grocerease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.profile);

    }
    one_item_page oneItemPage = new one_item_page();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, oneItemPage).commit();
                return true;
            case R.id.scan:
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
                return true;
            case R.id.profile:
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
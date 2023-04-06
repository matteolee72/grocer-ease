package com.example.grocerease;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class FavouritesActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{



    private UserDatabaseObject userObject;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Intent intent = getIntent();
        userObject = (UserDatabaseObject) intent.getSerializableExtra(MainActivity.USEROBJECTKEY);
        Log.d("user object has reached fav", "onCreate: "+userObject.getUserPassword());

        NavigationBarView navigationBarView;
        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.profile);

        Button profileButton;
        profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ButtonPress","Profile Button Clicked");
                Intent intent = new Intent(FavouritesActivity.this, ProfileActivity.class);
                intent.putExtra(MainActivity.USEROBJECTKEY,userObject);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // Select back this page, so no changes
                // By default, this code block should not be activated on reselect
                Log.i("mainNavigation", "Home button pressed");
                Intent intent1 = new Intent(FavouritesActivity.this, MainActivity.class);
                intent1.putExtra(MainActivity.USEROBJECTKEY,userObject);
                startActivity(intent1);
                return true;
            case R.id.scan:
                // Start the scan page when the scan button is pressed
                Log.i("mainNavigation", "Scan button pressed");
                Intent intent2 = new Intent(FavouritesActivity.this, ScanActivity.class);
                intent2.putExtra(MainActivity.USEROBJECTKEY,userObject);
                startActivity(intent2);
                return true;
            case R.id.profile: // database add activity instead of profile
                Log.i("mainNavigation", "Profile button pressed");
                return true;
        }
        return false;
    }
}

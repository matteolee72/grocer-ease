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

public class ProfileActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {


    private UserDatabaseObject userObject;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        userObject = (UserDatabaseObject) intent.getSerializableExtra(MainActivity.USEROBJECTKEY);
        Log.d("user object has reached profile", "onCreate: "+userObject.getUserPassword());

        Button favouritesButton;
        favouritesButton = findViewById(R.id.favourites_button);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ButtonPress","Favourites Button Clicked");
                Intent intent = new Intent(ProfileActivity.this, FavouritesActivity.class);
                intent.putExtra(MainActivity.USEROBJECTKEY, userObject);
                startActivity(intent);
            }
        });

        Button editButton;
        editButton = findViewById(R.id.edit_button);
    // TODO: Get the parent name of a data snapshot? Pass it to QuizActivity
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // Select back this page, so no changes
                // By default, this code block should not be activated on reselect
                Log.i("mainNavigation", "Home button pressed");
                Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
                intent1.putExtra(MainActivity.USEROBJECTKEY,userObject);
                startActivity(intent1);
                return true;
            case R.id.scan:
                // Start the scan page when the scan button is pressed
                Log.i("mainNavigation", "Scan button pressed");
                Intent intent2 = new Intent(ProfileActivity.this, ScanActivity.class);
                intent2.putExtra(MainActivity.USEROBJECTKEY,userObject);
                startActivity(intent2);
                return true;
            case R.id.profile: // database add activity instead of profile
                Log.i("mainNavigation", "Profile button pressed");
                Intent intent3 = new Intent(ProfileActivity.this, FavouritesActivity.class);
                intent3.putExtra(MainActivity.USEROBJECTKEY,userObject);
                startActivity(intent3);
                return true;
        }
        return false;
    }
}

package com.example.grocerease;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

public class FavouritesActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    private UserDatabaseObject userObject;
    private PreferencesHelper preferencesHelper;
    Gson gson = new Gson();

    @Override
    protected void onResume(){ //making sure history is re-rendered in the event it is updated
        super.onResume();
        // reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);
        String userName = preferencesHelper.readString("username","error");

        TextView userNameText = findViewById(R.id.username_textview);
        userNameText.setText(userName);

        NavigationBarView navigationBarView;
        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.profile);

        RecyclerView recyclerView = findViewById(R.id.favouritesRecyclerView);
        RecyclerView.Adapter<FavouritesAdapter.ViewHolder> favouritesAdapter = new FavouritesAdapter(this, userObject.getUserFavourites());
        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button profileButton;
        profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ButtonPress","Profile Button Clicked");
                Intent intent = new Intent(FavouritesActivity.this, ProfileActivity.class);
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
                startActivity(intent1);
                return true;
            case R.id.scan:
                // Start the scan page when the scan button is pressed
                Log.i("mainNavigation", "Scan button pressed");
                Intent intent2 = new Intent(FavouritesActivity.this, ScanActivity.class);
                startActivity(intent2);
                return true;
            case R.id.profile: // database add activity instead of profile
                Log.i("mainNavigation", "Profile button pressed");
                return true;
        }
        return false;
    }
}

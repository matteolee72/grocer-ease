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

import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private UserDatabaseObject userObject;
    private PreferencesHelper preferencesHelper;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);
        String userName = preferencesHelper.readString("username","error");

        TextView userNameText = findViewById(R.id.username_textview);
        userNameText.setText(userName);
        TextView bloodPressureText = findViewById(R.id.bloodPressure_text);
        bloodPressureText.setText(userObject.getUserPreferences().getBloodPressure());
        TextView bloodSugarText = findViewById(R.id.bloodSugar_text);
        bloodSugarText.setText(userObject.getUserPreferences().getBloodSugarLevels());
        TextView cholesterolText = findViewById(R.id.cholesterol_text);
        cholesterolText.setText(userObject.getUserPreferences().getHighCholesterol());
        TextView nameText = findViewById(R.id.name_text2);
        nameText.setText(userObject.getUserPreferences().getUserObjectName());
        TextView weightText = findViewById(R.id.weight_text);
        weightText.setText(userObject.getUserPreferences().getUserWeight());
        TextView heightText = findViewById(R.id.height_text);
        heightText.setText(userObject.getUserPreferences().getUserHeight());
        TextView birthdayText = findViewById(R.id.birthday_text);
        birthdayText.setText(userObject.getUserPreferences().getBirthday().toString());
        TextView genderText = findViewById(R.id.sex_text);
        genderText.setText(userObject.getUserPreferences().getSex());
        TextView goalText = findViewById(R.id.goal_text);
        goalText.setText(userObject.getUserPreferences().getWeightGoals());



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

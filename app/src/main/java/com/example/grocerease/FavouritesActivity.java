package com.example.grocerease;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerease.Objects.FoodDatabaseObject;
import com.example.grocerease.Objects.UserDatabaseObject;
import com.example.grocerease.Utils.CaptureAct;
import com.example.grocerease.Utils.FavouritesAdapter;
import com.example.grocerease.Utils.PreferencesHelper;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

/*** Favourites Activity
 * This Activity shows the user their favourites. ***/

public class FavouritesActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    private UserDatabaseObject userObject;
    private PreferencesHelper preferencesHelper;
    Gson gson = new Gson();

    /** onResume() ensures that favourites is re-rendered in the event it is updated **/
    @Override
    protected void onResume(){
        super.onResume();
        // reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);
    }

    /** onCreate() method **/
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Getting the user object and username
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);
        String userName = preferencesHelper.readString("username","error");

        // Setting username Textview
        TextView userNameText = findViewById(R.id.username_textview);
        userNameText.setText(userName);

        // Setting imageView - depending on user gender
        ImageView imageView = findViewById(R.id.photo);
        if (userObject.getUserPreferences().getSex().equals("Male")){
            imageView.setImageResource(R.drawable.boy_profile);
        } else {
            imageView.setImageResource(R.drawable.girl_profile);
        }

        // Setting navigationBarView
        NavigationBarView navigationBarView;
        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.profile);

        // Instantiating the recyclerView
        RecyclerView recyclerView = findViewById(R.id.favouritesRecyclerView);
        RecyclerView.Adapter<FavouritesAdapter.ViewHolder> favouritesAdapter = new FavouritesAdapter(this, userObject.getUserFavourites());
        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Instantiating profileButton -> goes to profile activity
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

    /** Logic for Navbar **/
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
                scanCode();
                return true;
            case R.id.profile: // database add activity instead of profile
                Log.i("mainNavigation", "Profile button pressed");
                return true;
        }
        return false;
    }

    /** Logic entering scan Activity from favourites Activity **/
    public void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
    {
        // Check if we get a valid output from the barcode scanner
        if(result.getContents() != null)
        {
            // Prepare a new intent singleItemToScan so we can get the result from the previous
            // activity and store it to firstFoodItem
            Intent singleItemToScan = getIntent();
            FoodDatabaseObject firstFoodItem = (FoodDatabaseObject) singleItemToScan.getSerializableExtra(MainActivity.FIRSTBARCODEKEY);

            // Make a log of what we receive from the server
            String barcodeNum = result.getContents();

            // If firstFoodItem contains nothing, then we assume that we are scanning the first barcode
            // so we pass the barcode number that we scan and pass it to the next activity
            if (firstFoodItem == null){
                Intent intent = new Intent(FavouritesActivity.this, SingleItemAnalyzeActivity.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum);
                startActivity(intent);
                finish();
            }
            else{
                // If firstFoodItem contains something, then we assume that we are now
                // scanning the second barcode. So we run this block of code.
                Intent intent = new Intent(FavouritesActivity.this, TwoItemCompareActivity.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, firstFoodItem); //Using putExtra, implement mPreferences next
                intent.putExtra(MainActivity.SECONDBARCODEKEY, barcodeNum);
                startActivity(intent);
                finish();
            }
        }
    });
}

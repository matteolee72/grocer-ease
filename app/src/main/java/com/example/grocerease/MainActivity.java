package com.example.grocerease;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.grocerease.Objects.FoodDatabaseObject;
import com.example.grocerease.Objects.UserDatabaseObject;
import com.example.grocerease.Utils.CaptureAct;
import com.example.grocerease.Utils.ListAdapter;
import com.example.grocerease.Utils.PreferencesHelper;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

/*** Main Activity
 * This Activity serves as the "homepage" of our app. It also shows the user their history. ***/

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    // Setting final Strings to be used as keys for passing data between Activities
    public static final String FIRSTBARCODEKEY = "firstBarcode";
    public static final String SECONDBARCODEKEY = "secondBarcode";
    public static final String USEROBJECTKEY = "userObjectKey";
    NavigationBarView navigationBarView;
    String barcodeNum;
    private PreferencesHelper preferencesHelper;
    private UserDatabaseObject userObject;
    Gson gson = new Gson();

    /** onResume() ensures that history is re-rendered in the event it is updated **/
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting navigationBarView
        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.home);

        // Reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);

        // Instantiating the recyclerView
        RecyclerView recyclerView = findViewById(R.id.historyRecyclerView);
        RecyclerView.Adapter<ListAdapter.ViewHolder> historyAdapter = new ListAdapter(this, userObject.getUserHistory());
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /** Logic for Navbar **/
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
                scanCode();
                return true;
            case R.id.profile: // database add activity instead of profile
                Log.i("mainNavigation", "Profile button pressed");
                Intent intent1 = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent1);
                return true;
        }
        return false;
    }
    // Set the settings to create the scanning activity and eventually call the barLauncher
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
            barcodeNum = result.getContents();

            // If firstFoodItem contains nothing, then we assume that we are scanning the first barcode
            // so we pass the barcode number that we scan and pass it to the next activity
            if (firstFoodItem == null){
                Intent intent = new Intent(MainActivity.this, SingleItemAnalyzeActivity.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum);
                startActivity(intent);
                finish();
            }
            else{
                // If firstFoodItem contains something, then we assume that we are now
                // scanning the second barcode. So we run this block of code.
                Intent intent = new Intent(MainActivity.this, TwoItemCompareActivity.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, firstFoodItem); //Using putExtra, implement mPreferences next
                intent.putExtra(MainActivity.SECONDBARCODEKEY, barcodeNum);
                startActivity(intent);
                finish();
            }
        }
    });


}
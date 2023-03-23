package com.example.grocerease;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


public class ScanActivity extends AppCompatActivity {
    // Prepare a variable to store the incoming barcode number
    String barcodeNum;


    // When the activity is created, get the previously scanned information if it exists
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        // Immediately launch the scancode function to retrieve the code using the barcode scanner
        scanCode();
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

    // Determine what to do when the barcode launcher receives the barcode
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
    {
        // Check if we get a valid output from the barcode scanner
        if(result.getContents() != null)
        {
            // Prepare a new intent singleItemToScan so we can get the result from the previous
            // activity and store it to firstFoodItem
            Intent singleItemToScan = getIntent();
            DatabaseItemObject firstFoodItem = (DatabaseItemObject) singleItemToScan.getSerializableExtra(MainActivity.FIRSTBARCODEKEY);

            // Make a log of what we receive from the server
            barcodeNum = result.getContents();
            Log.d("scanActivity", barcodeNum);

            // If firstFoodItem contains nothing, then we assume that we are scanning the first barcode
            // so we pass the barcode number that we scan and pass it to the next activity
            if (firstFoodItem == null){
                Intent intent = new Intent(ScanActivity.this, SingleItemAnalyze.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum); //Using putExtra, implement mPreferences next
                startActivity(intent);
            }
            else{
                // If firstFoodItem contains something, then we assume that we are now
                // scanning the second barcode. So we run this block of code.
                Intent intent = new Intent(ScanActivity.this, TwoItemCompare.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, firstFoodItem); //Using putExtra, implement mPreferences next
                intent.putExtra(MainActivity.SECONDBARCODEKEY, barcodeNum);
                startActivity(intent);
            }
        }
    });




}
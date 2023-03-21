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

    EditText debug_barcodenumber;
    Button debug_scanbutton;
    String barcodeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Intent singleItemToScan = getIntent();
        String firstBarcode = singleItemToScan.getStringExtra(MainActivity.FIRSTBARCODEKEY);
        Log.i("SingleItemAnalyse", "Barcode received: "+ firstBarcode);

        debug_barcodenumber = findViewById(R.id.editText_debug_code);
        debug_scanbutton = findViewById(R.id.button_scan_activity);



        debug_scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
                Log.i("ScanActivity", "Barcode Number: " + barcodeNum);

                // If the barcodeNum is not empty
                // ie there already a barcode stored in it due to the barcode scanning earlier
                if (!barcodeNum.isEmpty()) {
                    // We check if the stored first barcode from the 1 item page is passed into
                    // this function.
                    if (firstBarcode == null){
                        Intent intent = new Intent(ScanActivity.this, SingleItemAnalyze.class);
                        intent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum); //Using putExtra, implement mPreferences next
                        startActivity(intent);
                    }
                    else{
                        // If firstBarcode contains something, then we assume that we are now
                        // scanning the second barcode. So we run this block of code.
                        Intent intent = new Intent(ScanActivity.this, TwoItemCompare.class);
                        intent.putExtra(MainActivity.FIRSTBARCODEKEY, firstBarcode); //Using putExtra, implement mPreferences next
                        intent.putExtra(MainActivity.SECONDBARCODEKEY, barcodeNum);
                        startActivity(intent);
                    }
                }

            }
            // creating new scancode class to setup camera
            // stuff here not necessary, can change
            public void scanCode() {
                ScanOptions options = new ScanOptions();
                options.setPrompt("Volume up to flash on");
                options.setBeepEnabled(true);
                options.setOrientationLocked(true);
                options.setCaptureActivity(CaptureAct.class);
                barLauncher.launch(options);
            }

            // TO BE TESTED
            // able to retrieve data from barcode
            // if barcode is correct, we return result
            ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
            {
                if(result.getContents() != null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
                    builder.setTitle("Result");
                    builder.setMessage(result.getContents());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                }
            });
        });
    }
}
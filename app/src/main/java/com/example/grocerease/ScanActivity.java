package com.example.grocerease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ScanActivity extends AppCompatActivity {

    EditText debug_barcodenumber;
    Button debug_scanbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Intent singleItemToScan = getIntent();
        String barcodeNum1 = singleItemToScan.getStringExtra(MainActivity.FIRSTBARCODEKEY);
        Log.i("SingleItemAnalyse", "Barcode received: "+ barcodeNum1);

        debug_barcodenumber = findViewById(R.id.editText_debug_code);
        debug_scanbutton = findViewById(R.id.button_scan_activity);



        debug_scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String barcodeNum = debug_barcodenumber.getText().toString();
                Log.i("ScanActivity", "Barcode Number: " + barcodeNum);

                if (!barcodeNum.isEmpty()) {
                    if (barcodeNum1 == null){
                        Intent intent = new Intent(ScanActivity.this, SingleItemAnalyze.class);
                        intent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum); //Using putExtra, implement mPreferences next
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(ScanActivity.this, TwoItemCompare.class);
                        intent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum1); //Using putExtra, implement mPreferences next
                        intent.putExtra(MainActivity.SECONDBARCODEKEY, barcodeNum);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(ScanActivity.this, "Enter a barcode number", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
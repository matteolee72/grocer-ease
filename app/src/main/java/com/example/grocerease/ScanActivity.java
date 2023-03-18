package com.example.grocerease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class ScanActivity extends AppCompatActivity {

    EditText debug_barcodenumber;
    Button debug_scanbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        debug_barcodenumber = findViewById(R.id.editText_debug_code);
        debug_scanbutton = findViewById(R.id.button_scan_activity);

        String barcodeNum = debug_barcodenumber.getText().toString();
        debug_scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanActivity.this, SingleItemAnalyze.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum);
                startActivity(intent);
            }
        });
    }
}
package com.example.grocerease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class SingleItemAnalyze extends AppCompatActivity {

    TextView itemName, calories, mass, carbs, protein, fats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_analyze);

        itemName = findViewById(R.id.itemName);
        calories = findViewById(R.id.calories);
        mass = findViewById(R.id.mass);
        carbs = findViewById(R.id.carbs);
        protein = findViewById(R.id.protein);
        fats = findViewById(R.id.fats);

        Intent intent = getIntent();
        String barcodeNum = intent.getStringExtra(MainActivity.FIRSTBARCODEKEY);

    }
}
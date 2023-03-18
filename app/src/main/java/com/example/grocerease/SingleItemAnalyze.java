package com.example.grocerease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SingleItemAnalyze extends AppCompatActivity {

    TextView itemName, calories, mass, carbs, protein, fats;

    // -----==DEBUG START==------
    // TODO: Implement database here
    Map<String, HashMap> database;
    HashMap<String, String> item;
    TextView debug_barcodeNumber;
    // -----==DEBUG END==-----
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
        // ------- DEBUG -------
        debug_barcodeNumber = findViewById(R.id.debug_barcodeNum);

        database = new HashMap<>();
        item = new HashMap<>();
        item.put("name", "Marinara Sauce");
        item.put("calories", "100 kcal");
        item.put("mass", "200 g");
        item.put("carbohydrates", "300 g");
        item.put("protein", "400 g");
        item.put("fats", "500 g");
        HashMap<String, String> item1 = new HashMap<>();
        item1.put("name", "Carbonara Sauce");
        item1.put("calories", "500 kcal");
        item1.put("mass", "400 g");
        item1.put("carbohydrates", "300 g");
        item1.put("protein", "200 g");
        item1.put("fats", "100 g");
        database.put("123456", item);
        database.put("654321", item1);
        // ------- DEBUG -------

        Intent intent = getIntent();
        String barcodeNum = intent.getStringExtra(MainActivity.FIRSTBARCODEKEY);
        Log.i("SingleItemAnalyse", "Barcode received: "+ barcodeNum);

        HashMap item_dict = database.get(barcodeNum);
        if (item_dict == null) {
            Toast.makeText(SingleItemAnalyze.this, barcodeNum + " is not in database", Toast.LENGTH_LONG).show();
        }
        else {
            Log.i("SingleItemAnalyse", "item_dict" + item_dict.toString());
            itemName.setText(item_dict.get("name").toString());
            calories.setText(item_dict.get("calories").toString());
            mass.setText(item_dict.get("mass").toString());
            carbs.setText("Carbs\n" + item_dict.get("carbohydrates").toString());
            protein.setText("Protein\n" + item_dict.get("protein").toString());
            fats.setText("Fats\n" + item_dict.get("fats").toString());

            debug_barcodeNumber.setText(barcodeNum);
        }
    }
}

//{
//    "id" : 12345678,
//    "name" : marinara sauce 1
//    "calories": 5
//    "protein": 10
//    "carbohydrates": 20
//    "sugar" : 5
//        }
package com.example.grocerease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class TwoItemCompare extends AppCompatActivity {

    TextView itemName_1,mass_1,calories_1, carbs_1, protein_1, fats_1;
    TextView itemName_2,mass_2,calories_2, carbs_2, protein_2, fats_2;

    // -----==DEBUG START==------
    // TODO: Implement database here
    Map<String, HashMap> database;
    HashMap<String, String> item;
    TextView debug_barcodeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_item_compare);

        itemName_1 = findViewById(R.id.food_name_1);
        calories_1 = findViewById(R.id.calories_1);
        mass_1 = findViewById(R.id.food_mass_1);
        carbs_1 = findViewById(R.id.carbs_1);
        protein_1 = findViewById(R.id.protein_1);
        fats_1 = findViewById(R.id.fats_1);

        itemName_2 = findViewById(R.id.food_name_2);
        calories_2 = findViewById(R.id.calories_2);
        mass_2 = findViewById(R.id.food_mass_2);
        carbs_2 = findViewById(R.id.carbs_2);
        protein_2 = findViewById(R.id.protein_2);
        fats_2 = findViewById(R.id.fats_2);
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
        String barcodeNum1 = intent.getStringExtra(MainActivity.FIRSTBARCODEKEY);
        String barcodeNum2 = intent.getStringExtra(MainActivity.SECONDBARCODEKEY);
        Log.i("TwoItemCompare", "Barcode received: "+ barcodeNum1);
        Log.i("TwoItemCompare", "Barcode received: "+ barcodeNum2);

        HashMap item_dict1 = database.get(barcodeNum1);
        HashMap item_dict2 = database.get(barcodeNum2);
        if (item_dict1 == null) {
            Toast.makeText(TwoItemCompare.this, barcodeNum2 + " is not in database", Toast.LENGTH_LONG).show();
        }
        else {
            Log.i("SingleItemAnalyse", "item_dict" + item_dict1.toString());
            itemName_1.setText(item_dict1.get("name").toString());
            calories_1.setText(item_dict1.get("calories").toString());
            mass_1.setText(item_dict1.get("mass").toString());
            carbs_1.setText(item_dict1.get("carbohydrates").toString());
            protein_1.setText(item_dict1.get("protein").toString());
            fats_1.setText(item_dict1.get("fats").toString());

            itemName_2.setText(item_dict2.get("name").toString());
            calories_2.setText(item_dict2.get("calories").toString());
            mass_2.setText(item_dict2.get("mass").toString());
            carbs_2.setText(item_dict2.get("carbohydrates").toString());
            protein_2.setText(item_dict2.get("protein").toString());
            fats_2.setText(item_dict2.get("fats").toString());

            //debug_barcodeNumber.setText(barcodeNum1);
        }

        Button scan_button = findViewById(R.id.scan_button_single);

    }
}
package com.example.grocerease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SingleItemAnalyze extends AppCompatActivity {
    private DatabaseReference databaseReference;
    TextView itemName, calories, mass, carbs, protein, fats;

    // -----==DEBUG START==------
    // TODO: Implement database here
    Map<String, HashMap> database;
    HashMap<String, String> item;
    TextView debug_barcodeNumber;

    Map<String,String> food1;
    Object food2;

    // -----==DEBUG END==-----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_analyze);

        Intent intent = getIntent();
        String barcodeNum = intent.getStringExtra(MainActivity.FIRSTBARCODEKEY);
        Log.i("SingleItemAnalyse", "Barcode received: "+ barcodeNum);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(barcodeNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    food1 = (Map<String, String>) task.getResult().getValue();
                    Toast.makeText(SingleItemAnalyze.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_LONG).show();
                }
            }
        });

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
        database.put("7654321", item1);
        // ------- DEBUG -------


        HashMap item_dict = database.get(barcodeNum);

        if (item_dict == null) {
            Toast.makeText(SingleItemAnalyze.this, barcodeNum + " is not in database", Toast.LENGTH_LONG).show();
        }
        else {
            Log.i("SingleItemAnalyse", "item_dict" + item_dict.toString());

            itemName.setText(food1.get("foodName"));
            calories.setText(item_dict.get("calories").toString());
            mass.setText(item_dict.get("mass").toString());
            carbs.setText("Carbs\n" + item_dict.get("carbohydrates").toString());
            protein.setText("Protein\n" + item_dict.get("protein").toString());
            fats.setText("Fats\n" + item_dict.get("fats").toString());

            debug_barcodeNumber.setText(barcodeNum);
        }

        Button scan_button = findViewById(R.id.scan_button_single);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleItemAnalyze.this, ScanActivity.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum);
                startActivity(intent);
            }
        });
    }
}

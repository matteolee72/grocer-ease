package com.example.grocerease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseAddActivity extends AppCompatActivity {
    // Instantiate all the variables
    EditText foodID, foodName, foodCompany, foodMass, foodProtein, foodTotalFat, foodSaturatedFat, foodTransFat,
            foodCholesterol, foodCarbohydrate, foodTotalSugar, foodDietaryFibre, foodSodium, foodIron, foodCalories;
    Button addToDatabaseButton;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_add);

        foodID = findViewById(R.id.foodID);
        foodName = findViewById(R.id.foodName);
        foodCompany = findViewById(R.id.foodCompany);
        foodMass = findViewById(R.id.foodMass);
        foodCalories = findViewById(R.id.foodCalories);
        foodTotalFat = findViewById(R.id.foodTotalFat);
        foodSaturatedFat = findViewById(R.id.foodSaturatedFat);
        foodTransFat = findViewById(R.id.foodTransFat);
        foodCholesterol = findViewById(R.id.foodCholesterol);
        foodSodium = findViewById(R.id.foodSodium);
        foodCarbohydrate = findViewById(R.id.foodTotalCarbohydrates);
        foodDietaryFibre = findViewById(R.id.foodDietaryFibre);
        foodTotalSugar = findViewById(R.id.foodTotalSugars);
        foodProtein = findViewById(R.id.foodProtein);
        foodIron = findViewById(R.id.foodIron);

        addToDatabaseButton = findViewById(R.id.addToDatabaseButton);

        Intent previousData = getIntent();
        String foodIDIn = previousData.getStringExtra(MainActivity.FIRSTBARCODEKEY);
        if (!foodIDIn.isEmpty()) {
            foodID.setText(foodIDIn);
        }
        // Initialize a database object instance
        // Initialize a reference to the database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        addToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodIDStr = foodID.getText().toString();
                String foodNameStr = foodName.getText().toString();
                String foodCompanyStr = foodCompany.getText().toString();
                String foodMassStr = foodMass.getText().toString();
                String foodProteinStr = foodProtein.getText().toString();
                String foodTotalFatStr = foodTotalFat.getText().toString();
                String foodSaturatedFatStr = foodSaturatedFat.getText().toString();
                String foodTransFatStr = foodTransFat.getText().toString();
                String foodCholesterolStr = foodCholesterol.getText().toString();
                String foodCarbohydrateStr = foodCarbohydrate.getText().toString();
                String foodTotalSugarStr = foodTotalSugar.getText().toString();
                String foodDietaryFibreStr = foodDietaryFibre.getText().toString();
                String foodSodiumStr = foodSodium.getText().toString();
                String foodIronStr = foodIron.getText().toString();
                String foodCaloriesStr = foodCalories.getText().toString();
                //----------------==DEBUG==------------------
                String foodImageURL = "grocery_placeholder.png";
                //--------------==END DEBUG==----------------
                
                FoodDatabaseObject foodItem = new FoodDatabaseObject(foodNameStr,
                                                                    foodMassStr,
                                                                    foodProteinStr,
                                                                    foodTotalFatStr,
                                                                    foodSaturatedFatStr,
                                                                    foodTransFatStr,
                                                                    foodCholesterolStr,
                                                                    foodCarbohydrateStr,
                                                                    foodTotalSugarStr,
                                                                    foodDietaryFibreStr,
                                                                    foodSodiumStr,
                                                                    foodIronStr,
                                                                    foodCaloriesStr,
                                                                    foodImageURL,
                                                                    foodCompanyStr);

                Log.d("databaseAddActivity", foodIDStr);
                Log.d("databaseAddActivity", foodItem.toString());
                if (foodIDStr.isEmpty()) {
                    Toast.makeText(DatabaseAddActivity.this, "Key in a food ID value", Toast.LENGTH_LONG).show();
                }
                else {
                    databaseReference.child("Food").child(foodIDStr).setValue(foodItem); //key is foodIDStr, value is foodItem
                }
            }
        });
    }
}

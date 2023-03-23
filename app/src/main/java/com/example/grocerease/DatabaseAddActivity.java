package com.example.grocerease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseAddActivity extends AppCompatActivity {
    // Instantiate all the variables
    EditText foodID, foodName, foodProtein, foodTotalFat, foodSaturatedFat, foodTransFat,
            foodCholesterol, foodCarbohydrate, foodTotalSugar, foodDietaryFibre, foodSodium, foodIron;
    Button addToDatabaseButton;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_add);

        foodID = findViewById(R.id.foodID);
        foodName = findViewById(R.id.foodName);
        foodProtein = findViewById(R.id.foodProtein);
        foodTotalFat = findViewById(R.id.foodTotalFat);
        foodSaturatedFat = findViewById(R.id.foodSaturatedFat);
        foodTransFat = findViewById(R.id.foodTransFat);
        foodCholesterol = findViewById(R.id.foodCholesterol);
        foodCarbohydrate = findViewById(R.id.foodCarbohydrate);
        foodTotalSugar = findViewById(R.id.foodTotalSugar);
        foodDietaryFibre = findViewById(R.id.foodDietaryFibre);
        foodSodium = findViewById(R.id.foodSodium);
        foodIron = findViewById(R.id.foodIron);

        addToDatabaseButton = findViewById(R.id.addToDatabaseButton);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        addToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodIDStr = foodID.getText().toString();
                String foodNameStr = foodName.getText().toString();
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

                DatabaseItemObject foodItem = new DatabaseItemObject(foodNameStr, foodProteinStr, foodTotalFatStr, foodSaturatedFatStr, foodTransFatStr, foodCholesterolStr, foodCarbohydrateStr, foodTotalSugarStr, foodDietaryFibreStr, foodSodiumStr, foodIronStr);
                Log.d("databaseAddActivity", foodIDStr);
                Log.d("databaseAddActivity", foodItem.toString());
                if (foodIDStr.isEmpty()) {
                    Toast.makeText(DatabaseAddActivity.this, "Key in a food ID value", Toast.LENGTH_LONG).show();
                }
                else {
                    databaseReference.child(foodIDStr).setValue(foodItem);
                    databaseReference.child(foodIDStr).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        }
                    });
                }
            }
        });
    }
}

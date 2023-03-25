package com.example.grocerease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class TwoItemCompare extends AppCompatActivity {
    TextView itemName_1,mass_1,calories_1, carbs_1, protein_1, fats_1;
    TextView itemName_2,mass_2,calories_2, carbs_2, protein_2, fats_2;

    private DatabaseReference databaseReference;
    DatabaseItemObject foodObject1;
    DatabaseItemObject foodObject2;
    String barcodeNum2;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TwoItemCompare.this, MainActivity.class);
        startActivity(intent);
    }

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

        Intent intent = getIntent();
        foodObject1 = (DatabaseItemObject) intent.getSerializableExtra(MainActivity.FIRSTBARCODEKEY);
        barcodeNum2 = intent.getStringExtra(MainActivity.SECONDBARCODEKEY);
        Log.i("TwoItemCompare", "Barcode received: "+ foodObject1.toString());
        Log.i("TwoItemCompare", "Barcode received: "+ barcodeNum2);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(barcodeNum2).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else if (task.getResult().getValue(Object.class) == null) {
                    Log.e("firebase", "Item does not exist in database");
                    Toast.makeText(TwoItemCompare.this, barcodeNum2.toString() + " is not in database", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(TwoItemCompare.this);
                    builder.setMessage("Would you like to add it to the database?").setTitle("Item does not exist in database");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent addActivityIntent = new Intent(TwoItemCompare.this, DatabaseAddActivity.class);
                            addActivityIntent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum2);
                            startActivity(addActivityIntent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }

                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    foodObject2 = task.getResult().getValue(DatabaseItemObject.class);
                    Log.i("TwoItemCompare", "Object1 from intent" + foodObject1.toString());
                    Log.i("TwoItemCompare", "Object2 from database" + foodObject2.toString());
                    itemName_1.setText(foodObject1.getFoodName());
                    calories_1.setText(foodObject1.getFoodCalories());
                    mass_1.setText(":)");
                    carbs_1.setText(foodObject1.getFoodCarbohydrate());
                    protein_1.setText(foodObject1.getFoodProtein());
                    fats_1.setText(foodObject1.getFoodTotalFat());

                    itemName_2.setText(foodObject2.getFoodName());
                    calories_2.setText(foodObject2.getFoodCalories());
                    mass_2.setText(":)");
                    carbs_2.setText(foodObject2.getFoodCarbohydrate());
                    protein_2.setText(foodObject2.getFoodProtein());
                    fats_2.setText(foodObject2.getFoodTotalFat());
                }
            }
        });
    }
}
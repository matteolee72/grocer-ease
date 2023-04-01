package com.example.grocerease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.time.Instant;

public class SingleItemActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private StorageReference foodImageStorageReference;
    private FirebaseStorage storage;
    TextView itemName, itemCompany, mass, calories, percentage, totalFat, saturatedFat, transFat, cholesterol,
            sodium, totalCarbohydrates, dietaryFibres, totalSugars, protein, iron;
    DatabaseItemObject foodObject;
    Button scan_button;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SingleItemActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // use calculate to find normal sugar content and saturated fat content per 100g, and color code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item_activity);

        // Get a handle on all the items on the page
        itemName = findViewById(R.id.itemName);
        itemCompany = findViewById(R.id.itemCompany);
        mass = findViewById(R.id.grams_of_contents);
        calories = findViewById(R.id.number_of_calories);
        percentage = findViewById(R.id.perc_of_daily_intake_value);
        totalFat = findViewById(R.id.total_fat_value);
        saturatedFat = findViewById(R.id.saturated_fat_value);
        transFat = findViewById(R.id.trans_fat_value);
        cholesterol = findViewById(R.id.cholesterol_value);
        sodium = findViewById(R.id.sodium_value);
        totalCarbohydrates = findViewById(R.id.total_carbohydrates_value);
        dietaryFibres = findViewById(R.id.dietary_fibres_value);
        totalSugars = findViewById(R.id.total_sugars_value);
        protein = findViewById(R.id.protein_value);
        iron = findViewById(R.id.iron_value);

        // Get the barcode number from the previous intent
        // so that we can pass the information to the database reference
        Intent intent = getIntent();
        String barcodeNum = intent.getStringExtra(MainActivity.FIRSTBARCODEKEY);
        Log.i("SingleItemAnalyse", "Intent barcode received: "+ barcodeNum);

        ImageView imageView = findViewById(R.id.card1_foodImage_ImageView);


        // DatabaseReference provides a handle to the firebase database such that we can access the
        // information contained at the key <barcodeNum>
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // StorageReference provides a handle to the firebase storage service
        storage = FirebaseStorage.getInstance();

        databaseReference.child(barcodeNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else if (task.getResult().getValue(Object.class) == null) {
                    Log.e("firebase", "Item does not exist in database");
                    Toast.makeText(SingleItemActivity.this, barcodeNum + " is not in database", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SingleItemActivity.this);
                    builder.setMessage("Would you like to add it to the database?").setTitle("Item does not exist in the database");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent addActivityIntent = new Intent(SingleItemActivity.this, DatabaseAddActivity.class);
                            addActivityIntent.putExtra(MainActivity.FIRSTBARCODEKEY,barcodeNum);
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

                    foodObject = task.getResult().getValue(DatabaseItemObject.class);
                    // Get the result from the database and populate a foodObject of type DatabaseItemObject
                    itemName.setText(foodObject.getFoodName());
                    itemCompany.setText(foodObject.getFoodCompany());
                    mass.setText(foodObject.getFoodMass());
                    calories.setText(foodObject.getFoodCalories());
                    percentage.setText(foodObject.getFoodPercentage());
                    totalFat.setText(foodObject.getFoodTotalFat());
                    saturatedFat.setText(foodObject.getFoodSaturatedFat());
                    transFat.setText(foodObject.getFoodTransFat());
                    cholesterol.setText(foodObject.getFoodCholesterol());
                    sodium.setText(foodObject.getFoodSodium());
                    totalCarbohydrates.setText(foodObject.getFoodCarbohydrate());
                    dietaryFibres.setText(foodObject.getFoodDietaryFibre());
                    totalSugars.setText(foodObject.getFoodTotalSugar());
                    protein.setText(foodObject.getFoodProtein());
                    iron.setText(foodObject.getFoodIron());
                    String foodImageLink = foodObject.getFoodImageURL();
                    foodImageStorageReference = storage.getReference().child(foodImageLink);
                    GlideApp.with(getApplicationContext())
                            .load(foodImageStorageReference)
                            .into(imageView); //implement placeholder
                }
            }
        });
        scan_button = findViewById(R.id.scan_button_single);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleItemActivity.this, ScanActivity.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, foodObject);
                startActivity(intent);
            }
        });

    }
}
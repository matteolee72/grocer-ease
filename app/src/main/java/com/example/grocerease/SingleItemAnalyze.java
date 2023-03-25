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

public class SingleItemAnalyze extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private StorageReference foodImageStorageReference;
    private FirebaseStorage storage;
    TextView itemName, calories, mass, carbs, protein, fats;
    DatabaseItemObject foodObject;
    Button scan_button;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SingleItemAnalyze.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_analyze);

        // Get a handle on all the items on the page
        itemName = findViewById(R.id.itemName);
        calories = findViewById(R.id.calories);
        mass = findViewById(R.id.mass);
        carbs = findViewById(R.id.carbs);
        protein = findViewById(R.id.protein);
        fats = findViewById(R.id.fats);

        // Get the barcode number from the previous intent
        // so that we can pass the information to the database reference
        Intent intent = getIntent();
        String barcodeNum = intent.getStringExtra(MainActivity.FIRSTBARCODEKEY);
        Log.i("SingleItemAnalyse", "Intent barcode received: "+ barcodeNum);

        // StorageReference provides a handle to the firebase storage service
        storage = FirebaseStorage.getInstance();
        foodImageStorageReference = storage.getReference().child("12128669_XL1_20220418.jpg");
        ImageView imageView = findViewById(R.id.card1_foodImage_ImageView);


        // DatabaseReference provides a handle to the firebase database such that we can access the
        // information contained at the key <barcodeNum>
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(barcodeNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else if (task.getResult().getValue(Object.class) == null) {
                    Log.e("firebase", "Item does not exist in database");
                    Toast.makeText(SingleItemAnalyze.this, barcodeNum + " is not in database", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SingleItemAnalyze.this);
                    builder.setMessage("Would you like to add it to the database?").setTitle("Item does not exist in the database");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent addActivityIntent = new Intent(SingleItemAnalyze.this, DatabaseAddActivity.class);
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
                    calories.setText(foodObject.getFoodCalories());
                    carbs.setText("Total Sugar\n" + foodObject.getFoodTotalSugar());
                    protein.setText("Protein\n" + foodObject.getFoodProtein());
                    fats.setText("Fats\n" + foodObject.getFoodTotalFat());
                    GlideApp.with(getApplicationContext())
                            .load(foodImageStorageReference)
                            .into(imageView);
                }
            }
        });
        scan_button = findViewById(R.id.scan_button_single);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleItemAnalyze.this, ScanActivity.class);
                intent.putExtra(MainActivity.FIRSTBARCODEKEY, foodObject);
                startActivity(intent);
            }
        });

    }
}

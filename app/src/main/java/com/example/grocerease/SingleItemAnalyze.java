package com.example.grocerease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SingleItemAnalyze extends AppCompatActivity {

    //realtime database
    private DatabaseReference databaseReference;
    //for images
    private StorageReference foodImageStorageReference;
    private FirebaseStorage storage;
    TextView itemName, calories, mass, carbs, protein, fats;
    FoodDatabaseObject foodObject;
    UserDatabaseObject user;
    Button scan_button;
    private String username;

    private UserDatabaseObject userObject;

    private PreferencesHelper preferencesHelper;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SingleItemAnalyze.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_analyze);

        // reference to the local preferences
        preferencesHelper = new PreferencesHelper(this);
        if (preferencesHelper != null) {
            username = preferencesHelper.readString("username","error");
        }

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
        userObject = (UserDatabaseObject) intent.getSerializableExtra(MainActivity.USEROBJECTKEY);
        //Log.d("userName is", "onCreate: " + userObject.getUserName());

        Log.i("SingleItemAnalyse", "Intent barcode received: "+ barcodeNum);

        ImageView imageView = findViewById(R.id.card1_foodImage_ImageView);

        // DatabaseReference provides a handle to the firebase database such that we can access the
        // information contained at the key <barcodeNum>
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // StorageReference provides a handle to the firebase storage service
        storage = FirebaseStorage.getInstance();
        
        databaseReference.child("Food").child(barcodeNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

                    foodObject = task.getResult().getValue(FoodDatabaseObject.class); // get food object from database
                    // Get the result from the database and populate a foodObject of type FoodDatabaseObject

                    //add barcode to history if not full
                    UserHistoryObject userHistory = userObject.getUserHistory();
                    if (userHistory.isFull() == false){
                        userHistory.addToHistory(barcodeNum);
                        databaseReference.child("Users").child(username).child("userHistory").setValue(userHistory);
                    }
                    itemName.setText(foodObject.getFoodName());
                    calories.setText(foodObject.getFoodCalories());
                    carbs.setText("Total Sugar\n" + foodObject.getFoodTotalSugar());
                    protein.setText("Protein\n" + foodObject.getFoodProtein());
                    fats.setText("Fats\n" + foodObject.getFoodTotalFat());
                    String foodImageLink = foodObject.getFoodImageURL();
                    foodImageStorageReference = storage.getReference().child(foodImageLink);
                    Log.d("food image", "onComplete: " + foodImageStorageReference);
                    GlideApp.with(getApplicationContext())
                            .load(foodImageStorageReference)
                            .into(imageView); //TODO: implement placeholder
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
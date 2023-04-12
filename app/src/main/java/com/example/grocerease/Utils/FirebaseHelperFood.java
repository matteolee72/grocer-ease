package com.example.grocerease.Utils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelperFood {
    private DatabaseReference databaseReference;

    public FirebaseHelperFood() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void getFoodDatabaseObject(String foodId, OnCompleteListener<DataSnapshot> listener) {
        databaseReference.child("Food").child(foodId).get().addOnCompleteListener(listener);
    }
}
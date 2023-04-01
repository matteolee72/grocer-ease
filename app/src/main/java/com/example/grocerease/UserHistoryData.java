package com.example.grocerease;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryData {
    DatabaseReference databaseReference;
    DatabaseReference historyDatabaseReference;
    DatabaseReference foodDatabaseReference;
    FoodDatabaseObject foodObject;
    int size;
    List<String> stringList;
    TextView t;
    ImageView i;

    UserHistoryData(String username){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        historyDatabaseReference = databaseReference.child("Users").child(username).child("userHistory");
        databaseReference.child("Food").get();

        //this.t = t;
        //this.i = i;

        historyDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countListItems(snapshot);
                repopulateList(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void countListItems(DataSnapshot snapshot){
        size = (int) snapshot.getChildrenCount();
        //t.setText( String.valueOf(size));
    }

    private void repopulateList(DataSnapshot snapshot){
        stringList = new ArrayList<>();
        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
            String s = dataSnapshot.getValue(String.class);
            stringList.add(s);
        }
    }


    public String getName (int i){
        String barcodeNum = stringList.get(i);
        foodDatabaseReference.child(barcodeNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else{
                    foodObject = task.getResult().getValue(FoodDatabaseObject.class);
                }
            }
        });
        return foodObject.getFoodName();
    }

    public void removeWord(int i){
        String word = stringList.get(i);
        historyDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    historyDatabaseReference.child(dataSnapshot.getKey()).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public int getSize() {return size;}
}

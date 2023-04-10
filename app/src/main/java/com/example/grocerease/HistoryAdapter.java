package com.example.grocerease;
import android.content.Context;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    UserHistoryObject userHistoryObject;
    Context context;
    LayoutInflater mInflater;
    DatabaseReference databaseReference;

    public HistoryAdapter(Context context, UserHistoryObject userHistoryObject){
        this.context = context;
        this.userHistoryObject = userHistoryObject;
        mInflater = LayoutInflater.from(context);
        ArrayList<String> itemList = userHistoryObject.getFoodHistory();

    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        holder.setPosition(position);
        databaseReference.child("Food").child(userHistoryObject.getID(position)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else if (task.getResult().getValue(Object.class) == null) {
                    Log.e("firebase", "Item does not exist in database");
                }
                else {
                    FoodDatabaseObject foodDatabaseObject = task.getResult().getValue(FoodDatabaseObject.class);
                    holder.getTextView().setText(foodDatabaseObject.getFoodName());

                    String foodImageLink = foodDatabaseObject.getFoodImageURL();
                    StorageReference foodImageStorageReference = storage.getReference().child(foodImageLink);
                    Log.d("food image", "onComplete: " + foodImageStorageReference);
                    GlideApp.with(context)
                            .load(foodImageStorageReference)
                            .into(holder.getImageView());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userHistoryObject.getSize();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        private int position;
        public void setPosition(int position){
            this.position = position;
        }
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.listText);
            imageView = itemView.findViewById(R.id.listImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("activity parent", "onClick: " + ((Activity) context).getClass().getSimpleName());
                    if(((Activity) context).getClass().getSimpleName().equals("HistoryActivity")){
                        FoodDatabaseObject foodObject1 = (FoodDatabaseObject) ((Activity) context).getIntent().getSerializableExtra(MainActivity.FIRSTBARCODEKEY);
                        Intent intent = new Intent(context, TwoItemCompare.class);
                        intent.putExtra(MainActivity.SECONDBARCODEKEY, userHistoryObject.getID(position));
                        intent.putExtra(MainActivity.FIRSTBARCODEKEY, foodObject1);
                        context.startActivity(intent);
                    }
                    else{
                        UserDatabaseObject userObject = (UserDatabaseObject) ((Activity) context).getIntent().getSerializableExtra(MainActivity.USEROBJECTKEY);
                        Intent intent = new Intent(context, SingleItemAnalyze.class);
                        intent.putExtra(MainActivity.FIRSTBARCODEKEY, userHistoryObject.getID(position));
                        intent.putExtra(MainActivity.USEROBJECTKEY, userObject); // can this be removed? the top one too
                        context.startActivity(intent);
                    }
                }
            });
        }

        public TextView getTextView() {return textView;}
        public ImageView getImageView() {return imageView;}

    }
}

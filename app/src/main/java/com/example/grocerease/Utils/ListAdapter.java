package com.example.grocerease.Utils;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerease.Objects.FoodDatabaseObject;
import com.example.grocerease.GlideApp;
import com.example.grocerease.MainActivity;
import com.example.grocerease.Objects.UserFoodInterface;
import com.example.grocerease.R;
import com.example.grocerease.SingleItemAnalyzeActivity;
import com.example.grocerease.TwoItemCompareActivity;
import com.example.grocerease.Objects.UserHistoryObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/*** History Adapter
 * This Adapter is for the History Recycler View ***/

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    /** ATTRIBUTES **/
    UserFoodInterface userFoodInterface;
    Context context;
    LayoutInflater mInflater;
    FirebaseHelperFood firebaseHelperFood = new FirebaseHelperFood();

    /** CONSTRUCTOR **/
    public ListAdapter(Context context, UserFoodInterface userFoodInterface){
        this.context = context;
        this.userFoodInterface = userFoodInterface;
        mInflater = LayoutInflater.from(context);
        ArrayList<String> itemList = userFoodInterface.getFoodList();
    }

    /** CONCRETE METHODS FOR RECYCLERVIEW (onCreateViewHolder, onBindViewHolder, getItemCount)**/
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.setPosition(position);

        /** Use firebaseHelperFood to separate firebase GET logic from adapter */
        firebaseHelperFood.getFoodDatabaseObject(userFoodInterface.getID(position), new OnCompleteListener<DataSnapshot>() {
            FirebaseStorage storage = FirebaseStorage.getInstance();
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
                    holder.getCompanyTextView().setText(foodDatabaseObject.getFoodCompany());
                    holder.getCalorieTextView().setText(foodDatabaseObject.getFoodCalories());
                    holder.getMassTextView().setText(foodDatabaseObject.getFoodMass());

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
        return userFoodInterface.getSize();
    }

    /** ViewHolder class **/
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        private TextView companyTextView;
        private TextView calorieTextView;
        private TextView massTextView;
        private int position;
        public void setPosition(int position){
            this.position = position;
        }

        /** ViewHolder Constructor **/
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.listText);
            imageView = itemView.findViewById(R.id.listImage);
            companyTextView = itemView.findViewById(R.id.listCompany);
            calorieTextView = itemView.findViewById(R.id.listCalories);
            massTextView = itemView.findViewById(R.id.listMass);
            // Opening Single/Double item analyse when clicking on the RecyclerView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("activity parent", "onClick: " + ((Activity) context).getClass().getSimpleName());
                    String activityParent = ((Activity) context).getClass().getSimpleName();
                    if(activityParent.equals("ChooseHistoryActivity") || activityParent.equals("ChooseFavouritesActivity")){
                        FoodDatabaseObject foodObject1 = (FoodDatabaseObject) ((Activity) context).getIntent().getSerializableExtra(MainActivity.FIRSTBARCODEKEY);
                        Intent intent = new Intent(context, TwoItemCompareActivity.class);
                        intent.putExtra(MainActivity.SECONDBARCODEKEY, userFoodInterface.getID(position));
                        intent.putExtra(MainActivity.FIRSTBARCODEKEY, foodObject1);
                        context.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(context, SingleItemAnalyzeActivity.class);
                        intent.putExtra(MainActivity.FIRSTBARCODEKEY, userFoodInterface.getID(position));
                        context.startActivity(intent);
                    }
                }
            });
        }

        /** ViewHolder Getters **/
        public TextView getTextView() {return textView;}
        public ImageView getImageView() {return imageView;}
        public TextView getCompanyTextView() {return companyTextView;}
        public TextView getCalorieTextView() {return calorieTextView;}
        public TextView getMassTextView() {return massTextView;}
    }
}

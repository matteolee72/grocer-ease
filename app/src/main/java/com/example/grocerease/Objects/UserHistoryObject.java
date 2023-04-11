package com.example.grocerease.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/*** User History Object
 * This is a history object attached to each user in our Firebase.
 * It contains an arraylist of food IDs which users have added to their history.
 * We use it to interface with both our Adapters (for recyclerView) and our Database ***/

public class UserHistoryObject implements Serializable {

    /** ATTRIBUTES **/
    private ArrayList<String> foodHistory;
    private final int max_size = 10;

    /** NO ARG CONSTRUCTOR **/
    public UserHistoryObject() {
        this.foodHistory = new ArrayList<>();
    }

    /** Getters for interfacing with HistoryAdapter **/
    public String getID(int i){
        return this.getFoodHistory().get(i);
    }
    public int getSize(){
        return this.foodHistory.size();
    }
    public ArrayList<String> getFoodHistory() {return this.foodHistory;}

    /** Method to modify Arraylist **/
    // Note: a user is not able to remove from history. We do it for them when their history exceeds the max_size
    public ArrayList<String> addToHistory(String foodItem){
        if (!this.foodHistory.contains(foodItem)) {
            if (foodHistory.size()==this.max_size) { // if the history has reached the allocated capacity
                this.foodHistory.remove(0); // remove the 1st item
            }
            this.foodHistory.add(foodItem); // add the most recently scanned item
        }
        return this.foodHistory;
    }

}

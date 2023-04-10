package com.example.grocerease;

import java.io.Serializable;
import java.util.ArrayList;

public class UserHistoryObject implements Serializable {
    private ArrayList<String> foodHistory;
    private final int size = 10;

    public String getID(int i){
        return this.getFoodHistory().get(i);
    }
    public void removeID(int i){}
    public int getSize(){
        return this.foodHistory.size();
    }

    public ArrayList<String> getFoodHistory() {return this.foodHistory;}
    public boolean isFull(){
        if (this.foodHistory.size()==this.size){return true;}
        else {return false;}
    }
    public ArrayList<String> addToHistory(String foodItem){
        if (!this.foodHistory.contains(foodItem)) {
            if (foodHistory.size()==this.size) { // if the history has reached the allocated capacity
                this.foodHistory.remove(0); // remove the 1st item
            }
            this.foodHistory.add(foodItem); // add the most recently scanned item
        }
        return this.foodHistory;
    }
    public UserHistoryObject() {
        this.foodHistory = new ArrayList<>();
    }

}

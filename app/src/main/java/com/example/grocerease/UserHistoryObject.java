package com.example.grocerease;

import java.io.Serializable;
import java.util.ArrayList;

public class UserHistoryObject implements Serializable {
    private ArrayList<String> foodHistory;
    private int size = 5;

    public String getID(int i){
        return this.getFoodHistory().get(i);
    }
    public void removeID(int i){}

    public int getSize(){
        return foodHistory.size();
    }

    public ArrayList<String> getFoodHistory() {return foodHistory;}
    public boolean isFull(){
        if (foodHistory.size()==size){return true;}
        else {return false;}
    }
    public ArrayList<String> addToHistory(String foodItem){
        if (!foodHistory.contains(foodItem)) {
            if (isFull()) {
                foodHistory.remove(0);
            }
            foodHistory.add(foodItem);
        }
        return foodHistory;
    }
    public UserHistoryObject() {
        this.foodHistory = new ArrayList<>();
//        for (int i = 0; i<size;i++){
//            foodHistory.add("0");
//
//        }
    }

}

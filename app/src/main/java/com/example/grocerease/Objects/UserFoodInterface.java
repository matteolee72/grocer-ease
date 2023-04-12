package com.example.grocerease.Objects;

import java.util.ArrayList;

/** User Food Interface
 * This interface is implemented in the UserHistoryObject and UserFavouritesObject concrete classes.
 * We programme to a supertype so that we only need a single ListAdapter class for our recyclerViews
 * for both History and Favourites **/

public interface UserFoodInterface {
    abstract String getID(int i);
    abstract int getSize();
    abstract ArrayList<String> getFoodList();
}

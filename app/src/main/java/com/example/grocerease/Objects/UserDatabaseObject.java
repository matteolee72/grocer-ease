package com.example.grocerease.Objects;

import java.io.Serializable;

/*** User Database Object
 * This is a user object from our Firebase.
 * It contains all the user information from our Database
 * Aside from their username and password, we have 3 other custom objects: Favourites, History, and Preferences
 * We also have arg/no arg constructors to populate the Database ***/

public class UserDatabaseObject implements Serializable {

    /** ATTRIBUTES **/
    private String userName;
    private String userPassword;
    private UserFavouritesObject userFavourites;
    private UserHistoryObject userHistory;
    private UserPreferencesObject userPreferences;

    /** NO ARG CONSTRUCTOR **/
    public UserDatabaseObject(){
    }

    /** PARTIAL ARG CONSTRUCTOR **/
    // We use this when we create a new user
    public UserDatabaseObject(String userPassword) {
        this.userName = null;
        this.userPassword = userPassword;
        this.userFavourites = new UserFavouritesObject();
        this.userHistory = new UserHistoryObject();
        this.userPreferences = null;
    }

    /** COMPLETE ARG CONSTRUCTOR **/
    public UserDatabaseObject(String userName, String userPassword, UserFavouritesObject userFavourites, UserHistoryObject userHistory, UserPreferencesObject userPreferences) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userFavourites = userFavourites;
        this.userHistory = userHistory;
        this.userPreferences = userPreferences;
    }

    /** GETTERS **/
    public String getUserName() {return userName;}
    public String getUserPassword() {return userPassword;}
    public UserFavouritesObject getUserFavourites() {return userFavourites;}
    public UserHistoryObject getUserHistory() {return userHistory;}
    public UserPreferencesObject getUserPreferences() {return userPreferences;}

}

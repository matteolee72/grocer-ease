package com.example.grocerease.Objects;

import java.io.Serializable;
import java.util.Date;

/*** User Preferences Object
 * This is a preferences object attached to each user in our Firebase.
 * It contains their personal details and preferences.
 * We pass these preference objects into our item raters to assist users in decision-making ***/

public class UserPreferencesObject implements Serializable {

    /** ATTRIBUTES **/
    private String bloodPressure;
    private String bloodSugarLevels;
    private String highCholesterol;
    private String weightGoals;
    private String userObjectName;
    private String sex;
    private int userHeight;
    private int userWeight;
    private Date birthday;

    /** NO ARG CONSTRUCTOR **/
    public UserPreferencesObject(){
        this.bloodPressure = "Normal";
        this.bloodSugarLevels = "Normal";
        this.highCholesterol = "No";
        this.weightGoals = "Maintain";
        this.userWeight = 70;
        this.userHeight = 165;
        this.userObjectName = "-";
        this.sex = "-";
        this.birthday = null;
    }

    /** COMPLETE ARG CONSTRUCTOR **/
    public UserPreferencesObject(String bloodPressure, String bloodSugarLevels, String highCholesterol, String weightGoals, String name, String sex, int height, int weight, Date birthday){
        this.bloodPressure = bloodPressure;
        this.bloodSugarLevels = bloodSugarLevels;
        this.highCholesterol = highCholesterol;
        this.weightGoals = weightGoals;
        this.userWeight = weight;
        this.userHeight = height;
        this.userObjectName = name;
        this.sex = sex;
        this.birthday = birthday;
    }

    /** toString() method **/
    @Override
    public String toString() {
        return "UserPreferencesObject {" +
                " name ='" + userObjectName + '\'' +
                ", bloodPressure ='" + bloodPressure + '\'' +
                ", bloodSugarLevels ='" + bloodSugarLevels + '\'' +
                ", sex='" + sex + '\'' +
                ", highCholesterol ='" + highCholesterol + '\'' +
                ", weightGoals='" + weightGoals + '\'' +
                ", height='" + userHeight + '\'' +
                ", weight='" + userWeight + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    /** GETTERS **/
    public String getBloodPressure() {
        return bloodPressure;
    }
    public String getBloodSugarLevels() {
        return bloodSugarLevels;
    }
    public String getHighCholesterol() {
        return highCholesterol;
    }
    public String getWeightGoals() {
        return weightGoals;
    }
    public String getUserObjectName() {
        return userObjectName;
    }
    public String getSex() {
        return sex;
    }
    public int getUserHeight() {
        return userHeight;
    }
    public int getUserWeight() {
        return userWeight;
    }
    public Date getBirthday() {
        return birthday;
    }
}

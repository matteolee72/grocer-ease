package com.example.grocerease;

import java.io.Serializable;
import java.util.Date;

public class UserPreferencesObject implements Serializable {

    private String bloodPressure;
    private String bloodSugarLevels;
    private String highCholesterol;
    private String weightGoals;
    private String userObjectName;
    private String sex;
    private int userHeight;
    private int userWeight;
    private Date birthday;


    // getting Date object from DatePicker: new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());

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

    public UserPreferencesObject(){

    }
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




}

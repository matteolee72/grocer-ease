package com.example.grocerease;

import static com.example.grocerease.CreateAccountActivity.NEWPASSWORD;
import static com.example.grocerease.CreateAccountActivity.NEWUSERNAME;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class QuizActivity extends AppCompatActivity {
    private String bloodPressure;
    private String bloodSugarLevels;
    private String highCholesterol;
    private String weightGoals;
    private String name;
    private String sex;
    private int height;
    private int weight;
    private Date birthday;

    Button quizSubmitButton;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private PreferencesHelper preferencesHelper;

    Calendar cal = Calendar.getInstance();

    private Boolean isDateChanged = false;

    //TODO: Create default constructor for preferences and handle them appropriately in singleItemAnalyze and compare
    // e.g user creates account but does NOT fill in preferences, need to know how to display their preferences still.

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // reference to the local preferences
        preferencesHelper = new PreferencesHelper(this);

        //getting the username of the user that just created an account
        Intent intent = getIntent();
        String username = intent.getStringExtra(NEWUSERNAME);
        preferencesHelper.writeString("username",username);

        UserDatabaseObject userObject = (UserDatabaseObject) intent.getSerializableExtra(MainActivity.USEROBJECTKEY);
        Log.d("new user password is", userObject.getUserPassword());
        //get blood pressure from RadioGroup
        RadioGroup bloodPressureGroup = findViewById(R.id.bloodPressureGroup);

        //get blood sugar from RadioGroup
        RadioGroup bloodSugarLevelsGroup = findViewById(R.id.bloodSugarLevelsGroup);

        //get cholesterol from RadioGroup
        RadioGroup HighCholesterolGroup = findViewById(R.id.HighCholesterolGroup);
        //get WeightGoals from RadioGroup
        RadioGroup WeightGoalsGroup = findViewById(R.id.weightGoalsGroup);

        EditText userObjectName = findViewById(R.id.UserObjectName);

        RadioGroup userSex = findViewById(R.id.UserSex);

        EditText userHeight = findViewById(R.id.editTextHeight);
        EditText userWeight = findViewById(R.id.editTextWeight);
        CustomDatePicker userBirthday = findViewById(R.id.birthdayPicker);
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.add(Calendar.YEAR, -1);
        userBirthday.setMaxDate(calendarToday.getTimeInMillis());

        quizSubmitButton = findViewById(R.id.quizSubmit);

        //Initialize a database object instance
        //Initialize a reference to the database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //checking for valid SDK version - requirement of the listener
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            userBirthday.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    isDateChanged = true;
                }
            });
        }
        quizSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int bloodPressureID = bloodPressureGroup.getCheckedRadioButtonId();
                RadioButton radioBloodPressureButton = findViewById(bloodPressureID);
                int bloodSugarID = bloodSugarLevelsGroup.getCheckedRadioButtonId();
                RadioButton radioBloodSugarButton = findViewById(bloodSugarID);
                int highCholesterolID = HighCholesterolGroup.getCheckedRadioButtonId();
                RadioButton radioHighCholesterolButton = findViewById(highCholesterolID);
                int WeightGoalsID = WeightGoalsGroup.getCheckedRadioButtonId();
                RadioButton radioWeightGoalsButton = findViewById(WeightGoalsID);
                int userSexID = userSex.getCheckedRadioButtonId();
                RadioButton radioButtonUserSex = findViewById(userSexID);

                if(radioBloodPressureButton == null
                        || radioBloodSugarButton == null
                        || radioHighCholesterolButton == null
                        || radioWeightGoalsButton == null
                        || radioButtonUserSex == null
                        || userHeight.getText().toString().isEmpty()
                        || userWeight.getText().toString().isEmpty()
                        || userObjectName.getText().toString().isEmpty()
                        || isDateChanged == false){
                    Toast.makeText(QuizActivity.this, "Please ensure that all fields have been filled", Toast.LENGTH_LONG).show();
                }
                if (userHeight.getText().toString().equals("0") || userWeight.getText().toString().equals("0")) {
                    Toast.makeText(QuizActivity.this, "Please ensure that you enter a valid height and weight", Toast.LENGTH_LONG).show();
                } else{
                    //setting all attributes of the UserPreference Object
                    bloodPressure = radioBloodPressureButton.getText().toString();
                    bloodSugarLevels = radioBloodSugarButton.getText().toString();
                    highCholesterol = radioHighCholesterolButton.getText().toString();
                    weightGoals = radioWeightGoalsButton.getText().toString();
                    sex = radioButtonUserSex.getText().toString();
                    height = Integer.parseInt(userHeight.getText().toString());
                    weight = Integer.parseInt(userWeight.getText().toString());
                    name = userObjectName.getText().toString();
                    cal.set(Calendar.YEAR, userBirthday.getYear());
                    cal.set(Calendar.MONTH, userBirthday.getMonth());
                    cal.set(Calendar.DAY_OF_MONTH, userBirthday.getDayOfMonth());
                    birthday = cal.getTime();
                    //instantiating new UserPreferencesObject
                    UserPreferencesObject UserPrefObject = new UserPreferencesObject(bloodPressure,bloodSugarLevels,highCholesterol,weightGoals,name,sex,height,weight,birthday);
                    String pw = userObject.getUserPassword();
                    Log.d("user pw from object is", pw);
                    //adding it to the DataBase
                    databaseReference.child("Users").child(username).child("userPreferences").setValue(UserPrefObject);

                    //going back to main activity
                    UserDatabaseObject userObjectLocal = new UserDatabaseObject(username,pw,userObject.getUserFavourites(),userObject.getUserHistory(),UserPrefObject);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(userObjectLocal); // returns a Json String object
                    preferencesHelper.writeString("userObject", jsonString);//making sure we set userObject to preferences
                    preferencesHelper.writeString("username", username);

                    Intent intent = new Intent(QuizActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

}
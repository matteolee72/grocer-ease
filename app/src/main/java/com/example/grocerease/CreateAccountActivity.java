package com.example.grocerease;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerease.Objects.UserDatabaseObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*** Create Account Activity
 * This Activity is created when the user comes from Login and wants to create a new account.
 * It creates a new User Object with a partial constructor.***/

public class CreateAccountActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText username,password,retype_password;
    private Button submit_button;
    // Setting Strings to pass Username to Quiz Activity
    public static final String NEWUSERNAME = "newUserName";

    /** onCreate() method **/
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Getting the Views of buttons and edittext fields
        username = findViewById(R.id.newusername_edittext);
        password = findViewById(R.id.newpassword_edittext);
        retype_password = findViewById(R.id.retype_password_edittext);
        submit_button = findViewById(R.id.submit_button);

        // Setting Button functionality: Submit Button
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting the inputs in the text edit fields
                String username_input = username.getText().toString();
                String password_input = password.getText().toString();
                String r_password_input = retype_password.getText().toString();

                // Check if any of the fields are empty
                if (username_input.trim().equals("") ||
                        password_input.trim().equals("") ||
                        r_password_input.trim().equals("") ) {
                    Toast.makeText(CreateAccountActivity.this,
                            "Please fill in all the fields", Toast.LENGTH_LONG).show();
                }

                // Check if the same text has been put into the password text fields
                else if (!r_password_input.equals(password_input)){
                    Toast.makeText(CreateAccountActivity.this,
                            "Your passwords are not the same", Toast.LENGTH_LONG).show();
                }

                // Check if username is already in taken
                else {
                    // Checking is username in database
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Users").child(username_input).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());}
                            else if (task.getResult().getValue(Object.class) == null) {
                                Log.e("firebase", "Valid Username and Password");

                                // Add user to database with partial arg constructor
                                UserDatabaseObject newUser =  new UserDatabaseObject(password_input);
                                databaseReference.child("Users").child(username_input).setValue(newUser);

                                // Move to QuizActivity, pass username_input
                                Log.d("new user password from object is ", newUser.getUserPassword());
                                Intent intent = new Intent(CreateAccountActivity.this,QuizActivity.class);
                                intent.putExtra(NEWUSERNAME,username_input);
                                intent.putExtra(MainActivity.USEROBJECTKEY, newUser);
                                startActivity(intent);
                                finish();
                            }
                            // Handling when username is already inside th database
                            else {
                                Log.e("firebase", "Username already exists in database");
                                Toast.makeText(CreateAccountActivity.this, "Username already exists in database", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

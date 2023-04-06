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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText username, password;
    private Button login_button, create_account_button;
    UserDatabaseObject userObject;

    private PreferencesHelper preferencesHelper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // reference to the local preferences
        preferencesHelper = new PreferencesHelper(this);

        // Getting the Views of buttons and edittext fields
        username = findViewById(R.id.username_edittext);
        password = findViewById(R.id.password_edittext);
        login_button = findViewById(R.id.login_button);
        create_account_button = findViewById(R.id.create_account_button);

        // Setting Button functionality: Login Button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting the inputs in the text edit fields
                String username_input = username.getText().toString();
                String password_input = password.getText().toString();
                Log.d("username and password", username_input + password_input);
                // Getting the database reference associated with username
                if (username_input.trim().equals("")){
                    Toast.makeText(LoginActivity.this,
                            "Please fill in all the fields", Toast.LENGTH_LONG).show();
                }
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Users").child(username_input).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else if (task.getResult().getValue(Object.class) == null) {
                                Log.e("firebase", "User does not exist in database");
                                Toast.makeText(LoginActivity.this, "Username not in Database", Toast.LENGTH_LONG).show();
                            } else {
                                userObject = task.getResult().getValue(UserDatabaseObject.class);
                                String database_password = userObject.getUserPassword();
                                if (database_password.equals(password_input)) {

                                    // testing gson to add Json string to Shared Preferences
                                    Gson gson = new Gson();
                                    String jsonString = gson.toJson(userObject); // returns a Json String object
                                    preferencesHelper.writeString("userObject", jsonString);
                                    // adding username to preferences to allow for access throughout the application
                                    preferencesHelper.writeString("username",username_input);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.e("firebase", "Password does not correspond to user");
                                    Toast.makeText(LoginActivity.this, "Username or Password is Incorrect", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        // Setting Button functionality: Create Account Button
        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}

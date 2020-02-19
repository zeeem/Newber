package com.cmput301w20t23.newber.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.controllers.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private UserController userController;
    private String email;
    private String password;
    private String username;
    private String phone;
    private String confirmPassword;
    private String firstName;
    private String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        // hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        userController = new UserController(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // sign up user, called if "Sign Up" button is clicked
    public void signUp(View view) {

        firstName = ((EditText) (findViewById(R.id.userFirstNameSignUp))).getText().toString();
        lastName = ((EditText) (findViewById(R.id.userLastNameSignUp))).getText().toString();
        username = ((EditText) (findViewById(R.id.usernameSignUp))).getText().toString();
        phone = ((EditText) (findViewById(R.id.phoneSignUp))).getText().toString();
        email = ((EditText) (findViewById(R.id.emailSignUp))).getText().toString();
        password = ((EditText) (findViewById(R.id.passwordSignUp))).getText().toString();
        confirmPassword = ((EditText) (findViewById(R.id.confirmPasswordSignUp))).getText().toString();

        // if user inputs are valid
        if (userController.isSignUpValid(firstName, lastName, username, phone, email, password, confirmPassword)) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // if sign up email is unique and password meets minimum requirements
                    if (task.isSuccessful()) {
                        Log.d("MYTAG", "createUserWithEmail:success");
                        userController.createUser(firstName, lastName, username, phone, email);
                        // transition to login screen after sign up
                        Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                        finish();
                    }
                    // if sign up email already exists or password does not meet minimum requirements
                    else {
                        Log.w("MYTAG", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // transition to LoginActivity, called if "Already registered? LOGIN" is clicked
    public void login(View view) {
        Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        finish();
    }
}

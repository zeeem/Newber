package com.cmput301w20t23.newber.controllers;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import com.cmput301w20t23.newber.models.DatabaseManager;
import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.Rider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class UserController {
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseManager databaseManager;
    private Rider rider;

    public UserController(Context context) {
        this.context = context;
        this.mAuth = FirebaseAuth.getInstance();
        this.databaseManager = new DatabaseManager(context);
    }

    public boolean isLoginValid(String email, String password) {
        if ((email.trim()).length() == 0 | password.trim().length() == 0) {
            Toast.makeText(context, "Please enter a username and password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // check if all fields contain values in sign up form
    public boolean isSignUpValid(String role, String firstName, String lastName, String username, String phone, String email, String password, String confirmPassword) {
        if (firstName.trim().length() == 0 | lastName.trim().length() == 0 |
                username.trim().length() == 0 | phone.trim().length() == 0 | email.trim().length() == 0 |
                password.trim().length() == 0 | confirmPassword.trim().length() == 0) {
            Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (role.trim().length() == 0) {
            Toast.makeText(context, "Please select an account type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!phone.matches("^[+]?[0-9]{10,13}$")) {
            Toast.makeText(context, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!confirmPassword.equals(password)) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void createUser(String role, String firstName, String lastName, String username, String phone, String email) {
        databaseManager.createUniqueUser(role, firstName, lastName, username, phone, email);
    }

    public Rider getRiderProfileInfo() {
        FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rider = new Rider(
                        dataSnapshot.child("firstName").getValue(String.class),
                        dataSnapshot.child("lastName").getValue(String.class),
                        dataSnapshot.child("username").getValue(String.class),
                        dataSnapshot.child("phone").getValue(String.class),
                        dataSnapshot.child("email").getValue(String.class),
                        mAuth.getCurrentUser().getUid()
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                rider = null;
            }
        });

        return rider;
    }

    public Driver getDriverProfileInfo() {
        return null;
    }
}

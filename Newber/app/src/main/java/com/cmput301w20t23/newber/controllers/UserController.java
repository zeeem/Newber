package com.cmput301w20t23.newber.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301w20t23.newber.models.DataListener;
import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.Rating;
import com.cmput301w20t23.newber.models.Rider;
import com.cmput301w20t23.newber.models.User;
import com.cmput301w20t23.newber.views.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class UserController {
    private Context context;
    private FirebaseAuth mAuth;
    private Rider rider;
    private Driver driver;

    public UserController(Context context) {
        this.context = context;
        this.mAuth = FirebaseAuth.getInstance();
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

    public void createUser(final String role, final String firstName, final String lastName, final String username, final String phone, final String email) {
        FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Toast.makeText(context, "Username has already been taken", Toast.LENGTH_LONG).show();
                }
                else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    User userObj = new User(firstName, lastName, username, phone, email, user.getUid());

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userObj);

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("role").setValue(role);

                    // if the user to be created is a driver, create upvotes and downvotes fields in database
                    if (role.equals("Driver")) {
                        FirebaseDatabase.getInstance().getReference("drivers")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(new Rating(0, 0));
                    }

                    Intent signedUpIntent = new Intent(context, MainActivity.class);
                    signedUpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(signedUpIntent);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getRiderProfileInfo(final DataListener dataListener) {
        dataListener.onStart();
        FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataListener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dataListener.onFailure();
            }
        });
    }

    public void getDriverProfileInfo(final DataListener dataListener) {
        FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataListener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dataListener.onFailure();
            }
        });
    }

    public void getDriverRatingInfo(final DataListener dataListener) {
        FirebaseDatabase.getInstance().getReference("drivers")
                .child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataListener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dataListener.onFailure();
            }
        });
    }
}

package com.cmput301w20t23.newber.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.Rating;
import com.cmput301w20t23.newber.models.Rider;
import com.cmput301w20t23.newber.models.User;
import com.cmput301w20t23.newber.views.RiderMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

import static android.content.ContentValues.TAG;

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

    public void logout() {
        mAuth.signOut();
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

                    Intent signedUpIntent = new Intent(context, RiderMainActivity.class);
                    signedUpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(signedUpIntent);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean isContactInfoValid(String email, String phone) {
        if (phone.trim().length() == 0 | email.trim().length() == 0) {
            Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show();
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

        return true;
    }

    public void saveContactInfo(final Context context, final String email, String phone, String password) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // current credential
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), password);

        user.reauthenticate(credential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User re-authenticated.");
                        // update email
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            ref.child("email").setValue(email);
                                            Log.d(TAG, "User email address updated.");
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Password incorrect. Email could not be updated.", Toast.LENGTH_SHORT).show();
                    }
                });
        ref.child("phone").setValue(phone);
    }
}

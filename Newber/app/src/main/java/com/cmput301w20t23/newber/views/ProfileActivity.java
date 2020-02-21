package com.cmput301w20t23.newber.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.controllers.UserController;
import com.cmput301w20t23.newber.models.DataListener;
import com.cmput301w20t23.newber.models.Rider;
import com.cmput301w20t23.newber.models.Driver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private UserController userController;
    private TextView fullName;
    private TextView username;
    private TextView phone;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle(getString(R.string.profile));

        mAuth = FirebaseAuth.getInstance();
        userController = new UserController(this);

        fullName = findViewById(R.id.fullName);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String role = dataSnapshot.child("role").getValue(String.class);
                System.out.println(role);

                switch (role) {
                    case "Rider":
                        userController.getRiderProfileInfo(new DataListener() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                fullName.setText(dataSnapshot.child("firstName").getValue(String.class) + " " + dataSnapshot.child("lastName").getValue(String.class));
                                username.setText(dataSnapshot.child("username").getValue(String.class));
                                phone.setText(dataSnapshot.child("phone").getValue(String.class));
                                email.setText(dataSnapshot.child("email").getValue(String.class));
                            }

                            @Override
                            public void onStart() {
                                Log.d("onStart", "loadProfileStarted");
                            }

                            @Override
                            public void onFailure() {
                                Log.d("onFailure", "loadProfileFailed");
                            }
                        });
                        break;
                    case "Driver":
                        userController.getDriverProfileInfo(new DataListener() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                fullName.setText(dataSnapshot.child("firstName").getValue(String.class) + " " + dataSnapshot.child("lastName").getValue(String.class));
                                username.setText(dataSnapshot.child("username").getValue(String.class));
                                phone.setText(dataSnapshot.child("phone").getValue(String.class));
                                email.setText(dataSnapshot.child("email").getValue(String.class));
                            }

                            @Override
                            public void onStart() {
                                Log.d("onStart", "loadProfileStarted");
                            }

                            @Override
                            public void onFailure() {
                                Log.d("onFailure", "loadProfileFailed");
                            }
                        });
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

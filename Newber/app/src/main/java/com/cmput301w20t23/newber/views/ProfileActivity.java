package com.cmput301w20t23.newber.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private TextView ratingLabel;
    private LinearLayout ratingLayout;
    private TextView upvotes;
    private TextView downvotes;


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
                        ratingLabel = findViewById(R.id.ratingLabel);
                        ratingLayout = findViewById(R.id.ratingLayout);
                        ratingLabel.setVisibility(View.VISIBLE);
                        ratingLayout.setVisibility(View.VISIBLE);
                        upvotes = findViewById(R.id.upvotes);
                        downvotes = findViewById(R.id.downvotes);

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

                        userController.getDriverRatingInfo(new DataListener() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                upvotes.setText(Long.valueOf((long) dataSnapshot.child("upvotes").getValue()).toString());
                                downvotes.setText(Long.valueOf((long) dataSnapshot.child("downvotes").getValue()).toString());
                            }

                            @Override
                            public void onStart() {
                                Log.d("onStart", "loadRatingStarted");
                            }

                            @Override
                            public void onFailure() {
                                Log.d("onFailure", "loadRatingFailed");
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

    public void edit(View view) {
        AlertDialog.Builder editDialogBuilder = new AlertDialog.Builder(this);
        editDialogBuilder.setTitle("Edit Contact Information");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_dialog, null);
        editDialogBuilder.setView(dialogView);

        final EditText emailInput = (EditText) dialogView.findViewById(R.id.emailInput);
        final EditText phoneInput = (EditText) dialogView.findViewById(R.id.phoneInput);
        final EditText passwordInput = (EditText) dialogView.findViewById(R.id.passwordInput);
        emailInput.setText(email.getText());
        phoneInput.setText(phone.getText());

        editDialogBuilder.setPositiveButton("SAVE", null);

        editDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog editDialog = editDialogBuilder.create();

        editDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = editDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newEmail = emailInput.getText().toString();
                        String newPhone = phoneInput.getText().toString();
                        String password = passwordInput.getText().toString();

                        if (userController.isContactInfoValid(newEmail, newPhone)) {
                            userController.saveContactInfo(ProfileActivity.this, newEmail, newPhone, password);
                            email.setText(newEmail);
                            phone.setText(newPhone);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        editDialog.show();
    }

    public void logout(View view) {
        // TODO: implement logout
    }
}

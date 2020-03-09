package com.cmput301w20t23.newber.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.controllers.UserController;
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

        fullName = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String role = dataSnapshot.child("role").getValue(String.class);
                System.out.println(role);

                switch (role) {
                    case "Rider":
                        fullName.setText(dataSnapshot.child("firstName").getValue(String.class) + " " + dataSnapshot.child("lastName").getValue(String.class));
                        username.setText(dataSnapshot.child("username").getValue(String.class));
                        phone.setText(dataSnapshot.child("phone").getValue(String.class));
                        email.setText(dataSnapshot.child("email").getValue(String.class));
                        break;
                    case "Driver":
                        ratingLabel = findViewById(R.id.ratingLabel);
                        ratingLayout = findViewById(R.id.rating_layout);
                        ratingLabel.setVisibility(View.VISIBLE);
                        ratingLayout.setVisibility(View.VISIBLE);
                        upvotes = findViewById(R.id.upvotes);
                        downvotes = findViewById(R.id.downvotes);

                        fullName.setText(dataSnapshot.child("firstName").getValue(String.class) + " " + dataSnapshot.child("lastName").getValue(String.class));
                        username.setText(dataSnapshot.child("username").getValue(String.class));
                        phone.setText(dataSnapshot.child("phone").getValue(String.class));
                        email.setText(dataSnapshot.child("email").getValue(String.class));

                        loadRatings();

                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // options menu contains buttons for editing contact info and logging out
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                // edit contact info
                edit();
                return true;

            case R.id.logout:
                // log out
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadRatings() {
        FirebaseDatabase.getInstance().getReference("drivers")
                .child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upvotes.setText(Long.valueOf((long) dataSnapshot.child("upvotes").getValue()).toString());
                downvotes.setText(Long.valueOf((long) dataSnapshot.child("downvotes").getValue()).toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void edit() {
        AlertDialog.Builder editDialogBuilder = new AlertDialog.Builder(this);
        editDialogBuilder.setTitle("Edit Contact Information");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_dialog, null);
        editDialogBuilder.setView(dialogView);

        final EditText emailInput = (EditText) dialogView.findViewById(R.id.email_input);
        final EditText phoneInput = (EditText) dialogView.findViewById(R.id.phone_input);
        final EditText passwordInput = (EditText) dialogView.findViewById(R.id.password_input);
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
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        editDialog.show();
    }

    public void logout() {
        userController.logout();
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(i);
    }
}

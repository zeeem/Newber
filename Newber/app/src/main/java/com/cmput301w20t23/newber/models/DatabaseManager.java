package com.cmput301w20t23.newber.models;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cmput301w20t23.newber.views.MainActivity;
import com.cmput301w20t23.newber.views.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseManager {

    private Context context;
    private FirebaseAuth mAuth;

    public DatabaseManager(Context context) {
        this.context = context;
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void createUniqueUser(final String role, final String firstName, final String lastName, final String username, final String phone, final String email) {
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
}

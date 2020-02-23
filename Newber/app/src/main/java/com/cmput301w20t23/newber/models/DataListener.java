package com.cmput301w20t23.newber.models;

import com.google.firebase.database.DataSnapshot;

public interface DataListener {
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}

package com.cmput301w20t23.newber.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cmput301w20t23.newber.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle(getString(R.string.profile));
    }
}

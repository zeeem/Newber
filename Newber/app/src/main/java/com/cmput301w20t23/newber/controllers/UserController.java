package com.cmput301w20t23.newber.controllers;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import com.cmput301w20t23.newber.models.DatabaseManager;

public class UserController {
    private Context context;
    private DatabaseManager databaseManager;

    public UserController(Context context) {
        this.context = context;
        this.databaseManager = new DatabaseManager(context);
    }

    public boolean isLoginValid(String email, String password) {
        if ((email.trim()).length() == 0 | password.trim().length() == 0) {
            Toast.makeText(context, "Please enter a username and password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public boolean isSignUpValid(String firstName, String lastName, String username, String phone, String email, String password, String confirmPassword) {
        if ((email.trim()).length() == 0 | username.trim().length() == 0 |
                phone.trim().length() == 0 | password.trim().length() == 0 |
                confirmPassword.trim().length() == 0 | firstName.trim().length() == 0 |
                lastName.trim().length() == 0){
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

        if (!confirmPassword.equals(password)) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void createUser(String username, String firstName, String lastName, String phone, String email) {
        databaseManager.createUniqueUser(username, firstName, lastName, phone, email);
    }


}

package com.cmput301w20t23.newber;

import android.content.Context;

import com.cmput301w20t23.newber.controllers.UserController;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserControllerTest {
    private UserController testController;
    private Context testContext;
    private FirebaseAuth testAuth;

    /**
     * creates a context, sets it to the current app context, and sets the test controller's
     * context to the testContext. Also pulls from the firebase
     * This might not be the proper implementation, will need to ask team later
     */
    @Before
    public void setup(){
        testContext = testContext.getApplicationContext();
        testController = new UserController(testContext);
        testAuth = testAuth.getInstance();
    }

    @Test
    public void testIsLoginValid(){
        String testEmail = " ";
        String testPass = " ";
        Boolean testResult = testController.isLoginValid(testEmail, testPass);
        assertEquals(false, testResult);

        testEmail = "a";
        testPass = "b";
        testResult = testController.isLoginValid(testEmail, testPass);
        assertEquals(true, testResult);
    }

    @Test
    public void testIsSignUpValid(){
        String role = " ";
        String firstName = "a";
        String lastName = "b";
        String username = "c";
        String phone = "1234567890";
        String email = "d@e.f";
        String password = "g";
        String confirmPassword = "g";
        Boolean testResult = testController.isSignUpValid(role,firstName, lastName, username, phone, email, password, confirmPassword);
        assertEquals(false, testResult);

        role = "driver";
        phone = "54321";
        testResult = testController.isSignUpValid(role,firstName, lastName, username, phone, email, password, confirmPassword);
        assertEquals(false, testResult);

        phone = "1234567890";
        email = "not an email";
        testResult = testController.isSignUpValid(role,firstName, lastName, username, phone, email, password, confirmPassword);
        assertEquals(false, testResult);

        email = "d@e.f";
        confirmPassword = "h";
        testResult = testController.isSignUpValid(role,firstName, lastName, username, phone, email, password, confirmPassword);
        assertEquals(false, testResult);

        confirmPassword = "g";
        testResult = testController.isSignUpValid(role,firstName, lastName, username, phone, email, password, confirmPassword);
        assertEquals(true, testResult);
    }

    //I am unsure if logout and create user can be tested; reliant entirely on firebase

    @Test
    public void testIsContactInfoValid(){
        String email = "not an email";
        String phone = "letters";
        Boolean testResult = testController.isContactInfoValid(email, phone);
        assertEquals(false, testResult);

        email = "a@b.c";
        phone = "1234567890";
        testResult = testController.isContactInfoValid(email, phone);
        assertEquals(true, testResult);
    }

    //Again, unsure if save contact info can be tested since it is in firebase

}

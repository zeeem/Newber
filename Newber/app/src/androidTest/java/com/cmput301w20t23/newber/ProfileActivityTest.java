package com.cmput301w20t23.newber;

import android.widget.EditText;
import android.widget.TextView;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.cmput301w20t23.newber.views.LoginActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ProfileActivityTest {
    private Solo solo;


    /**
     * These tests will fail if a rider does not exist in the DB that has the following:
     * email: testLogin@test.com
     * pass : correctPassword
     * role: Rider
     */
    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }


    @Test
    public void testEditProfile(){
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        //login
        solo.enterText((EditText) solo.getView(R.id.email_login), "testLogin@test.com");
        solo.enterText((EditText) solo.getView(R.id.password_login), "correctPassword");
        solo.clickOnView(solo.getView(R.id.login_button));

        //setup changes
        solo.clickOnView(solo.getView(R.id.profile));
        solo.clickOnView(solo.getView(R.id.edit));
        solo.clickOnView(solo.getView(R.id.email_input));
        solo.clearEditText((EditText) solo.getView(R.id.email_input));
        solo.clickOnView(solo.getView(R.id.phone_input));
        solo.clearEditText((EditText) solo.getView(R.id.phone_input));
        solo.enterText((EditText) solo.getView(R.id.email_input), "newEmail@test.com");
        solo.enterText((EditText) solo.getView(R.id.phone_input), "0987654321");
        solo.enterText((EditText) solo.getView(R.id.password_input), "correctPassword");
        solo.clickOnButton("SAVE");

        //check changes
        solo.waitForDialogToClose(R.layout.activity_profile);
        TextView text;
        text = (TextView) solo.getView(R.id.email);
        assertEquals("testLogin@test.com", text.getText());
        text = (TextView) solo.getView(R.id.phone);
        assertEquals("0987654321", text.getText());

        //undo changes
        solo.clickOnView(solo.getView(R.id.edit));
        solo.clickOnView(solo.getView(R.id.email_input));
        solo.clearEditText((EditText) solo.getView(R.id.email_input));
        solo.clickOnView(solo.getView(R.id.phone_input));
        solo.clearEditText((EditText) solo.getView(R.id.phone_input));
        solo.enterText((EditText) solo.getView(R.id.email_input), "testLogin@test.com");
        solo.enterText((EditText) solo.getView(R.id.phone_input), "1234567890");
        solo.enterText((EditText) solo.getView(R.id.password_input), "correctPassword");
        solo.clickOnButton("SAVE");

        //logout
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.logout));
    }



    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

package com.cmput301w20t23.newber;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.cmput301w20t23.newber.views.LoginActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RequestFragmentTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void testRideRequest(){
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        //login
        solo.enterText((EditText) solo.getView(R.id.email_login), "testLogin@test.com");
        solo.enterText((EditText) solo.getView(R.id.password_login), "correctPassword");
        solo.clickOnView(solo.getView(R.id.login_button));
        solo.clickOnView((solo.getView(R.id.create_request_button)));
        //need to sleep so map loads
        solo.sleep(3000);

        //search
        solo.clickOnView(solo.getView(R.id.from_map_button));
        solo.clickLongOnView(solo.getView(R.id.map));
        solo.clickOnView(solo.getView(R.id.to_map_button));
        solo.clickLongOnView(solo.getView(R.id.map));
        solo.clickOnView(solo.getView(R.id.confirm_ride_request_button));


        //check
        TextView text;
        text = (TextView) solo.getView(R.id.pickup_location);
        assertEquals("8067 104 Ave NW, Edmonton, AB T5J 4X1, Canada", text.getText());
        text = (TextView) solo.getView(R.id.dropoff_location);
        assertEquals("8067 104 Ave NW, Edmonton, AB T5J 4X1, Canada", text.getText());

        //close and logout
        solo.clickOnView(solo.getView(R.id.rider_pending_request_button));
        //sleep so firebase updates
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.profile));
        solo.clickOnView(solo.getView(R.id.logout));



    }

}

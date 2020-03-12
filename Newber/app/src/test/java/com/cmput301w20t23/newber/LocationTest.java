package com.cmput301w20t23.newber;

import com.cmput301w20t23.newber.models.Location;
import com.cmput301w20t23.newber.models.Rating;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {
    private Location testLocation;

    @Before
    public void setUp() {
        testLocation = new Location();
        testLocation.setName("City");
        testLocation.setLatitude(12.345);
        testLocation.setLongitude(98.765);
    }

    @Test
    public void testSetAndGetName() {
        assertEquals("City", testLocation.getName());
        testLocation.setName("Town");
        assertEquals("Town", testLocation.getName());
    }

    @Test
    public void testSetAndGetLatitude() {
        assertEquals(12.345, testLocation.getLatitude(), 0.001);
        testLocation.setLatitude(54.321);
        assertEquals(54.321, testLocation.getLatitude(), 0.001);
    }

    @Test
    public void testSetAndGetLongitude() {
        assertEquals(98.765, testLocation.getLongitude(), 0.001);
        testLocation.setLongitude(56.789);
        assertEquals(56.789, testLocation.getLongitude(), 0.001);
    }

    @Test
    public void testSetLocationFromLatLng() {
        LatLng testLatLng = new LatLng(11.111, 22.222);
        testLocation.setLocationFromLatLng(testLatLng, "Town");
        assertEquals(11.111, testLocation.getLatitude(), 0.001);
        assertEquals(22.222, testLocation.getLongitude(), 0.001);
        assertEquals("Town", testLocation.getName());
    }

    @Test
    public void testToString() {
        String expected = "City: 12.345, 98.765";
        assertEquals(expected, testLocation.toString());
    }
}

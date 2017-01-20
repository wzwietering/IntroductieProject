package com.edulectronics.tinycircuit;

import android.support.test.runner.AndroidJUnit4;

import com.edulectronics.tinycircuit.Helpers.CoordinateHelper;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Wilmer on 12-12-2016.
 */

//This class is an Android test because the coordinatehelper uses the Resources, which are only
//existent when running the test on a device

@RunWith(AndroidJUnit4.class)
public class CoordinateHelperTest {
    @Test
    public void coordinateTest() {
        CoordinateHelper coordinateHelper = new CoordinateHelper(5, 200, 200);
        ConnectionPointOrientation connectionPointOrientation = ConnectionPointOrientation.Left;
        int position = 7;

        int resultX = coordinateHelper.getXLocation(position, connectionPointOrientation);
        int resultY = coordinateHelper.getYLocation(position, connectionPointOrientation);
        assertEquals(413, resultX);
        assertEquals(300, resultY);
    }
}

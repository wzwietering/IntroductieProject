package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.CoordinateHelper;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Wilmer on 12-12-2016.
 */

public class CoordinateHelperTest {
    @Test
    public void getXTest(){
        CoordinateHelper coordinateHelper = new CoordinateHelper(5, 150, 150);
        ConnectionPointOrientation connectionPointOrientation = ConnectionPointOrientation.Bottom;
        int position = 7;

        int resultX = coordinateHelper.getXLocation(position, connectionPointOrientation);
        int resultY = coordinateHelper.getYLocation(position, connectionPointOrientation);
        assertEquals(375, resultX);
        assertEquals(300, resultY);
    }
}

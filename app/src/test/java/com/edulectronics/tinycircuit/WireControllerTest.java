package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.WireController;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Wilmer on 12-12-2016.
 */

public class WireControllerTest {
    @Test
    public void wireControllerTest() {
        WireController wireController = new WireController(null);
        wireController.cellSize = 150;
        wireController.halfCellSize = 75;

        assertEquals(ConnectionPointOrientation.Top, wireController.area(80, 40));

        //Test the edge of two areas
        assertEquals(ConnectionPointOrientation.Right, wireController.area(100, 100));
    }
}

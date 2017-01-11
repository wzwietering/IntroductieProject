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
        WireController wireController = new WireController(null, 150, 150);

        assertEquals(ConnectionPointOrientation.Top, wireController.getClickedArea(80, 40));

        //Test the edge of two areas
        assertEquals(ConnectionPointOrientation.Right, wireController.getClickedArea(100, 100));
    }
}

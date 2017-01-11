package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.ConnectionController;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Wilmer on 12-12-2016.
 */

public class ConnectionControllerTest {
    @Test
    public void wireControllerTest() {
        ConnectionController connectionController = new ConnectionController(null, 150, 150);

        assertEquals(ConnectionPointOrientation.Top, connectionController.getClickedArea(80, 40));

        //Test the edge of two areas
        assertEquals(ConnectionPointOrientation.Right, connectionController.getClickedArea(100, 100));
    }
}
package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.ConnectionController;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ConnectionControllerTest {
    @Test
    public void wireControllerTest() {
        ConnectionController connectionController = new ConnectionController(null, 200, 200);

        //Test the edge of two areas
        assertEquals(ConnectionPointOrientation.Right, connectionController.getClickedArea(100));
    }
}

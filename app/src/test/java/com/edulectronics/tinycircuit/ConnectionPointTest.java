package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by Wilmer on 12-12-2016.
 */

public class ConnectionPointTest {
    @Test
    public void cpTest(){
        Lightbulb lightbulb = new Lightbulb();
        ConnectionPointOrientation connectionPointOrientation = ConnectionPointOrientation.Right;
        ConnectionPoint connectionPoint = new ConnectionPoint(lightbulb, connectionPointOrientation);

        assertFalse(connectionPoint.hasOutputConnection());

        connectionPoint.connect(new ConnectionPoint(lightbulb, ConnectionPointOrientation.Left));

        assertEquals(1, connectionPoint.getConnections().size());
        assertEquals(lightbulb, connectionPoint.getParentComponent());
    }
}

package com.edulectronics.tinycircuit;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


/**
 * Created by Lennart on 12-12-2016.
 */

public class ConnectorTest {
    @Test
    public void connectTest(){
        Lightbulb lightbulb = new Lightbulb();
        Powersource powersource = new Powersource();

        //create ConnectionPoints to use in Connector.connect method
        ConnectionPoint input = new ConnectionPoint(lightbulb, ConnectionPointOrientation.Left);
        ConnectionPoint output = new ConnectionPoint(powersource, ConnectionPointOrientation.Right);

        //Connect the components
        Connector.connect(input, output);
        assertEquals(output, input.getConnections().get(0).pointB);

        //Disconnect the components
        Connector.disconnect(input, output);
        assertEquals(0, input.getConnections().size());
    }
}

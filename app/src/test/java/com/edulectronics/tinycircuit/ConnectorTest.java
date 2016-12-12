package com.edulectronics.tinycircuit;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPointOrientation;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;


/**
 * Created by Lennart on 12-12-2016.
 */

public class ConnectorTest {
    @Test
    public void connectTest(){
        Lightbulb lightbulb = new Lightbulb();
        Powersource powersource = new Powersource(5);

        //create ConnectionPoints to use in Connector.connect method
        ConnectionPoint input = new ConnectionPoint(lightbulb, ConnectionPointOrientation.Left);
        ConnectionPoint output = new ConnectionPoint(powersource, ConnectionPointOrientation.Right);
        Connector.connect(input, output);

        //recreate list to compare with the one made in ConnectionPoint
        List<ConnectionPoint> inputList= new ArrayList<ConnectionPoint>();
        inputList.add(output);
        assertEquals(input.getConnections(), inputList);

        //same for Connector.disconnect
        Connector.disconnect(input,output);
        inputList.remove(output);
        assertEquals(input.getConnections(), inputList);
    }
}

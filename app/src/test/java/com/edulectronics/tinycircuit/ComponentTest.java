package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ComponentTest {
    @Test
    public void connectedPowersource() throws Exception {
        Powersource powersource = new Powersource();
        ConnectionPoint output = powersource.getConnectionPointByIndex(0);
        ConnectionPoint input = powersource.getConnectionPointByIndex(1);

        Connector.connect(input,output);

        assertTrue(powersource.hasOutputConnection());
    }

    @Test
    public void disconnectedPowersource(){
        Powersource powersource = new Powersource();
        assertFalse(powersource.hasOutputConnection());
    }

    @Test
    public void disconnectedCircuit(){
        Powersource powersource = new Powersource();
        ConnectionPoint powerOutput = powersource.getConnectionPointByIndex(0);

        Lightbulb bulb = new Lightbulb();
        ConnectionPoint bulbInput = bulb.getConnectionPointByIndex(0);

        Connector.connect(bulbInput, powerOutput);

        assertFalse(powersource.hasOutputConnection());
    }

    @Test
    public void imageResource(){
        Powersource powersource = new Powersource();
        assertEquals(R.drawable.battery, powersource.getImage());
    }
}
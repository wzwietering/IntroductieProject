package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maaike on 28-11-2016.
 */

public class LightbulbTest {
    CircuitController circuitController;

    @Before
    public void setup() {
        circuitController = new CircuitController(5, 5);
    }

    @Test
    public void connectedLightBulbIsOn(){
        Powersource powersource = new Powersource();
        ConnectionPoint powerOutput = powersource.getOutput();
        ConnectionPoint powerInput = powersource.getInput();

        Lightbulb bulb = new Lightbulb();
        ConnectionPoint bulbOutput = bulb.getConnectionPointByIndex(0);
        ConnectionPoint bulbInput = bulb.getConnectionPointByIndex(1);

        Connector.connect(bulbInput, powerOutput);
        Connector.connect(powerInput, bulbOutput);

        circuitController.addComponent(powersource, 1);
        circuitController.addComponent(bulb, 2);

        circuitController.run();

        assertTrue(bulb.isOn);
    }

    @Test
    public void disconnectedLightBulbIsOff(){
        Lightbulb bulb = new Lightbulb();
        circuitController.addComponent(bulb, 2);

        circuitController.run();

        assertFalse(bulb.isOn);
    }

    @Test
    public void imageResource(){
        Lightbulb lightbulb = new Lightbulb();
        assertEquals(R.drawable.lightbulb_off, lightbulb.getImage());
    }
}

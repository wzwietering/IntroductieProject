package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maaike on 28-11-2016.
 */

public class LightbulbTest {
    @Test
    public void connectedLightBulbIsOn(){
        Powersource powersource = new Powersource(5);
        ConnectionPoint powerOutput = powersource.getOutput();
        ConnectionPoint powerInput = powersource.getInput();

        Lightbulb bulb = new Lightbulb();
        ConnectionPoint bulbOutput = bulb.getConnectionPointByIndex(0);
        ConnectionPoint bulbInput = bulb.getConnectionPointByIndex(1);

        Connector.connect(bulbInput, powerOutput);
        Connector.connect(powerInput, bulbOutput);

        powersource.startConnection();
        assertTrue(bulb.isOn);
    }

    @Test
    public void weakPowersourceLightbulbNotOn(){
        Powersource powersource = new Powersource(3);
        ConnectionPoint powerOutput = powersource.getOutput();
        ConnectionPoint powerInput = powersource.getInput();

        Lightbulb bulb = new Lightbulb();
        ConnectionPoint bulbOutput = bulb.getConnectionPointByIndex(0);
        ConnectionPoint bulbInput = bulb.getConnectionPointByIndex(0);

        Connector.connect(bulbInput, powerOutput);
        Connector.connect(powerInput, bulbOutput);

        powersource.startConnection();
        assertFalse(bulb.isOn);
    }

    @Test
    public void disconnectedLightBulbIsOff(){
        Powersource powersource = new Powersource(5);
        ConnectionPoint powerOutput = powersource.getOutput();

        Lightbulb bulb = new Lightbulb();
        ConnectionPoint bulbInput = bulb.getConnectionPointByIndex(0);

        Connector.connect(bulbInput, powerOutput);

        powersource.startConnection();
        assertFalse(bulb.isOn);
    }

    @Test
    public void imageResource(){
        Lightbulb lightbulb = new Lightbulb();
        assertEquals(R.mipmap.lightbulb_on, lightbulb.getImage());
    }
}

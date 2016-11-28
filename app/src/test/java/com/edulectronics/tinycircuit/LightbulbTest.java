package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Input;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Output;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maaike on 28-11-2016.
 */

public class LightbulbTest {
    @Test
    public void connectedLightBulbIsOn(){
        Powersource powersource = new Powersource(5);
        Output powerOutput = powersource.getOutputByIndex(0);
        Input powerInput = powersource.getInputByIndex(0);

        Lightbulb bulb = new Lightbulb();
        Output bulbOutput = bulb.getOutputByIndex(0);
        Input bulbInput = bulb.getInputByIndex(0);

        Connector.getInstance().connect(bulbInput, powerOutput);
        Connector.getInstance().connect(powerInput, bulbOutput);

        powersource.startConnection();
        assertTrue(bulb.isOn);
    }


    @Test
    public void disconnectedLightBulbIsOff(){
        Powersource powersource = new Powersource(5);
        Output powerOutput = powersource.getOutputByIndex(0);

        Lightbulb bulb = new Lightbulb();
        Input bulbInput = bulb.getInputByIndex(0);

        Connector.getInstance().connect(bulbInput, powerOutput);

        powersource.startConnection();
        assertFalse(bulb.isOn);
    }
}

package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;

import org.junit.Test;
import org.junit.experimental.theories.internal.ParameterizedAssertionError;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maaike on 28-11-2016.
 */

public class LightbulbTest {

    @Test
    public void connectedLightBulbBlows(){
        ArrayList<Component> components = new ArrayList<Component>();

        Powersource powersource = new Powersource();
        ConnectionPoint powerOutput = powersource.getOutput();
        ConnectionPoint powerInput = powersource.getInput();

        Lightbulb bulb = new Lightbulb();
        ConnectionPoint bulbOutput = bulb.getConnectionPointByIndex(0);
        ConnectionPoint bulbInput = bulb.getConnectionPointByIndex(1);

        Connector.connect(bulbInput, powerOutput);
        Connector.connect(powerInput, bulbOutput);

        components.add(bulb);
        components.add(powersource);
        CircuitController controller = CircuitBuilder.getCircuitController(components);

        controller.run();
        assertTrue(bulb.isBroken());
    }


    @Test
    public void disconnectedLightBulbIsOff(){
        ArrayList<Component> components = new ArrayList<Component>();

        Powersource powersource = new Powersource();
        ConnectionPoint powerOutput = powersource.getConnectionPointByIndex(1);

        Lightbulb bulb = new Lightbulb();
        ConnectionPoint bulbInput = bulb.getConnectionPointByIndex(0);

        Connector.connect(bulbInput, powerOutput);
        components.add(bulb);
        components.add(powersource);
        CircuitController controller = CircuitBuilder.getCircuitController(components);

        controller.run();
        assertFalse(bulb.isOn);
    }

    @Test
    public void connectedLightBulbWithResistorIsOn(){
        ArrayList<Component> components = new ArrayList<Component>();

        Powersource powersource = new Powersource();
        Lightbulb bulb = new Lightbulb();
        Resistor resistor = new Resistor();

        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(0));
        Connector.connect(resistor.getConnectionPointByIndex(0), bulb.getConnectionPointByIndex(1));
        Connector.connect(resistor.getConnectionPointByIndex(1), powersource.getInput());

        components.add(bulb);
        components.add(powersource);
        components.add(resistor);
        CircuitController controller = CircuitBuilder.getCircuitController(components);

        controller.run();
        assertTrue(bulb.isOn);
        assertFalse(bulb.isBroken());
    }

    @Test
    public void imageResource(){
        Lightbulb lightbulb = new Lightbulb();
        assertEquals(R.drawable.lightbulb_off, lightbulb.getImage());

        lightbulb.isOn = true;
        assertEquals(R.drawable.lightbulb_broken, lightbulb.getImage());

        lightbulb.setResistance(true);
        assertEquals(R.drawable.lightbulb_on, lightbulb.getImage());
    }
}

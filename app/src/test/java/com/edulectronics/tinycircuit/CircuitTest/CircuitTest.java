package com.edulectronics.tinycircuit.CircuitTest;

import com.edulectronics.tinycircuit.CircuitBuilder;
import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitTest {
    static CircuitController c;
    Lightbulb light = new Lightbulb();

    @BeforeClass
    public static void setup(){
        c = new CircuitController(5, 5);
    }

    @Test
    public void addComponent(){
        c.addComponent(light, 6);
        assertTrue(c.circuit.occupied(6));
        c.removeComponent(6);
    }

    @Test
    public void removeComponent(){
        c = CircuitBuilder.getCircuitController();
        c.removeComponent(1);

        assertEquals(false, c.circuit.occupied(5));
    }

    @Test
    public void checkEmpty(){
        for (int i = 0; i < c.circuit.getAllComponents().length; i++) {
            assertFalse(c.circuit.occupied(i));
        }
    }

    @Test
    public void getComponent(){
        int position = 6;
        c.addComponent(light, position);
        assertEquals(light, c.getComponent(position));
        c.removeComponent(6);
    }

    @Test
    public void parallelCircuitNoResistance() {
        ArrayList<Component> components = new ArrayList<Component>();

        Powersource powersource = new Powersource();
        Resistor resistor1 = new Resistor();
        Lightbulb bulb = new Lightbulb();

        // Connect lightbulb directly to powersource
        Connector.connect(powersource.getInput(), bulb.getConnectionPointByIndex(0));
        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(1));

        // And the resistor also, in parallel, directly to output.
        Connector.connect(powersource.getInput(), resistor1.getConnectionPointByIndex(0));
        Connector.connect(powersource.getOutput(), resistor1.getConnectionPointByIndex(1));

        components.add(resistor1);
        components.add(powersource);
        components.add(bulb);
        CircuitController controller = CircuitBuilder.getCircuitController(components);

        controller.run(null);
        assertTrue(bulb.isBroken());
    }

    @Test
    public void parallelCircuitWithSomeResistance() {
        ArrayList<Component> components = new ArrayList<Component>();

        Powersource powersource = new Powersource();
        Resistor resistor = new Resistor();
        Lightbulb bulb = new Lightbulb();
        Lightbulb bulb2 = new Lightbulb();

        // Connect lightbulb directly to powersource
        Connector.connect(powersource.getInput(), bulb.getConnectionPointByIndex(0));
        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(1));

        // And the second lightbulb in series with the resistor.
        Connector.connect(powersource.getInput(), resistor.getConnectionPointByIndex(0));
        Connector.connect(resistor.getConnectionPointByIndex(1), bulb.getConnectionPointByIndex(0));
        Connector.connect(powersource.getOutput(), bulb.getConnectionPointByIndex(1));

        components.add(resistor);
        components.add(powersource);
        components.add(bulb);
        components.add(bulb2);
        CircuitController controller = CircuitBuilder.getCircuitController(components);

        controller.run(null);
        assertTrue(bulb.isBroken());
        assertFalse(bulb2.isBroken());
    }
}

package com.edulectronics.tinycircuit.CircuitTest;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitTest {
    Set<Component> s = new HashSet<>();
    CircuitController c;
    Lightbulb light = new Lightbulb();

    @Test
    public void addComponent(){
        s.add(light);
        c = new CircuitController(s, 5, 5);
        c.addComponent(light, 1, 1);

        assertEquals(true, c.circuit.occupied(1, 1));
        assertEquals(c.circuit.components[1][1].getClass(), Lightbulb.class);
    }

    @Test
    public void removeComponent(){
        c = new CircuitController(s, 5, 5);
        c.removeComponent(1, 1);

        assertEquals(false, c.circuit.occupied(1, 1));
    }
}

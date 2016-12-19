package com.edulectronics.tinycircuit.CircuitTest;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitTest {
    CircuitController c;
    Lightbulb light = new Lightbulb();

    @Test
    public void addComponent(){
        c = CircuitController.getInstance();
        c.setProperties(5, 5, null);
        c.addComponent(light, 6);

        assertEquals(true, c.circuit.occupied(6));
    }

    @Test
    public void removeComponent(){
        c = CircuitController.getInstance();
        c.setProperties(5, 5, null);
        c.removeComponent(1);

        assertEquals(false, c.circuit.occupied(5));
    }
}

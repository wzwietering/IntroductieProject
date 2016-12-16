package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitTest {
    static CircuitController c = CircuitController.getInstance();
    Lightbulb light = new Lightbulb();

    @BeforeClass
    public static void setup(){
        c.setProperties(null, 5, 5);
    }

    @Test
    public void addComponent(){
        c.addComponent(light, 6);
        assertTrue(c.circuit.occupied(6));
        c.removeComponent(6);
    }

    @Test
    public void checkEmpty(){
        for (int i = 0; i < c.circuit.components.length; i++) {
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
    public void setProperties(){
        int dimension = 8;
        c.setProperties(null, dimension, dimension);
        assertEquals(dimension * dimension, c.circuit.components.length);

        //Return to original state for other tests
        setup();
    }
}
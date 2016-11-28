package com.edulectronics.tinycircuit.CircuitTest;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitTest {
    Set<Component> s = new HashSet<>();
    CircuitController c;
    Lightbulb light = new Lightbulb();
    boolean[][] b = new boolean[5][5];
    ArrayList<Component> l = new ArrayList<>();

    @Test
    public void addComponent(){
        c = new CircuitController(s);
        s.add(light);
        c.addComponent(light, 1, 1);
        l.add(light);
        b[1][1] = true;

        assertEquals(l, c.circuit.components);
        assertArrayEquals(b, c.occupation);
    }

    @Test
    public void removeComponent(){
        c = new CircuitController(s);
        c.removeComponent(light, 1, 1);

        assertEquals(l, c.circuit.components);
        assertArrayEquals(b, c.occupation);
    }
}

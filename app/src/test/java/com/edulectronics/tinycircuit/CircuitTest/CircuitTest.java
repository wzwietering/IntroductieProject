package com.edulectronics.tinycircuit.CircuitTest;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Wilmer on 28-11-2016.
 */

public class CircuitTest {
    CircuitController c = new CircuitController();
    Lightbulb light = new Lightbulb();
    boolean[][] b = new boolean[5][5];
    ArrayList<Component> l = new ArrayList<>();

    @Test
    public void addComponent(){
        c.addComponent(light, 1, 1);
        l.add(light);
        b[1][1] = true;

        assertEquals(l, c.circuitModel.components);
        assertArrayEquals(b, c.occupation);
    }

    @Test
    public void removeComponent(){
        c.removeComponent(light, 1, 1);

        assertEquals(l, c.circuitModel.components);
        assertArrayEquals(b, c.occupation);
    }
}

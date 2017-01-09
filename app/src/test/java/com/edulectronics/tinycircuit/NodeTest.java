package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Models.Components.Lightbulb;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Lennart on 12-12-2016.
 */

public class NodeTest {
    public Lightbulb lightbulb = new Lightbulb();
    Node node = new Node(150,150);

    @Test
    public void occupyTests(){
        node.occupy(lightbulb);
        assertTrue(node.occupied);

        node.release();
        assertFalse(node.occupied);
    }
}



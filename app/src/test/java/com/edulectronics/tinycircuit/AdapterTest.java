package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.ui.adapters.CircuitAdapter;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;


/**
 * Created by Wilmer on 29-11-2016.
 */

public class AdapterTest {
    CircuitController controller = new CircuitController(new HashSet<Component>(), 5, 5);
    CircuitAdapter circuitAdapter = new CircuitAdapter(null, controller);

    @Test
    public void adapterTests(){
        Lightbulb l = new Lightbulb();
        controller.addComponent(l, 4, 2);

        assertEquals(l, circuitAdapter.getItem(14));
        assertEquals(null, circuitAdapter.getItem(3000));
        assertEquals(25, circuitAdapter.getCount());
    }
}

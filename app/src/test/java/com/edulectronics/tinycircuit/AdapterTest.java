package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Views.Adapters.CircuitAdapter;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;


/**
 * Created by Wilmer on 29-11-2016.
 */

public class AdapterTest {

    CircuitController controller = CircuitController.getInstance();
    CircuitAdapter circuitAdapter = new CircuitAdapter(null);

    @Test
    public void adapterTests(){
        controller.setProperties(5, 5, null);

        Lightbulb l = new Lightbulb();
        controller.addComponent(l, 14);

        assertEquals(l, circuitAdapter.getItem(14));
        assertEquals(25, circuitAdapter.getCount());
    }
}

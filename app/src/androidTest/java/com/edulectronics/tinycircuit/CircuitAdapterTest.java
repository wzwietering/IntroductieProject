package com.edulectronics.tinycircuit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Views.Adapters.CircuitAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Wilmer on 12-12-2016.
 */

@RunWith(AndroidJUnit4.class)
public class CircuitAdapterTest {
    Context context = InstrumentationRegistry.getTargetContext();
    CircuitController circuitController = CircuitController.getInstance();
    CircuitAdapter circuitAdapter = new CircuitAdapter(context);
    Lightbulb lightbulb = new Lightbulb();

    //More tests would be nice, but they require a views :\
    public void setup(){
        circuitController.setProperties(null, 4, 4);
        circuitController.addComponent(lightbulb, 5);
    }

    @Test
    public void countTest(){
        assertEquals(16, circuitAdapter.getCount());
    }

    public void equalityTest(){
        assertEquals(lightbulb, circuitAdapter.getItem(5));
    }
}

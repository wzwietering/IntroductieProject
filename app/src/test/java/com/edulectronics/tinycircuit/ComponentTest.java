package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Input;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Output;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ComponentTest {
    @Test
    public void connectedPowersource() throws Exception {
        Powersource powersource = new Powersource(5);
        Output output = powersource.getOutputByIndex(0);
        Input input = powersource.getInputByIndex(0);

        Connector.connect(input,output);

        assertTrue(powersource.hasOutputConnection());
    }

    @Test
    public void disconnectedPowersource(){
        Powersource powersource = new Powersource(5);
        assertFalse(powersource.hasOutputConnection());
    }

    @Test
    public void disconnectedCircuit(){
        Powersource powersource = new Powersource(5);
        Output powerOutput = powersource.getOutputByIndex(0);

        Lightbulb bulb = new Lightbulb();
        Input bulbInput = bulb.getInputByIndex(0);

        Connector.connect(bulbInput, powerOutput);

        assertFalse(powersource.hasOutputConnection());
    }
}
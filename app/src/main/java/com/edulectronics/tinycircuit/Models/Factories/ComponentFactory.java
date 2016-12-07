package com.edulectronics.tinycircuit.Models.Factories;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;

/**
 * Created by Maaike on 28-11-2016.
 */
public class ComponentFactory {

    //TODO: geen hardcoded strings gebruiken!!! Uit resources halen.

    public static Component CreateComponent(String name, double value) {
        switch (name) {
            case "Batterij":
                return new Powersource(value);
            case "Gloeilamp":
                return new Lightbulb();
            case "Weerstand":
                return new Resistor(value);
        }
        throw new IllegalArgumentException("component name unknown");
    }
}

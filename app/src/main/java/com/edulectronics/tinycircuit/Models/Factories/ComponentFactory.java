package com.edulectronics.tinycircuit.Models.Factories;

import com.edulectronics.tinycircuit.Models.Components.Bell;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.Models.Components.Resistor;
import com.edulectronics.tinycircuit.Models.Components.Switch;
public class ComponentFactory {

    //TODO: geen hardcoded strings gebruiken!!! Uit resources halen.

    public static Component CreateComponent(String name) {
        switch (name) {
            case "Batterij":
            case "Battery":
                return new Powersource();
            case "Gloeilamp":
            case "Lightbulb":
                return new Lightbulb();
            case "Weerstand":
            case "Resistor":
                return new Resistor();
            case "Schakelaar":
            case "Switch":
                return new Switch();
            case "Bel":
            case "Bell":
                return new Bell();
        }
        throw new IllegalArgumentException("component name unknown");
    }
}

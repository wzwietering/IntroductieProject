package com.edulectronics.tinycircuit.Models.Factories;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Maaike on 28-11-2016.
 */
public class ComponentFactory {

    //TODO: geen hardcoded strings gebruiken!!! Uit resouces halen.
    
    public static Component CreateComponent(String name) {
        switch (name) {
            case "Batterij":
                return new Powersource(5);
            case "Gloeilamp":
                return new Lightbulb();
            case "Weerstand":
                return new Lightbulb();
        }
        throw new IllegalArgumentException("component name unknown");
    }
}

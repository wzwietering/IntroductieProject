package com.edulectronics.tinycircuit.Models.Factories;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Components.Powersource;

/**
 * Created by Maaike on 28-11-2016.
 */
public class ComponentFactory {

    public static Component CreateComponent(String name) {
        switch (name) {
            case "Powersource":
                return new Powersource(5);
            case "Lightbulb":
                return new Lightbulb();
        }
        throw new IllegalArgumentException("component name unknown");
    }
}

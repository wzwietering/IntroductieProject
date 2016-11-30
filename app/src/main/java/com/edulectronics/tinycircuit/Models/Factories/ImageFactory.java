package com.edulectronics.tinycircuit.Models.Factories;

import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 30-11-2016.
 */

public class ImageFactory {
    public int GetImage(Object object){
        String component = object.getClass().getSimpleName();
        switch (component){
            case "Lightbulb":
                return R.mipmap.lightbulb_on;
            case "Resistor":
                return R.mipmap.resistor;
            case "Powersource":
                return R.mipmap.battery;
            default:
                throw new IllegalArgumentException("Unknown component: " + component);
        }
    }
}

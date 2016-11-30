package com.edulectronics.tinycircuit.Models.Factories;

import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.adapters.CircuitAdapter;

/**
 * Created by Wilmer on 30-11-2016.
 */

public class ImageFactory {
    public int GetImage(Object object){
        switch (object.getClass().toString()){
            case "Lightbulb":
                return R.mipmap.lightbulb_on;
            case "Resistor":
                return R.mipmap.resistor;
            case "Powersource":
                return R.mipmap.battery;
            default:
                throw new IllegalArgumentException("Unknown component");
        }
    }
}

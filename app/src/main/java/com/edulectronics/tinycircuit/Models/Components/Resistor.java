package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 7-12-2016.
 */

public class Resistor extends Component {
    double resistance;

    public Resistor(double resistance){
        this.resistance = resistance;
    }

    @Override
    public int getImage(){
        //TODO use different texture for different types
        if(this.resistance == 0){
            return R.drawable.resistor;
        }else{
            return R.drawable.resistor;
        }
    }
}

package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 7-12-2016.
 */

public class Resistor extends Component {
    double resistance;
    double voltageOut = 0;

    public Resistor(double resistance){
        this.resistance = resistance;
    }

    @Override
    public void handleInputChange() {
        voltageOut = Math.abs(connectionPoints.get(0).getVoltageIn() - connectionPoints.get(1).getVoltageIn());
        setNewOutputValues();
    }

    @Override
    public void setNewOutputValues(){
        for (ConnectionPoint connectionPoint: connectionPoints) {
            connectionPoint.setVoltageOut(this.voltageOut);
        }
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

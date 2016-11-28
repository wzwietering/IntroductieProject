package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Output;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Lightbulb extends Component {

    public double voltageThreshold = 5;
    public boolean isOn = false;

    @Override
    public void handleInputChange() {
        double voltage = 0;
        for (Output output: outputs
             ) {
            voltage += output.getOutputVoltage();
        }
        if(voltage >= voltageThreshold)
        {
            switchState(true);
        }
    }

    private void switchState(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public void setNewOutputValues() {

    }
}

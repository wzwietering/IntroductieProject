package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Components.Connectors.Input;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Output;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Lightbulb extends Component {

    public double voltageThreshold = 5;
    public boolean isOn = false;

    @Override
    public void handleInputChange() {
        double voltage = 0;
        for (Input input: inputs) {
            voltage += input.getInputVoltage();
        }
        if(voltage >= voltageThreshold)
        {
            switchState(true);
        }
        this.outputVoltage = voltage;
        setNewOutputValues();
    }

    @Override
    public void setNewOutputValues() {
        for (Output output: outputs) {
            output.setOutputVoltage(this.outputVoltage);
        }
    }

    @Override
    public int getImage() {
        return R.mipmap.lightbulb_on;
    }

    private void switchState(boolean isOn) {
        this.isOn = isOn;
    }
}

package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Components.Connectors.Input;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Output;
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
    public void handleInputChange() {
        double voltage = 0;
        for (Input input: inputs) {
            voltage += input.getInputVoltage();
        }
        this.outputVoltage = voltage;
        setNewOutputValues();
    }

    @Override
    public void setNewOutputValues(){
        for (Output output: outputs) {
            output.setOutputVoltage(this.outputVoltage);
        }
    }

    @Override
    public int getImage(){
        //TODO use different texture for different types
        if(this.resistance == 0){
            return R.mipmap.resistor;
        }else{
            return R.mipmap.resistor;
        }
    }
}

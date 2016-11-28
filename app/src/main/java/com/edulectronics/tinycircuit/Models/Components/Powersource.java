package com.edulectronics.tinycircuit.Models.Components;


import com.edulectronics.tinycircuit.Models.Input;
import com.edulectronics.tinycircuit.Models.Output;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Powersource extends Component {
    private final double voltage;

    public Powersource(double voltage){
        this.voltage = voltage;
        this.inputs.add(new Input(this));
        this.outputs.add(new Output());
    }

    @Override
    public boolean hasOutputConnection()
    {
        return true;
    }

    @Override
    public void handleInputChange(){}

    @Override
    public void setNewOutputValues(){
        for (Output output: outputs
             ) {
            output.setOutputVoltage(this.voltage);
        }
    }
}

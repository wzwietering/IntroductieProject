package com.edulectronics.tinycircuit.Models.Components;


import com.edulectronics.tinycircuit.Models.Input;
import com.edulectronics.tinycircuit.Models.Output;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Powersource extends Component {

    public Powersource(double voltage){
        this.outputVoltage = voltage;
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

    public void setNewOutputValues(){
        for (Output output: outputs
             ) {
            output.setOutputVoltage(this.outputVoltage);
        }
    }
}

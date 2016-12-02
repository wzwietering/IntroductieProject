package com.edulectronics.tinycircuit.Models.Components;


import com.edulectronics.tinycircuit.Models.Components.Connectors.Input;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Output;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Powersource extends Component {

    public Powersource(double voltage){
        this.outputVoltage = voltage;
    }

    public void startConnection(){
        if(this.hasOutputConnection()) {
            this.setNewOutputValues();
    }
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

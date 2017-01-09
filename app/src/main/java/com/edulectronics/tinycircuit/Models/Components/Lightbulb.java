package com.edulectronics.tinycircuit.Models.Components;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Lightbulb extends Component {

    public double voltageThreshold = 5;
    public boolean isOn = false;


    double voltageOut = 0;

    @Override
    public void handleInputChange() {
        double voltage = Math.abs(connectionPoints.get(0).getVoltageIn() - connectionPoints.get(1).getVoltageIn());

        if(voltage >= voltageThreshold)
        {
            setSwitchState(true);
        }
        voltageOut = voltage;
        setNewOutputValues();
    }

    @Override
    public void setNewOutputValues() {
        for (ConnectionPoint connectionPoint: connectionPoints) {
            connectionPoint.setVoltageOut(this.voltageOut);
        }
    }

    private void setSwitchState(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public int getImage(){
        return R.drawable.lightbulb_on;
    }

    public boolean isBroken() {
        return this.isOn && !this.hasResistance;
    }
}

package com.edulectronics.tinycircuit.Models.Components;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Switch extends Component {

    boolean isOn = false;
    double voltageOut = 0;

    @Override
    public boolean handleClick() {
        this.toggle();
        return true;
    }

    @Override
    public void handleInputChange() {
        if (isOn) {
    //        double voltage = Math.abs(connectionPoints.get(0).getVoltageIn() - connectionPoints.get(1).getVoltageIn());
     //       voltageOut = voltage;
        }
        else {
            voltageOut = 0;
        }
        setNewOutputValues();
    }

    @Override
    public void setNewOutputValues() {
//        for (ConnectionPoint connectionPoint: connectionPoints) {
//            connectionPoint.setVoltageOut(this.voltageOut);
//        }
    }

    public void toggle() {
        this.isOn = !this.isOn;
    }

    @Override
    public int getImage() {
        return isOn ? R.drawable.switch_on : R.drawable.switch_off;
    }
}

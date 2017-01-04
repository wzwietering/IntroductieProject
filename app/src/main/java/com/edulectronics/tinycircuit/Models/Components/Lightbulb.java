package com.edulectronics.tinycircuit.Models.Components;
import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Lightbulb extends Component {

    public double voltageThreshold = 5;
    public boolean isOn = false;
    public boolean isBroken = false;

    double voltageOut = 0;


    private void setSwitchState(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public int getImage(){
        return R.drawable.lightbulb_on;
    }

    @Override
    public void handleNoResistance() {
        isBroken = true;
    }
}

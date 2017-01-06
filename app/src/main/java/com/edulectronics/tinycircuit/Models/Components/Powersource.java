package com.edulectronics.tinycircuit.Models.Components;


import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Powersource extends Component {


    // This is the 'start' of checking whether a circuit is actually a circuit.
    // Always call this method on the powersource first.
    public boolean hasOutputConnection() {
        return super.hasOutputConnection(this.getInput());
    }

    public ConnectionPoint getInput() {
        return this.connectionPoints.get(0);
    }

    public ConnectionPoint getOutput() {
        return this.connectionPoints.get(1);
    }

    @Override
    public int getImage(){
        return R.drawable.battery;
    }
}

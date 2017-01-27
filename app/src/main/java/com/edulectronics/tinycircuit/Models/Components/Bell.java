package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 27-1-2017.
 */

public class Bell extends Component {
    public boolean isOn = false;

    @Override
    public boolean handleClick() {
        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.ic_launcher;
    }

    @Override
    public void handleInputHigh() {
        this.isOn = true;
    }

    @Override
    public void reset() {
        super.reset();
        this.isOn = false;
    }

    //Only returns sound if there is power and resistance
    public int getSound(){
        if(isOn && hasResistance) {
            return R.raw.bell2;
        } else {
            return 0;
        }
    }
}

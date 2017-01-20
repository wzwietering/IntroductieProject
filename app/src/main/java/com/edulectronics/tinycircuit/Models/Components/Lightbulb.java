package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.R;

/**
 * Created by Maaike on 28-11-2016.
 */

public class Lightbulb extends Component {
    public boolean isOn = false;

    @Override
    public int getImage() {
        if (isOn) {
            if (hasResistance) {
                return R.drawable.lightbulb_on;
            } else {
                return R.drawable.lightbulb_broken;
            }
        }
        return R.drawable.lightbulb_off;
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

    public boolean isBroken() {
        return this.isOn && !this.hasResistance;
    }
}

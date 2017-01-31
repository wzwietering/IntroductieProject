package com.edulectronics.tinycircuit.Models.Components;

import android.os.Handler;
import android.os.Looper;

import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;

/**
 * Created by Wilmer on 27-1-2017.
 */

public class Bell extends Component {
    public boolean isOn = false;

    private  int timesRung = 0;

    @Override
    public boolean handleClick() {
        return true;
    }

    @Override
    public int getImage() {
        if (isOn) {
            return timesRung % 2 == 0 ? R.drawable.bell_2 : R.drawable.bell;
        }
        return R.drawable.bell;
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

    //Only returns sound if there is power
    public int getSound(){
        if(isOn) {
            return R.raw.bell2;
        } else {
            return 0;
        }
    }

    public void doRing(final GridCell gridCell) {
        final GridCell _gridCell = gridCell;
        final Component thisComponent = this;

        if(this.isOn) {
            if (timesRung < 12) {
                final int image = thisComponent.getImage();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        _gridCell.setImageResource(image);
                    }
                }, 200 * timesRung) ;
                timesRung++;
                doRing(_gridCell);
            }
            else {
                this.isOn = false;
                timesRung = 0;
            }
        }
    }
}

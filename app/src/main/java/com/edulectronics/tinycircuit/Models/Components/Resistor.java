package com.edulectronics.tinycircuit.Models.Components;

import com.edulectronics.tinycircuit.Models.Components.Connectors.ConnectionPoint;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Wilmer on 7-12-2016.
 */

public class Resistor extends Component {

    @Override
    public int getImage(){
        return R.drawable.resistor;
    }
}

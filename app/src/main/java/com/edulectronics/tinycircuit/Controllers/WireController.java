package com.edulectronics.tinycircuit.Controllers;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Views.DrawView;

/**
 * Created by Wilmer on 11-12-2016.
 */

public class WireController {
    Component first, second;
    DrawView drawView;

    public WireController(DrawView drawView){
        this.drawView = drawView;
    }

    public void wire(Component component){
        if(first == null) {
            first = component;
        } else {
            second = component;
            first.connect(first.getConnectionPointByIndex(0).orientation, second.getConnectionPointByIndex(0));
            drawView.invalidate();
        }
    }
}

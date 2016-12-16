package com.edulectronics.tinycircuit.Controllers;

import com.edulectronics.tinycircuit.Models.Components.Component;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by bernd on 16/12/2016.
 */

public class LevelController {
    private ArrayList<Component> availableComponents;

    public LevelController(int level) {
        this.availableComponents = getAvailableComponents(level);
    }

    public boolean available(Component component) {
        if (availableComponents.contains(component))
            return true;
        return false;
    }

    public boolean ownedByLevel(int position) {
        return false;
    }

    private ArrayList<Component> getAvailableComponents(int level) {
        return new ArrayList<>();
    }
}

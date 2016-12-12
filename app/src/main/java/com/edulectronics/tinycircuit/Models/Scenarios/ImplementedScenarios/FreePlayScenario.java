package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;

import java.io.Serializable;

/**
 * Created by Maaike on 12-12-2016.
 */

public class FreePlayScenario extends DesignScenario {

    @Override
    public int getPrompt() {
        return -1;
    }
}

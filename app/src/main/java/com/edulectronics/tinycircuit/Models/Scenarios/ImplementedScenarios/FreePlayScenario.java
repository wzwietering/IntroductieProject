package com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios;

import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;

/**
 * Created by Maaike on 12-12-2016.
 */

public class FreePlayScenario extends DesignScenario {

    @Override
    public int getPrompt() {
        return -1;
    }

    @Override
    public int getID(){
        return 0;
    }
}

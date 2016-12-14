package com.edulectronics.tinycircuit.Models.Components.Connectors;

import java.util.ArrayList;

/**
 * Created by bernd on 14/12/2016.
 */

public class Wire {
    ArrayList<Line> lines;

    public void addLine(Line line) {
        lines.add(line);
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
}

package com.edulectronics.tinycircuit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DragTest {
    Context context = InstrumentationRegistry.getTargetContext();
    CircuitController controller = new CircuitController(5, 5);

    @Test
    public void emptyGridCell() {
        GridCell gridCell = new GridCell(controller, context);
        assertEquals(null, gridCell.getComponent());
    }

    @Test
    public void gridCellTest() {
        GridCell gridCell = new GridCell(controller, context);
        Lightbulb lightbulb = new Lightbulb();

        assertTrue(gridCell.isEmpty());
        gridCell.setComponent(lightbulb);
        assertFalse(gridCell.isEmpty());
    }
}

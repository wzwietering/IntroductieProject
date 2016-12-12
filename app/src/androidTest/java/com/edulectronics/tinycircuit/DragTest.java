package com.edulectronics.tinycircuit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Wilmer on 12-12-2016.
 */

@RunWith(AndroidJUnit4.class)
public class DragTest {
    Context context = InstrumentationRegistry.getTargetContext();

    @Test(expected = NullPointerException.class)
    public void emptyGridCell(){
        GridCell gridCell = new GridCell(context);
        gridCell.getComponent();
    }

    @Test
    public void gridCellTest(){
        GridCell gridCell = new GridCell(context);
        Lightbulb lightbulb = new Lightbulb();

        assertTrue(gridCell.isEmpty);
        gridCell.setComponent(lightbulb);
        assertFalse(gridCell.isEmpty);
    }
}

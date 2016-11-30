package com.edulectronics.tinycircuit;

import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Factories.ImageFactory;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * Created by Wilmer on 30-11-2016.
 */

public class ImageFactoryTest {
    ImageFactory imageFactory = new ImageFactory();

    @Test
    public void TestImageFactory(){
        Lightbulb lightbulb = new Lightbulb();

        assertEquals(R.mipmap.lightbulb_on, imageFactory.GetImage(lightbulb));
    }

    /*Nonexisting components should trigger an exception*/
    @Test(expected = IllegalArgumentException.class)
    public void TestDefault(){
        imageFactory.GetImage(new Object());
    }
}

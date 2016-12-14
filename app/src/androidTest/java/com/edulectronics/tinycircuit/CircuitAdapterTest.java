package com.edulectronics.tinycircuit;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Views.CircuitActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Wilmer on 12-12-2016.
 */

@RunWith(AndroidJUnit4.class)
public class CircuitAdapterTest {
    static CircuitController circuitController = CircuitController.getInstance();

    @BeforeClass
    public static void setup(){
        circuitController.setProperties(null, 4, 4);
    }

    @Rule
    public ActivityTestRule<CircuitActivity> circuitActivity = new ActivityTestRule<>(CircuitActivity.class);

    @Test
    public void menuOpen(){
        onView(withId(R.id.activity_main)).perform(DrawerActions.open());
        onView(withId(R.id.navigationview)).check(matches(isDisplayed()));
    }
}

package com.edulectronics.tinycircuit;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Views.CircuitActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Wilmer on 12-12-2016.
 */

@RunWith(AndroidJUnit4.class)
public class CircuitAdapterTest {
    static CircuitController circuitController = CircuitController.getInstance();

    @BeforeClass
    public static void setup(){
        circuitController.setProperties(4, 4, null);
    }

    @Rule
    public ActivityTestRule<CircuitActivity> circuitActivity = new ActivityTestRule<>(CircuitActivity.class);

    @Test
    public void menuOpen(){
        //The close operations are used to ensure that the menu is openable and
        // is left closed for other tests.
        onView(withId(R.id.activity_main)).perform(DrawerActions.close());

        onView(withId(R.id.activity_main)).perform(DrawerActions.open());
        onView(withId(R.id.navigationview)).check(matches(isDisplayed()));

        onView(withId(R.id.activity_main)).perform(DrawerActions.close());
    }

    @Test
    public void menuClose(){
        onView(withId(R.id.activity_main)).perform(DrawerActions.open());
        onView(withId(R.id.activity_main)).perform(DrawerActions.close());
        onView(withId(R.id.navigationview)).check(matches(not(isDisplayed())));
    }

    @Test
    public void menuClosed(){
        onView(withId(R.id.navigationview)).check((matches(not(isDisplayed()))));
    }

    @Test
    public void dragItem(){
        Lightbulb lightbulb = new Lightbulb();
        circuitController.addComponent(lightbulb, 3);

        onData(is(instanceOf(Lightbulb.class))).inAdapterView(withId(R.id.circuit)).perform(drag());
        assertEquals(Lightbulb.class, circuitController.getComponent(7).getClass());
        assertEquals(null, circuitController.getComponent(3));
    }

    private static ViewAction drag(){
        return new GeneralSwipeAction(Swipe.FAST, getCoordinates(75, 75),
                getCoordinates(140, 200), Press.FINGER);
    }

    //Used for coordinates on the screen
    private static CoordinatesProvider getCoordinates(final float offsetX, final float offsetY){
        return new CoordinatesProvider() {
            @Override
            public float[] calculateCoordinates(View view) {
                int[] screenPosition = new int[2];
                view.getLocationOnScreen(screenPosition);

                float[] coordinates = {screenPosition[0] + offsetX, screenPosition[1] + offsetY};
                return coordinates;
            }
        };
    }
}

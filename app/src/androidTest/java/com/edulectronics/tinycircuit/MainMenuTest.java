package com.edulectronics.tinycircuit;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import com.edulectronics.tinycircuit.Views.CircuitActivity;
import com.edulectronics.tinycircuit.Views.ExerciseMenuActivity;
import com.edulectronics.tinycircuit.Views.MenuActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainMenuTest {
    @Rule
    public ActivityTestRule<MenuActivity> menuActivity =
            new ActivityTestRule<>(MenuActivity.class);

    @Test
    public void scenarioButton(){
        onData(anything()).inAdapterView(withId(R.id.buttonArea)).atPosition(0).perform(click());
        Activity current = getActivityInstance();
        assertEquals(ExerciseMenuActivity.class, current.getClass());
    }

    @Test
    public void freeplayButton(){
        onData(anything()).inAdapterView(withId(R.id.buttonArea)).atPosition(1).perform(click());
        Activity current = getActivityInstance();
        assertEquals(CircuitActivity.class, current.getClass());
    }

    public Activity getActivityInstance(){
        Run run = new Run();
        getInstrumentation().runOnMainSync(run);
        return run.currentActivity;
    }

    //Stupid mini class is used to get the current activity using a runnable, which is
    //required for Espresso
    private class Run implements Runnable{
        public Activity currentActivity;
        public void run() {
            Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
            if (resumedActivities.iterator().hasNext()){
                currentActivity = (Activity) resumedActivities.iterator().next();
            }
        }
    }
}

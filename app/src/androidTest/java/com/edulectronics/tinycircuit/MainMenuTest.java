package com.edulectronics.tinycircuit;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.edulectronics.tinycircuit.Views.MenuActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Wilmer on 14-12-2016.
 */

@RunWith(AndroidJUnit4.class)
public class MainMenuTest {
    @Rule
    public ActivityTestRule<MenuActivity> menuActivity =
            new ActivityTestRule<>(MenuActivity.class);

    @Test
    public void scenarioButton(){
        onView(withId(R.id.exerciseButton)).perform(click());
    }
}

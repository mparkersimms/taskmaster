package com.example.taskmaster;


import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


public class EspressoTests {

    @Test
    public void homePageTest() {
        onView(withId(R.id.homeTitle)).check(matches(withText("My Tasks")));

}
    @Test
    public void buttonTester(){
        onView(withId(R.id.addTasksButton)).perform(click());
    }

    }


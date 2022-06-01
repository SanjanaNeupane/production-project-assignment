package com.home.service.app;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule=new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private String mPass="rakibul";
    private String mPhone="+6583717502";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testUserInputScenario()
    {
        Espresso.onView(withId(R.id.loginphone)).perform(typeText(mPhone));
        Espresso.onView(withId(R.id.loginpassword)).perform(typeText(mPass));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.loginbutton)).perform(click());
    }
    @After
    public void tearDown() throws Exception {
    }
}
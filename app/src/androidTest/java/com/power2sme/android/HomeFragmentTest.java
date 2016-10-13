package com.power2sme.android;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

//import static android.support.test.espresso.contrib.DrawerActions.openDrawer;

/**
 * Created by tausif on 28/4/15.
 */

@RunWith(AndroidJUnit4.class)
public class HomeFragmentTest
{
    @Rule
    public ActivityTestRule<ContainerActivity> mActivityRule = new ActivityTestRule<>(ContainerActivity.class);

    AsyncTaskIdlingResource asyncTaskIdlingResource;

    @Before
    public void registerIntentServiceIdlingResource()
    {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        asyncTaskIdlingResource = new AsyncTaskIdlingResource(instrumentation.getTargetContext());
        Espresso.registerIdlingResources(asyncTaskIdlingResource);
    }
    @After
    public void unregisterIntentServiceIdlingResource()
    {
        Espresso.unregisterIdlingResources(asyncTaskIdlingResource);
    }

    @Test
    public void test_01_IsHomeFragmentOpened()
    {
        onView(allOf(withId(R.id.Button_getAQuote), withText(R.string.home_button_request_a_quote))).check(matches(isDisplayed()));
    }

    @Test
    public void test_02_IsUserNotLoggedin()
    {
        onView(allOf(withId(R.id.TextView_login), withText(R.string.label_login))).check(matches(isDisplayed()));
    }

    @Test
    public void test_03_IsUserLoggedin()
    {
        onView(allOf(withId(R.id.TextView_viewAllAccountUpdates), withText(R.string.home_label_viewall))).check(matches(isDisplayed()));
    }

    @Test
    public void test_04_IsDealsDisplayed()
    {
        onView(withId(R.id.LinearLayout_smeDealsItemsParent)).check(matches(isDisplayed()));
    }

    @Test
    public void test_05_IsAccountUpdatesDisplayed()
    {
        onView(withId(R.id.LinearLayout_accountUpdatesItemsParent)).check(matches(isDisplayed()));
    }

//    @Test
//    public void test_06_IsNewsDisplayed()
//    {
//        onView(withId(R.id.LinearLayout_smeNewsItemsParent)).perform(scrollTo()).check(matches(isDisplayed()));
//    }

    @Test
    public void test_07_tapOnRequestAccountStatementButton()
    {
        onView(allOf(withId(R.id.TextView_requestAccountStatement), withText(R.string.home_label_requestaccuntstatement))).perform(click());
//        onView(withText(R.string.your_account_statement_will_be_sent_within_48_hours_)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        onView(withText(R.string.your_account_statement_will_be_sent_within_48_hours_)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void test_08_tapOnViewAllUpdatesButton()
    {
        onView(allOf(withId(R.id.TextView_viewAllAccountUpdates), withText(R.string.home_label_viewall))).perform(scrollTo(), click());
//        Espresso.onData(is(instanceOf(UpdatesRecyclerAdapter.ViewHolder.class)));
        pressBack();
        onView(withId(R.id.TextView_viewAllAccountUpdates)).check(matches(withText(R.string.home_label_viewall)));
    }

    @Test
    public void test_09_tapOnViewAllDealsButton()
    {
        onView(allOf(withId(R.id.TextView_viewAllDeals), withText(R.string.home_label_viewall))).perform(scrollTo(), click());
        //Espresso.onData(is(instanceOf(ExpandableDealRecyclerViewAdapter.GroupViewHolder.class)));
        pressBack();
        onView(withId(R.id.TextView_viewAllDeals)).check(matches(withText(R.string.home_label_viewall)));
    }

//    @Test
//    public void test_10_tapOnViewAllNewsButton()
//    {
//        onView(allOf(withId(R.id.TextView_viewAllNews), withText(R.string.home_label_viewall))).perform(scrollTo(), click());
//        Espresso.onData(is(instanceOf(SMEKhabarRecyclerAdapter.ViewHolder.class)));
//        pressBack();
//        onView(withId(R.id.TextView_viewAllNews)).check(matches(withText(R.string.home_label_viewall)));
//    }

//    @Test
//    public void test_11_tapOnContactUsButton()
//    {
//        onView(allOf(withId(R.id.Button_contactUs), withText(R.string.contact_us))).perform(click());
//        onView(withId(R.id.contactUsHeading)).check(matches(withText(R.string.contactus_page_heading)));
//        pressBack();
//        onView(withId(R.id.Button_contactUs)).check(matches(withText(R.string.contact_us)));
//    }

    @Test
    public void test_12_tapOnRequestForQuoteButton()
    {
        onView(allOf(withId(R.id.Button_getAQuote), withText(R.string.home_button_request_a_quote))).perform(scrollTo(), click());
        onView(withId(R.id.TextView_contactInfoTabLabel)).check(matches(withText(R.string.addrfq_button_contactinfo)));
        pressBack();
        onView(withId(R.id.Button_getAQuote)).check(matches(withText(R.string.home_button_request_a_quote)));
    }

    @Test
    public void test_13_tapOnAccountUpdatesItem()
    {
        onData(is(instanceOf(View.class))).atPosition(0);
        onView(allOf(withId(R.id.Button_getAQuote), withText(R.string.home_button_request_a_quote))).perform(scrollTo(), click());
        onView(withId(R.id.TextView_contactInfoTabLabel)).check(matches(withText(R.string.addrfq_button_contactinfo)));
        pressBack();
        onView(withId(R.id.Button_getAQuote)).check(matches(withText(R.string.home_button_request_a_quote)));
    }
}

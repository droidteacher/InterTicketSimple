package hu.zsoltkiss.interticketsimple

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import hu.zsoltkiss.interticketsimple.ui.countries.CountryItemViewHolder
import hu.zsoltkiss.interticketsimple.ui.countries.CountryListActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("TestFunctionName")
@RunWith(AndroidJUnit4::class)
class CountryListActivityTest {

    @get : Rule
    var activityRule = ActivityScenarioRule(CountryListActivity::class.java)


    @Test
    fun WHEN_displayed_THEN_it_contains_empty_list() {
        onView(withId(R.id.btnRefresh)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withText("No items")).check(matches(isDisplayed()))
    }

    @Test
    fun WHEN_refresh_button_clicked_THEN_it_displays_countries() {
        onView(withId(R.id.btnRefresh)).perform(click())
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

        Thread.sleep(5000)

        onView(withId(R.id.countriesRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<CountryItemViewHolder>(10))

        onView(withText("Cyprus")).check(matches(isDisplayed()))

        onView(withId(R.id.countriesRecyclerView))
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.scrollTo<CountryItemViewHolder>(
                    hasDescendant(withText("London"))
                )
            )
    }

}
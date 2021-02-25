package com.macoev.aadstudyproject

import android.app.Application
import android.view.View
import android.widget.Button
import androidx.annotation.IdRes
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.macoev.aadstudyproject.activity.MainActivity
import com.macoev.aadstudyproject.adapter.UserViewHolder
import com.macoev.aadstudyproject.data.repository.Repository
import org.hamcrest.Matcher
import org.hamcrest.Matchers.any
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var repository: Repository
    private val context = ApplicationProvider.getApplicationContext<Application>()


//    @get:Rule
//    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        repository = RepositoryLocator.getUser(context)
        repository.deleteAll()
    }

    @After
    fun reset() {
        repository.deleteAll()
    }

    @Test
    fun addAndDeleteUser() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.rv)).check(matches(hasChildCount(0)))
        onView(withId(R.id.add)).perform(click()).perform(click()).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.rv))
            .check(matches(hasChildCount(3)))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(
                    1, recyclerChildAction<Button>(R.id.delete, click())
                )
            )
            .check(matches(hasChildCount(2)))

        Thread.sleep(2000)
        activityScenario.close()
    }

    private fun <T : View> recyclerChildAction(@IdRes id: Int, action: ViewAction): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return any(View::class.java)
            }

            override fun getDescription(): String {
                return "Performing action on RecyclerView child item"
            }

            override fun perform(uiController: UiController, view: View) {
                action.perform(uiController, view.findViewById<T>(id))
            }
        }
    }
}

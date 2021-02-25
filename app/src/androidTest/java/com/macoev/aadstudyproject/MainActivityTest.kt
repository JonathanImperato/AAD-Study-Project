package com.macoev.aadstudyproject

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.IdRes
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.work.Configuration
import androidx.work.Operation
import androidx.work.await
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.macoev.aadstudyproject.activity.MainActivity
import com.macoev.aadstudyproject.adapter.UserViewHolder
import com.macoev.aadstudyproject.data.entity.User
import com.macoev.aadstudyproject.data.repository.Repository
import com.macoev.aadstudyproject.work.DownloadWork
import com.macoev.aadstudyproject.work.RequestManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.any
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.KoinApiExtension

@SmallTest
@KoinApiExtension
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var repository: Repository<User>
    private val context = ApplicationProvider.getApplicationContext<Application>()

//    @get:Rule
//    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
        repository = RepositoryLocator.getUser(context)
        repository.deleteAll()
    }

    @After
    fun reset() {
        repository.deleteAll()
    }

    @Test
    fun testSleepWorker() = runBlockingTest {
        val worker = RequestManager(context).single<DownloadWork>()
        val res = worker.result.await()
//        assertThat(res, isA(Operation.State.SUCCESS::class.java))
        assertThat(res, `is`(Operation.SUCCESS))
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

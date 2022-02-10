package com.jason.test.newsapp.ui


import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.jason.test.newsapp.MockServer
import com.jason.test.newsapp.R
import com.jason.test.newsapp.ui.main.MainActivity
import com.jason.test.newsapp.ui.main.news.NewsFragment
import com.jason.test.newsapp.ui.main.news.NewsViewModel
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var scenario: FragmentScenario<NewsFragment>
    private lateinit var viewModel: NewsViewModel

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.setDispatcher(MockServer.ResponseDispatcher())
        mockWebServer.start(8080)

    }

    lateinit var newsFragment: NewsFragment

    @After
    fun dropdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun mainActivityDisplayCompleted() {
        launchActivity()
        onView(withId(R.id.toolbar)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.searchButton)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.swipe_refresh)).check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.inputText))
            .perform(ViewActions.click())
            .perform(ViewActions.replaceText("football"), ViewActions.closeSoftKeyboard())
            .perform(ViewActions.pressImeActionButton())
            .check(ViewAssertions.matches(withText("football")))
    }

    private fun launchActivity(): ActivityScenario<MainActivity>? =
        ActivityScenario.launch(MainActivity::class.java)

    /**
     * onView(isRoot()).perform(waitFor(50000))
     */
    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }


}

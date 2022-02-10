package com.jason.test.newsapp.ui


import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.jason.test.newsapp.R
import com.jason.test.newsapp.ui.main.news.NewsFragment
import com.jason.test.newsapp.ui.main.news.NewsViewModel
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock


@LargeTest
@RunWith(AndroidJUnit4::class)
class NewFragmentTest {

    lateinit var scenario: FragmentScenario<NewsFragment>
    private lateinit var viewModel: NewsViewModel

    @Before
    fun setup() {
        val mockNavController = mock(NavController::class.java)
        mockNavController.setViewModelStore(ViewModelStore())

        scenario = launchFragmentInContainer<NewsFragment>(themeResId = R.style.Theme_NewsApp) {
            NewsFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), mockNavController)
                    }
                }
            }
        }
    }

    @Test
    fun testUiShouldDisplayed() {
        onView(withId(R.id.searchButton)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.swipe_refresh)).check(ViewAssertions.matches(isDisplayed()))
    }


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

package com.jazim.stackup

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.presentation.state.UserUiState
import com.jazim.stackup.presentation.users.UsersScreen
import com.jazim.stackup.presentation.users.UsersViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTests {


    @MockK
    private lateinit var usersViewModel: UsersViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jazim.stackup", appContext.packageName)
    }

    @Test
    fun usersScreenShowsLoadingWhenUsersUnpopulated() {
        val mockState = UserUiState(isLoading = true)
        every { usersViewModel.usersState } returns MutableStateFlow(mockState)


        composeTestRule.setContent {
            UsersScreen(usersViewModel)
        }

        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun usersScreenDoesntShowRandomTestTagScreen() {
        every { usersViewModel.usersState } returns MutableStateFlow(UserUiState())

        composeTestRule.setContent {
            UsersScreen(usersViewModel)
        }

        composeTestRule.onNodeWithTag("foobar").assertIsNotDisplayed()
    }

    @Test
    fun usersScreenShowsErrorWhenErroring() {
        val mockState = UserUiState(false, emptyList(), "fakeError")
        every { usersViewModel.usersState } returns MutableStateFlow(mockState)

        composeTestRule.setContent {
            UsersScreen(usersViewModel)
        }

        composeTestRule.onNodeWithTag("ErrorScreen").assertIsDisplayed()
    }

    @Test
    fun usersScreenShowsMainScreenWhenOnHappyPath() {
        val fakeUserList = listOf(User(1,"foobar", 200, "", "", ""),User(2,"foobar", 400, "", "", ""))
        val mockState = UserUiState(false, fakeUserList, null)
        every { usersViewModel.usersState } returns MutableStateFlow(mockState)

        composeTestRule.setContent {
            UsersScreen(usersViewModel)
        }

        composeTestRule.onNodeWithTag("MainScreen").assertIsDisplayed()
    }

}
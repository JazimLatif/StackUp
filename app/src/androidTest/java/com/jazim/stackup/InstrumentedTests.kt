package com.jazim.stackup

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jazim.stackup.data.model.Badges
import com.jazim.stackup.data.model.User
import com.jazim.stackup.presentation.users.UsersMainScreen
import com.jazim.stackup.presentation.users.UsersUiState
import com.jazim.stackup.presentation.users.UsersViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val usersViewModel = mockk<UsersViewModel>()

    private fun showScreenWith(state: UsersUiState) {
        every { usersViewModel.usersState } returns MutableStateFlow(state)
        every { usersViewModel.pageNumberState } returns MutableStateFlow(1)
        composeTestRule.setContent {
            UsersMainScreen(navigateToUser = {}, usersViewModel = usersViewModel)
        }
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jazim.stackup", appContext.packageName)
    }

    @Test
    fun showsLoadingWhenLoading() {
        showScreenWith(UsersUiState.Loading)
        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun showsErrorWhenErroring() {
        showScreenWith(UsersUiState.Error("fakeError"))
        composeTestRule.onNodeWithTag("ErrorScreen").assertIsDisplayed()
    }

    @Test
    fun showsMainScreenOnHappyPath() {
        val users = listOf(
            User(1, "foobar", 200, "", "", "", Badges(1, 2, 5)),
            User(2, "foobar", 400, "", "", "", Badges(1, 2, 5))
        )
        showScreenWith(UsersUiState.Success(users))
        composeTestRule.onNodeWithTag("MainScreen").assertIsDisplayed()
    }
}
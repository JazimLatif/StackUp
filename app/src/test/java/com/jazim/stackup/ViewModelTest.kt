package com.jazim.stackup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.domain.repository.UsersRepository
import com.jazim.stackup.presentation.users.UsersViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class UsersViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var usersRepository: UsersRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        usersRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    private val testUsers = listOf(
        User(1, "foo", 100, "", "", "", false),
        User(2, "foo", 200, "", "", "", false)
    )

    @Test
    fun whenGetUsersSucceedsReturnSuccess() = runTest {

        coEvery { usersRepository.getUsers() } returns Result.success(testUsers)

        val usersViewModel = UsersViewModel(usersRepository)

        advanceUntilIdle()

        val actualState = usersViewModel.usersState.value
        assertEquals(false, actualState.isLoading)
        assertEquals(null, actualState.error)
        assertEquals(testUsers, actualState.users)
        coVerify { usersRepository.getUsers() }
    }

    @Test
    fun whenGetUsersFailsReturnFailure() = runTest {

        val errorMessage = "Failed to load users"
        coEvery { usersRepository.getUsers() } returns Result.failure(Exception(errorMessage))

        val usersViewModel = UsersViewModel(usersRepository)

        advanceUntilIdle()

        val actualState = usersViewModel.usersState.value
        assertEquals(false, actualState.isLoading)
        assertEquals(errorMessage, actualState.error)
        assertEquals(emptyList<User>(), actualState.users)
        coVerify { usersRepository.getUsers() }
    }

    @Test
    fun toggleFollowFlipsFollowedState() = runTest {


        coEvery { usersRepository.getUsers() } returns Result.success(testUsers)
        coEvery { usersRepository.toggleFollow(1, true) } returns Result.success(Unit)

        val usersViewModel = UsersViewModel(usersRepository)

        usersViewModel.toggleFollow(testUsers[0])
        advanceUntilIdle()

        val updatedUser = usersViewModel.usersState.value.users.first { it.id == 1 }
        assertEquals(true, updatedUser.isFollowed)

        coVerify { usersRepository.toggleFollow(1, true) }
    }

    // Since this test calls Log.e on the error of toggleFollow, I had to import roboelectric (since that's an Android function)
    @Test
    fun toggleFollowDoesntFlipFollowedStateOnFailure() = runTest {


        coEvery { usersRepository.getUsers() } returns Result.success(testUsers)
        val exception = Exception("Failed")
        coEvery { usersRepository.toggleFollow(1, true) } returns Result.failure(exception)
        val usersViewModel = UsersViewModel(usersRepository)
        usersViewModel.toggleFollow(testUsers[0])
        advanceUntilIdle()

        val updatedUser = usersViewModel.usersState.value.users.first { it.id == 1 }
        assertEquals(false,  updatedUser.isFollowed)
    }

}
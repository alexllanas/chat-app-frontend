package com.chatappfrontend.data

import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.data.repository.DefaultAuthRepository
import com.example.network.CAFNetworkDataSource
import com.example.network.model.UserDto
import com.example.security.DataStoreManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class DefaultAuthRepositoryTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var network: CAFNetworkDataSource
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var repository: DefaultAuthRepository

    @Before
    fun setUp() {
        network = mockk()
        dataStoreManager = mockk(relaxed = true)
        repository = DefaultAuthRepository(network, dataStoreManager)
    }

    @Test
    fun `registerUser should return Success when network response is successful`() = runTest {
        val dummyUserDto = UserDto(
            id = "123", username = "testuser", email = "test@example.com", accessToken = "abc123"
        )

        coEvery {
            network.registerUser("testuser", "test@example.com", "password")
        } returns Response.success(dummyUserDto)

        val result = repository.registerUser("testuser", "test@example.com", "password")

        assertTrue(result is ActionResult.Success)
        val user = (result as ActionResult.Success).data
        assertEquals("testuser", user.username)

        coVerify {
            dataStoreManager.saveUserSession(
                accessToken = "abc123",
                userId = "123",
                username = "testuser",
                email = "test@example.com"
            )
        }
    }

    @Test
    fun `registerUser should return Error when response is not successful`() = runTest {
        val errorJson = """{"error":"Email aaexists"}"""
        val errorResponse = Response.error<UserDto>(
            400,
            errorJson.toResponseBody("application/json".toMediaTypeOrNull())
        )

        coEvery { network.registerUser(any(), any(), any()) } returns errorResponse

        val result = repository.registerUser("alex", "test@example.com", "password123")

        assertEquals("Email exists", (result as ActionResult.Error).message)
    }

    @Test
    fun `registerUser should return Exception when network throws`() = runTest {
        coEvery {
            network.registerUser(any(), any(), any())
        } throws RuntimeException("Network failure")

        val result = repository.registerUser("testuser", "test@example.com", "password")

        assertTrue(result is ActionResult.Exception)
        assertEquals("Network failure", (result as ActionResult.Exception).exception.message)
    }

    @Test
    fun `login should return Success when response is successful`() = runTest {
        val dummyUserDto = UserDto("456", "anotheruser", "another@example.com", "token456")

        coEvery {
            network.login("another@example.com", "pass")
        } returns Response.success(dummyUserDto)

        val result = repository.login("another@example.com", "pass")

        assertTrue(result is ActionResult.Success)
        assertEquals("anotheruser", (result as ActionResult.Success).data.username)
    }

    @Test
    fun `logout should call clearUserSession`() = runTest {
        coEvery { dataStoreManager.clearUserSession() } just Runs

        repository.logout()

        coVerify { dataStoreManager.clearUserSession() }
    }
}
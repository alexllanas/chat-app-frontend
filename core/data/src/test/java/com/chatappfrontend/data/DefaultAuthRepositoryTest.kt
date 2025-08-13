package com.chatappfrontend.data

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.data.repository.DefaultAuthRepository
import com.chatappfrontend.network.RemoteDataSource
import com.chatappfrontend.network.model.AuthenticationResponseDTO
import com.chatappfrontend.security.DataStoreManager
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

    private lateinit var network: RemoteDataSource
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
        val dummyAuthenticationResponseDTO = AuthenticationResponseDTO(
            id = "123",
            username = "testuser",
            email = "test@example.com",
            accessToken = "abc123",
            lastLogin = "2023-10-01T12:00:00Z"
        )

        coEvery {
            network.registerUser("testuser", "test@example.com", "password")
        } returns Response.success(dummyAuthenticationResponseDTO)

        val result = repository.registerUser("testuser", "test@example.com", "password")

        assertTrue(result is ResultWrapper.Success)
        val user = (result as ResultWrapper.Success).data
        assertEquals("testuser", user.username)

        coVerify {
            dataStoreManager.saveUserSession(
                accessToken = "abc123",
                userId = "123",
                username = "testuser",
                email = "test@example.com",
                lastLogin = "2023-10-01T12:00:00Z"
            )
        }
    }

    @Test
    fun `registerUser should return Error when response is not successful`() = runTest {
        val errorJson = """{"error":"Email exists"}"""
        val errorResponse = Response.error<AuthenticationResponseDTO>(
            400,
            errorJson.toResponseBody("application/json".toMediaTypeOrNull())
        )

        coEvery { network.registerUser(any(), any(), any()) } returns errorResponse

        val result = repository.registerUser("alex", "test@example.com", "password123")

        assertEquals("Email exists", (result as ResultWrapper.Failure).message)
    }

    @Test
    fun `registerUser should return Exception when network throws`() = runTest {
        coEvery {
            network.registerUser(any(), any(), any())
        } throws RuntimeException("Network failure")

        val result = repository.registerUser("testuser", "test@example.com", "password")

        assertTrue(result is ResultWrapper.Error)
        assertEquals("Network failure", (result as ResultWrapper.Error).exception.message)
    }

    @Test
    fun `login should return Success when response is successful`() = runTest {
        val dummyAuthenticationResponseDTO = AuthenticationResponseDTO(
            id = "456",
            username = "anotheruser",
            email = "another@example.com",
            accessToken = "token456",
            lastLogin = "2023-10-01T12:00:00Z"
        )

        coEvery {
            network.login("another@example.com", "pass")
        } returns Response.success(dummyAuthenticationResponseDTO)

        val result = repository.login("another@example.com", "pass")

        assertTrue(result is ResultWrapper.Success)
        assertEquals("anotheruser", (result as ResultWrapper.Success).data.username)
    }

    @Test
    fun `logout should call clearUserSession`() = runTest {
        coEvery { dataStoreManager.clearUserSession() } just Runs

        repository.logout()

        coVerify { dataStoreManager.clearUserSession() }
    }
}
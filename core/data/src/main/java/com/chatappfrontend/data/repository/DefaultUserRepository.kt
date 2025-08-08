package com.chatappfrontend.data.repository

import com.chatappfrontend.common.Resource
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.data.mapper.toUser
import com.chatappfrontend.data.mapper.toUserEntity
import com.chatappfrontend.domain.repository.UserRepository
import com.example.database.LocalDataSource
import com.example.network.RemoteDataSource
import com.example.network.utils.safeApiCall
import com.example.security.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import networkBoundResource
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val db: LocalDataSource,
    private val api: RemoteDataSource
) : UserRepository {

    override suspend fun getUsers(currentUserId: String) = networkBoundResource(
        query = {
            db.getUsers().map { list ->
                list.map { user ->
                    user.toUser()
                }
            }
        },
        fetch = {
            api.getUsers(currentUserId = currentUserId).body()
        },
        saveFetchResult = { data ->
            val users = data?.users?.map { it.toUserEntity() }?: listOf()
            db.insertUsers(users = users)
        },
        shouldFetch = {
//            it.isEmpty()
            true
        }
    )
}
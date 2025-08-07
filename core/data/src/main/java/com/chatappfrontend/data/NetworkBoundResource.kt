/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.chatappfrontend.domain.repository.Resource
import kotlinx.coroutines.flow.*

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 * Credit: <a href="https://stackoverflow.com/users/6401755/juan-cruz-soler">Juan Cruz Soler</a> and <a href="https://stackoverflow.com/users/2997980/n1hk">N1hk</a>
 * @see <a href="https://stackoverflow.com/questions/58486364/networkboundresource-with-kotlin-coroutines?rq=1">source</a>
 *
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType>
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}

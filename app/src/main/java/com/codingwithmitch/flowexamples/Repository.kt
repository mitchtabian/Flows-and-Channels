package com.codingwithmitch.flowexamples

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val NETWORK_DELAY = 1500L

@ExperimentalCoroutinesApi
class Repository {

    private val TAG: String = "AppDebug"

    fun getObject1(): Flow<DataState<String>> {

        return flow {
            emit(DataState.loading(true))

            delay(NETWORK_DELAY)

            val result = DataSource.getObject1()

            emit(DataState.data(result))
        }
    }

    fun getObject2(): Flow<DataState<String>> {

        return flow {
            emit(DataState.loading<String>(true))

            delay(NETWORK_DELAY)

            val result = DataSource.getObject2()

            emit(DataState.data(result))

        }
    }

    fun getObject3(): Flow<DataState<String>>{

        return flow {
            emit(DataState.loading<String>(true))

            delay(NETWORK_DELAY)

            val result = DataSource.getObject3()

            emit(DataState.data(result))

        }
    }
}
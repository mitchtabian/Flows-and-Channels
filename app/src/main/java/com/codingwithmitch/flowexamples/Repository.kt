package com.codingwithmitch.flowexamples

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

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

            DataState.data(result)

        }.catch{
            emit(DataState.error<String>("Error getting Object_TWO."))
        }
    }

    fun getObject3(): Flow<DataState<String>>{

        return flow {
            emit(DataState.loading<String>(true))

            delay(NETWORK_DELAY)

            DataSource.getObject3().map { result ->

                DataState.data(result)
            }
        }.catch{
            emit(DataState.error<String>("Error getting Object_THREE."))
        }
    }
}
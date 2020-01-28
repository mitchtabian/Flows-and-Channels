package com.codingwithmitch.flowexamples.repository

import com.codingwithmitch.flowexamples.util.DataState
import com.codingwithmitch.flowexamples.data.DataSource
import com.codingwithmitch.flowexamples.ui.state.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@ExperimentalCoroutinesApi
class Repository {

    fun getObject1(): Flow<DataState<ViewState>> {

        return flow {

            val result = DataSource.getObject1()

            emit(
                DataState.data(
                    data = ViewState(object1 = result)
                )
            )
        }
    }

    fun getObject2(): Flow<DataState<ViewState>> {

        return flow {

            val result = DataSource.getObject2()

            emit(
                DataState.data(
                    data = ViewState(object2 = result)
                )
            )

        }
    }

    fun getObject3(): Flow<DataState<ViewState>>{

        return flow {

            val result = DataSource.getObject3()

            emit(
                DataState.data(
                    data = ViewState(object3 = result)
                )
            )

        }
    }
}
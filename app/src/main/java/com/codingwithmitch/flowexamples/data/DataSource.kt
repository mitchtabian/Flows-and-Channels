package com.codingwithmitch.flowexamples.data

import kotlinx.coroutines.delay

/**
 * Fake data source
 * Pretend this is getting data from Room or network with Retrofit
 */
object DataSource {

    const val NETWORK_DELAY = 1500L

    suspend fun getObject1(): String{
        delay(NETWORK_DELAY)
        return "Object_ONE"
    }

    suspend fun getObject2(): String {
        delay(NETWORK_DELAY)
        return "Object_TWO"
    }

    suspend fun getObject3(): String{
        delay(NETWORK_DELAY)
        return "Object_THREE"
    }
}
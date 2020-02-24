package com.codingwithmitch.flowexamples.data

import android.accounts.NetworkErrorException
import kotlinx.coroutines.delay
import java.io.IOException
import java.lang.NullPointerException
import kotlin.random.Random

/**
 * Fake data source
 * Pretend this is getting data from Room or network with Retrofit
 */
object DataSource {

    const val NETWORK_DELAY = 1500L // for testing

    @Throws(IOException::class)
    suspend fun getObject1(): String {
        delay(NETWORK_DELAY)
        if(randomFalseGenerator()){
            throw IOException("I/O problem")
        }
        return "Object_ONE"
    }

    @Throws(NetworkErrorException::class)
    suspend fun getObject2(): String {
        delay(NETWORK_DELAY)
        if(randomFalseGenerator()){
            throw NetworkErrorException("Network has something wrong")
        }
        return "Object_TWO"
    }

    @Throws(NullPointerException::class)
    suspend fun getObject3(): String{
        delay(NETWORK_DELAY)
        if(randomFalseGenerator()){
            throw NullPointerException("Object3 is NULL")
        }
        return "Object_THREE"
    }

    // return true if random == 4
    // will be true 1/5 of the time (20%)
    // Therefore jobs will fail 20% of the time by throwing exception
    private fun randomFalseGenerator(): Boolean{
        val random = Random.nextInt(0,5)
        return (random == 4)
    }

}





















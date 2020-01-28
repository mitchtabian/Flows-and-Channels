package com.codingwithmitch.flowexamples


object DataSource {

    suspend fun getObject1(): String{
        return "Object_ONE"
    }

    suspend fun getObject2(): String {
        return "Object_TWO"
    }

    suspend fun getObject3(): String{
        return "Object_THREE"
    }
}
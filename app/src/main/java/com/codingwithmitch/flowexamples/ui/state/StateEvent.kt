package com.codingwithmitch.flowexamples.ui.state

sealed class StateEvent {

    class GetObject1: StateEvent(){

        override fun toString(): String {
            return "GetObject1"
        }
    }

    class GetObject2: StateEvent(){

        override fun toString(): String {
            return "GetObject2"
        }
    }

    class GetObject3: StateEvent(){

        override fun toString(): String {
            return "GetObject3"
        }
    }
}
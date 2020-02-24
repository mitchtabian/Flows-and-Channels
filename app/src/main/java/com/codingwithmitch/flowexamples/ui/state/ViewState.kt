package com.codingwithmitch.flowexamples.ui.state

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ViewState(

    var activeJobCounter: HashSet<String> = HashSet(),

    var object1: String? = null,

    var object2: String? = null,

    var object3: String? = null

) : Parcelable
{

    companion object{

        const val VIEW_STATE_BUNDLE_KEY = "com.codingwithmitch.flowexamples.ui.state.ViewState"
    }
}
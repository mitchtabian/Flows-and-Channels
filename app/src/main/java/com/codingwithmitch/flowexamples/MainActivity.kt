package com.codingwithmitch.flowexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingwithmitch.flowexamples.StateEvent.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val TAG: String = "AppDebug"

    private val viewModelFactory: MyViewModelFactory by lazy{
        MyViewModelFactory(Repository())
    }
    private val viewModel: MyViewModel by viewModels{
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()

        viewModel.setStateEvent(GetObject1())
    }

    private fun subscribeObservers(){

        viewModel.dataState.observe(this, Observer { dataState ->

            if(dataState != null){
                Log.d(TAG, "dataState: ${dataState}")

                displayProgressBar(dataState.loading.isLoading)
            }
        })
    }

    private fun displayProgressBar(isLoading: Boolean){
        if(isLoading){
            progress_bar.visibility = View.VISIBLE
        }
        else{
            progress_bar.visibility = View.INVISIBLE
        }
    }
}







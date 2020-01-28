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
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

@FlowPreview
@InternalCoroutinesApi
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


        get_object_1.setOnClickListener {
            get_object_1.text = ""
            viewModel.setStateEvent(GetObject1())
        }

        get_object_2.setOnClickListener {
            get_object_2.text = ""
            viewModel.setStateEvent(GetObject2())
        }

        get_object_3.setOnClickListener {
            get_object_3.text = ""
            viewModel.setStateEvent(GetObject3())
        }
    }

    private fun subscribeObservers(){

        CoroutineScope(Main).launch{
            viewModel.channel.asFlow().flowOn(Main).collect(object: FlowCollector<DataState<ViewState>>{

                override suspend fun emit(value: DataState<ViewState>) {
                    Log.d(TAG, "MainActivity: emit: ${value}")

                    displayProgressBar(value.loading.isLoading)

                    value.data?.getContentIfNotHandled()?.let { data ->
                        viewModel.handleNewData(data)
                    }
                }

            })
        }

        viewModel.viewState.observe(this, Observer { viewState ->
            if(viewState != null){

                viewState.object1?.let { object1 ->
                    get_object_1.text = object1
                }

                viewState.object2?.let { object2 ->
                    get_object_2.text = object2
                }

                viewState.object3?.let { object3 ->
                    get_object_3.text = object3
                }
            }
        })


        viewModel.dataState.observe(this, Observer { dataState ->

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







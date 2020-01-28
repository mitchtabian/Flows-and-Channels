package com.codingwithmitch.flowexamples.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.codingwithmitch.flowexamples.util.MyViewModelFactory
import com.codingwithmitch.flowexamples.R
import com.codingwithmitch.flowexamples.ui.state.StateEvent.*
import com.codingwithmitch.flowexamples.repository.Repository
import com.codingwithmitch.flowexamples.ui.state.ViewState
import com.codingwithmitch.flowexamples.ui.state.ViewState.Companion.VIEW_STATE_BUNDLE_KEY
import com.codingwithmitch.flowexamples.ui.viewmodel.MyViewModel
import com.codingwithmitch.flowexamples.ui.viewmodel.getCurrentViewStateOrNew
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

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
            getObject1()
        }

        get_object_2.setOnClickListener {
            getObject2()
        }

        get_object_3.setOnClickListener {
            getObject3()
        }

        restoreViewState(savedInstanceState)
    }

    private fun restoreViewState(savedInstanceState: Bundle?){
        savedInstanceState?.let { inState ->
            (inState[VIEW_STATE_BUNDLE_KEY] as ViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(VIEW_STATE_BUNDLE_KEY, viewModel.getCurrentViewStateOrNew())
        super.onSaveInstanceState(outState)
    }

    private fun subscribeObservers(){

        viewModel.activeRequestCounter.observe(this, Observer { activeJobCounter ->
            setRequestCounter(activeJobCounter)
            if(activeJobCounter > 0){
                displayProgressBar(isLoading = true)
            }
            else{
                displayProgressBar(isLoading = false)
            }
        })

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
    }

    private fun getObject1(){
        get_object_1.text = ""
        viewModel.setStateEvent(GetObject1())
    }

    private fun getObject2(){
        get_object_2.text = ""
        viewModel.setStateEvent(GetObject2())
    }
    private fun getObject3(){
        get_object_3.text = ""
        viewModel.setStateEvent(GetObject3())
    }

    private fun setRequestCounter(counter: Int){
        display_request_counter.text = counter.toString()
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









package com.example.myapplication

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel:ViewModel() {
    var count:String by mutableStateOf("111")

    val ss:LiveData<String> = MutableLiveData()
    private val _countState= MutableStateFlow<Int>(0)
    val countState:StateFlow<Int> =_countState
    fun incr(){
        _countState.value++
    }
    fun decr(){
        _countState.value--
    }

}
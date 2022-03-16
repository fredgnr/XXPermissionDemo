package com.example.myapplication

import androidx.lifecycle.ViewModel
import java.util.*

class SecondViewModel(var count: Int=0):ViewModel() {
    var time :Long = System.currentTimeMillis()%1000
}
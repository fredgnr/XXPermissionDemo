package com.example.myapplication

import androidx.compose.runtime.compositionLocalOf

data class User(val name:String,var id:Int)

val UserCompositionLocal = compositionLocalOf { User("first",1) }
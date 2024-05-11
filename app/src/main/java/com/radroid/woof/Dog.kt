package com.radroid.woof

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Dog(@StringRes val dogName:Int,
   val dogAge:String,
    @DrawableRes val dogImage:Int,
    @StringRes val hobbies:Int)

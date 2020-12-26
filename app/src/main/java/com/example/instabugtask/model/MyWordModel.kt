package com.example.instabugtask.model

data class MyWordModel (
    var name : String  ,
    var count : Int = 0
        ) {

    fun increaseCount() {
        this.count++
    }

}
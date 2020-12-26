package com.example.instabugtask.view

import com.example.instabugtask.model.MyWordModel
import java.util.HashMap

interface WordView {
    fun updateWordsList(wordsMap : HashMap<String?, Int?>)
    fun onFailure(msg: String?)
    fun showLoading()
    fun hideLoading()
}
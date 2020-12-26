package com.example.instabugtask.view

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instabugtask.R
import com.example.instabugtask.WordAdapter
import com.example.instabugtask.database.Database
import com.example.instabugtask.model.MyWordRepo
import com.example.instabugtask.presenter.WordPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.net.InetAddress
import java.util.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity()  , WordView{


    lateinit var  wordPresenter : WordPresenter
    lateinit var  wordAdapter : WordAdapter
    lateinit var database: Database

    companion object{
        val TAG : String = MainActivity::class.java.simpleName

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        words_list.setHasFixedSize(true)
        database = Database(this, 1)
        val myLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        words_list.layoutManager = myLayoutManager
        wordPresenter = WordPresenter(Executors.newSingleThreadExecutor(), this, MyWordRepo())

        val connectivity = this.getSystemService(Service.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val info = connectivity!!.activeNetworkInfo

        if (info != null) {
            if (info!!.state == NetworkInfo.State.CONNECTED) {
                wordPresenter.getHtmlResponseFromUrl("https://instabug.com")
                Log.d(TAG, "CONNECTED")
            }
        } else {
            hideLoading()
            updateWordsList(database.all)
            Log.d(TAG, "NOT CONNECTED")
        }

    }

    override fun updateWordsList(list: HashMap<String?, Int?>) {
        runOnUiThread {
            val wordList: List<String> = ArrayList<String>(list.keys)
            val wordCountList: List<Int> = ArrayList<Int>(list.values)
            wordAdapter = WordAdapter(applicationContext, wordList, wordCountList)
            words_list.adapter = wordAdapter
            database.addAll(list)
        }

    }

    @SuppressLint("ShowToast")
    override fun onFailure(msg: String?) {
        runOnUiThread {
            Log.e(TAG, msg!!)
            Toast.makeText(this, msg, Toast.LENGTH_LONG)
        }
    }

    override fun showLoading() {
        runOnUiThread {
            loading.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        runOnUiThread {
            loading.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        wordPresenter.destroy()
    }
}
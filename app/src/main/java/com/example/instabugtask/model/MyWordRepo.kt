package com.example.instabugtask.model
import com.example.instabugtask.Result
import java.io.IOException
import java.net.URL


class MyWordRepo (){
    fun getFullResponse(url: String): Result<String>  {
        return try {
            val result = URL(url).readText()
            Result.Success(result)
        } catch (exception: IOException) {
            Result.Failure(exception.toString(), exception)
        }

    }

}
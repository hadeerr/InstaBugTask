package com.example.instabugtask.presenter

import com.example.instabugtask.Result
import com.example.instabugtask.model.MyWordRepo
import com.example.instabugtask.view.WordView
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService


class WordPresenter(
    val executor: Executor,
    val wordView: WordView,
    val wordRepo: MyWordRepo
)
{


    fun getHtmlResponseFromUrl(url: String) {
        wordView.showLoading()

        val worker = Runnable {



            when (val result = wordRepo.getFullResponse(url)) {
                is Result.Success -> {
                    val wordsWithoutTags = RemoveHtmlTags.removeTags(result.value)
                    var wordsMap = HashMap<String?, Int?>()
                    wordsMap = convertWordToMap(wordsWithoutTags)
                    wordView.updateWordsList(wordsMap)
                }

                is Result.Failure -> {
                    wordView.onFailure(result.throwable.message)
                }
            }

            wordView.hideLoading()
        }
        executor.execute(worker)
    }

    private fun convertWordToMap(wordsWithoutTags: String?): HashMap<String?, Int?> {
        val myMap = HashMap<String?, Int?>()
        val wordList: Array<String> = wordsWithoutTags?.split(" ")!!.toTypedArray()
        for (word in wordList) {
            if (word.trim { it <= ' ' }.isEmpty()) continue
            if (myMap[word] == null) {
                myMap[word] = 1
            } else myMap[word] = myMap[word]!!+1
        }
        return myMap
    }


    fun destroy() {
        if(executor is ExecutorService){
            executor.shutdown()
        }
    }
}
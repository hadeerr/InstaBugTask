package com.example.instabugtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter : RecyclerView.Adapter<WordAdapter.WordsViewHolder> {

    private  var context: Context
    private  var wordList: List<String?>
    private  var countList: List<Int?>

    constructor(context: Context, wordList: List<String?>, countList: List<Int?>) : super() {
        this.context = context
        this.wordList = wordList
        this.countList = countList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return WordsViewHolder(v)
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.wordItem.text = wordList!![position]
        holder.wordCount.text = countList!![position].toString()
    }

    override fun getItemCount(): Int {
        return wordList!!.size
    }

    class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItem : TextView = itemView.findViewById(R.id.word_item)
        val wordCount : TextView = itemView.findViewById(R.id.word_count)

    }

}

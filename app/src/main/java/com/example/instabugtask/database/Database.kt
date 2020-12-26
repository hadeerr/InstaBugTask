package com.example.instabugtask.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.util.*


class Database(private val context: Context, version: Int) :
    SQLiteOpenHelper(context, DataBase_Name, null, version) {
    private val TAG = "sql db "
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "Create Table "
                    + TableName + " ( "
                    + ColumnOne + " String Primary Key unique ,"
                    + ColumnTwo + " int )"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("Drop Table if exists $TableName")
        onCreate(db)
    }

    private fun add(word: String, count: Int) {
        try {
            val dp = writableDatabase
            val value = ContentValues()
            value.put(ColumnOne, word)
            value.put(ColumnTwo, count)
            dp.insert(TableName, null, value)
            dp.close()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            Log.e(TAG, (e.message)!!)
        }
    }


    fun addAll(map: HashMap<String?, Int?>) {
        try {
            //todo add thread
            Log.d(TAG, "Add All")
            val db = this.writableDatabase
            db.execSQL("DROP TABLE IF EXISTS $TableName")
            onCreate(db)
            val it: Iterator<*> = map.entries.iterator()
            while (it.hasNext()) {
                val pair = it.next() as Map.Entry<*, *>
                add(pair.key.toString(), pair.value.toString().toInt())
            }
//            DatabaseBackup.deleteBackup(context)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            Log.e(TAG, (e.message)!!)
        }
    }

    val all: HashMap<String?, Int?>
        get() {
            val map = HashMap<String?, Int?>()
            try {
                Log.d(TAG, "get All")
                val db = readableDatabase
                val cursor = db.rawQuery("select * from $TableName", null)
                cursor.moveToFirst()
                do {
                    val word = cursor.getString(0)
                    val count = cursor.getInt(1)
                    map[word] = count
                } while (cursor.moveToNext())
                db.close()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                Log.e(TAG, (e.message)!!)
            }
            return map
        }

    companion object {
        private const val TableName = "table_words"
        private const val ColumnOne = "word"
        private const val ColumnTwo = "count"
        private const val DataBase_Name = "insta_words.dp"
    }
}

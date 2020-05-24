package com.example.notebook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Timestamp
import java.text.SimpleDateFormat


//entity class must declared as data class
//define table name here
@Entity(tableName = "notebook_table")
data class Notebook(
    @PrimaryKey(autoGenerate = true)
    var notebookId: Long = 0L,
    @ColumnInfo(name = "content")
    var content: String = "",

    //SQLite can note store object ex.Timestamp, has to be changed to string
    @ColumnInfo(name = "timestamp")
    var timeStamp: String = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Timestamp(System.currentTimeMillis()))
) : Serializable

//serializable to pass between activity
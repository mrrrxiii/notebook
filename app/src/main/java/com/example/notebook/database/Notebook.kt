package com.example.notebook.database

import java.sql.Timestamp


//entity class must declared as data class


data class Notebook(
    val notebookId: Long=0L,

    var content: String="",

    var timeStamp: Timestamp= Timestamp(System.currentTimeMillis())
)
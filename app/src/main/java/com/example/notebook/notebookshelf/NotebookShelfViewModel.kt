package com.example.notebook.notebookshelf

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notebook.database.Notebook
import com.example.notebook.database.NotebookDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotebookShelfViewModel(
    var database: NotebookDatabaseDao,
    application: Application) : AndroidViewModel(application) {



    var viewModelJob= Job()
    var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


   var notebookList=database.getAllNights()


    init {
        uiScope.launch {
            for (i in 0..5){
                database.insert(Notebook(content = "this is $i"))
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
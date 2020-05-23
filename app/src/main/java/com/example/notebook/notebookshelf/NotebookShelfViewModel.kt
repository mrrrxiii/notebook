package com.example.notebook.notebookshelf

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notebook.database.Notebook
import com.example.notebook.database.NotebookDatabaseDao
import kotlinx.coroutines.*

class NotebookShelfViewModel(
    var database: NotebookDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    var viewModelJob= Job()
   var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


   var notebookList=database.getAllNights()


    private val _navigateToNotebookDetails=MutableLiveData<Notebook>()
    val navigateToNotebookDetails:LiveData<Notebook>
        get() = _navigateToNotebookDetails
    fun doneNavigating(){
        _navigateToNotebookDetails.value=null
    }


    fun onNewNotebook(){
        uiScope.launch {
            val newNotebook=Notebook()
            addNotebook(newNotebook)

            _navigateToNotebookDetails.value=getCurrent()

        }
    }



    private suspend fun getCurrent(): Notebook? {
        return withContext(Dispatchers.IO){
            database.getlateast()
        }
    }

    private suspend fun addNotebook(notebook: Notebook) {
        withContext(Dispatchers.IO){
            database.insert(notebook)
        }

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}
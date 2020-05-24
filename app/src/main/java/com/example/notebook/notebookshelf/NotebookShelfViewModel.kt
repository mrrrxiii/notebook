package com.example.notebook.notebookshelf

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notebook.database.Notebook
import com.example.notebook.database.NotebookDatabaseDao
import kotlinx.coroutines.*
//viewmodel should do all data processing and decision making
class NotebookShelfViewModel(
    var database: NotebookDatabaseDao,
    application: Application) : AndroidViewModel(application) {
//instantiate coroutine, a lightweight multithreads library
    var viewModelJob= Job()
   var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


   var notebookList=database.getAllNights()

//make a variable to be observed ,when the value is assigned, to trigger navigation
    private val _navigateToNotebookDetails=MutableLiveData<Notebook>()
    val navigateToNotebookDetails:LiveData<Notebook>
        get() = _navigateToNotebookDetails
    //reset
    fun doneNavigating(){
        _navigateToNotebookDetails.value=null
    }

//create new notebook and trigger the navigation
    fun onNewNotebook(){
        uiScope.launch {
            val newNotebook=Notebook()
            addNotebook(newNotebook)
//Livedata could not be run off main thread, so only getcurrent() is dispatched
            //get the lastest notebook from database according to id
            //use this assign variable as args to pass with navigation
            _navigateToNotebookDetails.value=getCurrent()

        }
    }


//the operation on database should be off main thread,otherwise the waiting time from database
    //is unpredictable
    //suspend keyword make the function could be paused
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

//close thread when  viewmodel is destroyed
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}
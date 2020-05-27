package com.example.notebook.notebookdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notebook.database.Notebook
import com.example.notebook.database.NotebookDatabaseDao
import kotlinx.coroutines.*

class NotebookDetailsViewModel(
    private val notebookId:Long,
    val database:NotebookDatabaseDao,
    application: Application
): AndroidViewModel(application) {

    private val viewModelJob= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)

    private var _current=MutableLiveData<Notebook>()
    val current: LiveData<Notebook>
        get() = _current

    init {
        getCurrent()
    }

    private fun getCurrent() {
        uiScope.launch {

            _current.value=getNotebook()
        }
    }

    private suspend fun getNotebook(): Notebook? {
            return withContext(Dispatchers.IO){
                database.get(notebookId)
            }
    }

    private val _navigatetoNotebookShelf=MutableLiveData<Boolean?>()

    val navigatetoNotebookShelf:LiveData<Boolean?>
        get() = _navigatetoNotebookShelf
    fun doneNavigating(){
        _navigatetoNotebookShelf.value=null
    }

    private val _navigateDel=MutableLiveData<Boolean?>()

    val navigateDel:LiveData<Boolean?>
        get() = _navigateDel
    fun doneDelete(){
        _navigateDel.value=null
    }




    fun onSaveNotebook(note:String){
        uiScope.launch {

               val old=_current.value as Notebook
                old.content=note
            //database operation must be off main thread
                withContext(Dispatchers.IO){
                database.update(old)
                }


        //this should be noticed, livedata could not be run off main thread
            _navigatetoNotebookShelf.value=true
        }


    }

    fun onDeleteNotebook(){
        uiScope.launch {
            val old=_current.value as Notebook
            //database operation must be off main thread
            withContext(Dispatchers.IO){
                database.delete(old.notebookId)
            }
            _navigateDel.value=true
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
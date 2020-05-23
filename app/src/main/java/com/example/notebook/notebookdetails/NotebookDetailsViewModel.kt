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




    fun onSaveNotebook(note:String){
        uiScope.launch {

               val old=_current.value as Notebook
                old.content=note
                withContext(Dispatchers.IO){
                database.update(old)
                }



            _navigatetoNotebookShelf.value=true
        }


    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
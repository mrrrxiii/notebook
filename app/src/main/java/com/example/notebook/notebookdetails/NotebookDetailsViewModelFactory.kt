package com.example.notebook.notebookdetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notebook.database.NotebookDatabaseDao

class NotebookDetailsViewModelFactory(
    private val notebookId:Long,
    private val dataSource: NotebookDatabaseDao,

    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotebookDetailsViewModel::class.java)) {
            return NotebookDetailsViewModel(notebookId,dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
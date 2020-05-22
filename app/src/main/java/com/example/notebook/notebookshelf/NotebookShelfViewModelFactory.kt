package com.example.notebook.notebookshelf

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notebook.database.NotebookDatabaseDao

class NotebookShelfViewModelFactory  (
    private val dataSource: NotebookDatabaseDao,
private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotebookShelfViewModel::class.java)) {
            return NotebookShelfViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
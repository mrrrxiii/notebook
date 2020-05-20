package com.example.notebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.database.Notebook
import com.example.notebook.notebookshelf.NotebookAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var recyclerView = this.findViewById<RecyclerView>(R.id.recycler_view)

        var notebookList = mutableListOf<Notebook>()

        for (i in 0..20) {
            var notebook = Notebook(notebookId = i.toLong(), content = "${i * i * i}")
            notebookList.add(notebook)
        }



        recyclerView.adapter=NotebookAdapter(notebook = notebookList)


    }
}
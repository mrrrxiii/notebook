package com.example.notebook.notebookshelf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.R
import com.example.notebook.database.Notebook

class NotebookAdapter(
    var notebook : MutableList<Notebook>
): RecyclerView.Adapter<NotebookAdapter.MyViewHolder>() {

    ///Specify the details inside unit layout in recyclerview,
    //the viewholder is like the unit layout itself
    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var title: TextView =itemView.findViewById<TextView>(R.id.unit_notebook_title)
        var timeStamp: TextView =itemView.findViewById<TextView>(R.id.unit_notebook_time)
    }

    //have the adapter binding the unit layout to the parent via customized MyViewHolder
    //parameter in the MyviewHolder is the unit layout
    //and the details of unit layout are specified in the inner class MyViewHolder
    //That is why inside class MyViewHolder, each element should be binded with R.id
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var unitView=LayoutInflater.from(parent.context)
            .inflate(R.layout.unit_notebook_listview, parent, false)

        return MyViewHolder(unitView)
    }

    override fun getItemCount(): Int {
        return notebook.size
    }

    //binding the data to the details of the unit layout
    //put specified data into viewholder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text=notebook[position].content
        holder.timeStamp.text=notebook[position].timeStamp.toString()
    }


}
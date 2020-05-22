package com.example.notebook.notebookshelf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.R
import com.example.notebook.database.Notebook

class NotebookShelfAdapter(): RecyclerView.Adapter<NotebookShelfAdapter.MyViewHolder>() {
        var data = listOf<Notebook>()
            set(value) {
                field=value
                notifyDataSetChanged()
            }


    //Specify the element in the viewholder,the itemview is the row laytou itself
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content:TextView = itemView.findViewById<TextView>(R.id.unit_notebook_title)
        var time:TextView = itemView.findViewById<TextView>(R.id.unit_notebook_time)
        var row=itemView
    }

    //inflate the viewholder into the layout file which is the row layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.unit_notebook_listview, parent, false)
        return MyViewHolder(view)
    }
    //how many items to show
    override fun getItemCount(): Int {
       return data.size
    }


    //binding data, what to show on each element
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.content.text=data[position].content
        holder.time.text=data[position].timeStamp.toString()
        holder.row.setOnClickListener {
            it.findNavController().navigate(NotebookShelfFragmentDirections.actionNotebookShelfFragmentToNotebookDetailsFragment(data[position]))
        }
    }


}
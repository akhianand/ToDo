package com.akhilesh.todo
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.todo_listitem.view.*


class ToDoAdapter (val items : ArrayList<ToDoDataEntry>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_listitem, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position : Int) {
        holder?.headline?.text = items.get(position).headline
        holder?.deadline?.text = items.get(position).deadline
        holder?.des?.text = items.get(position).descriptions

        holder?.itemView.setOnClickListener{_ ->
            val intent = Intent(context, ToDoFragment::class.java)
            intent.putExtra("id", items.get(position).ids)
            intent.putExtra("headline", items.get(position).headline)
            intent.putExtra("deadline", items.get(position).deadline)
            intent.putExtra("description", items.get(position).descriptions)

            context.startActivity(intent)

        }


    }


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }


}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val headline = view.txtHeadline
    val des = view.txtDescription
    val deadline = view.txtDeadline
    val priority = view.txtPriority


}
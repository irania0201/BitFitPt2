package com.example.bitfit_pt1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryAdapter(private val mEntries: List<DisplayEntry>) : RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val entryTitle: TextView = itemView.findViewById(R.id.EntryTitleText)
        val entryDate: TextView = itemView.findViewById(R.id.EntryDateText)
        val entryEntry: TextView = itemView.findViewById(R.id.EntryText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val itemView = inflater.inflate(R.layout.entry_detail, parent, false)
        // Return a new holder instance
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = mEntries[position]
        holder.entryTitle.text = entry.title.toString()
        holder.entryDate.text = entry.date.toString().substring(0, entry.date.toString().indexOf("T"))
        holder.entryEntry.text = entry.entry
    }

    override fun getItemCount(): Int {
        return mEntries.size
    }


}
package com.example.bitfit_pt1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class Entry : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entry, container, false)
        val entryRV: RecyclerView = view.findViewById(R.id.entryRV)
        val entryList: MutableList<DisplayEntry> = listOf<DisplayEntry>().toMutableList()
        val entryAdapter = EntryAdapter(entryList)
        entryRV.adapter = entryAdapter
        entryRV.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            (activity?.application as Application).db.entryDao().getAllByDateDesc().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayEntry(
                        entity.id,
                        entity.title,
                        entity.date,
                        entity.entry
                    )
                }.also { mappedList ->
                    entryList.clear()
                    entryList.addAll(mappedList)
                    entryAdapter.notifyDataSetChanged()
                }
            }
        }
        return view
    }

    companion object {
        fun newInstance(): Entry{
            return Entry()
        }
    }
}
package com.example.bitfit_pt1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

class Navigation : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_navigation, container, false)

        val avgText: TextView = view.findViewById(R.id.avgText)
        val minText: TextView = view.findViewById(R.id.minText)
        val maxText: TextView = view.findViewById(R.id.maxText)
        val entryList: MutableList<DisplayEntry> = listOf<DisplayEntry>().toMutableList()

        var total = 0.0
        var count = 0.0
        var min = 0
        var max = 0

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
                }
                if(entryList.size > 0) {
                    val words = entryList[0].entry?.split(" ")
                    if (words != null) {
                        min = words.size
                        max = words.size
                    }
                }

                entryList.forEach { displayDiary: DisplayEntry ->
                    count++
                    val words = displayDiary.entry?.split(" ")
                    if(words != null) {
                        total += words.size
                        min = min(min, words.size)
                        max = max(max, words.size)
                    }
                }
                ("Average words per entry: " + (total/count).toInt()).also { avgText.text = it }
                ("Minimum words per entry: $min").also { minText.text = it }
                ("Maximum words per entry: $max").also { maxText.text = it }
            }
        }


        return view
    }

    companion object {
        fun newInstance(): Navigation{
            return Navigation()
        }
    }
}
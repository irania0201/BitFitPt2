package com.example.bitfit_pt1

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.time.Instant

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun closeKeyboard() {
            val view = this.currentFocus
            if(view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }

        var entryTitleEmpty = true
        var entryEntryEmpty = true
        val entryTitleEntry: TextView = findViewById(R.id.TitleEntry)
        val entryEntry: TextView = findViewById(R.id.Entry)
        val addButton: Button = findViewById(R.id.addButton)
        val entryRV: RecyclerView = findViewById(R.id.EntryRV)
        val entryList: MutableList<DisplayEntry> = listOf<DisplayEntry>().toMutableList()
        val entryAdapter = EntryAdapter(entryList)
        entryRV.adapter = entryAdapter
        entryRV.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            (application as Application).db.entryDao().getAllByDateDesc().collect { databaseList ->
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


        entryTitleEntry.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                entryTitleEmpty = count <= 0
                addButton.isEnabled = (!entryTitleEmpty && !entryEntryEmpty)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        entryEntry.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                entryEntryEmpty = count <= 0
                addButton.isEnabled = (!entryTitleEmpty && !entryEntryEmpty)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        addButton.setOnClickListener {
            lifecycleScope.launch(IO) {
                Log.v("database", entryTitleEntry.text.toString())
                Log.v("database", entryEntry.text.toString())
                (application as Application).db.entryDao().insert(EntryEntity(
                    entryTitleEntry.text.toString(),
                    Instant.now(),
                    entryEntry.text.toString()
                ))
            }
            closeKeyboard()
            ///wait for insert to complete
            Thread.sleep(500)
            entryTitleEntry.text = ""
            entryEntry.text = ""
        }
    }
}
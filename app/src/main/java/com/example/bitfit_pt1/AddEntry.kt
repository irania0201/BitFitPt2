package com.example.bitfit_pt1

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.time.Instant

class AddEntry : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_entry_activity)

        fun closeKeyboard() {
            val view = this.currentFocus
            if(view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }

        var diaryTitleEmpty: Boolean
        var diaryEntryEmpty: Boolean
        val diaryTitleEntry: TextView = findViewById(R.id.DiaryTitleEntry)
        val diaryEntry: TextView = findViewById(R.id.DiaryEntry)
        val saveButton: Button = findViewById(R.id.saveButton)

        class InputTextWatcher: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                diaryTitleEmpty = diaryTitleEntry.text.toString().isEmpty()
                diaryEntryEmpty = diaryEntry.text.toString().isEmpty()
                saveButton.isEnabled = !diaryTitleEmpty && !diaryEntryEmpty
            }

        }

        diaryTitleEntry.addTextChangedListener(InputTextWatcher())
        diaryEntry.addTextChangedListener(InputTextWatcher())

        saveButton.setOnClickListener {
            lifecycleScope.launch(IO) {
                (application as Application).db.entryDao().insert(EntryEntity(
                    diaryTitleEntry.text.toString(),
                    Instant.now(),
                    diaryEntry.text.toString()
                ))
            }
            closeKeyboard()
            ///wait for insert to complete
            Thread.sleep(500)
            diaryTitleEntry.text = ""
            diaryEntry.text = ""
            this.finish()
        }
    }
}
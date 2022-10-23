package com.example.bitfit_pt1


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var addEntry: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // define your fragments here
        val entryFragment: Fragment = Entry()
        val navigationFragment: Fragment = Navigation()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        addEntry = findViewById(R.id.addButton)
        addEntry.setOnClickListener {
            val context = it.context
            val intent = Intent(context, AddEntry::class.java)
            context.startActivity(intent)
        }

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.entries -> fragment = entryFragment
                R.id.navigation -> fragment = navigationFragment
            }
            replaceFragment(fragment)
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.entries

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.logs_frame_layout, fragment).commit()
    }

}
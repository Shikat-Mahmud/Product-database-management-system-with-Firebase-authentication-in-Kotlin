package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Default fragment to display
        if (savedInstanceState == null) {
            openDashboardFragment()
        }

        val addBtn2 = findViewById<FloatingActionButton>(R.id.searchBtn)

        addBtn2.setOnClickListener {
            val intent = Intent(this, search::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Logic to handle Home menu item
                    openDashboardFragment()
                    true
                }
                R.id.addItem -> {
                    // Logic to handle Add Item menu item
                    openAddItemFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun openAddItemFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace FragmentAddItem() with your actual fragment instance
        val fragment = AddItem()

        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }

    private fun openDashboardFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace FragmentAddItem() with your actual fragment instance
        val fragment = Dashboard()

        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
}

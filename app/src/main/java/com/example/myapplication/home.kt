package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class home : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawerLayout)

        val drawerNavView = findViewById<NavigationView>(R.id.drawerNavigationView)
        drawerNavView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open_nav,R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,Dashboard()).commit()
            drawerNavView.setCheckedItem(R.id.nav_home)

            openDashboardFragment()
        }

        val searchButton = findViewById<FloatingActionButton>(R.id.searchBtn)

        searchButton.setOnClickListener {
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,Dashboard()).commit()
            R.id.nav_addItem -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,AddItem()).commit()
            R.id.nav_searchItem -> {
                val intent = Intent(this, search::class.java)
                startActivity(intent)
            }
            R.id.nav_info -> {
                val intent = Intent(this, info::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
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

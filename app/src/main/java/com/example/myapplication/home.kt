package com.example.myapplication

import adapters.ItemModelAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<RecyclerView>(R.id.itemRecyclerView)
        val firebaseHelper = FirebaseHelper()
        val adapter = ItemModelAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(this) // Set the layout manager

        recyclerView.adapter = adapter

        firebaseHelper.fetchItemData { itemList ->
            adapter.updateData(itemList)
        }

        val addBtn2 = findViewById<FloatingActionButton>(R.id.addBtn2)

        addBtn2.setOnClickListener {
            val addItem = Intent(this, add_item::class.java)
            startActivity(addItem)
        }

    }


}

package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val addBtn = findViewById<Button>(R.id.addBtn)
        val addBtn2 = findViewById<FloatingActionButton>(R.id.addBtn2)

        addBtn.setOnClickListener{
            val addItem = Intent(this,add_item::class.java)

            startActivity(addItem)
        }

        addBtn2.setOnClickListener{
            val addItem = Intent(this,add_item::class.java)

            startActivity(addItem)
        }

    }
}
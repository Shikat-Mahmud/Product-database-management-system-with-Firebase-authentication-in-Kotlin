package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val addBtn = findViewById<Button>(R.id.addBtn)

        addBtn.setOnClickListener{
            val addItem = Intent(this,add_item::class.java)

            startActivity(addItem)
        }

    }
}
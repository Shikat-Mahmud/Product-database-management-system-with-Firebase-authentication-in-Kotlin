package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class add_item : AppCompatActivity() {

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val dbReference = databaseReference.child("All Spare Parts") // Replace with your desired path

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        // Assuming you have EditText fields for input
        // Replace these with your actual EditText fields

        val sNo = findViewById<EditText>(R.id.sNo)
        val item = findViewById<EditText>(R.id.item)
        val currentLocation = findViewById<EditText>(R.id.currentLocation)
        val newLocation = findViewById<EditText>(R.id.newLocation)
        val status = findViewById<EditText>(R.id.status)
        val unit = findViewById<EditText>(R.id.unit)

        // Button to trigger data insertion
        val addBtn = findViewById<Button>(R.id.addBtn)
        addBtn.setOnClickListener {

            val sNo1 = sNo.text.toString()
            val item1 = item.text.toString()
            val currentLocation1 = currentLocation.text.toString()
            val newLocation1 = newLocation.text.toString()
            val status1 = status.text.toString()
            val unit1 = unit.text.toString()

            if(sNo1.isEmpty()){
                Toast.makeText(this,"Please enter the serial no",Toast.LENGTH_SHORT).show()
            }
            else{
                if(item1.isEmpty()){
                    Toast.makeText(this,"Please enter the item name",Toast.LENGTH_SHORT).show()
                }
                else{
                    val item = ItemModel(
                        sNo1,
                        item1,
                        currentLocation1,
                        newLocation1,
                        status1,
                        unit1
                    )
                    insertData(item)
                }
            }
        }
    }

    private fun insertData(data: ItemModel) {
        val newDataReference = dbReference.push()
        newDataReference.setValue(data)
            .addOnSuccessListener {
                // Data inserted successfully
                Toast.makeText(this,"Item inserted successfully",Toast.LENGTH_SHORT).show()
                // You can add any additional handling here
            }
            .addOnFailureListener {err->
                // Handle the error
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()
            }
    }
}
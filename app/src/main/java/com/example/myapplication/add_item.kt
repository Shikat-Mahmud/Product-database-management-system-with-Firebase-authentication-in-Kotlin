package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class add_item : AppCompatActivity() {

    private lateinit var sNo:EditText
    private lateinit var item:EditText
    private lateinit var currentLocation:EditText
    private lateinit var newLocation:EditText
    private lateinit var status:EditText
    private lateinit var unit:EditText
    private lateinit var addBtn:Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        sNo = findViewById(R.id.sNo)
        item = findViewById(R.id.item)
        currentLocation = findViewById(R.id.currentLocation)
        newLocation = findViewById(R.id.newLocation)
        status = findViewById(R.id.status)
        unit = findViewById(R.id.unit)
        addBtn = findViewById(R.id.addBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("All Spare Parts")

        addBtn.setOnClickListener{

            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
            insertItem()

        }



//        val usersRef = rootRef.child("users")
//        val specificUserRef = usersRef.child("user1234")


    }

    private fun insertItem()
    {
        // Getting all fields value
        val sNo1 = sNo.text.toString()
        val item1 = item.text.toString()
        val currentLocation1 = currentLocation.text.toString()
        val newLocation1 = newLocation.text.toString()
        val status1 = status.text.toString()
        val unit1 = unit.text.toString()


        if(sNo1.isEmpty()){
            sNo.error = "Please enter the item number"
        }
        if(item1.isEmpty()){
            item.error = "Please enter the item name"
        }

        val id = dbRef.push().key!!

        val item = ItemModel(id, sNo1, item1, currentLocation1, newLocation1, status1, unit1)

        dbRef.child(id).setValue(item)
            .addOnCompleteListener{
                Toast.makeText(this,"Item inserted successfully",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{err->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()
            }

    }
}
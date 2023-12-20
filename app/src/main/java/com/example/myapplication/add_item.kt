package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class add_item : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val rootRef = FirebaseDatabase.getInstance().reference

//        val usersRef = rootRef.child("users")
//        val specificUserRef = usersRef.child("user1234")


    }
}
package com.example.myapplication

import ItemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseHelper {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("All Spare Parts")

    fun fetchItemData(callback: (List<ItemModel>) -> Unit) {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val itemList = dataSnapshot.children.mapNotNull { snapshot ->
                    snapshot.getValue(ItemModel::class.java)
                }
                callback(itemList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors if any
            }
        })
    }

}

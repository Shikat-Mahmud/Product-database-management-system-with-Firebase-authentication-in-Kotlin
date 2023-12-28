package com.example.myapplication

import ItemModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase

class AddItem : Fragment() {
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val dbReference =
        databaseReference.child("All Spare Parts") // Replace with your desired path

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)

        // Assuming you have EditText fields for input
        // Replace these with your actual EditText fields
        val sNo = view.findViewById<EditText>(R.id.sNo)
        val item = view.findViewById<EditText>(R.id.item)
        val currentLocation = view.findViewById<EditText>(R.id.currentLocation)
        val newLocation = view.findViewById<EditText>(R.id.newLocation)
        val status = view.findViewById<EditText>(R.id.status)
        val unit = view.findViewById<EditText>(R.id.unit)

        // Assume you have a button in your layout with ID "addCsv"
        val addCsv = view.findViewById<Button>(R.id.addCsv)
        addCsv.setOnClickListener {
            // Instantiate CSVDataUploader with the current context and database reference
            val csvUploader = CSVDataUploader(requireContext(), databaseReference)

            // Upload and insert data from the CSV file
            csvUploader.uploadCSVData("AllSpareParts.csv")

            Toast.makeText(
                requireContext(),
                "Your CSV data inserted to the database successfully",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Button to trigger data insertion
        val addBtn = view.findViewById<Button>(R.id.addBtn)
        addBtn.setOnClickListener {

            val sNo1 = sNo.text.toString()
            val item1 = item.text.toString()
            val currentLocation1 = currentLocation.text.toString()
            val newLocation1 = newLocation.text.toString()
            val status1 = status.text.toString()
            val unit1 = unit.text.toString()

            if (sNo1.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter the serial no", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (item1.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please enter the item name",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val item = ItemModel(
                        sNo1,
                        item1,
                        currentLocation1,
                        newLocation1,
                        status1,
                        unit1
                    )
                    insertInputData(item)
                }
            }
        }

        return view
    }

    private fun insertInputData(data: ItemModel) {
        val newDataReference = dbReference.push()
        newDataReference.setValue(data)
            .addOnSuccessListener {
                // Data inserted successfully
                Toast.makeText(requireContext(), "Item inserted successfully", Toast.LENGTH_SHORT)
                    .show()
                // You can add any additional handling here
            }
            .addOnFailureListener { err ->
                // Handle the error
                Toast.makeText(requireContext(), "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
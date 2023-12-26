package com.example.myapplication

import ItemModel
import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.opencsv.CSVReader
import java.io.InputStreamReader

class CSVDataUploader(private val context: Context, private val databaseReference: DatabaseReference) {

    private val dbReference = databaseReference.child("All Spare Parts")

    fun uploadCSVData(csvFileName: String) {
        try {
            val inputStream = context.assets.open(csvFileName)
            val reader = CSVReader(InputStreamReader(inputStream))
            var record: Array<String>?

            // Skip the header row if needed
            reader.readNext()

            while (reader.readNext().also { record = it } != null) {
                if (record != null && record!!.size >= 6) {
                    val item = ItemModel(
                        record!![0] ?: "",
                        record!![1] ?: "",
                        record!![2] ?: "",
                        record!![3] ?: "",
                        record!![4] ?: "",
                        record!![5] ?: ""
                    )

                    insertData(item)
                }
            }
            reader.close()
            inputStream.close()
        } catch (e: Exception) {
            // Handle exceptions, e.g., file not found or parsing error
            e.printStackTrace()
        }
    }

    private fun insertData(data: ItemModel) {
        val newDataReference = dbReference.push()
        newDataReference.setValue(data)
            .addOnSuccessListener {
                // Data inserted successfully
                // You can add any additional handling here
            }
            .addOnFailureListener { err ->
                // Handle the error
                err.printStackTrace()
            }
    }
}
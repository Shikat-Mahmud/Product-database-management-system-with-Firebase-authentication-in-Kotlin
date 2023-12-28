package com.example.myapplication

import ItemModel
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class search : AppCompatActivity() {

    private lateinit var searchBox: EditText
    private lateinit var searchButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var firebaseHelper: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        searchBox = findViewById(R.id.searchBox)
        searchButton = findViewById(R.id.searchBtn)
        resultTextView = findViewById(R.id.resultTextView)
        firebaseHelper = FirebaseHelper()

        searchButton.setOnClickListener {
            val searchQuery = searchBox.text.toString()

            Log.d("SearchActivity", "Search Query: $searchQuery")

            firebaseHelper.fetchItemData { itemList ->
                Log.d("SearchActivity", "Item List Size: ${itemList.size}")

                val searchResults = itemList.filter { item ->
                    item.sNo?.contains(searchQuery, ignoreCase = true) == true ||
                            item.item?.contains(searchQuery, ignoreCase = true) == true ||
                            item.currentLocation?.contains(searchQuery, ignoreCase = true) == true ||
                            item.newLocation?.contains(searchQuery, ignoreCase = true) == true ||
                            item.status?.contains(searchQuery, ignoreCase = true) == true ||
                            item.unit?.contains(searchQuery, ignoreCase = true) == true
                }

                Log.d("SearchActivity", "Search Results Size: ${searchResults.size}")

                displaySearchResults(searchResults)
            }
        }
    }

    private fun displaySearchResults(results: List<ItemModel>) {
        if (results.isEmpty()) {
            Log.d("SearchActivity", "No results found")
            resultTextView.text = "Item not found"
        } else {
            Log.d("SearchActivity", "Results found: ${results.size}")
            // Display the search results in the canvas or any other view as needed
            // For now, let's just concatenate the items into a string and set it to resultTextView
            val resultString = results.joinToString("\n") { item ->
                "SNo: ${item.sNo}, Item: ${item.item}, Current Location: ${item.currentLocation}, " +
                        "New Location: ${item.newLocation}, Status: ${item.status}, Unit: ${item.unit}"
            }
            resultTextView.text = resultString
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

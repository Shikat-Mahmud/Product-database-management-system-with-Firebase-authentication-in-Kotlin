package com.example.myapplication

import ItemModel
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class search : AppCompatActivity() {

        private lateinit var searchBox: EditText
        private lateinit var searchButton: Button
        private lateinit var resultTextView: LinearLayout
        private lateinit var firebaseHelper: FirebaseHelper
        private lateinit var nestedScrollView: NestedScrollView
        private lateinit var fabScrollToTop: FloatingActionButton

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_search)

            nestedScrollView = findViewById(R.id.nestedScrollView)
            fabScrollToTop = findViewById(R.id.fabScrollToTop)

            fabScrollToTop.setOnClickListener {
                nestedScrollView.smoothScrollTo(0, 0)
            }

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
        // Clear existing views
        resultTextView.removeAllViews()

        if (results.isEmpty()) {
            // Item not found
            val notFoundTextView = createTextView("Item not found")
            resultTextView.addView(notFoundTextView)
        } else {
            // Display the search results in a custom layout
            val inflater = LayoutInflater.from(this)

            // Inflate and add a custom layout for each result
            for (item in results) {
                val resultItemView = inflater.inflate(R.layout.item_result, null)

                // Customize the layout based on your needs
                val textSNo = resultItemView.findViewById<TextView>(R.id.textSNo)
                textSNo.text = "Serial No: ${item.sNo}"

                val textItem = resultItemView.findViewById<TextView>(R.id.textItem)
                textItem.text = "Item: ${item.item}"

                val textCurrentLocation = resultItemView.findViewById<TextView>(R.id.textCurrentLocation)
                textCurrentLocation.text = "Current Location: ${item.currentLocation}"

                val textNewLocation = resultItemView.findViewById<TextView>(R.id.textNewLocation)
                textNewLocation.text = "New Location: ${item.newLocation}"

                val textStatus = resultItemView.findViewById<TextView>(R.id.textStatus)
                textStatus.text = "Status: ${item.status}"

                val textUnit = resultItemView.findViewById<TextView>(R.id.textUnit)
                textUnit.text = "Unit: ${item.unit}"

                // Set background color dynamically based on your requirements
                resultItemView.setBackgroundColor(getBackgroundColor(item))

                // Add the custom layout to the resultTextView
                resultTextView.addView(resultItemView)
            }
        }
    }

        private fun createTextView(text: String): TextView {
            val textView = TextView(this)
            textView.text = text
            textView.textSize = 16f
            textView.setTextColor(Color.BLACK)
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView.setPadding(16, 16, 16, 16)
            return textView
        }

        private fun getBackgroundColor(item: ItemModel): Int {
            // Implement your logic to determine the background color based on the item
            // Example: Set different colors based on the item's properties
            return when {
                item.status.equals("In stock", ignoreCase = true) -> Color.GREEN
                item.status.equals("Out of stock", ignoreCase = true) -> Color.RED
                else -> Color.WHITE
            }
        }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressed()
            return true
        }
    }

package com.example.myapplication

import adapters.ItemModelAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Dashboard : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemModelAdapter
    private lateinit var loader: ProgressBar // Add ProgressBar reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerView = view.findViewById(R.id.itemRecyclerView)
        adapter = ItemModelAdapter(emptyList())
        loader = view.findViewById(R.id.loader) // Initialize ProgressBar

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val firebaseHelper = FirebaseHelper()

        // Show loader before starting data fetch
        loader.visibility = View.VISIBLE

        firebaseHelper.fetchItemData { itemList ->
            adapter.updateData(itemList)

            // Hide loader after data is loaded
            loader.visibility = View.GONE
        }

        return view
    }
}

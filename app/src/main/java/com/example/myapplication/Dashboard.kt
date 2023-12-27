package com.example.myapplication

import adapters.ItemModelAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Dashboard : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemModelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerView = view.findViewById(R.id.itemRecyclerView)
        adapter = ItemModelAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(requireContext()) // Use requireContext() instead of this

        recyclerView.adapter = adapter

        val firebaseHelper = FirebaseHelper()

        firebaseHelper.fetchItemData { itemList ->
            adapter.updateData(itemList)
        }

        return view
    }
}

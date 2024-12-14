package com.example.deliveryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class OrderListFragment : Fragment(R.layout.fragment_order_list) {

    private lateinit var ordersAdapter: ArrayAdapter<String>
    private var displayOrders: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listViewOrders)

        ordersAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displayOrders)
        listView.adapter = ordersAdapter

        updateOrderList()
    }

    fun updateOrderList() {
        val ordersList = (activity as MainScreenActivity).ordersList
        displayOrders.clear()
        displayOrders.addAll(ordersList.map { "Груз: ${it[0]}, Адрес: ${it[1]}, Кузов: ${it[2]}, Грузчики: ${it[3]}, Цена: ${it[4]} руб." })
        ordersAdapter.notifyDataSetChanged()
    }
}

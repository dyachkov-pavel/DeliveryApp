package com.example.deliveryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout


class OrderDeliveryFragment : Fragment(R.layout.fragment_order_delivery) {

    private val vehiclePrices = mapOf(
        "Кузов S (вес - до 300 кг)" to 1500,
        "Кузов M (вес - до 700 кг)" to 2000,
        "Кузов L (вес - до 1400 кг)" to 2500,
        "Кузов XL (вес - до 200 кг)" to 3000,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_delivery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vehicleTypes = vehiclePrices.keys.toList()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, vehicleTypes)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerVehicleType = view.findViewById<Spinner>(R.id.spinnerVehicleType)
        spinnerVehicleType.adapter = adapter

        view.findViewById<Button>(R.id.buttonCalculatePrice).setOnClickListener {
            calculateTotalPrice(view);
        }

        view.findViewById<Button>(R.id.buttonSubmitOrder).setOnClickListener {
            submitOrder(view)
        }
    }

    private fun validateInputs(view: View) {

        val deliveryItem = view.findViewById<EditText>(R.id.editTextDeliveryItem).text.toString()
        val address = view.findViewById<EditText>(R.id.editTextAddress).text.toString()
        val loadersCountInput = view.findViewById<EditText>(R.id.editTextLoadersCount).text.toString()

        val deliveryItemInputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayoutDeliveryItem)
        val addressInputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayoutAddress)
        val loadersCountInputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayoutLoadersCount)

        deliveryItemInputLayout.error = null
        addressInputLayout.error = null
        loadersCountInputLayout.error = null

        val errorMessages = mutableListOf<String>()

        if (deliveryItem.isEmpty()) {
            errorMessages.add("Укажите груз")
            deliveryItemInputLayout.error = "Укажите груз"
        }

        if (address.isEmpty()) {
            errorMessages.add("Укажите адрес")
            addressInputLayout.error = "Укажите адрес"
        }

        if (loadersCountInput.isEmpty()) {
            errorMessages.add("Укажите количество грузчиков")
            loadersCountInputLayout.error = "Укажите количество грузчиков"
        } else if (loadersCountInput.toIntOrNull() == null || loadersCountInput.toInt() <= 0) {
            errorMessages.add("Количество грузчиков должно быть больше 0")
            loadersCountInputLayout.error = "Количество грузчиков должно быть больше 0"
        }

        if (errorMessages.isNotEmpty()) {
            return
        }
    }

    private fun calculateTotalPrice(view: View) {
        validateInputs(view)
        if (view.findViewById<TextInputLayout>(R.id.textInputLayoutDeliveryItem).error != null ||
            view.findViewById<TextInputLayout>(R.id.textInputLayoutAddress).error != null ||
            view.findViewById<TextInputLayout>(R.id.textInputLayoutLoadersCount).error != null) {
            return // Skip calculation if errors are visible.
        }

        val selectedVehicle = view.findViewById<Spinner>(R.id.spinnerVehicleType).selectedItem.toString()
        val loadersCount = view.findViewById<EditText>(R.id.editTextLoadersCount).text.toString().toInt()

        val vehiclePrice = vehiclePrices[selectedVehicle] ?: 0
        // Base cost per loader
        val baseCostPerLoader = 500
        // Additional cost for loaders beyond 2
        val additionalCostPerLoader = 250

        // Calculate total price based on the number of loaders
        var totalPrice = baseCostPerLoader * loadersCount

        // Add additional costs if there are more than 2 loaders
        if (loadersCount > 2) {
            totalPrice += (loadersCount - 2) * additionalCostPerLoader
        }
        totalPrice += vehiclePrice

        view.findViewById<TextView>(R.id.textViewTotalPrice).text = "Цена: $totalPrice руб."

        Toast.makeText(requireContext(), "Стоимость: $totalPrice руб.", Toast.LENGTH_LONG).show()
        return

    }

    private fun submitOrder(view: View) {
        validateInputs(view)
        if (view.findViewById<TextInputLayout>(R.id.textInputLayoutDeliveryItem).error != null ||
            view.findViewById<TextInputLayout>(R.id.textInputLayoutAddress).error != null ||
            view.findViewById<TextInputLayout>(R.id.textInputLayoutLoadersCount).error != null) {
            return
        }
        // Here you can implement logic to submit the order.
        // For example, you might want to save the order details to a database or send them to a server.

        Toast.makeText(requireContext(), "Заказ отправлен!", Toast.LENGTH_SHORT).show()
    }
}
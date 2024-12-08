package com.example.deliveryapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_input)

        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            validateInputs()
        }
    }

    private fun validateInputs() {
        val name = findViewById<EditText>(R.id.editTextName).text.toString()
        val surname = findViewById<EditText>(R.id.editTextSurname).text.toString()
        val phone = findViewById<EditText>(R.id.editTextPhone).text.toString()
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()

        val nameInputLayout = findViewById<TextInputLayout>(R.id.textInputLayoutName)
        val surnameInputLayout = findViewById<TextInputLayout>(R.id.textInputLayoutSurname)
        val phoneInputLayout = findViewById<TextInputLayout>(R.id.textInputLayoutPhone)
        val emailInputLayout = findViewById<TextInputLayout>(R.id.textInputLayoutEmail)

        nameInputLayout.error = null
        surnameInputLayout.error = null
        phoneInputLayout.error = null
        emailInputLayout.error = null

        val errorMessages = mutableListOf<String>()

        if (name.isEmpty()) {
            nameInputLayout.error = "Введите Имя"
            errorMessages.add("Введите Имя")
        }

        if (surname.isEmpty()) {
            surnameInputLayout.error = "Введите Фамилию"
            errorMessages.add("Введите Фамилию")
        }

        if (phone.isEmpty()) {
            phoneInputLayout.error = "Введите номер телефона"
            errorMessages.add("Введите номер телефона")
        } else if (!phone.matches("\\d{11}".toRegex())) {
            phoneInputLayout.error = "Введите валидный номер телефона (11 цифр)"
            errorMessages.add("Введите валидный номер телефона (11 цифр)")
        }

        if (email.isEmpty()) {
            emailInputLayout.error = "Введите email"
            errorMessages.add("Введите email")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = "Введите корректный email"
            errorMessages.add("Введите корректный email")
        }

        if (errorMessages.isNotEmpty()) {
            return
        } else {
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
        }
    }
}
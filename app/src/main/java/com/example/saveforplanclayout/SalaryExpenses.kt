package com.example.saveforplanclayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SalaryExpenses :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.salary_expenses)

        //Initializing sharedPreferenceManager
        val sharedPreferenceManager = SharedPreferenceManager(this)

        // Find the back button by its ID
        val backButton: Button = findViewById(R.id.btnBack)

        // Return to previous activity
        backButton.setOnClickListener {
            // Create an intent to go back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // User input savings and expenses
        val savings: EditText = findViewById(R.id.input_savings)
        val expenses: EditText = findViewById(R.id.input_expenses)
        val save_button: Button = findViewById(R.id.btnSaveAmountSavings)

        val savedSalary = sharedPreferenceManager.getSalaryAmount()
        val savedExpenses = sharedPreferenceManager.getExpensesAmount()

        // Set the values in the EditText fields
        savings.setText(savedSalary.toString())
        expenses.setText(savedExpenses.toString())

        savings.setOnClickListener{
            showSoftKeyboard(savings)
        }

        expenses.setOnClickListener{
            showSoftKeyboard(expenses)
        }

        save_button.setOnClickListener{
            val salary_float = savings.text.toString().toFloat()
            val expenses_float = expenses.text.toString().toFloat()

            //Delete previous record from Shared Preference
            sharedPreferenceManager.clearAll(this)

            //Add new record into Shared Preference
            sharedPreferenceManager.saveSalaryAmount(salary_float)
            sharedPreferenceManager.saveExpensesAmount(expenses_float)

            Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()

            // Create an intent to go back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            }

    }

    private fun showSoftKeyboard(editText: EditText){
        //Get the InputMethodManager
        val input_manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        //Show the soft keyboard
        input_manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}





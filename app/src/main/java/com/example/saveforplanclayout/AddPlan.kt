package com.example.saveforplanclayout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddPlan : AppCompatActivity() {

    // Inner data class to represent a plan
    data class Plan(val name: String, val totalAmount: Double)

    companion object {
        var plansList: MutableList<Plan> = mutableListOf()
    }

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_plan)

        sharedPreferenceManager = SharedPreferenceManager(this)

        // Find the back button by its ID
        val backButton: Button = findViewById(R.id.btnBack)

        backButton.setOnClickListener {
            // Create an intent to go back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Getting input from the user
        val planNameEditText: EditText = findViewById(R.id.planNameEditText)
        val totalAmountEditText: EditText = findViewById(R.id.totalAmountEditText)

        // Button to trigger the addition of the plan
        val addPlanButton: Button = findViewById(R.id.addPlanButton)

        addPlanButton.setOnClickListener {
            val planName: String = planNameEditText.text.toString()
            val totalAmount: Double = totalAmountEditText.text.toString().toDoubleOrNull() ?: 0.0

            // Check if planName and totalAmount are valid
            if (planName.isNotBlank() && totalAmount > 0) {
                // Call the function to handle adding the plan
                addPlan(planName, totalAmount)
                sharedPreferenceManager.savePlansList(plansList)
            } else {
                Toast.makeText(this, "Invalid input. Please enter valid plan details.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to add a plan
    private fun addPlan(planName: String, totalAmount: Double) {
        // Create a new plan
        val newPlan = Plan(planName, totalAmount)

        // Add the plan to the list
        plansList.add(newPlan)

        // Calculate the total cost of all plans
        val totalCostOfPlans = plansList.sumOf { it.totalAmount }

        // Check if there is enough money for all plans
        if (totalCostOfPlans <= getCurrentBankAccountAmount()) {
            // Subtract the cost of plans from the bank account
            subtractCostFromBankAccount(totalCostOfPlans)

            // Calculate days needed using the provided formula
            val daysNeeded = calculateDaysNeeded(sharedPreferenceManager.getSavingsAmount(), totalCostOfPlans)

            // Display a success message
            Toast.makeText(this, "Plan added successfully! You can achieve the plan this year", Toast.LENGTH_SHORT).show()

            // Update the shared preferences with the new plans list
            sharedPreferenceManager.savePlansList(plansList)
        } else {
            //TODO calculation logic
            // Display an insufficient funds message
            Toast.makeText(this, "Insufficient funds for this year...", Toast.LENGTH_SHORT).show()
            sharedPreferenceManager.savePlansList(plansList)
        }

        // Move to ViewPlan activity after adding the plan
        val intent = Intent(this, ViewPlan::class.java)
        startActivity(intent)
    }

    // Function to get the current bank account amount considering the allocated money for plans
    private fun getCurrentBankAccountAmount(): Double {
        // Assuming you have a function to get the initial bank account amount
        val initialBankAccountAmount = sharedPreferenceManager.getSavingsAmount()

        // Calculate the remaining amount by subtracting the total cost of plans
        return initialBankAccountAmount - plansList.sumOf { it.totalAmount }
    }

    // Function to subtract the cost of plans from the bank account
    private fun subtractCostFromBankAccount(cost: Double) {
        // Obtain the current amount from shared preferences
        val currentAmount = sharedPreferenceManager.getSavingsAmount()

        // Update the bank account amount after subtracting the cost
        sharedPreferenceManager.saveSavingsAmount((currentAmount - cost).toFloat())
    }

    // Function to calculate days needed
    private fun calculateDaysNeeded(savings: Float, totalCostOfPlan: Double): Int {
        //get daily expenses
        val dailyExpenses = sharedPreferenceManager.getExpensesAmount()
        //TODO
        // Edit formula
        val daysNeeded = ((savings - (dailyExpenses * 365)) / totalCostOfPlan).toInt()

        return daysNeeded
    }

}


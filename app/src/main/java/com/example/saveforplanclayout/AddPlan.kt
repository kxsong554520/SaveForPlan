package com.example.saveforplanclayout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddPlan : AppCompatActivity() {

    // Inner data class to represent a plan
    data class Plan(val name: String, val totalAmount: Double, val monthsNeeded: Int)

    companion object {
        var plansList: MutableList<Plan> = mutableListOf()
    }

    //Initialize sharedPreference
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_plan)

        sharedPreferenceManager = SharedPreferenceManager(this)

        // Map the back button by its ID
        val backButton: Button = findViewById(R.id.btnBack)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Map the relevant EditText and Buttons to its ID
        val planNameEditText: EditText = findViewById(R.id.planNameEditText)
        val totalAmountEditText: EditText = findViewById(R.id.totalAmountEditText)
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

        val salaryAmount = sharedPreferenceManager.getSalaryAmount()
        val expensesAmount = sharedPreferenceManager.getExpensesAmount()

        val monthsNeeded = calculateMonthsNeeded(salaryAmount, expensesAmount,totalAmount)

        // Create a new plan
        val newPlan = Plan(planName, totalAmount, monthsNeeded)

        sharedPreferenceManager.saveMonthsNeeded(monthsNeeded.toFloat())
        // Add the plan to the list
        plansList.add(newPlan)

        // Display a success message
        Toast.makeText(this, "Plan added successfully! You can achieve the plan in $monthsNeeded months", Toast.LENGTH_SHORT).show()

            // Update the shared preferences with the new plans list
            sharedPreferenceManager.savePlansList(plansList)

        // Move to ViewPlan activity after adding the plan
        val intent = Intent(this, ViewPlan::class.java)
        startActivity(intent)
    }

    // Function to calculate days needed
    private fun calculateMonthsNeeded(m_salary: Float ,expenses:Float ,CostOfPlan: Double): Int {

            val monthsNeeded = (CostOfPlan/(m_salary - (expenses * 30))).toInt()
            if (monthsNeeded >= 1){
                return (monthsNeeded + 1)
            } else {
                return 0
            }
    }
}


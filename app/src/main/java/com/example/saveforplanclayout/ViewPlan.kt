package com.example.saveforplanclayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ViewPlan :AppCompatActivity(){

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_plan)

        sharedPreferenceManager = SharedPreferenceManager(this)

        // Back Button to return to home page
        val backButton: Button = findViewById(R.id.btnBack)

        backButton.setOnClickListener {
            // Create an intent to go back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Clear Plans Button
        val clearPlansButton: Button = findViewById(R.id.btn_clearplan)
        clearPlansButton.setOnClickListener {
            // Call the clearAll function from SharedPreferenceManager
            sharedPreferenceManager.clearAll(this)

            // Clear the plansContainer in your layout
            val plansContainer: LinearLayout = findViewById(R.id.plansContainer)
            plansContainer.removeAllViews()

            // Display a message indicating that plans are cleared
            Toast.makeText(this, "Plans cleared successfully!", Toast.LENGTH_SHORT).show()
        }

        displayPlansAndDays()
    }

    private fun displayPlansAndDays() {
        // LinearLayout to add plans dynamically
        val plansContainer: LinearLayout = findViewById(R.id.plansContainer)

        // Retrieve the list of plans from SharedPreferences
        val loadedPlansList = sharedPreferenceManager.getPlansList()

        // Iterate through the plans and add them to the layout
        for (plan in loadedPlansList) {
            val planView = createPlanView(plan)
            plansContainer.addView(planView)
        }
    }

    private fun createPlanView(plan: AddPlan.Plan): View {
        // Create a TextView to display plan details
        val salary = sharedPreferenceManager.getSalaryAmount()
        val expense = sharedPreferenceManager.getExpensesAmount()
        val planTextView = TextView(this)
        val daysNeeded = calculateDaysNeeded(salary, expense, plan.totalAmount)

        // Set text with plan name and days needed
        planTextView.text = "Plan: ${plan.name}\n Amount: ${plan.totalAmount}, Days Needed: $daysNeeded"
        planTextView.textSize = 18f


        return planTextView
    }

    private fun calculateDaysNeeded(m_salary: Float ,expenses:Float ,CostOfPlan: Double): Int {

        val daysNeeded = (CostOfPlan/(m_salary - (expenses * 30))).toInt()
        if (daysNeeded >= 1){
            return daysNeeded
        } else {
            return 0
        }
    }

    private fun getCurrentBankAccountAmount(): Double {
        // Assuming you have a function to get the initial bank account amount
        val initialBankAccountAmount = getInitialBankAccountAmount()

        // Assuming you have a function to get the total cost of plans
        val totalCostOfPlans = getTotalCostOfPlans()

        // Calculate the remaining amount by subtracting the total cost of plans
        return initialBankAccountAmount - totalCostOfPlans
    }

    private fun getTotalCostOfPlans(): Double {
        // Retrieve the list of plans from SharedPreferences
        val plansList = sharedPreferenceManager.getPlansList()

        // Calculate the total cost of all plans
        return plansList.sumOf { it.totalAmount }
    }

    // Dummy function to get the initial bank account amount
    private fun getInitialBankAccountAmount(): Float {
        // Replace this with your logic to get the initial bank account amount
        return sharedPreferenceManager.getSalaryAmount()
    }
}